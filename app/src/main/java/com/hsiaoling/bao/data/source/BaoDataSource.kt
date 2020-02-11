package com.hsiaoling.bao.data.source

import androidx.lifecycle.LiveData
import com.hsiaoling.bao.data.BaoService
import com.hsiaoling.bao.data.Result
import com.hsiaoling.bao.data.Schedule
import com.hsiaoling.bao.data.Service

interface BaoDataSource {

    suspend fun insertServiceInMastert(service: Service):Result<Boolean>

    suspend fun getServicesInMaster():Result<List<Service>>

//    suspend fun removeBaoInMaster(baoService: BaoService)
}