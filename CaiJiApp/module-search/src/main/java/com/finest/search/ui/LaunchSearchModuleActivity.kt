package com.finest.search.ui


import android.annotation.SuppressLint
import android.view.MenuItem
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.alibaba.android.arouter.facade.annotation.Route
import com.finest.comm_base.mvvm.BaseMVVMActivity
import com.finest.comm_base.util.RoutePath
import com.finest.search.R
import com.finest.search.databinding.ActivityLaunchSearchModuleBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@Route(path = RoutePath.SEARCH_LAUNCH_ACTIVITY)
class LaunchSearchModuleActivity : BaseMVVMActivity() {
    lateinit var  dataBinding: ActivityLaunchSearchModuleBinding
    lateinit var navController: NavController
    var exitTime = 0L
    override fun getLayoutResId(): Int {
        return R.layout.activity_launch_search_module
    }

    override fun getActivityTitle(): String {
        return "main"
    }

    @SuppressLint("ResourceType")
    override fun initView() {
        var fragmentId =  intent.getStringExtra(RoutePath.Route_Destination)
        var bundle = intent.getBundleExtra(RoutePath.Route_Bundle)
        dataBinding = mViewDataBinding as  ActivityLaunchSearchModuleBinding
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        //动态设置startDestination和传参bundle
        navController.apply {
            setGraph(navInflater.inflate(R.navigation.nav_graph_search).apply {
                     startDestination =    when(fragmentId){
                              RoutePath.SEARCH_SEARCH->R.id.SearchFragment
                         else -> {
                             R.id.SearchFragment
                         }
                     }
            },bundle)
        }
        navController.addOnDestinationChangedListener { _, destination, _ ->
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        setSupportActionBar(dataBinding.toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
    }

    override fun onBackPressed() {
        if (navController.currentDestination!!.id == navController.graph.startDestination) {
         finish()
        }else{
            navController.navigateUp()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
    }
    //返回键监听
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        //返回false，内部fragment的onOptionsItemSelected方法可执行
        return false
    }
}