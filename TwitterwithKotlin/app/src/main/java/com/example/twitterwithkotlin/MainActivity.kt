package com.example.twitterwithkotlin

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.Image
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.core.graphics.scale
import com.example.twitterwithkotlin.model.TweetUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.auth.User
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream

const val KEY_TEST = "KEY_TEST"
const val KEY_USER = "KEY_USER"

class MainActivity : AppCompatActivity() {
    private var user: TweetUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.findViewById<Button>(R.id.main_signIn_button).setOnClickListener {
            if (this.findViewById<EditText>(R.id.main_name_edittext).text.isNotEmpty()
                || this.findViewById<EditText>(R.id.main_pwd_edittext).text.isNotEmpty()  //可以為: " "，格式是否符合交給Firebase
            )
                signIn()
        }
        this.findViewById<Button>(R.id.main_signUp_button).setOnClickListener {
            if (this.findViewById<EditText>(R.id.main_name_edittext).text.isNotEmpty()
                || this.findViewById<EditText>(R.id.main_pwd_edittext).text.isNotEmpty()
            )
                signUp()
        }
        this.findViewById<ImageView>(R.id.main_login_imageView).setOnClickListener {
            setImage()
        }

        checkUid()
    }

    private fun jumpActivity() {
        val intent = Intent(this, UserInterfaceActivity::class.java)
        //TODO: put extra user???
        startActivity(intent)
    }

    private fun signIn() {
        val name = this.findViewById<EditText>(R.id.main_name_edittext).text.toString()
        val password = this.findViewById<EditText>(R.id.main_pwd_edittext).text.toString()

        FirebaseAuth.getInstance().signInWithEmailAndPassword(name, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    //TODO: put extra user???
                    Log.d(KEY_TEST, "Sign in success. User name: ${user?.name}")
                    jumpActivity()
                } else {
                    Log.d(KEY_TEST, "Sign in failed: ${it.exception}")
                }
            }
    }

    private fun checkUid() {
        val uid = FirebaseAuth.getInstance().uid
        if (uid != null) {
//            val ref = FirebaseDatabase.getInstance().getReference("user")
//            ref.setValue("111")
//                .addOnCompleteListener {
//                    if (it.isSuccessful) {
//                        Log.d(KEY_TEST, "Set value success.")
//                    } else {
//                        Log.d(KEY_TEST, "Set value fail: ${it.exception}")
//                    }
//                }
            //TODO: set user???
            jumpActivity()
        }
    }

    private fun signUp() {
        val name = this.findViewById<EditText>(R.id.main_name_edittext).text.toString()
        val password = this.findViewById<EditText>(R.id.main_pwd_edittext).text.toString()

        val auth = FirebaseAuth.getInstance()
        auth.createUserWithEmailAndPassword(name, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Log.d(KEY_TEST, "Sign up success.")
                    addUsersIntoDatabase(name, password)
                } else {
                    Log.d(KEY_TEST, "Sign up failed: ${it.exception}")
                }
            }
    }

    private fun addUsersIntoDatabase(name: String, password: String) {
        val uid = FirebaseAuth.getInstance().uid
        val fireData = FirebaseDatabase.getInstance().getReference("users/$uid").push()
        fireData.setValue(TweetUser(uid!!, name))
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Log.d(KEY_TEST, "Add user success.")
                } else {
                    Log.d(KEY_TEST, "Add user failed: ${it.exception}")
                }
            }
    }

    private fun setImage() {
        val intent = Intent(Intent.ACTION_GET_CONTENT).setType("image/*")
        startActivityForResult(intent, 0)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null && requestCode == 0 && resultCode == RESULT_OK) {
            Log.d(KEY_TEST, "Data: $data")
            val imageUri = data.data
            this.findViewById<ImageView>(R.id.main_login_imageView).setImageURI(imageUri)
            this.findViewById<ImageView>(R.id.main_login_imageView).scaleType.apply {
                //TODO: 控制圖片顯示
            }

            val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, imageUri)
            //TODO: set image size???
//            val bitmapSet = bitmap.scale(150, 150)
            //TODO: set into imageview
            this.findViewById<ImageView>(R.id.main_login_imageView).setImageBitmap(bitmap)

            saveFireStorage(imageUri!!)
        }
    }

    private fun saveFireStorage(uri: Uri) {
        val uid = FirebaseAuth.getInstance().uid    //TODO: currentUser???
        val fireStorage = FirebaseStorage.getInstance().getReference("images/$uid")
        fireStorage.putFile(uri).addOnCompleteListener {
            if (it.isSuccessful) {
                Log.d(KEY_TEST, "Upload success")
            } else {
                Log.d(KEY_TEST, "Upload failed: ${it.exception}")
            }
        }
    }
}