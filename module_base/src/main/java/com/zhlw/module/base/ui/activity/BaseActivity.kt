package com.zhlw.module.base.ui.activity

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.zhlw.module.base.data.ResponseThrowable
import com.zhlw.module.base.ui.fragment.BaseFragment
import com.zhlw.module.base.ui.fragment.BlankFragment
import com.zhlw.module.base.ui.viewmodel.BaseViewModel
import com.zhlw.module.base.utils.SystemBarUtils
import com.zhlw.module.base.utils.otherwise
import com.zhlw.module.base.utils.yes


abstract class BaseActivity<T : ViewDataBinding, VM : BaseViewModel> : AppCompatActivity() {

    lateinit var binding: T

    @get:LayoutRes
    abstract val layoutRes: Int

    abstract val viewModel: VM

    open val containId: Int = 0

    open val mNavigationBarColor = Color.parseColor("#e3e3e3")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSystemBarStyle()
        binding = DataBindingUtil.setContentView(this,layoutRes)
        registerEvent()
        initEvent()
        initData()
    }

    private fun registerEvent(){
        registerErrorEvent()
        registerLoadingEvent()
    }

    abstract fun initEvent()

    abstract fun initData()

    abstract fun showErrorImpl(state: ResponseThrowable)

    abstract fun showLoadingImpl(state: Boolean)

    fun getCurrentFragment(): BaseFragment<*, *> {
        if (supportFragmentManager.findFragmentById(containId) != null){
            return supportFragmentManager.findFragmentById(containId) as BaseFragment<*, *>
        } else {
            return BlankFragment()
        }
    }

    fun addFragment(fragment: BaseFragment<*, *>) =
        handleFragmentTransaction(
            fragment = fragment,
            action = ACTION_ADD,
            isAddToBackStack = true,
            isExecutePending = true
        )

    fun replaceFragment(fragment: BaseFragment<*, *>, addToBackStack: Boolean = true) =
        handleFragmentTransaction(
            fragment = fragment,
            action = ACTION_REPLACE,
            isAddToBackStack = addToBackStack,
            isExecutePending = true
        )

    fun addFragmentAndHideOthers(fragment: BaseFragment<*, *>) =
        handleFragmentTransaction(
            fragment = fragment,
            action = ACTION_ADD_AND_HIDE_OTHERS,
            isAddToBackStack = true,
            isExecutePending = true
        )

    private fun handleFragmentTransaction(fragment: BaseFragment<*, *>,
                                          action: Int,
                                          isAddToBackStack: Boolean,
                                          isExecutePending: Boolean) =
        with(supportFragmentManager) {
            beginTransaction().let {

                handleFragment(action, containId, fragment, supportFragmentManager, it, isAddToBackStack)
                it.commitAllowingStateLoss()

                if (isExecutePending) {
                    try {
                        executePendingTransactions()
                    } catch (thrwoable: Throwable) {
                        Log.e("BaseActivity","handleFragmentTransaction error $thrwoable")
                    }
                }
            }
        }

    private fun handleFragment(action: Int,
                               containId: Int,
                               fragment: BaseFragment<*, *>,
                               manager: FragmentManager,
                               transaction: FragmentTransaction,
                               isAddToBackStack: Boolean) =
        with(manager) {
            when (action) {
                ACTION_ADD -> transaction.add(containId, fragment,fragment.transactionTag)
                ACTION_REPLACE -> transaction.replace(containId, fragment,fragment.transactionTag)
                ACTION_ADD_AND_HIDE_OTHERS -> {
                    fragments
                        .filter { it.isVisible }
                        .forEach { transaction.hide(it) }
                    transaction.add(containId, fragment,fragment.transactionTag)
                }
                else -> Unit
            }
            if (isAddToBackStack) transaction.addToBackStack(fragment.transactionTag)
        }

    override fun onBackPressed() : Unit =
        with(supportFragmentManager) {
            (backStackEntryCount > getFragmentCountToFinish())
                .yes {
                    (findFragmentById(containId) as BaseFragment<*, *>).let {
                        popBackStackImmediate()
                    }
                }
                .otherwise { finish() }
        }

    private fun getFragmentCountToFinish() = 1

    /**
     * 设置 app 字体不随系统字体设置改变
     */
    override fun getResources(): Resources? {
        val config = Configuration()
        config.setToDefaults()
        val context: Context = createConfigurationContext(config)
        return context.resources
    }

    private fun registerErrorEvent() = viewModel.isShowError.observe(this@BaseActivity, { showErrorImpl(it) })
    private fun registerLoadingEvent() = viewModel.isShowLoading.observe(this@BaseActivity, { showLoadingImpl(it) })

    private companion object {
        const val ACTION_ADD = 0
        const val ACTION_REPLACE = 1
        const val ACTION_ADD_AND_HIDE_OTHERS = 2
    }

    private fun setSystemBarStyle() {
        try {
            SystemBarUtils.switchTransStatusBar(this, true)
            SystemBarUtils.setSystemBarWhite(this, false)
            SystemBarUtils.setNavigationBarBgColor(this, mNavigationBarColor)
        } catch (e: Exception) {
            Log.e("BaseActivity","setSystemBarStyle error ${e.message}")
        }
    }

}