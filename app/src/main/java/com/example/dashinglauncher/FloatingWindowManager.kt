package com.example.customlauncher

import android.app.Service
import android.content.Intent
import android.graphics.PixelFormat
import android.view.Gravity
import android.view.LayoutInflater
import android.view.WindowManager
import com.example.dashinglauncher.MainActivity
import com.example.dashinglauncher.R

class FloatingWindowManager(private val service: MainActivity) {

    private var windowManager: WindowManager =
        service.getSystemService(Service.WINDOW_SERVICE) as WindowManager

    fun showApp(packageName: String) {
        // Настройки для нового окна
        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY, // Для Android 8.0 и выше
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
        )

        // Установите позицию окна справа
        params.gravity = Gravity.RIGHT
        params.x = 0
        params.y = 0

        // Инфлейт ваш слой (в данном случае просто пустой FrameLayout)
        val layoutInflater = LayoutInflater.from(service)
        val view = layoutInflater.inflate(R.layout.activity_floating, null)

        // Запустите приложение в новом окне
        val launchIntent = service.packageManager.getLaunchIntentForPackage(packageName)
        view.setOnClickListener {
            launchIntent?.let {
                it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                service.startActivity(it)
            }
        }

        // Добавьте вью в окно
        windowManager.addView(view, params)
    }

//    fun removeView() {
//        // Удаление окна
//        windowManager.removeViewImmediate(view)
//    }
}