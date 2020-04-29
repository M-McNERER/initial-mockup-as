package com.intialmockup.as.activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.intialmockup.as.others.R
import com.intialmockup.as.others.Settings
import java.util.*


class LaunchingActivity : AppCompatActivity() {

    private val PERMISSION_WRITE_EXTERNAL = 111

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launching)

        val settings = Settings.getInstance()

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), PERMISSION_WRITE_EXTERNAL)
        } else {
            Timer().schedule(object : TimerTask() {
                override fun run() {
                    val guidedActivity = Intent(applicationContext, GuidedActivity::class.java)
//                    ContextState.getInstance().init(State.INFO)
                    startActivity(guidedActivity)
//                    val home = Intent(applicationContext, HomeActivity::class.java)
//                    startActivity(home)
                }
            }, 1) /* 2000 */
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_WRITE_EXTERNAL -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.v(TAG, "Permissions accepted")
                    startActivity(Intent(applicationContext, HomeActivity::class.java))
                }
            }
        }
    }

    companion object {
        private const val TAG = "LaunchingActivity"
    }
}
