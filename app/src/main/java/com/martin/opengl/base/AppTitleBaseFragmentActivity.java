package com.martin.opengl.base;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.LinearLayout;

import com.martin.opengl.R;

import static android.support.v4.app.FragmentManager.POP_BACK_STACK_INCLUSIVE;

/**
 * 包含标题栏的Fragment
 */

public abstract class AppTitleBaseFragmentActivity extends FragmentActivity implements AppTitleBar.TitleBarListener {


    public abstract
    @LayoutRes
    int getLayoutId();


    public abstract String getTitleStr();

    protected abstract void initViewAndData(Bundle savedInstanceState);


    protected FragmentActivity mParent;
    protected AppTitleBar headerView;

    protected boolean showTitle() {
        return true;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mParent = this;
        if (showTitle()) {
            setContentView(addHeadView());
        } else {
            setContentView(getLayoutId());
        }
        initViewAndData(savedInstanceState);
    }

    private View addHeadView() {
        View contentView = getLayoutInflater().inflate(getLayoutId(), null);
        LinearLayout linearLayout = new LinearLayout(mParent);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        headerView = new AppTitleBar(mParent);
        linearLayout.addView(headerView);
        linearLayout.addView(contentView);
        linearLayout.setBackgroundColor(ContextCompat.getColor(mParent, R.color.app_back));
        headerView.setTitleText(getTitleStr())
                .setTitleBarListener(this);
        return linearLayout;
    }

    @Override
    public void leftClick() {
        popuBack();
    }

    @Override
    public void rightClick() {

    }

    protected AppTitleBar getTitleBar() {
        return headerView;
    }

    public void popuBack() {

        if (mParent.getSupportFragmentManager().getBackStackEntryCount() <= 1) {
            mParent.finish();
            return;
        }

        mParent.getSupportFragmentManager().popBackStackImmediate();
    }

    public void onBackPressed(String name) {
        mParent.getSupportFragmentManager().popBackStackImmediate(name, POP_BACK_STACK_INCLUSIVE);
    }

    public void backAndFinsh() {
        mParent.finish();
    }

    public void hideAndShow(@IdRes int contentId, Fragment hideFragment, Fragment showFragment) {
        hideAndShow(contentId, hideFragment, showFragment, true);
    }

    public void hideAndShow(@IdRes int contentId, Fragment hideFragment, Fragment showFragment, boolean addBackStack) {
        FragmentTransaction transaction = mParent.getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.fragment_slide_right_enter, R.anim.null_anim, R.anim.null_anim, R.anim.fragment_slide_right_exit)
                .add(contentId, showFragment, showFragment.getClass().getSimpleName())
                .hide(hideFragment);
        if (addBackStack) {
            transaction.addToBackStack(showFragment.getClass().getSimpleName());
        }
        transaction.commitAllowingStateLoss();
    }
}
