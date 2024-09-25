package eu.example.forcaster.activities

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import eu.example.forcaster.R
import eu.example.forcaster.databinding.DialogProgressBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

open class BaseActivity : AppCompatActivity() {


    private lateinit var mProgressDialog: Dialog

    private var doubleBackToExitPressedOnce = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
    }


    fun showProgressDialog(){

        val text = "Please wait.."

        mProgressDialog = Dialog(this)

        val dialogBinding = DialogProgressBinding.inflate(layoutInflater)

        mProgressDialog.setContentView(dialogBinding.root)
        dialogBinding.tvProgressText.text = text
        mProgressDialog.show()
    }
    fun hideProgressDialog(){
        if (::mProgressDialog.isInitialized && mProgressDialog.isShowing) {
            mProgressDialog.dismiss()
        }
    }

    fun showErrorSnackBar(message: String){
        val snackBar = Snackbar.make(findViewById(android.R.id.content),
            message, Snackbar.LENGTH_LONG)
        val snackBarView = snackBar.view
        snackBarView.setBackgroundColor(
            ContextCompat.getColor(this,
            R.color.snackBar_error_color))
        snackBar.show()
    }

    fun doubleBackToExit(){
        if(doubleBackToExitPressedOnce){
            super.onBackPressedDispatcher.onBackPressed()
            return
        }

        this.doubleBackToExitPressedOnce= true
        Toast.makeText(this,
            "Please click back again to exit",
            Toast.LENGTH_SHORT).show()

        lifecycleScope.launch {
            delay(2000)
            doubleBackToExitPressedOnce = false
        }
    }

}