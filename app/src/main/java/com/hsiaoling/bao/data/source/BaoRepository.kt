package com.hsiaoling.bao.data.source

import androidx.lifecycle.LiveData
import com.hsiaoling.bao.data.Master
import com.hsiaoling.bao.data.Result
import com.hsiaoling.bao.data.Salesman
import com.hsiaoling.bao.data.Service

interface BaoRepository {

    suspend fun getSalesmansResult():Result<List<Salesman>>
    suspend fun getMastersResult(): Result<List<Master>>
    suspend fun getOneServiceResult(date: String,masterId: String,serviceId:String): Result<Service>

    suspend fun getServicesInMaster(): Result<List<Service>>
    suspend fun addNewDayToMaster(service: Service):Result<Boolean>
    suspend fun getDateResult(date: String,masterId:String): Result<List<Service>>
    fun getLiveDateServices(date: String,masterId: String):LiveData<List<Service>>

    fun getLiveStatus(salesmanId:String):LiveData<List<Service>>
    suspend fun updateService(service: Service):Result<Boolean>

//    suspend fun removeBaoInMaster(baoService: BaoService)



}