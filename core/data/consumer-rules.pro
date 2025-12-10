-keep public interface com.core.data.local.LocalStorage {
    public *;
}

-keep public interface com.core.data.local.SecureStorage {
    public *;
}

-keep public interface com.core.data.remote.ConnectivityObserver {
    public *;
}

# Keep annotation definitions
-keep class org.koin.core.annotation.** { *; }

# Keep classes annotated with Koin annotations
-keep @org.koin.core.annotation.* class * { *; }