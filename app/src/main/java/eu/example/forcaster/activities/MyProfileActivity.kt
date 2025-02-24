package eu.example.forcaster.activities

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import eu.example.forcaster.R
import eu.example.forcaster.databinding.ActivityMyProfileBinding
import eu.example.forcaster.firebase.FireStoreClass
import eu.example.forcaster.models.User
import eu.example.forcaster.utils.Constants
import java.io.IOException


class MyProfileActivity : BaseActivity() {

    private var binding: ActivityMyProfileBinding? = null



    private var mSelectedImageFileUri: Uri? = null
    private var mProfileImageUrl : String = ""

    private lateinit var mUserDetails: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMyProfileBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setUpActionBar()

        FireStoreClass().loadUserData(this)

        binding?.ivProfileUserImage?.setOnClickListener{
            checkGalleryPermissionsAndOpenImageChooser()
        }

        binding?.btnUpdateMyProfile?.setOnClickListener {
            if(mSelectedImageFileUri !=null){
                uploadUserImage()
            }else{
                showProgressDialog()
                updateUserProfileData()
            }
        }
    }

    private fun checkGalleryPermissionsAndOpenImageChooser() {
        val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Manifest.permission.READ_MEDIA_IMAGES
        } else {
            Manifest.permission.READ_EXTERNAL_STORAGE
        }

        if (ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED) {
            Constants.showImageChooser(this@MyProfileActivity)
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(permission),
                Constants.READ_STORAGE_PERMISSION_CODE
            )
        }
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == Constants.READ_STORAGE_PERMISSION_CODE){
            if(grantResults.isNotEmpty()&&
                grantResults[0] == PackageManager.PERMISSION_GRANTED){

                Constants.showImageChooser(this@MyProfileActivity)
            }
        }else{
            Toast.makeText(this,
                "Oops, you just denied the permission for storage." +
                        " You can also allow it from settings.",
                Toast.LENGTH_LONG).show()
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK
            && requestCode == Constants.PICK_IMAGE_REQUEST_CODE
            && data!!.data !=null){
            mSelectedImageFileUri = data.data

            try {
                Glide
                    .with(this@MyProfileActivity)
                    .load(mSelectedImageFileUri)
                    .centerCrop()
                    .placeholder(R.drawable.ic_user_place_holder)
                    .into(binding?.ivProfileUserImage!!)
            }catch (e: IOException){
                e.printStackTrace()
            }


        }
    }


    private  fun setUpActionBar(){

        setSupportActionBar(binding?.toolbarMyProfileActivity)
        val actionBar = supportActionBar
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_vector_back)
            actionBar.title = resources.getString(R.string.my_profile)
        }

        binding?.toolbarMyProfileActivity?.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

    }

    fun setUserDataUI(user: User){

        mUserDetails = user

        Glide
            .with(this@MyProfileActivity)
            .load(user.image)
            .centerCrop()
            .placeholder(R.drawable.ic_user_place_holder)
            .into(binding?.ivProfileUserImage!!)

        binding?.etNameMyProfile?.setText(user.name)
        binding?.etEmailMyProfile?.setText(user.email)

        if(user.mobile != 0L){
            binding?.etMobileMyProfile?.setText(user.mobile.toString())
        }
    }

    private fun updateUserProfileData(){
        val userHashMap = HashMap<String,Any>()

        if(mProfileImageUrl.isNotEmpty()&& mProfileImageUrl !=mUserDetails.image)
            userHashMap[Constants.IMAGE] = mProfileImageUrl

        if(binding?.etNameMyProfile?.text.toString() != mUserDetails.name)
            userHashMap[Constants.NAME]= binding?.etNameMyProfile?.text.toString()

        if(binding?.etMobileMyProfile?.text.toString() != mUserDetails.mobile.toString())
            userHashMap[Constants.MOBILE]= binding?.etMobileMyProfile?.text.toString().toLong()


        FireStoreClass().updateUserProfileData(this@MyProfileActivity,userHashMap)

    }

    private fun uploadUserImage(){
        showProgressDialog()

        if(mSelectedImageFileUri !=null){

            val sRef :StorageReference =
                FirebaseStorage.getInstance().reference.child(
                    "USER_IMAGE" + System.currentTimeMillis()
                            + "." + Constants.getFileExtension(this@MyProfileActivity,mSelectedImageFileUri))

            sRef.putFile(mSelectedImageFileUri!!).addOnSuccessListener {
                    taskSnapshot ->

                Log.i("Firebase Image URL",
                    taskSnapshot.metadata!!.reference!!.downloadUrl.toString())

                taskSnapshot.metadata!!.reference!!.downloadUrl.addOnSuccessListener {
                        uri ->
                    Log.i("Downloadable Image URL",uri.toString())
                    mProfileImageUrl = uri.toString()
                    hideProgressDialog()
                    updateUserProfileData()
                }
            }.addOnFailureListener {
                    exception ->
                Toast.makeText(this@MyProfileActivity,
                    exception.message,Toast.LENGTH_LONG).show()
                hideProgressDialog()
            }
        }
    }

    fun profileUpdateSuccess(){
        hideProgressDialog()

        setResult(Activity.RESULT_OK)

        finish()
    }
}