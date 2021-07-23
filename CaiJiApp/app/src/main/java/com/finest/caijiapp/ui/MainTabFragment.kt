package com.finest.caijiapp.ui

import android.view.*
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.ToastUtils
import com.finest.caijiapp.R
import com.finest.caijiapp.databinding.FragmentMainTabBinding
import com.finest.comm_base.mvvm.BaseMVVMFragment
import com.finest.jetpack.ui.JetpackFragment
import com.finest.search.ui.SearchFragment
import com.finest.task.ui.TaskFragment


/**
 * 主Fragment管理fragment
 * Created by liangjiangze on 2020/05/08.
 */
class MainTabFragment : BaseMVVMFragment() {
    lateinit var  dataBinding: FragmentMainTabBinding
    private val mFragments = ArrayList<Fragment>(3)

    override fun initView() {
        mFragments.add(JetpackFragment())
        mFragments.add(TaskFragment())
        mFragments.add(SearchFragment())
        dataBinding = mViewDataBinding as FragmentMainTabBinding

        dataBinding.viewPager.adapter = object : FragmentStateAdapter(this) {
            override fun createFragment(position: Int): Fragment {
                return mFragments[position]
            }

            override fun getItemCount(): Int {
                return mFragments.size
            }
        }
        dataBinding.viewPager.isSaveEnabled = false
        dataBinding.navView.setOnNavigationItemSelectedListener { item ->
            val menu: Menu = dataBinding.navView.menu
            for (e in 0 until menu.size()) {
                val item1 = menu.getItem(e)
                if (item1 == item) {
                    dataBinding.viewPager.setCurrentItem(e, true)
                }
            }
            true
        }
        dataBinding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {

            override fun onPageSelected(position: Int) {
                val menu: Menu = dataBinding.navView.menu
                menu.getItem(position).isChecked = true
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        requireActivity().menuInflater.inflate(R.menu.menu_common_tool_bar, menu);

    }
    override fun onPrepareOptionsMenu(menu: Menu) {
        var  menuItem = menu!!.findItem(R.id.menu_save)
        when(dataBinding.viewPager.currentItem){
             0 ->{
                menuItem.title = "ject"
            }
            1 ->  menuItem.title = "task"
            2 ->  menuItem.title = "searc"
        }
        menuItem.isVisible = true
        super.onPrepareOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_save -> ToastUtils.showShort(item.title)
            else->{

            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun getLayoutResId(): Int {
        return R.layout.fragment_main_tab
    }

    override fun getActivityTitle(): String {
        return "MainTab"
    }


}

