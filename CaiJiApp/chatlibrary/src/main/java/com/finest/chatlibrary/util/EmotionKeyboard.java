package com.finest.chatlibrary.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.SizeUtils;

import java.lang.ref.WeakReference;

import timber.log.Timber;

/**
 * 会话界面More菜单弹出、以及输入法软键盘管理工具类 _转自CSDN_LQR
 *
 * @author JinG
 * @date 2019/9/19 11:17
 * @project HGFinest
 */
public class EmotionKeyboard {
    private WeakReference<Activity> mActivity;
    /**
     * 软键盘管理类
     */
    private InputMethodManager mInputManager;
    /**
     * 表情布局
     */
    private WeakReference<View> mEmotionLayout;
    /**
     * 内容布局view,即除了表情布局或者软键盘布局以外的布局，用于固定bar的高度，防止跳闪
     */
    private WeakReference<View> mContentView;
    private SharedPrefChat sp;
    private WeakReference<EditText> mEditText;
    private OnEmotionButtonOnClickListener mOnEmotionButtonOnClickListener;
    private InputTouchListener inputTouchListener;

    private EmotionKeyboard(Activity activity) {
        this.mActivity = new WeakReference<>(activity);
        this.mInputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        sp = SharedPrefChat.getInstance(activity);
    }

    /**
     * 绑定内容view，此view用于固定bar的高度，防止跳闪
     */
    private void bindToContent(View contentView) {
        this.mContentView = new WeakReference<>(contentView);
    }

    /**
     * 绑定内容view，此view用于固定bar的高度，防止跳闪
     */
    private void bindToEmotionLayout(View emotionLayout) {
        this.mEmotionLayout = new WeakReference<>(emotionLayout);
    }

