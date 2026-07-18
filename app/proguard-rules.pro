# This is a configuration file for ProGuard.
# http://proguard.sourceforge.net/index.html#manual/usage.html

-dontusemixedcaseclassnames
-verbose

# Preserve line numbers for debugging stack traces
-keepattributes SourceFile,LineNumberTable
-renamesourcefileattribute SourceFile

# For using GSON @Expose annotation
-keepattributes *Annotation*

# Gson specific classes
-dontwarn sun.misc.**
-dontwarn com.google.gson.**
-keep class com.google.gson.** { *; }
-keep interface com.google.gson.** { *; }

# Keep Room Database classes
-keep class androidx.room.** { *; }
-keep interface androidx.room.** { *; }

# Keep Hilt classes
-keep class dagger.hilt.** { *; }
-keep interface dagger.hilt.** { *; }

# Keep View constructors for inflation
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}
