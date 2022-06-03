package com.zhlw.component.mine.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zhlw.component.mine.R
import com.zhlw.component.mine.databinding.FragmentMineBinding
import com.zhlw.component.mine.ui.adapter.UserRepoAdapter
import com.zhlw.component.mine.viewmodel.MineFragmentViewModel
import com.zhlw.module.base.data.ResponseThrowable
import com.zhlw.module.base.ui.fragment.BaseFragment
import com.zhlw.module.base.utils.viewModels
import com.zhlw.module.common.utils.CCUtils
import dagger.hilt.android.AndroidEntryPoint


/**
 * A simple [Fragment] subclass.
 * Use the [MineFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class MineFragment : BaseFragment<FragmentMineBinding,MineFragmentViewModel>() {

    private val TAG = "MineFragment"

    private val mUserRepoAdapter by lazy { UserRepoAdapter(R.layout.item_mine_repository) }

    private var mErrorView : View ?= null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) =
        with(binding){
            lifecycleOwner = this@MineFragment
            data = this@MineFragment.viewModel
        }.also {
            Log.i(transactionTag,"MineFragment init start")

            binding.mineUnlogin.setOnClickListener {
                viewModel.startLogin()
            }

            val layoutManager = LinearLayoutManager(requireContext(),RecyclerView.VERTICAL,false)
            binding.rvMine.layoutManager = layoutManager
            binding.rvMine.adapter = mUserRepoAdapter
            mUserRepoAdapter.onAttachedToRecyclerView(binding.rvMine)

            viewModel.mUserRepoData.observe(this) {
                mUserRepoAdapter.data = it.toMutableList()
                mUserRepoAdapter.notifyItemRangeChanged(0, it.size)
                binding.rvMine.visibility = View.VISIBLE
            }

            viewModel.fetchData()
        }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment MineFragment.
         */
        @JvmStatic
        fun newInstance() = MineFragment()
    }

    override val viewModel : MineFragmentViewModel by viewModels()

    override val transactionTag : String = this::class.java.simpleName

    override val layoutRes : Int = R.layout.fragment_mine

    override fun showErrorImpl(state: ResponseThrowable) {
        Log.e(TAG,"showErrorImpl $state")
        if (viewModel.isUserLogin()){
            //未登录
            viewModel.setContainVisibility(View.GONE)
            binding.mineUnlogin.visibility = View.VISIBLE
        } else {
            //加载异常
            if (mErrorView == null) initErrorView()
            mErrorView?.visibility = View.VISIBLE
        }
    }

    private fun initErrorView(){
        mErrorView = binding.mineError.viewStub?.inflate()
        if (mErrorView != null){
            mErrorView?.setOnClickListener {
                viewModel.fetchData()
            }
        } else {
            Log.e(TAG,"initErrorView error")
        }
    }

    override fun showLoadingImpl(state: Boolean) {
        if (state){
            binding.rvMine.visibility = View.GONE
            mErrorView?.visibility = View.GONE
            binding.mineUnlogin.visibility = View.GONE
            binding.mineLoading.visibility = View.VISIBLE
        } else {
            binding.mineLoading.visibility = View.GONE
        }
    }

}