    /**
     * 绑定编辑框
     */
    @SuppressLint("ClickableViewAccessibility")
    private void bindToEditText(EditText editText) {
        mEditText = new WeakReference<>(editText);
        editText.clearFocus();
        editText.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP && (mEmotionLayout != null
                    && mEmotionLayout.get() != null && mEmotionLayout.get().isShown())) {
                lockContentHeight();//显示软件盘时，锁定内容高度，防止跳闪。
                hideEmotionLayout(true);//隐藏表情布局，显示软件盘

                if (mEditText != null && mEditText.get() != null) {
                    //软件盘显示后，释放内容高度
                    mEditText.get().postDelayed(this::unlockContentHeightDelayed, 200L);
                }
            }
            if (inputTouchListener != null && event.getAction() == MotionEvent.ACTION_UP) {
                if (mEditText != null && mEditText.get() != null) {
                    mEditText.get().requestFocus();
                }
                if (!isSoftInputShown()) {
                    showSoftInput();
                }
                inputTouchListener.onTouch();
            }
            return false;
        });

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                getSupportSoftInputHeight();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    /**
     * 绑定表情按钮（可以有多个表情按钮）
     *
     * @param emotionButton
     */
    private void bindToEmotionButton(View... emotionButton) {
        for (View view : emotionButton) {
            view.setOnClickListener(getOnEmotionButtonOnClickListener());
        }
    }

    private void setInputTouchListener(InputTouchListener inputTouchListener) {
        this.inputTouchListener = inputTouchListener;
    }

    private void setOnEmotionButtonOnClickListener(OnEmotionButtonOnClickListener onEmotionButtonOnClickListener) {
        mOnEmotionButtonOnClickListener = onEmotionButtonOnClickListener;
    }

    private View.OnClickListener getOnEmotionButtonOnClickListener() {
        return v -> {
            if (mOnEmotionButtonOnClickListener != null) {
                if (mOnEmotionButtonOnClickListener.onEmotionButtonOnClickListener(v)) {
                    return;
                }
            }

            if (mEmotionLayout != null && mEmotionLayout.get() != null && mEmotionLayout.get().isShown()) {
                lockContentHeight();//显示软件盘时，锁定内容高度，防止跳闪。
                hideEmotionLayout(true);//隐藏表情布局，显示软件盘
                unlockContentHeightDelayed();//软件盘显示后，释放内容高度
            } else {
                if (isSoftInputShown()) {//同上
                    lockContentHeight();
                    showEmotionLayout();
                    unlockContentHeightDelayed();
                } else {
                    showEmotionLayout();//两者都没显示，直接显示表情布局
                }
            }
        };
    }

    /**
     * 点击返回键时先隐藏表情布局
     *
     * @return
     */
    public boolean interceptBackPress() {
        if (mEmotionLayout != null && mEmotionLayout.get() != null && mEmotionLayout.get().isShown()) {
            hideEmotionLayout(false);
            return true;
        }
        return false;
    }

    private void showEmotionLayout() {
        int softInputHeight = getSupportSoftInputHeight();
        if (softInputHeight <= 0) {
            softInputHeight = getKeyBoardHeight();
        }
        hideSoftInput();
        if (mEmotionLayout != null && mEmotionLayout.get() != null) {
            mEmotionLayout.get().getLayoutParams().height = softInputHeight;
            Timber.i("Emotion：设置布局高度 [ %s ]", softInputHeight);
            mEmotionLayout.get().setVisibility(View.VISIBLE);
        }
    }
    /**
     * 获取软键盘高度
     *
     * @return
     */
    private int getKeyBoardHeight() {
        return sp.getInt(AppConstants.SHARE_PREFERENCE_SOFT_INPUT_HEIGHT, 835);
    }

    /**
     * 隐藏表情布局
     *
     * @param showSoftInput 是否显示软件盘
     */
    private void hideEmotionLayout(boolean showSoftInput) {
        if (mEmotionLayout != null && mEmotionLayout.get() != null && mEmotionLayout.get().isShown()) {
            mEmotionLayout.get().setVisibility(View.GONE);
            if (showSoftInput) {
                showSoftInput();
            }
        }
    }

    /**
     * 锁定内容高度，防止跳闪
     */
    private void lockContentHeight() {
        if (mContentView != null && mContentView.get() != null) {
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mContentView.get().getLayoutParams();
            params.height = mContentView.get().getHeight();
            params.weight = 0.0F;
        }
    }

    /**
     * 释放被锁定的内容高度
     */
    private void unlockContentHeightDelayed() {
        if (mEditText != null && mEditText.get() != null) {
            mEditText.get().postDelayed(() -> {
                if (mContentView != null && mContentView.get() != null) {
                    ((LinearLayout.LayoutParams) mContentView.get().getLayoutParams()).weight = 1.0F;
                }
            }, 200L);
        }
    }

    /**
     * 编辑框获取焦点，并显示软件盘
     */
    public void showSoftInput() {
        if (mEditText != null && mEditText.get() != null) {
            mEditText.get().requestFocus();
            mEditText.get().post(() ->
                    mInputManager.showSoftInput(mEditText.get(), 0)
            );
        }
    }

    /**
     * 隐藏软件盘
     */
    public void hideSoftInput() {
        if (mEditText != null && mEditText.get() != null) {
            mInputManager.hideSoftInputFromWindow(mEditText.get().getWindowToken(), 0);
        }
    }

    /**
     * 是否显示软件盘
     *
     * @return
     */
    public boolean isSoftInputShown() {
        return getSupportSoftInputHeight() > 0;
    }

    /**
     * 获取软件盘的高度
     *
     * @return
     */
    private int getSupportSoftInputHeight() {
        Rect r = new Rect();
        if (mActivity == null || mActivity.get() == null) {
            return sp.getInt(AppConstants.SHARE_PREFERENCE_SOFT_INPUT_HEIGHT, SizeUtils.dp2px(250));
        }
        /**
         * decorView是window中的最顶层view，可以从window中通过getDecorView获取到decorView。
         * 通过decorView获取到程序显示的区域，包括标题栏，但不包括状态栏。
         */
        mActivity.get().getWindow().getDecorView().getWindowVisibleDisplayFrame(r);

        Timber.i("Emotion：可视区域 [ %s ]", r.toShortString());
        //获取屏幕的高度
        int screenHeight = mActivity.get().getWindow().getDecorView().getRootView().getHeight();
        //计算软件盘的高度
        int heightDiff = screenHeight - (r.bottom - r.top);
        int realKeyboardHeight = heightDiff - getStatusBarHeight(mActivity.get()) - getNavBarHeight(mActivity.get());
        if (realKeyboardHeight < 0) {
            Log.w("LQR", "EmotionKeyboard--Warning: value of softInputHeight is below zero!");
        }
        //存一份到本地
        if (realKeyboardHeight > 0) {
            sp.putInt(AppConstants.SHARE_PREFERENCE_SOFT_INPUT_HEIGHT, realKeyboardHeight);
        }

        Timber.i("Emotion：键盘高度 [ %s ]", realKeyboardHeight);
        return realKeyboardHeight;
    }

    /**
     * 获取导航栏高度
     */
    public static int getNavBarHeight(Context context) {
        Resources res = context.getResources();
        int resourceId = res.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId != 0) {
            return res.getDimensionPixelSize(resourceId);
        } else {
            return 0;
        }
    }

    /**
     * 获取状态栏的高度
     *
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        int statusBarHeight = 0;
        try {
            int resId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (resId > 0) {
                statusBarHeight = context.getResources().getDimensionPixelSize(resId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusBarHeight;
    }

    /**
     * 获取软键盘高度
     *
     * @return
     */
