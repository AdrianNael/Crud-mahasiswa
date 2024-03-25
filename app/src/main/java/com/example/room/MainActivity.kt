package com.example.room

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.example.room.adapter.UserAdapter
import com.example.room.data.AppDatabase
import com.example.room.data.entity.User
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var fab: FloatingActionButton
    private var list = mutableListOf<User>()
    private lateinit var adapter: UserAdapter
    private lateinit var database: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.recycler_view)
        fab = findViewById(R.id.fab)


        adapter = UserAdapter(list)
        database = AppDatabase.getInstance(applicationContext)
        //implementasi listener
        adapter.setDialog(object : UserAdapter.Dialog {
            override fun onClick(position: Int) {
                // Untuk membuat tampilan dialog
                val dialog = AlertDialog.Builder(this@MainActivity)
                dialog.setTitle("Pilih Aksi yang Dinginkan")
                dialog.setItems(R.array.items_option) { dialog, which ->
                    // Aksi untuk tiap item
                    when (which) {
                        0 -> {
                            // Coding edit
                            val intent = Intent(this@MainActivity, EditorActivity::class.java)
                            // Mengambil data, id diambil dari list
                            intent.putExtra("id", list[position].uid)
                            // Untuk membuka EditorActivity
                            startActivity(intent)
                        }

                        1 -> {
                            // Coding delete
                            val animation = AnimationUtils.loadAnimation(
                                this@MainActivity,
                                android.R.anim.slide_in_left
                            )
                            animation.duration = 1000
                            recyclerView.findViewHolderForAdapterPosition(position)?.itemView?.startAnimation(
                                animation
                            )


                            // Menghapus data dari database
                            database.userDao().delete(list[position])
                            // Menghapus item dari list
                            list.removeAt(position)
                            // Memberitahu adapter bahwa item telah dihapus
                            adapter.notifyItemRemoved(position)
                        }

                        else -> dialog.dismiss()
                    }
                }
                dialog.show()
            }
        })


        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(applicationContext, VERTICAL, false)
        recyclerView.addItemDecoration(DividerItemDecoration(applicationContext, VERTICAL))

        fab.setOnClickListener {
            startActivity(Intent(this, EditorActivity::class.java))
        }
    }

    //panggil getData
    override fun onResume() {
        super.onResume()
        getData()
    }

    //membuat data
    @SuppressLint("NotifyDataSetChanged")
    fun getData() {
        list.clear()
        list.addAll(database.userDao().getAll())
        adapter.notifyDataSetChanged()
    }

}