package com.zhlw.zhlwcomponentdemo.viewmodel

import androidx.fragment.app.Fragment
import com.zhlw.module.base.ui.fragment.BaseFragment
import com.zhlw.module.base.ui.fragment.BlankFragment
import com.zhlw.module.base.ui.viewmodel.BaseViewModel
import com.zhlw.module.base.utils.SingleLiveEvent
import com.zhlw.module.common.constant.RouteConstant
import com.zhlw.module.common.ui.NormalTabEntity
import com.zhlw.module.common.utils.CCUtils
import com.zhlw.zhlwcomponentdemo.R

class MainActivityViewModel : BaseViewModel() {

    var mCurrentFragmentIndex = 0

    var mFragments: MutableList<BaseFragment<*, *>>? = null

    var mFragmentsName: MutableList<String>? = null

    var mTabsEntitys: MutableList<NormalTabEntity>? = null

    val mShowCurrentFragment : SingleLiveEvent<Int> = SingleLiveEvent(0)

    val mSetTabData : SingleLiveEvent<List<NormalTabEntity>> = SingleLiveEvent()

    val mTabInitFinish : SingleLiveEvent<List<Fragment>> = SingleLiveEvent()

    fun initData(){
        clearList()
        initTabLayoutData()
        setFragmentByIndex(mCurrentFragmentIndex,true)
        setTabs()
    }

    private fun initTabLayoutData(){
        mTabsEntitys = mutableListOf()
        mFragmentsName = mutableListOf()

        val exploreEntity = NormalTabEntity("探索",R.drawable.ic_explore_select,R.drawable.ic_explore_unselect)
        val mineEntity = NormalTabEntity("个人",R.drawable.ic_person_select, R.drawable.ic_person_unselect)

        mTabsEntitys?.let {
            it.add(exploreEntity)
            it.add(mineEntity)
        }

        //此处顺序要与上面一致
        mFragmentsName?.let {
            it.add(RouteConstant.KEY_EXPLOREFRAGMENT)
            it.add(RouteConstant.KEY_MINEFRAGMENT)
        }

    }

    fun setFragmentByIndex(currentIndex : Int, reset : Boolean){
        if (mFragments == null){
            mFragments = mutableListOf()

            //根据tab数量添加对应占位fragment
            for (name in mFragmentsName!!) {
                mFragments!!.add(BlankFragment())
            }
        }
        var index = currentIndex
        if (currentIndex >= mFragmentsName!!.size) {
            index = mFragmentsName!!.size - 1
        }
        val fragmentName = mFragmentsName!![index]
        var currentFragment = mFragments!![index]
        if (currentFragment is BlankFragment || reset){
            if (RouteConstant.KEY_EXPLOREFRAGMENT.equals(fragmentName)){
                currentFragment = CCUtils.getExploreFragment()
            }
            if (RouteConstant.KEY_MINEFRAGMENT.equals(fragmentName)){
                currentFragment = CCUtils.getMineFragment()
            }
            mFragments?.set(index,currentFragment)
        }
        mTabInitFinish.value = mFragments
    }

    private fun clearList() {
        mFragments?.clear()
        mFragments = null

        mFragmentsName?.clear()
        mFragmentsName = null

        mTabsEntitys?.clear()
        mTabsEntitys = null
    }

    fun setTabs(){
        mSetTabData.value = mTabsEntitys
    }

    fun showCurrentFragment(){
        mShowCurrentFragment.value = mCurrentFragmentIndex
    }

    fun getCurrentFragment(index: Int) = mFragments?.get(index)

}