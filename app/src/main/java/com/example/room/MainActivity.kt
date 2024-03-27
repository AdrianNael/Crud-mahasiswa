package com.example.room

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
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
    private var list = mutableListOf<User>() // untuk menyimpan data user
    private lateinit var adapter: UserAdapter
    private lateinit var database: AppDatabase // Objek database

    private lateinit var editSearch: EditText
    private lateinit var btnSearch: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.recycler_view)
        fab = findViewById(R.id.fab)

        editSearch = findViewById(R.id.edit_search)
        btnSearch = findViewById(R.id.btn_search)

        // Inisialisasi adapter untuk RecyclerView
        adapter = UserAdapter(list)
        database = AppDatabase.getInstance(applicationContext)

        // Mengatur aksi ketika item di RecyclerView diklik
        adapter.setDialog(object : UserAdapter.Dialog {
            override fun onClick(position: Int) {
                // Membuat dialog untuk pilihan aksi
                val dialog = AlertDialog.Builder(this@MainActivity)
                dialog.setTitle("Pilih Aksi yang Dinginkan")
                dialog.setItems(R.array.items_option) { dialog, which ->
                    // Pilihan aksi
                    when (which) {
                        0 -> {
                            // Edit data user
                            val intent = Intent(this@MainActivity, EditorActivity::class.java)
                            intent.putExtra("id", list[position].uid)
                            startActivity(intent)
                        }
                        1 -> {
                            // Hapus data user
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
        recyclerView.layoutManager = LinearLayoutManager(applicationContext, VERTICAL, false) // mengatur letak
        recyclerView.addItemDecoration(DividerItemDecoration(applicationContext, VERTICAL)) //nambah item

        // function tombol
        fab.setOnClickListener {
            startActivity(Intent(this, EditorActivity::class.java))
        }

        // function searching
        btnSearch.setOnClickListener {
            if (editSearch.text.isNotEmpty()) {
                searchData(editSearch.text.toString())
            } else {
                getData()
                Toast.makeText(applicationContext, "Silahkan isi Dahulu", LENGTH_SHORT).show()
            }
        }
        // function untuk editseach
        editSearch.setOnKeyListener { v, keycode, event ->
            if (editSearch.text.isNotEmpty()) {
                searchData(editSearch.text.toString())
            } else {
                getData()
            }
            false
        }
    }

    // Panggil fungsi getData()
    override fun onResume() {
        super.onResume()
        getData()
    }

    //  mendapatkan data dari database dan menampilkannya di RecyclerView
    @SuppressLint("NotifyDataSetChanged")
    fun getData() {
        list.clear()
        list.addAll(database.userDao().getAll())
        adapter.notifyDataSetChanged()
    }

    //  untuk mencari data berdasarkan nama
    @SuppressLint("NotifyDataSetChanged")
    fun searchData(search: String) {
        list.clear()
        list.addAll(database.userDao().searchByName(search))
        adapter.notifyDataSetChanged()
    }
}
