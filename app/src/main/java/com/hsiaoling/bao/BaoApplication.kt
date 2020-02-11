package com.hsiaoling.bao

import android.app.Application
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.hsiaoling.bao.data.source.BaoRepository
import com.hsiaoling.bao.data.source.DefaultBaoRepository
import com.hsiaoling.bao.data.source.local.BaoLocalDataSource
import com.hsiaoling.bao.data.source.remote.BaoRemoteDataSource
import kotlin.properties.Delegates

class BaoApplication : Application() {


    val repository: BaoRepository = DefaultBaoRepository(BaoRemoteDataSource, BaoLocalDataSource)

    companion object {
        var instance: BaoApplication by Delegates.notNull()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

//        FirebaseFirestore.getInstance().firestoreSettings = FirebaseFirestoreSettings.Builder().setTimestampsInSnapshotsEnabled(true).build()

    }
}
