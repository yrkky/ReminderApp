object androidx {

    object core {
        private val version = "1.9.0"
        val ktx = "androidx.core:core-ktx:$version"
    }

    object compose {
        private val version = "1.1.1"

        object material {
            val material = "androidx.compose.material:material:$version"
            val extended = "androidx.compose.material:material-icons-extended:$version"
        }

        object ui {
            val ui = "androidx.compose.ui:ui:$version"
            val preview = "androidx.compose.ui:ui-tooling-preview:$version"
            val ui_tooling = "androidx.compose.ui:ui-tooling:$version"
            val ui_test_manifest = "androidx.compose.ui:ui-test-manifest:$version"
            val ui_test_junit = "androidx.compose.ui:ui-test-junit4:$version"
        }

        object foundation {
            val foundation = "androidx.compose.foundation:foundation:$version"
        }

    }

    object room {
        private val version = "2.4.3"
        val ktx = "androidx.room:room-ktx:$version"
        val runtime = "androidx.room:room-runtime:$version"
        val compiler = "androidx.room:room-compiler:$version"
    }

    object constraintlayout {
        private val version = "1.0.0-rc02"

        val compose = "androidx.constraintlayout:constraintlayout-compose:$version"
    }

    object lifecycle {
        private val version = "2.5.1"

        val runtime = "androidx.lifecycle:lifecycle-runtime-ktx:$version"
        val compose = "androidx.lifecycle:lifecycle-viewmodel-compose:$version"
    }

    object activity {
        private val version = "1.3.1"
        val activity_compose = "androidx.activity:activity-compose:$version"
    }

    object navigation {
        private val version = "2.5.3"
        val compose = "androidx.navigation:navigation-compose:$version"

        object hilt {
            private val version = "1.0.0"
            val compose = "androidx.hilt:hilt-navigation-compose:$version"
        }
    }

    object appcompat {
        private val version = "1.6.1"

        object app {
            val appcompatactivity = "androidx.appcompat:appcompat:$version"
        }
    }

    object test {
        object espresso {
            private val version = "3.4.0"
            val core = "androidx.test.espresso:espresso-core:$version"
        }

        object ext {
            private val version = "1.1.5"
            val junit = "androidx.test.ext:junit:$version"
        }
    }

    object work {
        private val work_version = "2.8.0"

        val runtime = "androidx.work:work-runtime:$work_version"

        val runtime_ktx = "androidx.work:work-runtime-ktx:$work_version"

        val rxjava2 = "androidx.work:work-rxjava2:$work_version"

        val gcm = "androidx.work:work-gcm:$work_version"

        val testing = "androidx.work:work-testing:$work_version"

        val multiprocess = "androidx.work:work-multiprocess:$work_version"
    }

}