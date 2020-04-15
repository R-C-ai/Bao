package com.hsiaoling.bao.data.source

import androidx.lifecycle.LiveData
import com.hsiaoling.bao.data.*
import com.hsiaoling.bao.salesaomunt.UserType
import com.hsiaoling.bao.servicestatus.ServiceAction

interface BaoDataSource {

    suspend fun  getSalesmansResult():Result<List<User>>

    suspend fun getLoginUserResult(usersId:String, usersName:String): Result<User?>

    suspend fun getMastersResult():Result<List<User>>

    suspend fun addNewDayToMaster(service: Service):Result<Boolean>

    suspend fun addNewSalesman(user: User):Result<User>

    suspend fun getServicesInMaster():Result<List<Service>>

    suspend fun getAddServiceResult(date: String,masterId: String,serviceId:String):Result<Service>


    suspend fun getDateResult(date: String,masterId:String):Result<List<Service>>

    fun getLiveRev(user: User,firstDay:Long,endDay:Long):LiveData<List<Service>>

    fun getLiveDateServices(date: String,masterId: String):LiveData<List<Service>>

//    fun getLiveStatus(salesmanId:String):LiveData<List<Service>>

    suspend fun addMasterService(service: Service):Result<Boolean>

    suspend fun deleteService(service: Service):Result<Boolean>

//    suspend fun updateStatusBySalesman(service: Service,serviceAction: ServiceAction):Result<Boolean>

    suspend fun updateStatus(service: Service,serviceAction: ServiceAction):Result<Boolean>

    fun getLiveM(user: User,firstDay:Long,endDay:Long):LiveData<List<Service>>

    fun getMonthLiveStatus(user: User,firstDay: Long,endDay: Long, completeHandler:(List<Service>) -> Unit)


    fun getMasterMonthLiveStatus(masterId: String,firstDay: Long,endDay: Long, completeHandler: (List<Service>) -> Unit)

    fun getSalesmanMonthLiveStatus(salesmanId: String,firstDay: Long,endDay: Long, completeHandler:(List<Service>) -> Unit)

}