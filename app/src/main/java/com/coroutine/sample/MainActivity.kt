package com.coroutine.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.coroutine.sample.R

/**
 * Show layout.activity_main and setup data binding.
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

}
