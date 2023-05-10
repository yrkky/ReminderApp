package com.syrjakoyrjanai.reminderapp.ui.reminder

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat.from
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.*
import com.google.android.gms.location.*
import com.syrjakoyrjanai.core.domain.entity.Category
import com.syrjakoyrjanai.core.domain.entity.Reminder
import com.syrjakoyrjanai.core.domain.repository.CategoryRepository
import com.syrjakoyrjanai.core.domain.repository.ReminderRepository
import com.syrjakoyrjanai.reminderapp.Graph
import com.syrjakoyrjanai.reminderapp.ui.category.CategoryViewState
import com.syrjakoyrjanai.reminderapp.ui.utils.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

private lateinit var editReminder: Reminder

@HiltViewModel
class MainViewModel @Inject constructor(
    private val reminderRepository: ReminderRepository,
    private val categoryRepository: CategoryRepository
): ViewModel() {

    private val geofencingClient = LocationServices.getGeofencingClient(Graph.appContext)

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
            createGeofence(reminder)
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



    //fun getReminder(reminderId: Long) {
    //    viewModelScope.launch {
    //        reminderRepository.getOne(Reminder)
    //        notifyUserOfReminder(Reminder)
    //    }
    //}

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
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return
            }
            notify(notificationId, builder.build())
        }
    }

    private fun createGeofence(reminder: Reminder) {
        Log.i("createGeofence", "Inside createGeofence ${reminder.location_x} ${reminder.location_y}")
        val geofence = Geofence.Builder()
            .setRequestId(reminder.title)
            .setCircularRegion(reminder.location_x, reminder.location_y, GEOFENCE_RADIUS.toFloat())
            .setExpirationDuration(Geofence.NEVER_EXPIRE)
            .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER or Geofence.GEOFENCE_TRANSITION_DWELL)
            .setLoiteringDelay(GEOFENCE_DWELL_DELAY)
            .setNotificationResponsiveness(0)
            .build()

        val geofenceRequest = GeofencingRequest.Builder()
            .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
            .addGeofence(geofence)
            .build()

        Log.i("createGeofence", "Intent: ${reminder.reminderId} ${reminder.title} ${reminder.message} ")

        val intent = Intent(Graph.appContext, GeofenceReceiver::class.java)
            .putExtra("reminder_id", reminder.reminderId.toString())
            .putExtra("title", "Near reminder: ${reminder.title}")
            .putExtra(
                "message",
                "Content: ${reminder.message} Location: ${reminder.location_x}, ${reminder.location_y}"
            )

        val pendingIntent = PendingIntent.getBroadcast(
            Graph.appContext, 0, intent, PendingIntent.FLAG_MUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        if (ContextCompat.checkSelfPermission(
                Graph.appContext, Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Log.i("CreateGeoFence", "No permissions to get fine location")
            return
        }

        if (ContextCompat.checkSelfPermission(
                    Graph.appContext, Manifest.permission.ACCESS_BACKGROUND_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
        ) {
            Log.i("CreateGeoFence", "No permissions to get background location")
            return
        }

        geofencingClient.addGeofences(geofenceRequest, pendingIntent).run {
            addOnSuccessListener {
                Toast.makeText(Graph.appContext, "Reminder Geofence Set", Toast.LENGTH_SHORT).show()
                Log.i("CreateGeoFence", "Geofence Added")
            }
            addOnFailureListener {
                Log.e("CreateGeoFence", "Failed to add geofence: ${it.message}")
            }
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

//        remindertime.set(Calendar.YEAR, reminder.reminderTime.year)
//        remindertime.set(Calendar.MONTH, reminder.reminderTime.monthValue - 1)
//        remindertime.set(Calendar.DAY_OF_MONTH, reminder.reminderTime.dayOfMonth)
//        remindertime.set(Calendar.HOUR_OF_DAY, reminder.reminderTime.hour)
//        remindertime.set(Calendar.MINUTE, reminder.reminderTime.minute)
//        remindertime.set(Calendar.SECOND, 0)

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
        intent.setClassName(context, "com.yrkky.mobilecomp.MainActivity").apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)


        val builder = NotificationCompat.Builder(
            Graph.appContext,
            "channel_id"
        )
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle("You have reminder ${reminder.title}")
            .setContentText("${reminder.message}")
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

    private fun dummyData() : List<Reminder> {
        return listOf(
            Reminder(
                title = "Wash dishes",
                categoryId = 1,
                message = "This is a description of a reminder",
                reminderTime = LocalDateTime.now(),
                creationTime = LocalDateTime.now(),
                location_x = 65.2514,
                location_y = 47.2541,
                creatorId = 1,
                reminderSeen = LocalDateTime.now(),
                icon = "error"
            ),
            Reminder(
                title = "Vacuum",
                categoryId = 2,
                message = "This is a description of a reminder",
                reminderTime = LocalDateTime.now(),
                creationTime = LocalDateTime.now(),
                location_x = 65.2514,
                location_y = 47.2541,
                creatorId = 1,
                reminderSeen = LocalDateTime.now(),
                icon = "warning",
            ),
            Reminder(
                title = "Laundry",
                categoryId = 3,
                message = "This is a description of a reminder",
                reminderTime = LocalDateTime.now(),
                creationTime = LocalDateTime.now(),
                location_x = 65.2514,
                location_y = 47.2541,
                creatorId = 1,
                reminderSeen = LocalDateTime.now(),
                icon = "important",
            ),
            Reminder(
                title = "Do homework",
                categoryId = 4,
                message = "This is a description of a reminder",
                reminderTime = LocalDateTime.now(),
                creationTime = LocalDateTime.now(),
                location_x = 65.2514,
                location_y = 47.2541,
                creatorId = 1,
                reminderSeen = LocalDateTime.now(),
                icon = "star",
            )
        )
    }

    init {
        createNotificationChannel()

        LocationRequest.create().apply {
            interval = 2000
            fastestInterval = 1000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        fakeData().forEach {
            viewModelScope.launch {
                categoryRepository.addCategory(it)
            }
        }
//        dummyData().forEach {
//            viewModelScope.launch {
//                saveReminder(it)
//            }
//        }
        viewModelScope.launch {
            loadCategories()
        }
    }
}

