package com.example.androidsnsproject

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidsnsproject.databinding.FragmentFriendBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FriendFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var userArrList: ArrayList<Friend>
    private lateinit var myAdapter: FriendAdapter
    private lateinit var db: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_friend, container, false)

        recyclerView = view.findViewById(R.id.friend_list)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)

        userArrList = arrayListOf()

        myAdapter = FriendAdapter(userArrList)

        recyclerView.adapter = myAdapter

        eventChangeListener()

        // Inflate the layout for this fragment
        return view
    }

    private fun eventChangeListener() {
        db = FirebaseFirestore.getInstance()
        db.collection("users").
                addSnapshotListener(object: EventListener<QuerySnapshot>{
                    @SuppressLint("NotifyDataSetChanged")
                    override fun onEvent(
                        value: QuerySnapshot?,
                        error: FirebaseFirestoreException?
                    ) {
                        if(error != null) {
                            Log.e("firestore Error", error.message.toString())
                            return
                        }

                        for(dc: DocumentChange in value?.documentChanges!!) {
                            if(dc.type == DocumentChange.Type.ADDED) {
                                userArrList.add(dc.document.toObject(Friend::class.java))
                            }
                        }

                        myAdapter.notifyDataSetChanged()
                    }
                })
    }
}