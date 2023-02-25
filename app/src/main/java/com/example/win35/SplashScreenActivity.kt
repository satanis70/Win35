package com.example.win35

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.os.postDelayed
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SplashScreenActivity : AppCompatActivity() {
    private var drawable: Drawable? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        val ivLogo = findViewById<ImageView>(R.id.imageView_logo)
        CoroutineScope(Dispatchers.IO).launch{
            val loader = ImageLoader(this@SplashScreenActivity)
            val request = ImageRequest.Builder(this@SplashScreenActivity)
                .data("http://49.12.202.175/win35/images.jpg")
                .allowHardware(false)
                .build()

            drawable = (loader.execute(request) as SuccessResult).drawable
            launch(Dispatchers.Main){
                findViewById<ConstraintLayout>(R.id.constraint_splash).background = drawable
            }
        }
        Picasso.get().load("http://49.12.202.175/win35/logo.png").into(ivLogo)
        Handler().postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }
}