package com.example.convidados.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.convidados.service.guestrepository.constants.GuestConstant
import com.example.convidados.service.guestrepository.model.GuestModel
import com.example.convidados.service.guestrepository.repository.GuestRepository

class GuestsViewModel(application: Application) : AndroidViewModel(application) {

    val mGuestRepository = GuestRepository.getInstance(application.applicationContext)
    private val mGuestList = MutableLiveData<List<GuestModel>>()
    val guestList: LiveData<List<GuestModel>> = mGuestList

    fun load(filter: Int) {

        if (filter == GuestConstant.FILTER.EMPTY) {
            mGuestList.value = mGuestRepository.getAll()

        } else if (filter == GuestConstant.FILTER.PRESENT) {
            mGuestList.value = mGuestRepository.getPresent()

        } else {
            mGuestList.value = mGuestRepository.getAbsent()
        }


    }

    fun delete(id: Int) {
        mGuestRepository.delete(id)
    }
}