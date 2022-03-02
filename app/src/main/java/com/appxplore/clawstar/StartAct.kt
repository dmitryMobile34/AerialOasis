package com.appxplore.clawstar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class StartAct : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
    }

    fun gamestart(view: View) {
        Intent(applicationContext, Gameplay::class.java).also { startActivity(it) }
        finish()
    }
}