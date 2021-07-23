package com.finest.jetpack.bean

/**
 * Created by liangjiangze on 2021/1/7.
 * 电脑包含主机，显示器，鼠标，主机包含cpu，硬盘
 */
class Computer(var motherBoard: MotherBoard, var monitor: Monitor, var mouse: Mouse) {
    var price:Double?=null
    fun getPrice22():Double{
      this.price =  motherBoard.getPrice()+ monitor.price!!+mouse.price!!
      return price!!
    }

    //主机
    class MotherBoard(var cpu: Cpu, var hardDisk: HardDisk){
       fun getPrice():Double{
         return cpu!!.price!! + hardDisk!!.price!!
       }
    }

    //显示器
    class Monitor{
        var price:Double?=null
        // 品牌
        var brand:String?=null
        //屏幕多大
        var monitorSize:String?=null
    }
    //鼠标
    class Mouse{
        var price:Double?=null
        // 品牌
        var brand:String?=null
        //灵敏度
        var speed:String?=null
    }
    class Cpu(var price:Double){
        constructor():this(0.00)
        // 品牌
        var brand:String?=null
        //多大内存
        var cpuSize:String?=null
    }
    // 硬盘
    class HardDisk(var price:Double){
        constructor():this(0.00)
        // 品牌
        var brand:String?=null
        //是否是机械硬盘
        var isJx:Boolean?=null
    }
}