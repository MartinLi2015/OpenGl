package com.martin.opengl.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.martin.opengl.R;
import com.martin.opengl.airHockeyortho.AirHockeyActivityOrtho;
import com.martin.opengl.airhockey1.AirHockeyActivity;
import com.martin.opengl.airhockey2.AirHockeyActivity2;
import com.martin.opengl.base.AppTitleBaseFragmentActivity;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */

public class MainActivity extends AppTitleBaseFragmentActivity {

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public String getTitleStr() {
        return "OpenGl";
    }

    @Override
    protected void initViewAndData(Bundle savedInstanceState) {
        headerView.setLeftImageVisiable(false);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_main_list);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(new BaseQuickAdapter<MainActivityBean, BaseViewHolder>(R.layout.rv_main_list_item, getData()) {
            @Override
            protected void convert(BaseViewHolder helper, MainActivityBean item) {
                helper.setText(R.id.tv_list_title, item.title);
                helper.setText(R.id.tv_list_description, item.description);
            }
        });
        recyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                MainActivityBean item = (MainActivityBean) adapter.getItem(position);
                Intent intent = new Intent(MainActivity.this, item.clz);
                startActivity(intent);
            }
        });


    }

    private List<MainActivityBean> getData() {
        List<MainActivityBean> list = new ArrayList<>();
        MainActivityBean bean = new MainActivityBean();
        bean.title = "opengl 基础";
        bean.description = "主要讲述opengl基础图形的绘制以及坐标";
        bean.clz = AirHockeyActivity.class;
        list.add(bean);

        bean = new MainActivityBean();
        bean.title = "Adding Color and Shader";
        bean.description = "添加负责色值与阴影";
        bean.clz = AirHockeyActivity2.class;
        list.add(bean);
        bean = new MainActivityBean();
        bean.title = "Adjusting to the Screen's Aspect Ratio";
        bean.description = "调整屏幕比率";
        bean.clz = AirHockeyActivityOrtho.class;
        list.add(bean);

        return list;

    }

}
