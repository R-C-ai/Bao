package com.hsiaoling.bao.data.source

import androidx.lifecycle.LiveData
import com.hsiaoling.bao.data.BaoService
import com.hsiaoling.bao.data.Schedule
import com.hsiaoling.bao.data.Result
import com.hsiaoling.bao.data.Service

interface BaoRepository {


    suspend fun getServicesInMaster(): Result<List<Service>>
    suspend fun insertServiceInMaster(service: Service):Result<Boolean>
//    suspend fun removeBaoInMaster(baoService: BaoService)



}