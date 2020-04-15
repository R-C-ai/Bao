package com.hsiaoling.bao.data.source.local

import androidx.lifecycle.LiveData
import com.hsiaoling.bao.data.*
import com.hsiaoling.bao.data.source.BaoDataSource
import com.hsiaoling.bao.salesaomunt.UserType
import com.hsiaoling.bao.servicestatus.ServiceAction

object BaoLocalDataSource:BaoDataSource {

    override suspend fun getSalesmansResult(): Result<List<User>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun getLoginUserResult(salesId:String, salesName:String): Result<User> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun getMastersResult(): Result<List<User>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun addNewDayToMaster(service: Service): Result<Boolean> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun addNewSalesman(user: User): Result<User> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun addMasterService(service: Service): Result<Boolean> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

//    override suspend fun updateStatusBySalesman(
//        service: Service,
//        serviceAction: ServiceAction
//    ): Result<Boolean> {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }

    override suspend fun updateStatus(
        service: Service,
        serviceAction: ServiceAction
    ): Result<Boolean> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    override suspend fun getAddServiceResult(date: String,masterId: String,serviceId:String): Result<Service> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun getServicesInMaster(): Result<List<Service>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun getDateResult(date: String,masterId:String): Result<List<Service>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getLiveRev(user: User,firstDay:Long,endDay:Long): LiveData<List<Service>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getLiveM(user: User, firstDay: Long, endDay: Long): LiveData<List<Service>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getLiveDateServices(date: String,masterId: String): LiveData<List<Service>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

//    override fun getLiveStatus(salesmanId:String): LiveData<List<Service>> {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }

//    override suspend fun removeBaoInMaster(baoService: BaoService) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }

    override fun getMonthLiveStatus(
        user: User,
        firstDay: Long,
        endDay: Long,
        completeHandler: (List<Service>) -> Unit
    ) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }




    override fun getMasterMonthLiveStatus(
        masterId: String,
        firstDay: Long,
        endDay: Long,
        completeHandler: (List<Service>) -> Unit
    ) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    override fun getSalesmanMonthLiveStatus(
        salesmanId: String,
        firstDay: Long,
        endDay: Long,
        completeHandler: (List<Service>) -> Unit
    ) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun deleteService(service: Service): Result<Boolean> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}