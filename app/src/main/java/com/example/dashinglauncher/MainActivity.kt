package com.example.dashinglauncher

import android.app.ActivityOptions
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.PixelFormat
import android.graphics.Rect
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listView: ListView = findViewById(R.id.app_list)
        val packageManager: PackageManager = packageManager
        val apps = packageManager.getInstalledApplications(PackageManager.GET_META_DATA)
            .filter { packageManager.getLaunchIntentForPackage(it.packageName) != null }
            .map {
                AppInfo(
                    packageManager.getApplicationLabel(it).toString(),
                    it.packageName,
                    packageManager.getApplicationIcon(it.packageName)
                )
            }

        listView.adapter = AppAdapter(this, apps)
        listView.setOnItemClickListener { _, _, position, _ ->
            val app = apps[position]
            launchAppInRightArea(app.packageName)
        }
    }

    private fun launchAppInRightArea(packageName: String) {
        val launchIntent = packageManager.getLaunchIntentForPackage(packageName)
        launchIntent?.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//        launchIntent?.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
//        launchIntent?.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
//        launchIntent?.addFlags(Intent.FLAG_ACTIVITY_LAUNCH_ADJACENT); //split screen

        if (launchIntent != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            // Set the bounds for the right area of the screen
            val bounds = Rect(500, 300, 100, 100)
            val displayMetrics = resources.displayMetrics
//            bounds.left = displayMetrics.widthPixels / 2
//            bounds.top = 0
//            bounds.right = displayMetrics.widthPixels
//            bounds.bottom = displayMetrics.heightPixels

//            bounds.left = 500
//            bounds.top = 300
//            bounds.right = 100
//            bounds.bottom = 100

            val qq = packageManager.hasSystemFeature(PackageManager.FEATURE_FREEFORM_WINDOW_MANAGEMENT)

            Log.d("FEATURE_FREEFORM_WINDOW_MANAGEMENT", qq.toString())
            // Use ActivityOptions to specify the bounds
            val options = ActivityOptions.makeBasic()
            options.launchBounds = bounds
//            options.setLaunchBounds(bounds)

            // Start the activity with the specified bounds
            startActivity(launchIntent, options.toBundle())
        }
//        else if (launchIntent != null) {
//            // Fallback for devices that do not support ActivityOptions.setLaunchBounds
//            startActivity(launchIntent)
//        } else {
//            // Handle the case where the launch intent is null
//            // For example, show a toast or log an error
//        }
    }
}