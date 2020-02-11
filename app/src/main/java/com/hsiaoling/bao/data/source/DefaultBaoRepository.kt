package com.hsiaoling.bao.data.source

import androidx.lifecycle.LiveData
import com.hsiaoling.bao.data.BaoService
import com.hsiaoling.bao.data.Schedule
import com.hsiaoling.bao.data.source.local.BaoLocalDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import com.hsiaoling.bao.data.Result
import com.hsiaoling.bao.data.Service

class DefaultBaoRepository (private val baoRemoteDataSource:BaoDataSource,
                            private val baoLocalDataSource: BaoDataSource
//                            private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO

):BaoRepository {

    override suspend fun insertServiceInMaster(service: Service):Result<Boolean> {
       return baoRemoteDataSource.insertServiceInMastert(service)
    }

    override suspend fun getServicesInMaster():Result<List<Service>>{
        return baoRemoteDataSource.getServicesInMaster()
    }

//    override suspend fun removeBaoInMaster(baoService: BaoService) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
    
}



