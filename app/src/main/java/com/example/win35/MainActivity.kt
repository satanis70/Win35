package com.example.win35

import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private var drawable: Drawable? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        CoroutineScope(Dispatchers.IO).launch{
            val loader = ImageLoader(this@MainActivity)
            val request = ImageRequest.Builder(this@MainActivity)
                .data("http://49.12.202.175/win35/images.jpg")
                .allowHardware(false)
                .build()

            drawable = (loader.execute(request) as SuccessResult).drawable
            launch(Dispatchers.Main){
                findViewById<LinearLayout>(R.id.linear_main).background = drawable
            }
        }
    }
}