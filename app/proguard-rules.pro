# Add this rule based on the error message structure
-keep class com.continental.kaas.core.repository.data.** { *; }

# Keep the standard WorkManager rule (even if not in docs v0.8.6)
-keep public class * extends androidx.work.ListenableWorker {
    public <init>(android.content.Context, androidx.work.WorkerParameters);
}

# Include the rules documented in v0.8.6 [cite: 60, 61, 62]
-keep class com.continental.kaas.core.security.core.** {*;}
-keepclassmembers class ** extends com.fasterxml.jackson.databind.ser.std.** { public <init>(...); }
-keepclassmembers class ** extends com.fasterxml.jackson.databind.deser.std.** { public <init>(...); }
-keepclassmembers class * { @com.fasterxml.jackson.annotation.JsonProperty <methods>; @com.fasterxml.jackson.annotation.JsonProperty <fields>; }