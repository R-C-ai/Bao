package com.hsiaoling.bao.data.source.remote

import android.icu.util.Calendar
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.hsiaoling.bao.BaoApplication
import com.hsiaoling.bao.R
import com.hsiaoling.bao.data.Master
import com.hsiaoling.bao.data.source.BaoDataSource
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import com.hsiaoling.bao.data.Result
import com.hsiaoling.bao.data.Salesman
import com.hsiaoling.bao.data.Service
import com.hsiaoling.bao.servicestatus.ServiceAction
import com.hsiaoling.bao.util.Logger


object BaoRemoteDataSource:BaoDataSource {

    private const val PATH_STORE = "store"
    private const val PATH_SERVICE = "service"
    private const val PATH_MASTER = "master"
    private const val PATH_SALESMAN = "salesman"
    private const val KEY_DATE = "date"
    private const val PATH_SCHEDULESORT = "scheduleSort"
    private const val KEY_MASTERID = "masterId"
    private const val KEY_MASTER = "master"
    private const val KEY_SALESMANID = "salesmanId"
    private const val KEY_STATUS = "status"


    // get all exist salesman data
    override suspend fun getSalesmansResult(): Result<List<Salesman>> = suspendCoroutine { continuation->
        FirebaseFirestore.getInstance()
            .collection("store")
            .document("4d7yMjfPO5lw66u8sHnt")
            .collection(PATH_SALESMAN)
            .get()
            .addOnCompleteListener { task ->
                if(task.isSuccessful){
                    val list = mutableListOf<Salesman>()
                    for (document in task.result!!){

                        val salesman = document.toObject(Salesman::class.java)
                        Log.i("Hsiao","isSuccessGetSalesman=$list")
                        list.add(salesman)
                    }
                    continuation.resume(Result.Success(list))
                } else if (task.exception != null) {
                    task.exception?.let {
                        Log.i("Hsiao","exception")
                        continuation.resume(Result.Error(it))
                    }
                } else {
                    continuation.resume(Result.Fail(BaoApplication.instance.getString(R.string.you_know_nothing)))
                }

            }
    }

    // get all login salesman data
    override suspend fun getLoginSalesmansResult(salesId:String,salesName:String): Result<Salesman?> = suspendCoroutine { continuation->
        Log.i("Hsiao","getLoginSalesmansResult, salesId=$salesId,$salesName")
        FirebaseFirestore.getInstance()
            .collection("store")
            .document("4d7yMjfPO5lw66u8sHnt")
            .collection(PATH_SALESMAN)
            .whereEqualTo("id", salesId)
            .whereEqualTo("name",salesName)
            .get()
            .addOnCompleteListener { task ->
                if(task.isSuccessful){
                  val salesman = MutableLiveData<Salesman>()
                    Log.i("Hsiao","task.result!!=${task.result!!}, task.result!!.size=${task.result!!.size()}")

                    if (task.result!!.size() == 0) {

                        continuation.resume(Result.Success(null))
                    } else {

                        for (document in task.result!!){

                            val salesman = document.toObject(Salesman::class.java)
                            Log.i("Hsiao","isSuccessGetSalesman=$salesman")
                            continuation.resume(Result.Success(salesman))
                            break
                        }
                    }

                } else if (task.exception != null) {
                    task.exception?.let {
                        Log.i("Hsiao","exception")
                        continuation.resume(Result.Error(it))
                    }
                } else {
                    continuation.resume(Result.Fail(BaoApplication.instance.getString(R.string.you_know_nothing)))
                }

            }
    }


