package com.finest.chatlibrary.view;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.finest.chatlibrary.R;
import com.finest.chatlibrary.menu.AuroraMenuBean;
import com.zhouwei.indicatorview.CircleIndicatorView;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 类的描述
 *
 * @author JinG
 * @date 2019/9/19 14:45
 * @project HGFinest
 */
public class PageMenuLayout extends LinearLayout {
    private ViewPager vpMenuPage;
    private CircleIndicatorView tlMenuPage;
    private List<AuroraMenuBean> data = new ArrayList<>();
    private FragmentPagerAdapter mFragmentAdapter;
    private int menuColNum = 4;
    private int menuLineNum = 2;

    public PageMenuLayout(Context context) {
        super(context);
        init(context);
    }

    public PageMenuLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PageMenuLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        inflate(context, R.layout.dispatch_layout_page_menu, this);

        vpMenuPage = findViewById(R.id.vp_page_menu);
        tlMenuPage = findViewById(R.id.indicator_view);
    }

    public void setData(List<AuroraMenuBean> data, FragmentManager fragmentManager) {
        this.data = data;

        List<Fragment> fragments = generateFragmentList();
        mFragmentAdapter = new FragmentPagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        };

        vpMenuPage.setAdapter(mFragmentAdapter);
        tlMenuPage.setUpWithViewPager(vpMenuPage);
    }

    private List<Fragment> generateFragmentList() {
        List<Fragment> fragments = new ArrayList<>();
        Map<Integer, List<AuroraMenuBean>> pageMap = generatePageMenuMap();

        Set<Integer> keySet = pageMap.keySet();

        if (keySet.size() > 0) {
            for (int i = 0; i < keySet.size(); i++) {
                MenuFragment menuFragment = new MenuFragment(pageMap.get(i));
                fragments.add(menuFragment);
            }
        }

        return fragments;
    }

    private Map<Integer, List<AuroraMenuBean>> generatePageMenuMap() {
        Map<Integer, List<AuroraMenuBean>> pageMap = new HashMap<>(16);
        if (data != null && !data.isEmpty()) {

            int pageTemp = data.size() % (menuColNum * menuLineNum);
            int pageSize = data.size() / (menuColNum * menuLineNum);

            if (pageTemp > 0) {
                pageSize += 1;
            }

            if (pageSize <= 1) {
                tlMenuPage.setVisibility(GONE);
            }

            for (int i = 0; i < pageSize; i++) {
                if (i == pageSize - 1) {
                    pageMap.put(i, data.subList(i * menuColNum * menuLineNum, data.size()));
                } else {
                    pageMap.put(i, data.subList(i * menuColNum * menuLineNum, 8 + i * menuColNum * menuLineNum));
                }
            }
        }

        return pageMap;
    }

    /**
     * 更新菜单名字
     *
     * @param menuName    旧名字
     * @param newMenuName 新名字
     */
    public void updateMenuByName(String menuName, String newMenuName) {
        if (mFragmentAdapter != null && mFragmentAdapter.getCount() > 0) {
            for (int i = 0; i < mFragmentAdapter.getCount(); i++) {
                MenuFragment menuFragment = (MenuFragment) mFragmentAdapter.getItem(i);

                List<AuroraMenuBean> menuBeans = menuFragment.menuBeans;

                if (menuBeans != null && !menuBeans.isEmpty()) {
                    for (AuroraMenuBean menu : menuBeans) {
                        if (menuName.equals(menu.getMenuName())) {
                            menu.setMenuName(newMenuName);
                            menuFragment.menuAdapter.notifyItemChanged(menuBeans.indexOf(menu));
                        }
                    }
                }
            }
        }
    }

    /**
     * 聊天界面菜单页（分多个菜单页展示菜单）
     *
     * @author JinG
     * @date 2019/9/19 15:24
     * @project HGFinest
     */
    public static class MenuFragment extends Fragment {
        private RecyclerView rvMenu;
        private MenuAdapter menuAdapter;
        public List<AuroraMenuBean> menuBeans;
        View inflaterView ;
        public MenuFragment(List<AuroraMenuBean> data) {
            this.menuBeans = data;
        }


        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            initView();
        }

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
           inflaterView = inflater.inflate(
                   R.layout.dispatch_item_menu, container,
                    false
            );
            return inflaterView;
        }

        protected void initView() {
            initRecyclerView();
        }
        public void setMenuAdapter(List<AuroraMenuBean> menuBeans){
            this.menuBeans = menuBeans;
            menuAdapter.notifyDataSetChanged();
        }
        private void initRecyclerView() {
            rvMenu = inflaterView.findViewById(R.id.rv_menu);
            GridLayoutManager manager = new GridLayoutManager(getActivity(), 4);
            rvMenu.setLayoutManager(manager);

            menuAdapter = new MenuAdapter(R.layout.dispatch_item_aurora_menu, menuBeans);
            menuAdapter.setOnItemClickListener((adapter, view, position) -> {
                if (menuBeans.get(position).getOnClickListener() != null) {
                    menuBeans.get(position).getOnClickListener().onClick(view);
                }
            });
            rvMenu.setAdapter(menuAdapter);
        }

    }

    public static class MenuAdapter extends BaseQuickAdapter<AuroraMenuBean, BaseViewHolder> {

        public MenuAdapter(int layoutResId, @Nullable List<AuroraMenuBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, AuroraMenuBean item) {
            if (item != null) {
                helper.setImageResource(R.id.iv_menu_icon, item.getMenuDrawable());
                helper.setText(R.id.tv_menu_name, item.getMenuName());
            }
        }
    }
}
