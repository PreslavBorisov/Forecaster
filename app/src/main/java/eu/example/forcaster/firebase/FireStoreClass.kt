package eu.example.forcaster.firebase

import android.app.Activity
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import eu.example.forcaster.activities.MainActivity
import eu.example.forcaster.activities.MyProfileActivity
import eu.example.forcaster.activities.SignInActivity
import eu.example.forcaster.activities.SignUpActivity
import eu.example.forcaster.models.User
import eu.example.forcaster.utils.Constants

class FireStoreClass {

    private val mFireStore = FirebaseFirestore.getInstance()


    fun registerUser(activity: SignUpActivity,userInfo: User){
        mFireStore
            .collection(Constants.USERS)
            .document(getCurrentUserId())
            .set(userInfo, SetOptions.merge())
            .addOnSuccessListener {
                activity.userRegisteredSuccess()
            }.addOnFailureListener {
                Log.e(activity.javaClass.simpleName,"Error writing document")
            }
    }

    fun getCurrentUserId(): String{
        val currentUser = FirebaseAuth.getInstance().currentUser
        var currentUserId = ""
        if(currentUser != null){
            currentUserId = currentUser.uid
        }
        return currentUserId
    }

    fun updateUserProfileData(activity: Activity,
                              userHashMap: HashMap<String,Any>){
        mFireStore
            .collection(Constants.USERS)
            .document(getCurrentUserId())
            .update(userHashMap)
            .addOnSuccessListener {
                Log.i(activity.javaClass.simpleName,
                    "Profile Data updated successfully")
                Toast.makeText(activity,"Profile updated successfully!"
                    ,Toast.LENGTH_SHORT).show()

                if(activity is MyProfileActivity){
                    activity.profileUpdateSuccess()
                }


            }.addOnFailureListener {
                    e->
                if(activity is MyProfileActivity){
                    activity.hideProgressDialog()
                }
                Log.e(activity.javaClass.simpleName,
                    "Error while creating a board",e)
                Toast.makeText(activity,"Error when updating the profile!"
                    ,Toast.LENGTH_SHORT).show()
            }
    }

    fun loadUserData(activity: Activity){
        mFireStore
            .collection(Constants.USERS)
            .document(getCurrentUserId())
            .get()
            .addOnSuccessListener { document ->
                val loggedInUser = document.toObject(User::class.java)!!

                when(activity){
                    is SignInActivity ->{
                        activity.signInSuccess()
                    }
                    is MainActivity ->{
                        activity.updateNavigationUserDetails(loggedInUser)
                    }is MyProfileActivity ->{
                        activity.setUserDataUI(loggedInUser)
                    }
                }
            }.addOnFailureListener {_ ->
                when(activity){
                    is SignInActivity ->{
                        activity.hideProgressDialog()
                    }
                    is MainActivity ->{
                        activity.hideProgressDialog()
                    }
                    is MyProfileActivity ->{
                        activity.hideProgressDialog()
                    }
                }
                Log.e("Sign in user","Error writing document")
            }
    }



   /* fun createFavoriteItem(activity: Activity,tag: String){
        mFireStore
            .collection(Constants.FAVORITE)
            .document()
            .set(tag, SetOptions.merge())
            .addOnSuccessListener {
                Log.i("Add favorite","Successful favorite add")

            }.addOnFailureListener {e ->
                Log.e(activity.javaClass.simpleName,"Error writing document",e)
            }
    }*/




}