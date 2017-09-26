package com.martin.opengl.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.martin.opengl.R;

import org.greenrobot.eventbus.EventBus;

import me.yokeyword.swipebackfragment.SwipeBackFragment;

import static android.support.v4.app.FragmentManager.POP_BACK_STACK_INCLUSIVE;

/**
 * 包含标题栏的Fragment
 */

public abstract class AppTitleBaseFragment extends SwipeBackFragment implements AppTitleBar.TitleBarListener {


    private ProgressDialog mLoadingDialog;

    public abstract
    @LayoutRes
    int getLayoutId();

    public abstract
    @NonNull
    String getTitle();

    public boolean showTitle() {
        return true;
    }

    public boolean boolAttachSwipeBack() {
        return true;
    }

    protected abstract void initViewAndData(View rootView);


    protected FragmentActivity mParent;
    protected Context mApplicationContext;
    private AppTitleBar headerView;

    protected boolean useEventBus() {
        return false;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentActivity) {
            mParent = (FragmentActivity) context;
        }
        mApplicationContext = context.getApplicationContext();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        dismissLoadingDialog();
        if (useEventBus()) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contentView = inflater.inflate(getLayoutId(), container, false);
        initViewAndData(contentView);
        if (!showTitle()) {
            return boolAttachSwipeBack() ? attachToSwipeBack(contentView) : contentView;
        }
        LinearLayout linearLayout = new LinearLayout(mParent);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        headerView = new AppTitleBar(mParent);
        linearLayout.addView(headerView);
        linearLayout.addView(contentView);
        headerView.setTitleText(getTitle())
                .setTitleBarListener(this);
        if (useEventBus()) {
            EventBus.getDefault().register(this);
        }
        return boolAttachSwipeBack() ? attachToSwipeBack(linearLayout) : linearLayout;
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

        /*if (mParent.getSupportFragmentManager().getBackStackEntryCount() <= 1) {
            mParent.finish();
            return;
        }*/

        mParent.getSupportFragmentManager().popBackStackImmediate();
    }

    public void popuBack(String fragmentTag) {

        mParent.getSupportFragmentManager().popBackStackImmediate(fragmentTag, POP_BACK_STACK_INCLUSIVE);
    }

    public void onBackPressed(String name) {
        mParent.getSupportFragmentManager().popBackStackImmediate(name, POP_BACK_STACK_INCLUSIVE);
    }

    public void backAndFinsh() {
        mParent.finish();
    }

    public void addFragment(int contentId, Fragment fragment) {
        mParent.getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fragment_slide_right_enter, R.anim.null_anim, R.anim.null_anim, R.anim.fragment_slide_right_exit)
                .add(contentId, fragment, fragment.getClass().getSimpleName())
                .addToBackStack(fragment.getClass().getSimpleName())
                .commitAllowingStateLoss();
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


    protected void showLoadingDialog(String message, DialogInterface.OnCancelListener listener) {
        if (message == null) {
            message = "奋力加载中...";
        }

        if (mLoadingDialog == null) {
            mLoadingDialog = ProgressDialog.show(mParent, null, message);
            mLoadingDialog.setCancelable(true);
            mLoadingDialog.setCanceledOnTouchOutside(false);
            mLoadingDialog.setOnCancelListener(listener);
        }
        if (mLoadingDialog.isShowing()) {
            if (!TextUtils.isEmpty(message)) {
                mLoadingDialog.setMessage(message);
            }
            return;
        }
        mLoadingDialog.setMessage(message);
        mLoadingDialog.show();
    }

    protected void dismissLoadingDialog() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }
    }

}
