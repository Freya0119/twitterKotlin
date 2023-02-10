package com.example.twitterwithkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.adapters.NumberPickerBindingAdapter.setValue
import androidx.databinding.adapters.ViewGroupBindingAdapter.setListener
import androidx.recyclerview.widget.RecyclerView
import com.example.twitterwithkotlin.databinding.ActivityUserInterfaceBinding
import com.example.twitterwithkotlin.model.*
import com.example.twitterwithkotlin.model.adapter.TweetAdapter
import com.example.twitterwithkotlin.model.viewmodel.DataModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class UserInterfaceActivity : AppCompatActivity() { //貼文畫面: 1.點擊評論
    private lateinit var _binding: ActivityUserInterfaceBinding
    private val binding get() = _binding

    private lateinit var recyclerView: RecyclerView

    private var dataModel = DataModel() //TODO: for data

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityUserInterfaceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = FirebaseAuth.getInstance().uid

        recyclerView = this.findViewById(R.id.user_inter_recyclerView)

        setListener()

        fetch()
    }

    private fun fetch() {
        val uid = FirebaseAuth.getInstance().uid    //TODO: currentUser???
        val fireData = FirebaseDatabase.getInstance().getReference("tweets/$uid")
        fireData.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach {
                    val body = it.getValue(TweetBody::class.java)
                    Log.d(KEY_TEST, "Body: ${body!!.bodyText}")
                    dataModel.add(body!!)
                }
                recyclerView.adapter = TweetAdapter(dataModel.allItems)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d(KEY_TEST, "Fetch fail: $error")
            }
        })
    }

    private fun setListener() {
        binding.userInterSendButton.setOnClickListener {
            if (binding.userInterEditText.text.isNotEmpty()) {
                val uid = FirebaseAuth.getInstance().uid    //TODO: currentUser???
                val text = binding.userInterEditText.text.toString()
                send("tweets/$uid", 2, text)   //1: TweetUser, 2: TweetBody, 3: TweetMessage
                binding.userInterEditText.text.clear()
            }
        }
    }
}

fun send(path: String, classType: Int, message: String) {
    val uid = FirebaseAuth.getInstance().uid    //TODO: currentUser???

    val fireData = FirebaseDatabase.getInstance().getReference(path)
    when (classType) {
//        0 -> fireData.setValue(TestClass(message!!))  //for test
        1 -> fireData.push().setValue(
            TweetUser(uid!!, "Set user")
        )
        2 -> fireData.push().setValue(
            TweetBody(TweetUser(uid!!, "test name"), message)
        )
        3 -> fireData.setValue( //not push
            TweetBody(TweetUser(uid!!, "test name"), "set with message"),   //cannot set post body!!!
            TweetMessage("who name", message)
        )
    }
}
