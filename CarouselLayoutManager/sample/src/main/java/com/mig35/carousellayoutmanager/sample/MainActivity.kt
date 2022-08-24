package com.mig35.carousellayoutmanager.sample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.demo_horizontal).setOnClickListener {
            val intent = Intent()
            intent.setClass(baseContext, CarouselPreviewActivity::class.java)
            startActivity(intent)
        }
        findViewById<Button>(R.id.demo_vertical).setOnClickListener {

            val intent = Intent()
            startActivity(intent)
        }
    }
}