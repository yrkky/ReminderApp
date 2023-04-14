object google {

    object accompanist {
        private val version = "0.21.0-beta"

        val insets = "com.google.accompanist:accompanist-insets:$version"
    }

    object dagger {
        object hilt {
            private val version = "2.44.2"
            private val compiler_version = "2.44"

            val android = "com.google.dagger:hilt-android:$version"
            val compiler = "com.google.dagger:hilt-android-compiler:$compiler_version"
        }
    }

    object maps {
        object android {
            private val version = "2.2.3"

            val maps_utils = "com.google.maps.android:android-maps-utils:$version"

            object maps {
                private val version = "3.3.0"

                val ktx = "com.google.maps.android:maps-ktx:$version"
                val utils = "com.google.maps.android:maps-utils-ktx:$version"
            }

            object gms {
                private val version = "18.0.0"

                val play_maps = "com.google.android.gms:play-services-maps:$version"
                val play_location = "com.google.android.gms:play-services-location:$version"
            }

        }
    }

}
