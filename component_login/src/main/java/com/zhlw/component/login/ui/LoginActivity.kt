package com.zhlw.component.login.ui

import androidx.lifecycle.lifecycleScope
import com.zhlw.component.login.R
import com.zhlw.component.login.databinding.ActivityLoginBinding
import com.zhlw.component.login.viewmodel.LoginActivityViewModel
import com.zhlw.module.base.data.ResponseThrowable
import com.zhlw.module.base.ui.activity.BaseActivity
import com.zhlw.module.base.utils.viewModels
import com.zhlw.module.common.utils.CCUtils
import kotlinx.coroutines.delay

class LoginActivity : BaseActivity<ActivityLoginBinding,LoginActivityViewModel>() {

    override val layoutRes : Int = R.layout.activity_login

    override val viewModel : LoginActivityViewModel by viewModels()

    override fun initEvent() {
        binding.loginBtn.setOnClickListener {
            val userName = binding.edUsername.text
            viewModel.login(this,userName)
        }
    }

    override fun initData() {
        viewModel.loginEvent.observe(this){
            if (it == viewModel.loadSuccessCode){
                binding.loginBtn.loadingSuccessful()
                lifecycleScope.launchWhenResumed {
                    delay(640)
                    CCUtils.startMainActivity(this@LoginActivity)
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

}