package com.hsiaoling.bao.data.source

import androidx.lifecycle.LiveData
import com.hsiaoling.bao.data.Master
import com.hsiaoling.bao.data.Result
import com.hsiaoling.bao.data.Service

interface BaoDataSource {

    suspend fun getMastersResult():Result<List<Master>>

    suspend fun addNewDayToMaster(service: Service):Result<Boolean>

    suspend fun getServicesInMaster():Result<List<Service>>

    suspend fun getOneServiceResult(date: String,masterId: String,serviceId:String):Result<Service>


    suspend fun getDateResult(date: String,masterId:String):Result<List<Service>>

    fun getLiveDateServices(date: String,masterId: String):LiveData<List<Service>>

    suspend fun updateService(service: Service):Result<Boolean>
//    suspend fun removeBaoInMaster(baoService: BaoService)
}