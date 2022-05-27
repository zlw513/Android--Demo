package com.zhlw.module.base.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.zhlw.module.base.data.ResponseThrowable
import com.zhlw.module.base.ui.viewmodel.BaseViewModel

abstract class BaseFragment<T : ViewDataBinding, VM : BaseViewModel> : Fragment() {

    lateinit var binding: T

    abstract val viewModel: VM

    abstract val transactionTag: String

    @get:LayoutRes
    abstract val layoutRes: Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        init()
        return DataBindingUtil.inflate<T>(inflater,layoutRes,container,false).also { binding = it }.root
    }

    private fun init(){
        registerErrorEvent()
        registerLoadingEvent()
    }

    abstract fun showErrorImpl(state: ResponseThrowable)

    abstract fun showLoadingImpl(state: Boolean)

    private fun registerErrorEvent() = viewModel.isShowError.observe(viewLifecycleOwner, { showErrorImpl(it) })
    private fun registerLoadingEvent() = viewModel.isShowLoading.observe(viewLifecycleOwner, { showLoadingImpl(it) })

}