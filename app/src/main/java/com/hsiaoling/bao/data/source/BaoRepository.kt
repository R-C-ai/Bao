package com.hsiaoling.bao.data.source

import androidx.lifecycle.LiveData
import com.hsiaoling.bao.data.*
import com.hsiaoling.bao.salesaomunt.UserType
import com.hsiaoling.bao.servicestatus.ServiceAction

interface BaoRepository {

    suspend fun getSalesmansResult():Result<List<User>>
    suspend fun getLoginSalesmansResult(salesId:String,salesName:String):Result<User?>
    suspend fun getMastersResult(): Result<List<User>>
    suspend fun getOneServiceResult(date: String,masterId: String,serviceId:String): Result<Service>


    suspend fun getServicesInMaster(): Result<List<Service>>
    suspend fun addNewDayToMaster(service: Service):Result<Boolean>
    suspend fun addNewSalesman(user: User):Result<User>
    suspend fun getDateResult(date: String,masterId:String): Result<List<Service>>
    fun getLiveRev(user: User,firstDay:Long,endDay:Long): LiveData<List<Service>>


    fun getLiveDateServices(date: String,masterId: String):LiveData<List<Service>>
    fun getMasterLiveStatus(masterId: String, completeHandler:(List<Service>) -> Unit)
    fun getSalesmanLiveStatus(salesmanId: String, completeHandler:(List<Service>) -> Unit)

//    fun getLiveStatus(salesmanId:String):LiveData<List<Service>>
    suspend fun updateService(service: Service):Result<Boolean>

    suspend fun deleteService(service: Service):Result<Boolean>

//    suspend fun updateStatusBySalesman(service: Service,serviceAction: ServiceAction):Result<Boolean>
    suspend fun updateStatus(service: Service,serviceAction: ServiceAction):Result<Boolean>

//    suspend fun removeBaoInMaster(baoService: BaoService)



}