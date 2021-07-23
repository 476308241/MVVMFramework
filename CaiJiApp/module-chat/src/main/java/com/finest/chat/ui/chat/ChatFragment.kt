package com.finest.chat.ui.chat

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.finest.chat.R
import com.finest.chat.config.ClassNameConfig
import com.finest.chat.config.LiveEventKey
import com.finest.chat.config.LiveEventValue
import com.finest.chatlibrary.commons.model.IMessage
import com.finest.chatlibrary.entity.MessageBean
import com.finest.chatlibrary.entity.PoliceUser
import com.finest.chatlibrary.menu.AuroraMenuBean
import com.finest.chatlibrary.util.DSPConstants
import com.finest.chatlibrary.util.NiceDateUtils
import com.finest.comm_base.db.dao.DispatcherInfoDao
import com.finest.comm_base.util.RoutePath
import com.jeremyliao.liveeventbus.LiveEventBus
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject

/**
 * Created by liangjiangze on 2021/1/13.
 *
 */
@AndroidEntryPoint
@Route(path = RoutePath.CHAT_CHAT)
class ChatFragment : BaseChatFragment(){
    @Inject
    lateinit var dispatcherInfoDao: DispatcherInfoDao
    private val addMenuNames =
        arrayOf("重定位", "签收", "签到", "反馈", "警情升级")
    private val addMenuIcons = intArrayOf(
        R.mipmap.ic_chat_relocation, R.mipmap.ic_chat_sign_off,
        R.mipmap.ic_chat_sign_in, R.mipmap.ic_chat_feedback, R.mipmap.ic_chat_dispatch_update
    )

    override fun initView() {
        super.initView()
        LiveEventBus.get(ClassNameConfig.ChatFragment, LiveEventValue::class.java).observe(this, androidx.lifecycle.Observer {
            when(it.code){
                LiveEventKey.have_new_msg ->{
                    showToast("收到信息")

                    var xdbh = "111"
                    var policeNo ="222"
                    var groudId = "333"
                    var deptCode = "3333"
                    var state = 0
                   var  info =  dispatcherInfoDao!!.getDispatcherInfoByGroudId()
                    //是不是这条警情的，不是不添加上去
                    var imageList =  msgListAdapter!!.data as  MutableList<MessageBean>
                    var message = MessageBean(info!![0]!!.content, IMessage.MessageType.SEND_TEXT.ordinal)
                    message.setUserInfo(
                        PoliceUser(
                            "userSetInfo.getLoginId()", "XYZ-二部",
                            "userSetInfo.getDeptCode()", DSPConstants.CHAT_SEND_HEAD_PORTRAIT
                        )
                    )
                    message.timeString =
                        (NiceDateUtils.dateToString(Date(), NiceDateUtils.FORMAT_ONE))
                    message.messageStatus = IMessage.MessageStatus.SEND_SUCCEED
                    imageList.add(message)
                    setData(imageList,false)

                }
            }
        })
    }


    override fun generateBuzzMenu(): ArrayList<AuroraMenuBean> {
        var menuBeanList =ArrayList<AuroraMenuBean>()

        for (i in addMenuNames.indices) {
            val auroraMenuBean = AuroraMenuBean(addMenuIcons[i],addMenuNames[i])
            auroraMenuBean.onClickListener = MenuClickListener(auroraMenuBean)
            menuBeanList.add(auroraMenuBean)
        }
        return  menuBeanList
    }
    /**
     * 更多菜单的点击事件
     */
    inner class MenuClickListener(private val menuBean: AuroraMenuBean) :
        View.OnClickListener {
        override fun onClick(v: View) {
            when (menuBean.menuName) {
                "重定位" ->{
                    showToast("重定位")
                }
                else ->{
                    showToast("其他")
                }
            }
        }

    }
}