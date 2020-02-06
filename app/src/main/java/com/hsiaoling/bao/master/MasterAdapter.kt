package com.hsiaoling.bao.master

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.hsiaoling.bao.master.dailyItem.MasterDailyItemFragment

class MasterAdapter (fragmentManager: FragmentManager): FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getItem(position: Int): Fragment {
        return MasterDailyItemFragment(MasterTypeFliter.values()[position])
    }

    override fun getCount() = MasterTypeFliter.values().size

    override fun getPageTitle(position: Int): CharSequence? {
        return MasterTypeFliter.values()[position].value
    }
}