//    private int getKeyBoardHeight() {
//        return sp.getInt(AppConstants.SHARE_PREFERENCE_SOFT_INPUT_HEIGHT, 835);
//    }

    /**
     * 表情类（包含加号等）按钮点击事件
     */
    public interface OnEmotionButtonOnClickListener {
        /**
         * 主要是为了适用仿微信的情况，微信有一个表情按钮和一个功能按钮，这2个按钮都是控制了底部区域的显隐
         *
         * @param view
         * @return true:拦截切换输入法，false:让输入法正常切换
         */
        boolean onEmotionButtonOnClickListener(View view);
    }

    /**
     * 输入框触摸事件
     */
    public interface InputTouchListener {
        void onTouch();
    }

    public static class Builder {
        private Activity mActivity;
        private View mContentView;
        private View mInputView;
        private View mEmotionLayout;
        private View[] mEmotionButton;
        private EditText mEditText;
        private OnEmotionButtonOnClickListener mEmotionButtonOnClickListener;
        private InputTouchListener mInputTouchListener;

        public Builder(Activity activity) {
            this.mActivity = activity;
        }

        /**
         * 绑定内容view，此view用于固定bar的高度，防止跳闪
         */
        public Builder bindToContent(View contentView) {
            this.mContentView = contentView;
            return this;
        }

        /**
         * 绑定内容view，此view用于固定bar的高度，防止跳闪
         */
        public Builder bindToInputView(View inputView) {
            this.mInputView = inputView;
            return this;
        }

        /**
         * 绑定编辑框
         */
        public Builder bindToEditText(EditText editText) {
            this.mEditText = editText;
            return this;
        }

        /**
         * 绑定表情布局
         *
         * @param emotionLayout
         * @return
         */
        public Builder bindToEmotionLayout(View emotionLayout) {
            this.mEmotionLayout = emotionLayout;
            return this;
        }

        /**
         * 绑定表情按钮（可以有多个表情按钮）
         *
         * @param emotionButton
         * @return
         */
        public Builder bindToEmotionButton(View... emotionButton) {
            this.mEmotionButton = emotionButton;
            return this;
        }

        /**
         * editText触摸事件
         */
        public Builder bindInputTouchListener(InputTouchListener inputTouchListener) {
            this.mInputTouchListener = inputTouchListener;
            return this;
        }

        /**
         * 加号等按钮点击事件
         */
        public Builder bindEmotionButtonOnClickListener(OnEmotionButtonOnClickListener emotionButtonOnClickListener) {
            this.mEmotionButtonOnClickListener = emotionButtonOnClickListener;
            return this;
        }

        public EmotionKeyboard build() {
            EmotionKeyboard emotionKeyboard = new EmotionKeyboard(this.mActivity);
            emotionKeyboard.bindToContent(this.mContentView);
            emotionKeyboard.bindToEditText(this.mEditText);
            emotionKeyboard.bindToEmotionLayout(this.mEmotionLayout);
            emotionKeyboard.bindToEmotionButton(this.mEmotionButton);
            emotionKeyboard.setInputTouchListener(this.mInputTouchListener);
            emotionKeyboard.setOnEmotionButtonOnClickListener(this.mEmotionButtonOnClickListener);

            //设置软件盘的模式：SOFT_INPUT_ADJUST_RESIZE  这个属性表示Activity的主窗口总是会被调整大小，从而保证软键盘显示空间。
            //从而方便我们计算软件盘的高度
            mActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN |
                    WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            //隐藏软件盘
            emotionKeyboard.hideSoftInput();
            return emotionKeyboard;
        }
    }
}
