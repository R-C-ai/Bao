package com.hsiaoling.bao.data.source.local

import androidx.lifecycle.LiveData
import com.hsiaoling.bao.data.BaoService
import com.hsiaoling.bao.data.Result
import com.hsiaoling.bao.data.Schedule
import com.hsiaoling.bao.data.Service
import com.hsiaoling.bao.data.source.BaoDataSource

object BaoLocalDataSource:BaoDataSource {

    override suspend fun insertServiceInMastert(service: Service): Result<Boolean> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    override suspend fun getServicesInMaster(): Result<List<Service>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

//    override suspend fun removeBaoInMaster(baoService: BaoService) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
}