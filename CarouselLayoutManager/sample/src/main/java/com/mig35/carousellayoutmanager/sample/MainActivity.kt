package com.mig35.carousellayoutmanager.sample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // window.requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
        setContentView(R.layout.activity_main)
        findViewById<ImageView>(R.id.targetImage).setImageResource(intent.getIntExtra("imageres", R.drawable.a1))
        findViewById<ImageView>(R.id.targetImage).setOnClickListener {
            onBackPressed()
        }
    }
}