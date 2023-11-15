package com.example.ian

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.ArrayList

class Podpiski  : ComponentActivity() {
    var recview: RecyclerView? = null
    var datalist: ArrayList<model?>? = null
    var db: FirebaseFirestore? = null
    var button: Button? = null
    var adapter: adapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view)
        recview = findViewById<View>(R.id.recview) as RecyclerView
        recview!!.layoutManager = LinearLayoutManager(this)
        button = findViewById(R.id.button) as Button
        val auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser!!.email
        datalist = ArrayList()
        adapter = adapter(context = this,datalist)
        recview!!.adapter = adapter
        db = FirebaseFirestore.getInstance()
        db!!.collection(currentUser.toString())
            .get()
            .addOnSuccessListener { queryDocumentSnapshots ->
                val list = queryDocumentSnapshots.documents
                for (d in list) {
                    val obj = d.toObject(model::class.java)
                    datalist!!.add(obj)
                }
                adapter?.notifyDataSetChanged()
            }
        button!!.setOnClickListener {
            val intent = Intent(this@Podpiski, Addpodpis::class.java)
            startActivity(intent)
            finish()
        }
    }
}