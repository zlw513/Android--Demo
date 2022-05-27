package com.zhlw.component.explore.ui.fragment

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.zhlw.component.explore.R
import com.zhlw.component.explore.databinding.FragmentExploreBinding
import com.zhlw.component.explore.ui.ExploreHeaderView
import com.zhlw.component.explore.ui.ExploreTypePopupWindow
import com.zhlw.component.explore.ui.TrendingHeaderView
import com.zhlw.component.explore.ui.adapter.ExploreAdapter
import com.zhlw.component.explore.ui.adapter.TrendingAdapter
import com.zhlw.component.explore.util.ExplorePopupWindow
import com.zhlw.component.explore.viewmodel.ExploreFragmentViewModel
import com.zhlw.module.base.data.ResponseThrowable
import com.zhlw.module.base.ui.fragment.BaseFragment
import com.zhlw.module.base.utils.viewModels
import com.zhlw.module.common.utils.CCUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExploreFragment : BaseFragment<FragmentExploreBinding,ExploreFragmentViewModel>() {

    private val TAG = "ExploreFragment"
    private val mTrendingAdapter by lazy { TrendingAdapter(R.layout.item_trending) }
    private val mExploreAdapter by lazy { ExploreAdapter(R.layout.item_explore) }
    private var mErrorView : View ?= null
    private var mExplorePopupWindow : ExploreTypePopupWindow ?= null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding){
        data = this@ExploreFragment.viewModel
        lifecycleOwner = this@ExploreFragment
    }.also {
        Log.i(transactionTag,"ExploreFragment init start")
        if (TextUtils.isEmpty(viewModel.getUserName())){
            binding.llTips.visibility = View.VISIBLE
            binding.llTips.setOnClickListener {
                CCUtils.startLoginActivity(requireContext())
            }
        } else {

            binding.headerTrending.setOnItemClickListener(object : TrendingHeaderView.OnItemClickListener{
                override fun onHeaderItemClicked(dateRange: String) {
                    viewModel.setDataRange(dateRange)
                }
            })

            binding.fabExplore.setOnClickListener {
                //show popup window
                if (binding.exploreLoadingView.visibility == View.VISIBLE){
                    Toast.makeText(requireContext(),"加载中，请稍后",Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                if (mExplorePopupWindow == null) mExplorePopupWindow = ExplorePopupWindow.getExplorePopupWindow(requireContext()){ type ->
                    binding.rvExplore.adapter = if(SHOW_TYPE_EXPLORE == type) mExploreAdapter else mTrendingAdapter
                    if (SHOW_TYPE_EXPLORE == type) mExploreAdapter.onAttachedToRecyclerView(binding.rvExplore) else mTrendingAdapter.onAttachedToRecyclerView(binding.rvExplore)
                    viewModel.fetchData(type)
                }
                mExplorePopupWindow!!.showPopupWindow()
            }

            val manager = LinearLayoutManager(requireContext(),VERTICAL,false)
            binding.rvExplore.layoutManager = manager
            binding.rvExplore.adapter = mTrendingAdapter
            mTrendingAdapter.onAttachedToRecyclerView(binding.rvExplore)

            mExploreAdapter.addHeaderView(ExploreHeaderView(requireContext()))

            viewModel.mTrendingDataResult.observe(this){
                mTrendingAdapter.setNewInstance(it)
                mTrendingAdapter.notifyItemRangeChanged(0,it.size)
                binding.rvExplore.visibility = View.VISIBLE
                binding.headerTrending.visibility = View.VISIBLE
            }

            viewModel.mExploreDataResult.observe(this){
                mExploreAdapter.setNewInstance(it)
                mExploreAdapter.notifyItemRangeChanged(0,it.size)
                binding.rvExplore.visibility = View.VISIBLE
                binding.headerTrending.visibility = View.GONE
            }

            viewModel.fetchData(viewModel.getFetchType())
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = ExploreFragment()

        val SHOW_TYPE_EXPLORE = "explore"
        val SHOW_TYPE_TRENDING = "trending"
    }

    override val viewModel: ExploreFragmentViewModel by viewModels()

    override val transactionTag: String = ExploreFragment::class.java.simpleName

    override val layoutRes: Int = R.layout.fragment_explore

    override fun showErrorImpl(state: ResponseThrowable) {
        if (mErrorView == null) initErrorView()
        mErrorView?.visibility = View.VISIBLE
    }

    private fun initErrorView(){
        mErrorView = binding.exploreError.viewStub?.inflate()
        if (mErrorView != null){
            mErrorView?.setOnClickListener {
                viewModel.fetchData(viewModel.getFetchType())
            }
        } else {
            Log.e(TAG,"initErrorView error")
        }
    }

    override fun showLoadingImpl(state: Boolean) {
        if (state){
            binding.rvExplore.visibility = View.GONE
            binding.headerTrending.visibility = View.GONE
            mErrorView?.visibility = View.GONE
            binding.exploreLoadingView.visibility = View.VISIBLE
        } else {
            binding.exploreLoadingView.visibility = View.GONE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mExplorePopupWindow?.dismiss()
        mExplorePopupWindow = null
    }

}