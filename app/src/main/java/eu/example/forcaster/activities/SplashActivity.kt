package eu.example.forcaster.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowInsets
import android.view.WindowInsetsController
import eu.example.forcaster.databinding.ActivitySplashBinding
import eu.example.forcaster.firebase.FireStoreClass

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private var binding: ActivitySplashBinding? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        window.insetsController?.let { controller ->
            controller.hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
            controller.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }

        val typeFace: Typeface = Typeface.createFromAsset(assets,"carbon bl.ttf")

        binding?.tvName?.typeface =typeFace

        Handler(Looper.getMainLooper()).postDelayed(
            {
                val currentUserId = FireStoreClass().getCurrentUserId()

                if(currentUserId.isNotEmpty()){
                    startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                }else{
                    startActivity(Intent(this@SplashActivity, IntroActivity::class.java))
                }
                finish()
        },5000)

    }
}