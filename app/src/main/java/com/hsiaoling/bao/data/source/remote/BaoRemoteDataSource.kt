package com.hsiaoling.bao.data.source.remote

import android.icu.util.Calendar
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.hsiaoling.bao.BaoApplication
import com.hsiaoling.bao.R
import com.hsiaoling.bao.data.BaoService
import com.hsiaoling.bao.data.Schedule
import com.hsiaoling.bao.data.source.BaoDataSource
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import com.hsiaoling.bao.data.Result
import com.hsiaoling.bao.data.Service
import com.hsiaoling.bao.util.Logger


object BaoRemoteDataSource:BaoDataSource {

    private const val PATH_SERVICES = "service"
    private const val KEY_DATE = "date"



    override suspend fun getServicesInMaster(): Result<List<Service>> = suspendCoroutine { continuation->

        Log.i("FirestoregetServicesInMaster","getServicesInMaster")
        FirebaseFirestore.getInstance()
            .collection("store")
                //TODO: CHANGE ID TO DYNAMIC
            .document("4d7yMjfPO5lw66u8sHnt")
            .collection(PATH_SERVICES)
            .orderBy(KEY_DATE,Query.Direction.DESCENDING)
            .get()
            .addOnCompleteListener { task ->
                if(task.isSuccessful){
                    val list = mutableListOf<Service>()
                    for (document in task.result!!){

                        val service = document.toObject(Service::class.java)
                        Log.i("FirestoregetServicesInMaster","isSuccessfulgetdata=$list")
                        list.add(service)
                    }
                    continuation.resume(Result.Success(list))
                } else if (task.exception != null) {
                    task.exception?.let {
                        Log.i("FirestoregetServicesInMaster","exception")
                        continuation.resume(Result.Error(it))
                    }
                } else {
                    continuation.resume(Result.Fail(BaoApplication.instance.getString(R.string.you_know_nothing)))
                }

            }
    }

    override suspend fun insertServiceInMastert(service: Service): Result<Boolean> = suspendCoroutine { continuation ->
        val services = FirebaseFirestore.getInstance().collection(PATH_SERVICES)
        val document = services.document()

        service.service_id = document.id
//        service.reserve_time = Calendar.getInstance().timeInMillis

        document
            .set(service)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Logger.i("add: $service")

                    continuation.resume(Result.Success(true))
                } else {
                    task.exception?.let {

                        Logger.w("[${this::class.simpleName}] Error getting documents. ${it.message}")
                        continuation.resume(Result.Error(it))
                    }
                    continuation.resume(Result.Fail(BaoApplication.instance.getString(R.string.you_know_nothing)))
                }
            }
    }


//    override suspend fun removeBaoInMaster(baoService: BaoService) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }

}