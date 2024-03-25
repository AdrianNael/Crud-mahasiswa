package com.example.room.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User (
    @PrimaryKey(autoGenerate = true) var uid:Int? = null,
    @ColumnInfo(name = "full_name") var fullName: String?,
    @ColumnInfo(name = "jurusan") var jurusan: String?,
    @ColumnInfo(name = "alamat") var alamat: String?,
    @ColumnInfo(name = "email") var email:String?,
    @ColumnInfo(name = "phone") var phone:String?,
    @ColumnInfo(name = "nik") var nik: Int = generateNextNIK()

){
    companion object {
        private var lastGeneratedNIK = 20240001

        private fun generateNextNIK(): Int {
            lastGeneratedNIK++
            return lastGeneratedNIK
        }
    }
}
