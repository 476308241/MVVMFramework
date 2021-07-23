package com.finest.jetpack.ui.jetpackdemo

import androidx.fragment.app.viewModels

import com.finest.comm_base.mvvm.BaseMVVMFragment
import com.finest.jetpack.R
import com.finest.jetpack.bean.Computer
import com.finest.jetpack.databinding.FragmentDaggerhiltBinding
import com.finest.jetpack.viewmodel.DaggerHiltViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber


/**
 * Created by liangjiangze on 2021/1/7.
 * 在 Fragment 中添加 @AndroidEntryPoint 注解，必须在 Fragment 依赖的 Activity 上也添加 @AndroidEntryPoint 注解
 *
 *
 * 用@Inject标记的构造函数如果有参数，那么这个参数也需要其它地方提供依赖。
 * 但是@Inject有一个缺陷，就是对于第三方的类无能为力。因为我们不能修改第三方的构造函数，
 * 所以对于String还有其他的一些我们不能修改的类，只能用@Module中的@Provides来提供实例了
 * https://www.jianshu.com/p/47c7306b2994
 * 用@Module标记的类提供依赖是一个常规套路，我们在项目中运用最多的也是这种方式。
 * 构造方法有改动时，只需要修改@Module里面的
 *
 */
@AndroidEntryPoint
class DaggerHiltFragment : BaseMVVMFragment() {
    private val viewModel by viewModels<DaggerHiltViewModel>()

    lateinit var dataBinding: FragmentDaggerhiltBinding
//    @Inject
//    lateinit var dataModule: DataModule
//    @Inject
//    lateinit var computerDaggerHilt: ComputerDaggerHilt


    override fun getLayoutResId(): Int {
        return R.layout.fragment_daggerhilt
    }

    override fun getActivityTitle(): String {
        return ""
    }

    override fun initView() {

//        val cup22 = ComputerDaggerHilt.Cpu(1000.00)
//        computerDaggerHilt.motherBoard.cpu = cup22
//        Timber.e(computerDaggerHilt.getPrice().toString())
        dataBinding = mViewDataBinding as FragmentDaggerhiltBinding

        val cup = Computer.Cpu(1000.00)
        val hardDisk = Computer.HardDisk(200.00)
        //组装主板
        val motherBoard = Computer.MotherBoard(cup,hardDisk)
        Timber.e("主板价格:"+motherBoard.getPrice().toString())
        val monitor = Computer.Monitor()
        monitor.price = 500.00
        val mouse = Computer.Mouse()
        mouse.price = 80.00
        //组装电脑
        val computer = Computer(motherBoard,monitor,mouse)
        Timber.e("电脑价格:"+computer.getPrice22().toString())
        dataBinding.computer = computer

    }

}