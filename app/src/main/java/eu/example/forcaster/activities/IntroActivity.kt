package eu.example.forcaster.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowInsetsController
import eu.example.forcaster.databinding.ActivityIntroBinding

class IntroActivity : AppCompatActivity() {

    private var binding: ActivityIntroBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        window.insetsController?.let { controller ->
            controller.hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
            controller.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }

        binding?.btnSignIn?.setOnClickListener {
            startActivity(Intent(this@IntroActivity,SignInActivity::class.java))
        }

        binding?.btnSignUp?.setOnClickListener {
            startActivity(Intent(this@IntroActivity,SignUpActivity::class.java))
        }



    }
}