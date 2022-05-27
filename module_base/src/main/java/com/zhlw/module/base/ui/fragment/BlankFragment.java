package com.zhlw.module.base.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.zhlw.module.base.data.ResponseThrowable;
import com.zhlw.module.base.ui.viewmodel.BaseViewModel;

import org.jetbrains.annotations.NotNull;

public class BlankFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return new View(getContext());
    }

    @NotNull
    @Override
    public BaseViewModel getViewModel() {
        return null;
    }

    @NotNull
    @Override
    public String getTransactionTag() {
        return "BlankFragment";
    }

    @Override
    public int getLayoutRes() {
        return 0;
    }

    @Override
    public void showErrorImpl(@NotNull ResponseThrowable state) {

    }

    @Override
    public void showLoadingImpl(boolean state) {

    }
}
