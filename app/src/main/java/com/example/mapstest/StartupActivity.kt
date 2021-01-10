package com.example.mapstest

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity


class StartupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_startup)


        // 地図描画前前にダウンロードしたいデータや処理がある場合は
        // それらの処理が終わってから遷移するようにする
        // 2秒後にMapActivityへ遷移
        Handler(Looper.getMainLooper()).postDelayed(Runnable {
            // 渡したいパラメータがある場合はintentに追加する
            val intent: Intent = Intent(this@StartupActivity, MapsActivity::class.java)
            // 地図画面遷移後、本画面へ戻れないようにするために設定
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)

        }, 2000)
    }

}