package com.example.room.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// Mendefinisikan entitas dalam database Room
@Entity
data class User (
    // Mendefinisikan primary key dengan opsi auto-generate
    @PrimaryKey(autoGenerate = true) var uid: Int? = null,

    @ColumnInfo(name = "full_name") var fullName: String?,
    @ColumnInfo(name = "nik") var nik: String?,
    @ColumnInfo(name = "jurusan") var jurusan: String?,
    @ColumnInfo(name = "alamat") var alamat: String?,
    @ColumnInfo(name = "email") var email: String?,
    @ColumnInfo(name = "phone") var phone: String?
)
