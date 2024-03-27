package com.example.room.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.room.data.entity.User

// DAO untuk berinteraksi dengan entitas User dalam database
@Dao
interface UserDao {
    // Mendapatkan semua pengguna dari database, diurutkan berdasarkan nama
    @Query("SELECT * FROM user ORDER BY full_name ASC")
    fun getAll(): List<User>

    // Mencari pengguna berdasarkan nama lengkap
    @Query("SELECT * FROM user WHERE full_name LIKE '%' || :search || '%'")
    fun searchByName(search: String): List<User>

    // Memuat pengguna berdasarkan ID
    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<User>

    // Menyisipkan pengguna ke dalam database
    @Insert
    fun insertAll(vararg users: User)

    // Menghapus pengguna dari database
    @Delete
    fun delete(user: User)

    // Mendapatkan pengguna berdasarkan ID
    @Query("SELECT * FROM user WHERE uid = :uid")
    fun get(uid: Int): User

    // Memperbarui pengguna dalam database
    @Update
    fun update(user: User)
}
