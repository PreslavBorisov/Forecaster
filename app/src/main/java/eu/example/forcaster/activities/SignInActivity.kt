package eu.example.forcaster.activities


import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import eu.example.forcaster.R
import eu.example.forcaster.databinding.ActivitySignInBinding
import eu.example.forcaster.firebase.FireStoreClass
import eu.example.forcaster.models.User

class SignInActivity : BaseActivity() {

    private var binding: ActivitySignInBinding?= null
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        auth = FirebaseAuth.getInstance()

        window.insetsController?.let { controller ->
            controller.hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
            controller.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }

        binding?.btnSignIn?.setOnClickListener {
            signInRegistration()
        }

        setUpActonBar()

    }

    private fun setUpActonBar(){
        setSupportActionBar(binding?.toolbarSignInActivity)
        val actionBar = supportActionBar
        if(actionBar!=null){

            actionBar.setHomeAsUpIndicator(R.drawable.ic_vector_back)
            actionBar.setDisplayHomeAsUpEnabled(true)
        }
        binding?.toolbarSignInActivity?.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

    }

    fun signInSuccess(){
        hideProgressDialog()

        startActivity(Intent(this@SignInActivity,MainActivity::class.java))
        finish()
    }

    private fun signInRegistration(){
        val email: String = binding?.etEmail?.text.toString().trim{it<=' '}
        val password: String = binding?.etPassword?.text.toString().trim{it<=' '}

        if(validateForm(email,password)){
            showProgressDialog()

            auth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this) {task ->
                    hideProgressDialog()
                    if(task.isSuccessful){
                        FireStoreClass().loadUserData(this@SignInActivity)
                    }else{
                        Log.w("Sign in", "signInWithEmail:failure", task.exception)
                        Toast.makeText(
                            baseContext,
                            "Authentication failed.",
                            Toast.LENGTH_SHORT,
                        ).show()
                    }
                }
        }
    }

    private fun validateForm(email: String, password: String): Boolean{

        return when{
            TextUtils.isEmpty(email) ->{
                showErrorSnackBar("Please enter an email address")
                false
            }
            TextUtils.isEmpty(password) -> {
                showErrorSnackBar("Please enter a password")
                false
            }
            else -> {
                true
            }
        }

    }

}