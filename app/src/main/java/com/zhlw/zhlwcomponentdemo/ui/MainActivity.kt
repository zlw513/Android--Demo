package com.zhlw.zhlwcomponentdemo.ui

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.flyco.tablayout.listener.OnTabSelectListener
import com.google.android.material.tabs.TabLayoutMediator
import com.zhlw.module.base.data.ResponseThrowable
import com.zhlw.module.base.ui.activity.BaseActivity
import com.zhlw.module.base.utils.viewModels
import com.zhlw.zhlwcomponentdemo.R
import com.zhlw.zhlwcomponentdemo.databinding.ActivityMainBinding
import com.zhlw.zhlwcomponentdemo.viewmodel.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding,MainActivityViewModel>() {

    private val TAG = "MainActivity"

    private var mFragmentPageAdapter : MainFragmentPageAdapter? = null

    private val OFFSCREEN_PAGE_LIMIT = 2

    override val layoutRes: Int = R.layout.activity_main

    override val viewModel : MainActivityViewModel by viewModels()

    override fun initEvent() {
        binding.navigationTablayout.setOnTabSelectListener(object : OnTabSelectListener {
            override fun onTabSelect(position: Int) {
                Log.i(TAG,"navigationTablayout-onTabSelect  $position")
                viewModel.setFragmentByIndex(position,false)
                viewModel.mCurrentFragmentIndex = position
                viewModel.showCurrentFragment()
            }

            override fun onTabReselect(position: Int) {

            }
        })

        binding.containViewpager.run {
            offscreenPageLimit = OFFSCREEN_PAGE_LIMIT
            addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener(){
                override fun onPageSelected(position: Int) {
                    if (position < 0) return
                    binding.navigationTablayout.currentTab = position
                    viewModel.setFragmentByIndex(position,false)
                    viewModel.mCurrentFragmentIndex = position
                    viewModel.showCurrentFragment()
                }
            })
        }

    }

    override fun initData() {
        viewModel.run {
            mTabInitFinish.observe(this@MainActivity,{
                if (mFragmentPageAdapter == null){
                    mFragmentPageAdapter = MainFragmentPageAdapter(supportFragmentManager,it)
                    binding.containViewpager.adapter = mFragmentPageAdapter
                } else {
                    mFragmentPageAdapter!!.mFragmentList = it
                    mFragmentPageAdapter!!.notifyDataSetChanged()
                }
            })

            mSetTabData.observe(this@MainActivity,{
                Log.i(TAG,"mSetTabData  $it")
                binding.navigationTablayout.setTabData(ArrayList(it))
            })

            mShowCurrentFragment.observe(this@MainActivity,{
                binding.containViewpager.setCurrentItem(it,false)
            })
        }
        viewModel.initData()
    }

    override fun showErrorImpl(state: ResponseThrowable) {

    }

    override fun showLoadingImpl(state: Boolean) {

    }

    class MainFragmentPageAdapter(fm: FragmentManager, fragmentList: List<Fragment>) : FragmentStatePagerAdapter(fm) {

        var mFragmentList: List<Fragment>? = fragmentList

        override fun getCount(): Int {
            return mFragmentList?.size ?: 0
        }

        override fun getItem(position: Int): Fragment {
            return mFragmentList!![position]
        }

        override fun getItemPosition(`object`: Any): Int {
            var index: Int = mFragmentList?.indexOf(`object`) ?: 0
            if (index < 0) {
                index = POSITION_NONE
            }
            return index
        }
    }

}