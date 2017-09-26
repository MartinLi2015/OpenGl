package com.martin.opengl.base;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.martin.opengl.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */

public class BaseRefreshLazyFragment<T> extends AppBaseLazyLoadFragment implements OnRefreshLoadmoreListener {

    protected BaseQuickAdapter<T, BaseViewHolder> baseQuickAdapter;
    protected RecyclerView rc_list;


    public List<T> getList() {
        return mList;
    }

    protected List<T> mList;

    protected SmartRefreshLayout smart_refresh_layout;


    @Override
    public int getLayoutId() {
        return R.layout.fragment_smart_refresh_and_loadmore;
    }

    @NonNull
    @Override
    public String getTitle() {
        return "";
    }

    @Override
    protected void initViewAndData(View view) {
        mList = new ArrayList<>();

        smart_refresh_layout = (SmartRefreshLayout) view.findViewById(R.id.smart_refresh_layout);
        smart_refresh_layout.setOnRefreshLoadmoreListener(this);
        smart_refresh_layout.setEnableLoadmore(loadMoreEnabled());

        rc_list = (RecyclerView) view.findViewById(R.id.rv_list);
        LinearLayoutManager manager = new LinearLayoutManager(mParent);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rc_list.setLayoutManager(manager);
        baseQuickAdapter = initBaseQuickAdapter();
        rc_list.setAdapter(baseQuickAdapter);
    }

    protected BaseQuickAdapter<T,BaseViewHolder> initBaseQuickAdapter() {
        BaseQuickAdapter<T,BaseViewHolder>  baseQuickAdapter = new BaseQuickAdapter<T, BaseViewHolder>(getItemLayoutId(), mList) {
            @Override
            protected void convert(BaseViewHolder helper, T item) {
                customAdapterConvert(helper, item);
            }
        };
        return baseQuickAdapter;
    }

    public void endRefresh(boolean suc) {
        smart_refresh_layout.finishRefresh(suc);
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {

    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {

    }


    @Override
    public void fetchData() {
        smart_refresh_layout.autoRefresh();
    }

    public void customAdapterConvert(BaseViewHolder helper, T item) {

    }

    @LayoutRes
    public int getItemLayoutId() {
        return 0;
    }

    // 默认不支持加载更多
    public boolean loadMoreEnabled() {
        return false;
    }

}
