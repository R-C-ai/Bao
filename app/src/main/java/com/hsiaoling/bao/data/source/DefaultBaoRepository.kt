package com.hsiaoling.bao.data.source

import com.hsiaoling.bao.data.Master
import com.hsiaoling.bao.data.Result
import com.hsiaoling.bao.data.Service

class DefaultBaoRepository (private val baoRemoteDataSource:BaoDataSource,
                            private val baoLocalDataSource: BaoDataSource
//                            private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO

):BaoRepository {

    override suspend fun insertServiceInMaster(service: Service):Result<Boolean> {
       return baoRemoteDataSource.insertServiceInMastert(service)
    }

    override suspend fun getMastersResult():Result<List<Master>>{
        return baoRemoteDataSource.getMastersResult()
    }


    override suspend fun getServicesInMaster():Result<List<Service>>{
        return baoRemoteDataSource.getServicesInMaster()
    }

    override suspend fun getDateResult(date: String): Result<List<Service>> {
        return baoRemoteDataSource.getDateResult(date)
    }

//    override suspend fun removeBaoInMaster(baoService: BaoService) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
    
}



