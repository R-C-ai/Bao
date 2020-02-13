package com.hsiaoling.bao.data.source

import com.hsiaoling.bao.data.Master
import com.hsiaoling.bao.data.Result
import com.hsiaoling.bao.data.Service

interface BaoDataSource {

    suspend fun getMastersResult():Result<List<Master>>

    suspend fun insertServiceInMastert(service: Service):Result<Boolean>

    suspend fun getServicesInMaster():Result<List<Service>>

    suspend fun getDateResult(date: String):Result<List<Service>>
//    suspend fun removeBaoInMaster(baoService: BaoService)
}