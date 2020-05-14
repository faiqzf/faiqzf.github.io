package com.anoma.crud

import android.app.ProgressDialog
import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.View
import android.widget.Toast
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import kotlinx.android.synthetic.main.activity_manage_mahasiswa.*
import org.json.JSONObject

class ManageMahasiswaActivity : AppCompatActivity() {
    lateinit var i: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_mahasiswa)

        i = intent

        if(i.hasExtra("editmode")){
            if(i.getStringExtra("editmode").equals("1")){
                onEditMode()
            }
        }

        btnCreate.setOnClickListener {
            create()
        }

        btnUpdate.setOnClickListener {
            update()
        }

        btnDelete.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Konfirmasi")
                .setMessage("Hapus data ini ?")
                .setPositiveButton("HAPUS", DialogInterface.OnClickListener { dialogInterface, i ->
                    delete()
                })
                .setNegativeButton("BATAL",DialogInterface.OnClickListener { dialogInterface, i ->
                    dialogInterface.dismiss()
                })
                .show()
        }

    }

    private fun onEditMode(){

        txNim.setText(i.getStringExtra("nim"))
        txNama.setText(i.getStringExtra("nama"))
        txJurusan.setText(i.getStringExtra("jurusan"))
        txEmail.setText(i.getStringExtra("email"))
        txNim.isEnabled = false

        btnCreate.visibility = View.GONE
        btnUpdate.visibility = View.VISIBLE
        btnDelete.visibility = View.VISIBLE

    }

    private fun create(){

        val loading = ProgressDialog(this)
        loading.setMessage("Menambahkan data...")
        loading.show()
        AndroidNetworking.post(ApiEndPoint.CREATE)
            .addBodyParameter("nim",txNim.text.toString())
            .addBodyParameter("nama",txNama.text.toString())
            .addBodyParameter("jurusan",txJurusan.text.toString())
            .addBodyParameter("email",txEmail.text.toString())
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {

                override fun onResponse(response: JSONObject?) {
                    loading.dismiss()
                    Toast.makeText(applicationContext,response?.getString("message"),Toast.LENGTH_SHORT).show()

                    if(response?.getString("message")?.contains("successfully")!!){
                        this@ManageMahasiswaActivity.finish()
                    }
                }

                override fun onError(anError: ANError?) {
                    loading.dismiss()
                    Log.d("ONERROR",anError?.errorDetail?.toString())
                    Toast.makeText(applicationContext,"Connection Failure",Toast.LENGTH_SHORT).show()
                }


            })
    }

    private fun update(){

        val loading = ProgressDialog(this)
        loading.setMessage("Mengubah data...")
        loading.show()

        AndroidNetworking.post(ApiEndPoint.UPDATE)
            .addBodyParameter("nim",txNim.text.toString())
            .addBodyParameter("nama",txNama.text.toString())
            .addBodyParameter("jurusan",txJurusan.text.toString())
            .addBodyParameter("email",txEmail.text.toString())
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {

                override fun onResponse(response: JSONObject?) {

                    loading.dismiss()
                    Toast.makeText(applicationContext,response?.getString("message"),Toast.LENGTH_SHORT).show()

                    if(response?.getString("message")?.contains("successfully")!!){
                        this@ManageMahasiswaActivity.finish()
                    }

                }

                override fun onError(anError: ANError?) {
                    loading.dismiss()
                    Log.d("ONERROR",anError?.errorDetail?.toString())
                    Toast.makeText(applicationContext,"Connection Failure", Toast.LENGTH_SHORT).show()                    }


            })

    }

    private fun delete(){

        val loading = ProgressDialog(this)
        loading.setMessage("Menghapus data...")
        loading.show()

        AndroidNetworking.get(ApiEndPoint.DELETE+"?nim="+txNim.text.toString())
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {

                override fun onResponse(response: JSONObject?) {

                    loading.dismiss()
                    Toast.makeText(applicationContext,response?.getString("message"),Toast.LENGTH_SHORT).show()

                    if(response?.getString("message")?.contains("successfully")!!){
                        this@ManageMahasiswaActivity.finish()
                    }

                }

                override fun onError(anError: ANError?) {
                    loading.dismiss()
                    Log.d("ONERROR",anError?.errorDetail?.toString())
                    Toast.makeText(applicationContext,"Connection Failure", Toast.LENGTH_SHORT).show()                    }


            })

    }
}
