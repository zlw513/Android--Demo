package com.zhlw.component.mine.viewmodel

import android.text.TextUtils
import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import com.zhlw.component.mine.repository.MineRepository
import com.zhlw.lib.data.mine.PublicUserInfo
import com.zhlw.lib.data.repository.RepositoryInfo
import com.zhlw.module.base.ui.viewmodel.BaseViewModel
import com.zhlw.module.base.ui.viewmodel.UIState
import com.zhlw.module.base.utils.PreferencesUtils
import com.zhlw.module.base.utils.SingleLiveEvent
import com.zhlw.module.common.constant.STORE_USERID_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MineFragmentViewModel @Inject constructor(
    val mineRepository: MineRepository
) : BaseViewModel() {

    private val TAG = "MineFragmentViewModel"

    private val _userInfo = SingleLiveEvent<PublicUserInfo>()

    /**
     * 用户仓库数据
     */
    val mUserRepoData = SingleLiveEvent<List<RepositoryInfo>>()

    /**
     * 数据展示↓,xml中用
     */
    val mUIUserInfo : LiveData<PublicUserInfo> = _userInfo

    /**
     * 主内容显示隐藏
     */
    val mUIContainVisibility = SingleLiveEvent(View.GONE)

    fun fetchData(){
        request(
            uiState = UIState(isShowLoadingView = true,isShowErrorView = true),
            block = {
                val username = mineRepository.getUserName()
                if (TextUtils.isEmpty(username)) {
                    throw NullPointerException("用户未登录")
                } else {

                    val userInfoDefer = async {
                        mineRepository.fetchUserInfo(username!!)
                    }

                    val userRepoDefer = async {
                        mineRepository.fetchUserRepository(username!!)
                    }

                    val userInfo = userInfoDefer.await()
                    val userRepo = userRepoDefer.await()

                    withContext(Dispatchers.Main){
                        _userInfo.value = userInfo
                        mUserRepoData.value = userRepo
                        setContainVisibility(View.VISIBLE)
                    }

                    storeUserId(userInfo.id)
                }
            },
            error = {
                Log.e(TAG,"getUserInfo error $it")
            }
        )
    }

    fun isUserLogin() : Boolean{
        return TextUtils.isEmpty(mineRepository.getUserName())
    }

    fun setContainVisibility(visible : Int){
        mUIContainVisibility.value = visible
    }

    private fun storeUserId(id : Long){
        mineRepository.storeUserId(id)
    }

}