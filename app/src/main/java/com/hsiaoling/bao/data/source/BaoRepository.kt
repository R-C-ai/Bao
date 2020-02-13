package com.hsiaoling.bao.data.source

import com.hsiaoling.bao.data.Result
import com.hsiaoling.bao.data.Service

interface BaoRepository {


    suspend fun getServicesInMaster(): Result<List<Service>>
    suspend fun insertServiceInMaster(service: Service):Result<Boolean>
    suspend fun getDateResult(date: String): Result<List<Service>>

//    suspend fun removeBaoInMaster(baoService: BaoService)



}