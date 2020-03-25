package com.hsiaoling.bao.login

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hsiaoling.bao.BaoApplication
import com.hsiaoling.bao.data.Salesman
import com.hsiaoling.bao.data.User
import com.hsiaoling.bao.salesaomunt.UserType

/**
 * Created by Wayne Chen in Jul. 2019.
 */
object UserManager {

   var user: User? = null

   fun getUserType(): UserType {
      user?.let {
         return when (it.type) {
            "salesman" -> UserType.SALESMAN
            "master" -> UserType.MASTER
            else  -> UserType.SALESMAN
         }
      }
      return UserType.SALESMAN

   }

}

