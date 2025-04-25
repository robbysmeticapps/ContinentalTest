package com.example.continentaltest

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.work.Configuration
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.continental.kaas.library.KAAS
import com.continental.kaas.logging.DebugTree

class TestApplication : Application(), Configuration.Provider {

    override fun onCreate() {
        super.onCreate()

        val config = KAAS.Config(this)
            .allowRunOnSimulator()
            .allowDebugMode()
            .allowRooted()
            .setLogger(DebugTree())

        // Initialize the KaaS SDK singleton
        KAAS.shared.init(config)
    }

    // Override this method to provide custom WorkManager configuration
    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder()
            .setWorkerFactory(object : WorkerFactory() {
                // This method is called by WorkManager when it needs to create a worker
                override fun createWorker(
                    appContext: Context,
                    workerClassName: String,
                    workerParameters: WorkerParameters
                ): ListenableWorker? {
                    // Log the attempt to create a worker, including class name
                    Log.d(
                        "CustomWorkerFactory",
                        "Attempting to create worker: $workerClassName"
                    )

                    return try {
                        // Use reflection to get the Class object for the worker
                        val workerClass = Class.forName(workerClassName)
                            .asSubclass(ListenableWorker::class.java)

                        // Find the constructor that takes Context and WorkerParameters
                        val constructor = workerClass.getDeclaredConstructor(
                            Context::class.java,
                            WorkerParameters::class.java
                        )

                        // Create an instance of the worker using the constructor
                        val workerInstance = constructor.newInstance(appContext, workerParameters)

                        // Log successful creation
                        Log.d(
                            "CustomWorkerFactory",
                            "Successfully created instance of $workerClassName"
                        )

                        // Return the created worker instance
                        workerInstance

                    } catch (e: Exception) {
                        // Log any errors during instantiation (e.g., class not found, wrong constructor)
                        Log.e(
                            "CustomWorkerFactory",
                            "Could not create worker: $workerClassName",
                            e // Log the full exception
                        )
                        // Return null if worker creation failed
                        null
                    }
                }
            })
            .setMinimumLoggingLevel(Log.INFO) // Set logging level (can be adjusted)
            .build()
    }
}