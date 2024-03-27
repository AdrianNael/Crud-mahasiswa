package com.example.room.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.room.data.entity.User
import com.example.room.R;

// Kelas adapter untuk RecyclerView yang menampilkan daftar pengguna
class UserAdapter(var list: List<User>) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    private lateinit var dialog: Dialog

    // Metode untuk mengatur pendengar dialog untuk klik item
    fun setDialog(dialog: Dialog) {
        this.dialog = dialog
    }

    // Antarmuka yang mendefinisikan metode untuk menangani klik item
    interface Dialog {
        fun onClick(position: Int)
    }

    // Kelas ViewHolder untuk menyimpan tampilan untuk setiap item di RecyclerView
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var fullName: TextView
        var alamat: TextView
        var email: TextView
        var phone: TextView
        var nik : TextView
        var jurusan : TextView

        // Konstruktor yang menginisialisasi tampilan dan menetapkan pendengar klik
        init {
            fullName = view.findViewById(R.id.full_name)
            nik = view.findViewById(R.id.nik)
            jurusan = view.findViewById(R.id.jurusan)
            alamat = view.findViewById(R.id.alamat_rumah)
            email = view.findViewById(R.id.email)
            phone = view.findViewById(R.id.phone)
            // Menetapkan pendengar klik ke itemView
            view.setOnClickListener {
                dialog.onClick(layoutPosition)
            }
        }
    }

    // Metode untuk membuat instance ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_user, parent, false)
        return ViewHolder(view)
    }

    // Metode untuk mendapatkan jumlah item dalam daftar
    override fun getItemCount(): Int {
        return list.size
    }

    // Metode untuk memasangkan data ke tampilan setiap item dalam daftar
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.fullName.text = list[position].fullName
        holder.nik.text = list[position].nik
        holder.jurusan.text = list[position].jurusan
        holder.alamat.text = list[position].alamat
        holder.email.text = list[position].email
        holder.phone.text = list[position].phone
    }
}
