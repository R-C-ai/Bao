package com.hsiaoling.bao.data.source

import androidx.lifecycle.LiveData
import com.hsiaoling.bao.data.Master
import com.hsiaoling.bao.data.Result
import com.hsiaoling.bao.data.Salesman
import com.hsiaoling.bao.data.Service

class DefaultBaoRepository (private val baoRemoteDataSource:BaoDataSource,
                            private val baoLocalDataSource: BaoDataSource
//                            private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO

):BaoRepository {

    override suspend fun addNewDayToMaster(service: Service):Result<Boolean> {
       return baoRemoteDataSource.addNewDayToMaster(service)
    }

    override suspend fun getSalesmansResult():Result<List<Salesman>>{
        return baoRemoteDataSource.getSalesmansResult()
    }

    override suspend fun getMastersResult():Result<List<Master>>{
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

    override fun getLiveDateServices(date: String,masterId: String): LiveData<List<Service>> {
        return baoRemoteDataSource.getLiveDateServices(date,masterId)
    }

    override fun getLiveStatus(salesmanId:String): LiveData<List<Service>> {
        return baoRemoteDataSource.getLiveStatus(salesmanId)
    }

    override suspend fun updateService(service: Service): Result<Boolean> {
        return baoRemoteDataSource.updateService(service)
    }

//    override suspend fun removeBaoInMaster(baoService: BaoService) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
    
}



