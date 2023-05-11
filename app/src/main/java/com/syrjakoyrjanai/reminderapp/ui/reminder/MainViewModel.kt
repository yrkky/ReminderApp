package com.syrjakoyrjanai.reminderapp.ui.reminder

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat.from
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.*
import com.syrjakoyrjanai.core.domain.entity.Category
import com.syrjakoyrjanai.core.domain.entity.Reminder
import com.syrjakoyrjanai.core.domain.repository.CategoryRepository
import com.syrjakoyrjanai.core.domain.repository.ReminderRepository
import com.syrjakoyrjanai.reminderapp.Graph
import com.syrjakoyrjanai.reminderapp.ui.category.CategoryViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import com.syrjakoyrjanai.reminderapp.R
import com.syrjakoyrjanai.reminderapp.tools.NotificationWorker

private lateinit var editReminder: Reminder

@HiltViewModel
class MainViewModel @Inject constructor(
    private val reminderRepository: ReminderRepository,
    private val categoryRepository: CategoryRepository
): ViewModel() {

    private val _reminderViewState = MutableStateFlow<ReminderViewState>(ReminderViewState.Loading)
    val reminderState: StateFlow<ReminderViewState> = _reminderViewState

    private val _categoryList: MutableStateFlow<List<Category>> = MutableStateFlow(mutableListOf())
    val categories: StateFlow<List<Category>> =_categoryList

    private val _categoryViewState = MutableStateFlow<CategoryViewState>(CategoryViewState.Loading)
    val categoryState: StateFlow<CategoryViewState> = _categoryViewState

    private val _selectedCategory = MutableStateFlow<Category?>(null)

    fun saveReminder(reminder: Reminder, notify: Boolean) {
        viewModelScope.launch {
            reminderRepository.addReminder(reminder)
            if (notify) {
                notifyUserOfReminder(reminder)
                setOneTimeNotification(reminder)
            }
        }
    }

    fun setEditReminder(reminder: Reminder) {
        editReminder = reminder
    }

    fun getEditReminder() : Reminder{
        return editReminder
    }

    fun editReminder(reminder: Reminder) {
        viewModelScope.launch {
            reminderRepository.editReminder(reminder)
            setOneTimeNotification(reminder)
        }
    }

    fun onCategorySelected(category: Category) {
        _selectedCategory.value = category
    }

    private fun notifyUserOfReminder(reminder: Reminder) {
        val formattedTime = LocalDateTime.parse(reminder.reminderTime.toString()).format(
            DateTimeFormatter.ofPattern("dd.MM.yyyy HH.mm"))
        val notificationId = 10
        val builder = NotificationCompat.Builder(
            Graph.appContext,
            "channel_id"
        )
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("New Reminder made")
            .setContentText("Will remind ${reminder.title} on ${formattedTime}")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setStyle(NotificationCompat.BigPictureStyle().setBigContentTitle("New Reminder made"))

        with(from(Graph.appContext)) {
            if (ActivityCompat.checkSelfPermission(
                    Graph.appContext,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                //    here to request the missing permissions, and then overriding
                //    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                //    to handle the case where the user grants the permission. See the documentation
                //    for ActivityCompat#requestPermissions for more details.
                return
            }
            notify(notificationId, builder.build())
        }
    }


    private fun createNotificationChannel() {
        val name = "NotificationChannel"
        val descriptionText = "NotificationChannelDescription"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel("channel_id", name, importance).apply {
            description = descriptionText
        }
        val notificationManager = Graph.appContext
            .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    private fun setOneTimeNotification(reminder: Reminder) {
        val now = Calendar.getInstance()

        val remindertime = Calendar.getInstance()
        //simpler way than below:
        remindertime.time = Date.from(reminder.reminderTime.atZone(ZoneId.systemDefault()).toInstant())

        Log.i("SetOneTimeNotification", "Now: ${now.time} Reminder: ${remindertime.time}")
        val time = remindertime.timeInMillis - now.timeInMillis
        Log.i("SetOneTimeNotification", "Next notification ${reminder.title} will be shown in ${time / 1000} seconds")

        val workManager = WorkManager.getInstance(Graph.appContext)
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val notificationWorker = OneTimeWorkRequestBuilder<NotificationWorker>()
            .setInitialDelay(time, TimeUnit.MILLISECONDS)
            .setConstraints(constraints)
            .build()

        workManager.enqueue(notificationWorker)

        workManager.getWorkInfoByIdLiveData(notificationWorker.id)
            .observeForever { workInfo ->
                if (workInfo.state == WorkInfo.State.SUCCEEDED) {
                    createSuccessNotification(reminder)
                } else {
                    notifyUserOfReminder(reminder)
                }
            }
    }

    private fun createSuccessNotification(reminder: Reminder) {
        val notificationId = 10
        val intent = Intent()
        val context = Graph.appContext
        intent.setClassName(context, "com.syrjakoyrjanai.reminderapp.MainActivity").apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)


        val builder = NotificationCompat.Builder(
            Graph.appContext,
            "channel_id"
        )
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle("You have reminder ${reminder.title}")
            .setContentText("${reminder.title}")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setStyle(NotificationCompat.BigPictureStyle().setBigContentTitle("${reminder.title}"))

        with(from(Graph.appContext)) {
            if (ActivityCompat.checkSelfPermission(
                    Graph.appContext,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            notify(notificationId, builder.build())
        }
    }

    private fun createFailureNotification(reminder: Reminder) {
        val notificationId = 10
        val builder = NotificationCompat.Builder(
            Graph.appContext,
            "channel_id"
        )
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle("Notification failed")
            .setContentText("Notification for ${reminder.title} failed")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(from(Graph.appContext)) {
            if (ActivityCompat.checkSelfPermission(
                    Graph.appContext,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {

                return
            }
            notify(notificationId, builder.build())
        }
    }


    fun loadRemindersFor(category: Category?) {
        if (category != null) {
            viewModelScope.launch {
                val Reminders = reminderRepository.loadAllReminders()
                _reminderViewState.value =
                    ReminderViewState.Success(
                        Reminders.filter {
                            it.categoryId == category.categoryId }
                    )
            }
        }
    }

    fun loadAllReminders() {
            viewModelScope.launch {
                val Reminders = reminderRepository.loadAllReminders()
                _reminderViewState.value =
                    ReminderViewState.Success(
                        Reminders
                    )
            }
    }

    private suspend fun loadCategories() {
        combine(
            categoryRepository.loadCategories()
                .onEach { categories ->
                    if (categories.isNotEmpty() && _selectedCategory.value == null) {
                        _selectedCategory.value = categories.first()
                    }
                },
            _selectedCategory
        ) { categories, selectedCategory ->
            _categoryViewState.value = CategoryViewState.Success(selectedCategory, categories)
            _categoryList.value = categories
        }
            .catch { error -> CategoryViewState.Error(error) }
            .launchIn(viewModelScope)
    }

    fun deleteReminder(reminder: Reminder) {
        viewModelScope.launch {
            reminderRepository.deleteReminder(reminder)
        }
    }

    private fun fakeData() = listOf(
        Category(name = "All"),
        Category(name = "School"),
        Category(name = "Family"),
        Category(name = "Work"),
        Category(name = "Hobby"),
        Category(name = "House"),
        Category(name = "Misc"),
        Category(name = "Own Project 1"),
        Category(name = "Own Project 2"),
    )

    //private fun dummyData() : List<Reminder> {
    //    return listOf(
    //        Reminder(
    //            title = "Wash dishes",
    //            categoryId = 1,
    //            reminderTime = LocalDateTime.now(),
    //            creationTime = LocalDateTime.now(),
    //            reminderSeen = LocalDateTime.now(),
    //        ),
    //        Reminder(
    //            title = "Vacuum",
    //            categoryId = 2,
    //            reminderTime = LocalDateTime.now(),
    //            creationTime = LocalDateTime.now(),
    //            reminderSeen = LocalDateTime.now(),
    //        ),
    //        Reminder(
    //            title = "Laundry",
    //            categoryId = 3,
    //            reminderTime = LocalDateTime.now(),
    //            creationTime = LocalDateTime.now(),
    //            reminderSeen = LocalDateTime.now(),
    //        ),
    //        Reminder(
    //            title = "Do homework",
    //            categoryId = 4,
    //            reminderTime = LocalDateTime.now(),
    //            creationTime = LocalDateTime.now(),
    //            reminderSeen = LocalDateTime.now(),
    //        )
    //    )
    //}

    init {
        createNotificationChannel()


        fakeData().forEach {
            viewModelScope.launch {
                categoryRepository.addCategory(it)
            }
        }
        //dummyData().forEach {
        //    viewModelScope.launch {
        //        saveReminder(it, false)
        //    }
        //}
        viewModelScope.launch {
            loadCategories()
        }
    }
}

