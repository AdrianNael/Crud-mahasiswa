package com.example.room.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.room.data.dao.UserDao
import com.example.room.data.entity.User

@Database(entities = [User::class], version = 5)
abstract class AppDatabase : RoomDatabase() {

    // mendapatkan objek UserDao
    abstract fun userDao(): UserDao

    companion object {
        // Mendeklarasikan instance database sebagai variabel nullable
        private var instance: AppDatabase? = null

        // Metode untuk mendapatkan instance tunggal dari kelas AppDatabase
        fun getInstance(context: Context): AppDatabase {
            // Jika instance belum dibuat, maka buat instance baru
            if (instance == null) {
                // Membangun database menggunakan Room
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java, "app-database"
                )
                    .fallbackToDestructiveMigration() // Mengizinkan migrasi data yang merusak jika diperlukan
                    .allowMainThreadQueries() // Mengizinkan kueri dijalankan di thread utama (harus digunakan dengan hati-hati)
                    .build()
            }
            return instance!!
        }
    }
}
