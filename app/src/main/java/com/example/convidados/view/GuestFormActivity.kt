package com.example.convidados.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.convidados.viewmodel.GuestFormViewModel
import com.example.convidados.R
import com.example.convidados.service.guestrepository.constants.GuestConstant
import kotlinx.android.synthetic.main.activity_guest_form.*

class GuestFormActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mViewModel: GuestFormViewModel

    var mGuestId: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guest_form)
        mViewModel = ViewModelProvider(this).get(GuestFormViewModel::class.java)

        setListener()
        observe()
        loadData()
        radio_presence.isChecked = true
    }

    fun observe() {
        mViewModel.saveGuest.observe(this, Observer {
            if (it) {
                Toast.makeText(applicationContext, "Sucesso", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(applicationContext, "Falha", Toast.LENGTH_LONG).show()
            }
            finish()
        })
        mViewModel.guest.observe(this, Observer {
            edit_name.setText(it.name)
            if (it.presence){
                radio_presence.isChecked = true
            }else{
                radio_absent.isChecked = true
            }
        })
    }
    private fun loadData(){
        val bundle = intent.extras
        if (bundle != null){
            mGuestId = bundle.getInt(GuestConstant.GUESTID)
            mViewModel.load(mGuestId)
        }
    }

    override fun onClick(v: View) {
        val id = v.id
        if (id == button_save.id) {
            val name = edit_name.text.toString()
            val presence = radio_presence.isChecked
            mViewModel.save(mGuestId, name, presence)

        }
    }

    private fun setListener() {
        button_save.setOnClickListener(this)
    }

}
