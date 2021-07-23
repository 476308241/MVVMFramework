package com.finest.chatlibrary.menu;

import android.view.View;

/**
 * use XhsEmotionsKeyboard(h.hps.//github.com/w446108264/XhsEmoticonsKeyboard)
 * author: sj
 */
public class AuroraMenuBean {
    private int menuDrawable;
    private String menuName;
    private View.OnClickListener onClickListener;

    public AuroraMenuBean() {
    }

    public AuroraMenuBean(int menuDrawable, String menuName) {
        this.menuDrawable = menuDrawable;
        this.menuName = menuName;
    }

    public AuroraMenuBean(int menuDrawable, String menuName, View.OnClickListener onClickListener) {
        this.menuDrawable = menuDrawable;
        this.menuName = menuName;
        this.onClickListener = onClickListener;
    }

    public int getMenuDrawable() {
        return menuDrawable;
    }

    public void setMenuDrawable(int menuDrawable) {
        this.menuDrawable = menuDrawable;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public View.OnClickListener getOnClickListener() {
        return onClickListener;
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
}