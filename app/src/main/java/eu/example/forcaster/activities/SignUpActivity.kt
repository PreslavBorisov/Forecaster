package eu.example.forcaster.activities

import android.os.Bundle
import android.text.TextUtils
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import eu.example.forcaster.R
import eu.example.forcaster.databinding.ActivitySignUpBinding
import eu.example.forcaster.firebase.FireStoreClass
import eu.example.forcaster.models.User

class SignUpActivity : BaseActivity() {

    private var binding: ActivitySignUpBinding?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        window.insetsController?.let { controller ->
            controller.hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
            controller.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }

        setUpActionBar()

    }

    fun userRegisteredSuccess(){

        Toast.makeText(this@SignUpActivity,
            "you have successfully registered the",
            Toast.LENGTH_LONG).show()

        hideProgressDialog()

        FirebaseAuth.getInstance().signOut()
        finish()
    }


    private fun setUpActionBar(){
        setSupportActionBar(binding?.toolbarSignUpActivity)

        val actionBar = supportActionBar

        if(actionBar!=null){
            actionBar.setHomeAsUpIndicator(R.drawable.ic_vector_back)
            actionBar.setDisplayHomeAsUpEnabled(true)
        }

        binding?.toolbarSignUpActivity?.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        binding?.btnSignUp?.setOnClickListener {
            registerUser()
        }
    }

    private fun registerUser(){
        val name: String = binding?.etNameSignUp?.text.toString().trim{it <= ' ' }
        val email: String = binding?.etEmailSignUp?.text.toString().trim{it<=' '}
        val password:String = binding?.etPasswordSignUp?.text.toString().trim{it<=' '}

        if (validateForm(name,email,password)){
            showProgressDialog()

            FirebaseAuth.getInstance()
                .createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener { task ->

                    if(task.isSuccessful){
                        val firebaseUser: FirebaseUser = task.result!!.user!!
                        val registeredEmail = firebaseUser.email!!
                        val user = User(firebaseUser.uid,name,registeredEmail)

                        FireStoreClass().registerUser(this,user)
                    }
                    else{
                        Toast.makeText(this@SignUpActivity,
                            "Authentication failed.",
                            Toast.LENGTH_SHORT).show()

                        hideProgressDialog()
                    }
                }
        }
    }
    private fun validateForm(name: String,email : String, password: String):Boolean{

        return when{
            TextUtils.isEmpty(name)->{
                showErrorSnackBar("Please enter a name")
                false
            }
            TextUtils.isEmpty(email)->{
                showErrorSnackBar("Please enter an email address")
                false
            }
            TextUtils.isEmpty(password)->{
                showErrorSnackBar("Please enter a password")
                false
            }else->{
                true
            }
        }
    }
}