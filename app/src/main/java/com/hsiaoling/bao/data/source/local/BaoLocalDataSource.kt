package com.hsiaoling.bao.data.source.local

import androidx.lifecycle.LiveData
import com.hsiaoling.bao.data.Master
import com.hsiaoling.bao.data.Result
import com.hsiaoling.bao.data.Salesman
import com.hsiaoling.bao.data.Service
import com.hsiaoling.bao.data.source.BaoDataSource
import com.hsiaoling.bao.servicestatus.ServiceAction

object BaoLocalDataSource:BaoDataSource {

    override suspend fun getSalesmansResult(): Result<List<Salesman>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun getLoginSalesmansResult(salesId: String): Result<Salesman> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun getMastersResult(): Result<List<Master>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun addNewDayToMaster(service: Service): Result<Boolean> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun addNewSalesman(salesman: Salesman): Result<Salesman> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun updateService(service: Service): Result<Boolean> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun updateStatus(
        service: Service,
        serviceAction: ServiceAction
    ): Result<Boolean> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun getOneServiceResult(date: String,masterId: String,serviceId:String): Result<Service> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun getServicesInMaster(): Result<List<Service>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun getDateResult(date: String,masterId:String): Result<List<Service>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getLiveDateServices(date: String,masterId: String): LiveData<List<Service>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getLiveStatus(salesmanId:String): LiveData<List<Service>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

//    override suspend fun removeBaoInMaster(baoService: BaoService) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }

    override fun getLiveStatus(salesmanId: String, completeHandler: (List<Service>) -> Unit) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}