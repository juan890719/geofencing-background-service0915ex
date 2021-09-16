package com.example.geofencingbackgroundex0915;

import android.content.Context;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.work.BackoffPolicy;
import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.util.concurrent.TimeUnit;

@RequiresApi(api = Build.VERSION_CODES.M)
public class BackgroundService extends Worker{

    private static final String TAG = BackgroundService.class.getSimpleName();
    Context context = getApplicationContext();
    WorkManager workManager;

    public BackgroundService(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        // Do the work here--in this case, upload the images.


        // Indicate whether the work finished successfully with the Result
        return Result.success();
    }

    public void settingManager() {
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();
        WorkRequest workRequest = new PeriodicWorkRequest
                .Builder(BackgroundService.class, 12, TimeUnit.HOURS, PeriodicWorkRequest.MIN_PERIODIC_INTERVAL_MILLIS, TimeUnit.MILLISECONDS)
                .setBackoffCriteria(BackoffPolicy.LINEAR, PeriodicWorkRequest.MIN_BACKOFF_MILLIS, TimeUnit.MILLISECONDS)
                .setConstraints(constraints)
                .addTag("geofencing")
                .build();
        workManager.getInstance(context).enqueueUniquePeriodicWork(
                "sendLogs",
                ExistingPeriodicWorkPolicy.KEEP,
                (PeriodicWorkRequest) workRequest);
    }
}
