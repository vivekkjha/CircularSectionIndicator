package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val cv = findViewById<CircularSectionIndicatorView>(R.id.view_csi)

       /* cv.setup(200,20, mapOf(
            Pair(1,CircularSectionIndicatorView.State.NORMAL),
            Pair(2,CircularSectionIndicatorView.State.ACTIVE),
            Pair(3,CircularSectionIndicatorView.State.SKIPPED),
            Pair(4,CircularSectionIndicatorView.State.VISITED),
            Pair(5,CircularSectionIndicatorView.State.NORMAL),
            Pair(6,CircularSectionIndicatorView.State.ACTIVE),
            Pair(7,CircularSectionIndicatorView.State.SKIPPED),
            Pair(8,CircularSectionIndicatorView.State.VISITED)*//*
            Pair(9,CircularSectionIndicatorView.State.NORMAL),
            Pair(10,CircularSectionIndicatorView.State.ACTIVE)*//*
        ))*/

        cv.setup(200,20,10)

        Handler().postDelayed(Runnable {
            cv.updateState(1,CircularSectionIndicatorView.State.SKIPPED)
        },3000)
    }
}