    // add newSalesman for Loginman
    override suspend fun addNewSalesman(salesman: Salesman): Result<Salesman> = suspendCoroutine { continuation ->
        val salesman = FirebaseFirestore.getInstance().collection("store").document("4d7yMjfPO5lw66u8sHnt").collection(
            PATH_SALESMAN).document()

            .set(mapOf(
                "id" to salesman.id,
                "name" to salesman.name
            ))

            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Logger.i("HsiaoAddNewSalesman: $salesman")

                    continuation.resume(Result.Success(salesman))
                } else {
                    task.exception?.let {

                        Logger.w("[${this::class.simpleName}] Error getting documents. ${it.message}")
                        continuation.resume(Result.Error(it))
                    }
                    continuation.resume(Result.Fail(BaoApplication.instance.getString(R.string.you_know_nothing)))
                }
            }
    }


         // get all exist  Master Data
    override suspend fun getMastersResult(): Result<List<Master>> = suspendCoroutine { continuation->
        FirebaseFirestore.getInstance()
            .collection("store")
            .document("4d7yMjfPO5lw66u8sHnt")
            .collection(PATH_MASTER)
            .get()
            .addOnCompleteListener { task ->
                if(task.isSuccessful){
                    val list = mutableListOf<Master>()
                    for (document in task.result!!){

                        val master = document.toObject(Master::class.java)
                        Log.i("HsiaoGetFirestoreMaster","isSuccessGetMaster=$list")
                        list.add(master)
                    }
                    continuation.resume(Result.Success(list))
                } else if (task.exception != null) {
                    task.exception?.let {
                        Log.i("master","exception")
                        continuation.resume(Result.Error(it))
                    }
                } else {
                    continuation.resume(Result.Fail(BaoApplication.instance.getString(R.string.you_know_nothing)))
                }

            }
    }

    // get exist data  when add by   addOnCompleteListener  , need to refresh to update and get data
    override suspend fun getDateResult(date: String, masterId: String): Result<List<Service>> = suspendCoroutine { continuation->
        FirebaseFirestore.getInstance()
            .collection("store")
            .document("4d7yMjfPO5lw66u8sHnt")
            .collection(PATH_SERVICE)
            .whereEqualTo(KEY_DATE, date)
            .whereEqualTo(KEY_MASTERID,masterId)
            .orderBy("scheduleSort",Query.Direction.ASCENDING)
//            .orderBy(PATH_SCHEDULESORT,Query.Direction.ASCENDING)
            .get()
            .addOnCompleteListener { task ->
                if(task.isSuccessful){
                    val list = mutableListOf<Service>()
                    for (document in task.result!!){

                        val service = document.toObject(Service::class.java)
                        Log.i("HsiaoGetDateResult","isSuccessDateResult=$list")
                        list.add(service)
                    }
                    continuation.resume(Result.Success(list))
                } else if (task.exception != null) {
                    task.exception?.let {
                        Log.i("selectedDate","exception")
                        continuation.resume(Result.Error(it))
                    }
                } else {
                    continuation.resume(Result.Fail(BaoApplication.instance.getString(R.string.you_know_nothing)))
                }

            }
    }
            // get updated services right after add service  by addSnapshotListener
    override fun getLiveDateServices(date: String, masterId: String): LiveData<List<Service>> {
        val liveData = MutableLiveData<List<Service>>()
        FirebaseFirestore.getInstance()
            .collection("store")
            .document("4d7yMjfPO5lw66u8sHnt")
            .collection(PATH_SERVICE)
            .whereEqualTo(KEY_DATE,date)
            .whereEqualTo(KEY_MASTERID,masterId)
            .orderBy("scheduleSort",Query.Direction.ASCENDING)
            .addSnapshotListener { snapshot, exception ->
                Logger.i("addSnapshotListener detect")
                exception?.let {
                    Logger.w("[] Error getting documents. ${it.message}")
                }
                val list = mutableListOf<Service>()
                for (document in snapshot!!) {
                    Logger.d(document.id + " => " + document.data)
                    val service = document.toObject(Service::class.java)
                    list.add(service)
                }
                liveData.value = list
            }
        return liveData
    }

    override fun getLiveStatus(salesmanId: String, completeHandler: (List<Service>) -> Unit) {
        Logger.w("getLiveStatus $salesmanId")

        FirebaseFirestore.getInstance()
            .collection("store")
            .document("4d7yMjfPO5lw66u8sHnt")
            .collection(PATH_SERVICE)
            .whereEqualTo(KEY_SALESMANID,salesmanId)
            .whereGreaterThan(KEY_STATUS,0)
            .orderBy(KEY_STATUS,Query.Direction.ASCENDING)
            .orderBy("updateTime",Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, exception ->
                Logger.i("addSnapshotListener detect")
                exception?.let {
                    Logger.w("[] Error getting documents. ${it.message}")
                }
                val list = mutableListOf<Service>()
                snapshot?.let {
                    for (document in snapshot   !!) {
                        Logger.d(document.id + " =============> " + document.data)
                        val service = document.toObject(Service::class.java)
                        list.add(service)
                    }
                    completeHandler(list)
                    Logger.d( " liveData.value =============> $list")
                }
            }
    }

    // get updated service which the statue >0,  intime by addSnapshotListener
    override fun getLiveStatus(salesmanId:String): LiveData<List<Service>> {
        val liveData = MutableLiveData<List<Service>>()

        Logger.w("getLiveStatus $salesmanId")

        FirebaseFirestore.getInstance()
            .collection("store")
            .document("4d7yMjfPO5lw66u8sHnt")
            .collection(PATH_SERVICE)
            .whereEqualTo(KEY_SALESMANID,salesmanId)
            .whereGreaterThan(KEY_STATUS,0)
            .orderBy(KEY_STATUS,Query.Direction.ASCENDING)
            .orderBy("updateTime",Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, exception ->


                Logger.i("addSnapshotListener detect")
                exception?.let {
                    Logger.w("[] Error getting documents. ${it.message}")
                }
                val list = mutableListOf<Service>()
                snapshot?.let {
                    for (document in snapshot   !!) {
                        Logger.d(document.id + " =============> " + document.data)
                        val service = document.toObject(Service::class.java)
                        list.add(service)
                    }
                    liveData.value = list
                    Logger.d( " liveData.value =============> $list")
                    Log.i("Hsiao","liveData.value =============> $list")
                }
            }
        return liveData
    }

    // if there is no service in a new day , creat reservations for the day
    override suspend fun addNewDayToMaster(service: Service): Result<Boolean> = suspendCoroutine { continuation ->
        val services = FirebaseFirestore.getInstance().collection("store").document("4d7yMjfPO5lw66u8sHnt").collection(
            PATH_SERVICE)
        val document = services.document()
        service.serviceId = document.id

        document
            .set(service)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Logger.i("HsiaoAddNew: $service")

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

   // make a new reserve for the exist empty schedule
    override suspend fun updateService(service: Service): Result<Boolean> = suspendCoroutine { continuation ->
        FirebaseFirestore.getInstance().collection("store").document("4d7yMjfPO5lw66u8sHnt").collection(
            PATH_SERVICE).document(service.serviceId)
            .update(mapOf(
                "status" to 1,
                "customerNo" to service.customerNo,
                "salesmanId" to service.salesmanId,
                "salesmanName" to service.salesmanName,
                "device" to service.device,
                "screen" to service.screen,
                "back" to service.back,
                "price" to service.price,
                "reserveTime" to Calendar.getInstance().timeInMillis,
                "updateTime" to Calendar.getInstance().timeInMillis
            ))
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Logger.i("HsiaoaddOnCompleteListener: $service")
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



    // change exist status from recerve to processing, done
    override suspend fun updateStatus(service: Service, action: ServiceAction): Result<Boolean> = suspendCoroutine { continuation ->

        FirebaseFirestore.getInstance().collection("store").document("4d7yMjfPO5lw66u8sHnt").collection(
            PATH_SERVICE).document(service.serviceId)
            .update(mapOf(
                "status" to action.value,
                "doneTime" to  Calendar.getInstance().timeInMillis,
                "updateTime" to Calendar.getInstance().timeInMillis
            ))
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Logger.i("HsiaoaddOnCompleteListener: $service")
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


    // get target serviceId exist servive updated data
    override suspend fun getOneServiceResult(date: String,masterId: String,serviceId:String): Result<Service> = suspendCoroutine { continuation->
        FirebaseFirestore.getInstance()
            .collection("store")
            .document("4d7yMjfPO5lw66u8sHnt")
            .collection(PATH_SERVICE)
            .whereEqualTo(KEY_DATE, date)
            .whereEqualTo("masterId",masterId)
            .whereEqualTo("serviceId",serviceId)
            .get()
            .addOnCompleteListener { task ->
                if(task.isSuccessful){
                    val service = MutableLiveData<Service>()
                    for (document in task.result!!){

                        val service = document.toObject(Service::class.java)
                        Log.i("HsiaoLingGetOneService","GetByServiveId=$service")
                        (service)
                    }
//                    continuation.resume(Result.Success())
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



    override suspend fun getServicesInMaster(): Result<List<Service>> = suspendCoroutine { continuation->
        FirebaseFirestore.getInstance()
            .collection("store")
            .document("4d7yMjfPO5lw66u8sHnt")
            .collection(PATH_SERVICE)

            .get()
            .addOnCompleteListener { task ->
                if(task.isSuccessful){
                    val list = mutableListOf<Service>()
                    for (document in task.result!!){

                        val service = document.toObject(Service::class.java)
                        Log.i("HsiaoLingGetSort","GetBySchedule=$list")
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



}