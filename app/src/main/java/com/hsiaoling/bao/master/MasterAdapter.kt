package com.hsiaoling.bao.master

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.hsiaoling.bao.data.Master
import com.hsiaoling.bao.data.User
import com.hsiaoling.bao.master.dailyItem.MasterDailyItemFragment

class MasterAdapter (fragmentManager: FragmentManager,val masters:List<User>): FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getItem(position: Int): Fragment {
        return MasterDailyItemFragment(masters[position])
    }
    override fun getCount() = masters.size

    override fun getPageTitle(position: Int): CharSequence? {
        return masters[position].name
    }
}

