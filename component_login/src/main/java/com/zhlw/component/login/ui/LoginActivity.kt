package com.zhlw.component.login.ui

import androidx.lifecycle.lifecycleScope
import com.zhlw.component.login.R
import com.zhlw.component.login.databinding.ActivityLoginBinding
import com.zhlw.component.login.viewmodel.LoginActivityViewModel
import com.zhlw.module.base.data.ResponseThrowable
import com.zhlw.module.base.ui.activity.BaseActivity
import com.zhlw.module.base.utils.viewModels
import com.zhlw.module.common.constant.RouteConstant
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding,LoginActivityViewModel>() {

    private var callId : String = ""

    private var isLoginSuccess = false

    override val layoutRes : Int = R.layout.activity_login

    override val viewModel : LoginActivityViewModel by viewModels()

    override fun initEvent() {
        callId = intent.getStringExtra(RouteConstant.PARAM_LOGIN_ACTIVITY) ?: ""
        binding.loginBtn.setOnClickListener {
            val userName = binding.edUsername.text
            viewModel.login(this,userName)
        }
    }

    override fun initData() {
        viewModel.loginEvent.observe(this){
            if (it == viewModel.loadSuccessCode){
                binding.loginBtn.loadingSuccessful()
                isLoginSuccess = true
                lifecycleScope.launchWhenResumed {
                    delay(640)
                    finishAndRemoveTask()
                }
            } else if (it == viewModel.loadStartCode){
                binding.loginBtn.startLoading()
            } else {
                binding.loginBtn.loadingFailed()
            }
        }
    }

    override fun showErrorImpl(state: ResponseThrowable) {

    }

    override fun showLoadingImpl(state: Boolean) {

    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.sendLoginResult(callId,isLoginSuccess)
    }

}