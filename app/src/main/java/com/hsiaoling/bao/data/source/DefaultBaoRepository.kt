package com.hsiaoling.bao.data.source

import androidx.lifecycle.LiveData
import com.hsiaoling.bao.data.*
import com.hsiaoling.bao.salesaomunt.UserType
import com.hsiaoling.bao.servicestatus.ServiceAction

class DefaultBaoRepository (private val baoRemoteDataSource:BaoDataSource,
                            private val baoLocalDataSource: BaoDataSource
//                            private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO

):BaoRepository {

    override suspend fun addNewDayToMaster(service: Service):Result<Boolean> {
       return baoRemoteDataSource.addNewDayToMaster(service)
    }

    override suspend fun addNewSalesman(user: User): Result<User> {
        return baoRemoteDataSource.addNewSalesman(user)
    }

    override suspend fun getLoginSalesmansResult(salesId:String,salesName:String): Result<User?> {
        return baoRemoteDataSource.getLoginUsersResult(salesId,salesName)
    }

    override suspend fun getSalesmansResult():Result<List<User>>{
        return baoRemoteDataSource.getSalesmansResult()
    }

    override suspend fun getMastersResult():Result<List<User>>{
        return baoRemoteDataSource.getMastersResult()
    }

    override suspend fun getOneServiceResult(date: String,masterId: String,serviceId:String): Result<Service> {
        return baoRemoteDataSource.getOneServiceResult(date,masterId,serviceId)

    }

    override suspend fun getServicesInMaster():Result<List<Service>>{
        return baoRemoteDataSource.getServicesInMaster()
    }

    override suspend fun getDateResult(date: String,masterId:String): Result<List<Service>> {
        return baoRemoteDataSource.getDateResult(date,masterId)
    }

    override fun getLiveRev(user: User,firstDay:Long,endDay:Long): LiveData<List<Service>> {
        return baoRemoteDataSource.getLiveRev(user,firstDay,endDay)
    }

    override fun getLiveDateServices(date: String,masterId: String): LiveData<List<Service>> {
        return baoRemoteDataSource.getLiveDateServices(date,masterId)
    }

//    override fun getLiveStatus(salesmanId:String): LiveData<List<Service>> {
//        return baoRemoteDataSource.getLiveStatus(salesmanId)
//    }

    override suspend fun updateService(service: Service): Result<Boolean> {
        return baoRemoteDataSource.updateService(service)
    }

    override suspend fun deleteService(service: Service): Result<Boolean> {
        return baoRemoteDataSource.deleteService(service)
    }

//    override suspend fun updateStatusBySalesman(service: Service,serviceAction: ServiceAction): Result<Boolean> {
//        return baoRemoteDataSource.updateStatusBySalesman(service,serviceAction)
//    }

    override suspend fun updateStatus(service: Service,serviceAction: ServiceAction): Result<Boolean> {
        return baoRemoteDataSource.updateStatus(service,serviceAction)
    }



//    override suspend fun removeBaoInMaster(baoService: BaoService) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }

    override fun getMasterLiveStatus(masterId: String, completeHandler: (List<Service>) -> Unit) {
        return baoRemoteDataSource.getMasterLiveStatus(masterId, completeHandler)
    }

    override fun getSalesmanLiveStatus(
        salesmanId: String,
        completeHandler: (List<Service>) -> Unit
    ) {
        return baoRemoteDataSource.getSalesmanLiveStatus(salesmanId,completeHandler)
    }

}



