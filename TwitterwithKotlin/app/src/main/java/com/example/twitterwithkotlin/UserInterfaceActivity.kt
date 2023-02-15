package com.example.twitterwithkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.example.twitterwithkotlin.databinding.ActivityUserInterfaceBinding
import com.example.twitterwithkotlin.model.*
import com.example.twitterwithkotlin.model.adapter.TweetAdapter
import com.example.twitterwithkotlin.model.viewmodel.DataModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
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

        recyclerView = binding.userInterRecyclerView

        setButtonListener()

        fetch()
    }

    private fun fetch() {
        val uid = FirebaseAuth.getInstance().uid    //TODO: currentUser???
        val fireData = FirebaseDatabase.getInstance().getReference("tweets/$uid")
        fireData.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                Log.d(KEY_TEST, "child add")
                val item = snapshot.getValue(TweetBody::class.java)
                if (item != null) {
                    dataModel.add(item)
                }
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                Log.d(KEY_TEST, "child change")
                val item = snapshot.getValue(TweetBody::class.java)
                if (item != null) {
                    dataModel.add(item)
                }
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                Log.d(KEY_TEST, "child remove")
                val item = snapshot.getValue(TweetBody::class.java)
                if (item != null) {
                    dataModel.add(item)
                }
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                Log.d(KEY_TEST, "child move")
                val item = snapshot.getValue(TweetBody::class.java)
                if (item != null) {
                    dataModel.add(item)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d(KEY_TEST, "cancel: $error")
            }
        })
    }

    private fun setButtonListener() {
        binding.sendImageButton.setOnClickListener {
            Log.d(KEY_TEST, "send image")
            val item = TweetBody()
            val fireDatabase = FirebaseDatabase.getInstance().getReference("tweets").push()
            fireDatabase.setValue(item)
        }
    }
}