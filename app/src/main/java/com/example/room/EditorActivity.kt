package com.example.room

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.room.data.AppDatabase
import com.example.room.data.entity.User

class EditorActivity : AppCompatActivity() {
    private lateinit var fullName: EditText
    private lateinit var alamat : EditText
    private lateinit var email: EditText
    private lateinit var phone: EditText
    private lateinit var nik : EditText
    private lateinit var jurusan : EditText

    private lateinit var btnSave: Button

    private lateinit var database: AppDatabase // Objek untuk mengakses database

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editor) // Mengatur tata letak tampilan dari file XML activity_editor

        // Inisialisasi elemen UI
        fullName = findViewById(R.id.et_name)
        nik = findViewById(R.id.et_nik)
        jurusan = findViewById(R.id.et_jurusan)
        alamat = findViewById(R.id.et_alamat)
        email = findViewById(R.id.et_email)
        phone = findViewById(R.id.et_phone)
        btnSave = findViewById(R.id.btn_save)

        database = AppDatabase.getInstance(applicationContext) // Inisialisasi database

        // Mengambil data intent jika ada
        val intent = intent.extras
        if (intent != null){
            // Jika intent tidak null, maka data akan diedit
            val id = intent.getInt("id", 0)
            var user = database.userDao().get(id)
            // Menampilkan data ke dalam form
            fullName.setText(user.fullName)
            nik.setText(user.nik)
            jurusan.setText(user.jurusan)
            alamat.setText(user.alamat)
            email.setText(user.email)
            phone.setText(user.phone)
        }

        // Event listener untuk tombol Save
        btnSave.setOnClickListener {
            // Memeriksa apakah semua field sudah terisi
            if (fullName.text.isNotEmpty() && nik.text.isNotEmpty() && jurusan.text.isNotEmpty() ) {
                if (intent != null){
                    // Jika intent tidak null, maka ini adalah proses edit data
                    database.userDao().update(
                        User(
                            intent.getInt("id",0), // Mengambil id dari intent
                            fullName.text.toString(),
                            nik.text.toString(),
                            jurusan.text.toString(),
                            alamat.text.toString(),
                            email.text.toString(),
                            phone.text.toString(),
                        )
                    )
                } else {
                    // Jika intent null, maka ini adalah proses penambahan data
                    database.userDao().insertAll(
                        User(
                            null, // Karena ini proses penambahan, id akan di-generate oleh Room
                            fullName.text.toString(),
                            nik.text.toString(),
                            jurusan.text.toString(),
                            alamat.text.toString(),
                            email.text.toString(),
                            phone.text.toString(),
                        )
                    )
                }
                finish() // Menutup EditorActivity setelah operasi selesai
            } else {
                // Jika ada field yang kosong, tampilkan pesan toast
                Toast.makeText(applicationContext, "Mohon lengkapi semua data", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}
