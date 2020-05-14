package com.anoma.crud

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_mahasiswa_list.view.*

class RVAdapterMahasiswa (private val context: Context, private val arrayList: ArrayList<Mahasiswa>) : RecyclerView.Adapter<RVAdapterMahasiswa.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(LayoutInflater.from(parent.context).inflate(R.layout.activity_mahasiswa_list,parent,false))
    }

    override fun getItemCount(): Int = arrayList!!.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.view.nimList.text = "NIM : "+arrayList?.get(position)?.nim
        holder.view.namaList.text = "Nama : "+arrayList?.get(position)?.nama
        holder.view.jurusanList.text = "Jurusan : "+arrayList?.get(position)?.jurusan
        holder.view.emailList.text = "Email : "+arrayList?.get(position)?.email

        holder.view.cvList.setOnClickListener {
            val i = Intent(context,ManageMahasiswaActivity::class.java)
            i.putExtra("editmode","1")
            i.putExtra("nim",arrayList?.get(position)?.nim)
            i.putExtra("nama",arrayList?.get(position)?.nama)
            i.putExtra("jurusan",arrayList?.get(position)?.jurusan)
            i.putExtra("email",arrayList?.get(position)?.email)
            context.startActivity(i)
        }
    }

    class Holder(val view: View) : RecyclerView.ViewHolder(view)

}
