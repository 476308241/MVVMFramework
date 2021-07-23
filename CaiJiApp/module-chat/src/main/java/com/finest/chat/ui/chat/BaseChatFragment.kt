package com.finest.chat.ui.chat

import android.app.Activity
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.widget.ImageView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arialyy.aria.core.Aria
import com.arialyy.aria.core.download.DownloadTaskListener
import com.arialyy.aria.core.task.DownloadTask
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions
import com.finest.chat.databinding.FragmentBaseChatBinding
import com.finest.chat.R
import com.finest.chat.config.LiveEventKey

import com.finest.chat.viewmodel.BaseChatViewModel
import com.finest.chatlibrary.adapter.MessageListAdapter
import com.finest.chatlibrary.commons.model.*
import com.finest.chatlibrary.entity.DispatcherInfo
import com.finest.chatlibrary.entity.MessageBean
import com.finest.chatlibrary.entity.PoliceUser
import com.finest.chatlibrary.menu.AuroraMenuBean
import com.finest.chatlibrary.util.*
import com.finest.comm_base.mvvm.BaseMVVMFragment
import com.finest.comm_base.util.GlideEngine
import com.finest.comm_base.util.GlideUtil
import com.finest.comm_base.util.UIState
import com.jeremyliao.liveeventbus.LiveEventBus
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.listener.OnResultCallbackListener
import dagger.hilt.android.AndroidEntryPoint


import timber.log.Timber
import java.io.File
import java.lang.reflect.Field
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.roundToInt


/**
 * Created by liangjiangze on 2021/1/13.
 * 会话
 */
@AndroidEntryPoint
abstract class BaseChatFragment : BaseMVVMFragment(), OnTouchListener, MessageListAdapter.OnMsgStatusViewClickListener<IMessage>,
    MessageListAdapter.OnMsgLongClickListener<IMessage>, DownloadTaskListener {

    private val viewModel by viewModels<BaseChatViewModel>()
    lateinit var dataBinding: FragmentBaseChatBinding
    var msgListAdapter: MessageListAdapter<MessageBean>? = null
    private var moreLoadingView: View? = null
    private var eventY = 0f
    private var mEmotionKeyboard: EmotionKeyboard? = null
    private val menuNames = arrayOf("图片", "视频", "文件")
    private val menuIcons =
        intArrayOf(R.mipmap.icon_chat_picture, R.mipmap.icon_chat_video, R.mipmap.icon_chat_file)
    private var isAudioMode = false
    private var currentPage = 0
    private val DOWNLOAD_URL ="https://www.baidu.com/img/PCtm_d9c8750bed0b3c7d089fa7d55720d6cf.png"
    private val DOWNLOAD_URL2 ="https://seopic.699pic.com/photo/50129/2157.jpg_wh1200.jpg"

    override fun getLayoutResId(): Int {
        return R.layout.fragment_base_chat
    }


    override fun getActivityTitle(): String {
        return "聊天"
    }


    override fun initView() {
        Aria.download(this).register()
        dataBinding = mViewDataBinding as FragmentBaseChatBinding
        dataBinding.viewModel = viewModel
        initEmotionKeyboard()
        initAudioBtn()
        initSendTextBtn()
        initEtContent()
        initRecyclerView()
        initMenu()
        LiveEventBus.get(LiveEventKey.chatFragment_onBackPressed)
            .observe(this, androidx.lifecycle.Observer {
                onBackPressed()
            })

        viewModel.testData.observe(this, Observer {
            try {
                when (it) {
                    is UIState.Success<*> -> {
                        val messageBean = msgListAdapter!!.getMessageById(it.data as String)
                        messageBean.messageStatus = IMessage.MessageStatus.RECEIVE_SUCCEED
                        if (messageBean.type == IMessage.MessageType.SEND_IMAGE.ordinal||
                            messageBean.type == IMessage.MessageType.SEND_VIDEO.ordinal){
                            messageBean.messageStatus = IMessage.MessageStatus.SEND_SUCCEED
                        }
                        msgListAdapter!!.updateMessage(messageBean)

                        showToast("成功")
                    }
                    is UIState.Error<*> -> {
                        val messageBean = msgListAdapter!!.getMessageById(it.msg)
                        messageBean.messageStatus = IMessage.MessageStatus.RECEIVE_FAILED
                        msgListAdapter!!.updateMessage(messageBean)
                        showToast("失败")

                    }
                    else -> {
                        showToast("else")

                    }
                }
            } catch (e: Exception) {
                Timber.e(e.message)
            }
        })
        val messageBeans = ArrayList<MessageBean>()
        for (i in 1..3) {
            var message = MessageBean("init$i", IMessage.MessageType.SEND_TEXT.ordinal)
            message.setUserInfo(
                PoliceUser(
                    "userSetInfo.getLoginId()", "XYZ-二部",
                    "userSetInfo.getDeptCode()", DSPConstants.CHAT_SEND_HEAD_PORTRAIT
                )
            )
            message.timeString =
                (NiceDateUtils.dateToString(Date(), NiceDateUtils.FORMAT_ONE))
            message.messageStatus = IMessage.MessageStatus.SEND_SUCCEED
            messageBeans.add(message)
        }
        var message = MessageBean("init44.pdf", IMessage.MessageType.RECEIVE_FILE.ordinal)
        message.setUserInfo(
            PoliceUser(
                "userSetInfo.getLoginId()", "XYZ-二部",
                "userSetInfo.getDeptCode()", DSPConstants.CHAT_SEND_HEAD_PORTRAIT
            )
        )
        message.mediaFilePath = DOWNLOAD_URL
        message.timeString =
            (NiceDateUtils.dateToString(Date(), NiceDateUtils.FORMAT_ONE))
        message.messageStatus = IMessage.MessageStatus.RECEIVE_GOING
        messageBeans.add(message)
        var message22 = MessageBean("init555.doc", IMessage.MessageType.RECEIVE_FILE.ordinal)
        message22.setUserInfo(
            PoliceUser(
                "userSetInfo.getLoginId()", "XYZ-二部",
                "userSetInfo.getDeptCode()", DSPConstants.CHAT_SEND_HEAD_PORTRAIT
            )
        )
        message22.mediaFilePath = DOWNLOAD_URL2
        message22.timeString =
            (NiceDateUtils.dateToString(Date(), NiceDateUtils.FORMAT_ONE))
        message22.messageStatus = IMessage.MessageStatus.RECEIVE_GOING
        messageBeans.add(message22)
        setData(messageBeans, false)
    }

    private fun onBackPressed() {
        if (dataBinding.pageMenuLayout.isShown || mEmotionKeyboard!!.isSoftInputShown) {
            dataBinding.layoutInput.auroraEtChatInput.clearFocus()
            mEmotionKeyboard?.hideSoftInput()
            hideMoreLayout()
        } else {
            findNavController().navigateUp()

        }
    }

    /**
     * 初始化发送文本按钮事件
     */
    private fun initSendTextBtn() {
        dataBinding.layoutInput.btnAuroraSend.setOnClickListener { v ->
            val content: String = dataBinding.layoutInput.auroraEtChatInput.text.toString()

            //再添加到列表显示
            val message =
                MessageBean(content, IMessage.MessageType.RECEIVE_TEXT.ordinal)
            message.setUserInfo(
                PoliceUser(
                    "userSetInfo.getLoginId()", "XYZ-二部",
                    "userSetInfo.getDeptCode()", DSPConstants.CHAT_SEND_HEAD_PORTRAIT
                )
            )
            message.timeString =
                (NiceDateUtils.dateToString(Date(), NiceDateUtils.FORMAT_ONE))
            message.messageStatus = IMessage.MessageStatus.RECEIVE_GOING

            //更新适配器
            msgListAdapter!!.addData(message, true)

            //发送到服务
            viewModel.sendTextToServer(message)
             dataBinding.layoutInput.auroraEtChatInput.setText("")
        }
    }

    /**
     * 初始化录音相关事件
     */
    private fun initAudioBtn() {
        //设置录音和文本切换开关
         dataBinding.layoutInput.ivSendSound.setOnClickListener { v ->
            if (isAudioMode) {
                hideAudioButton()
                 dataBinding.layoutInput.auroraEtChatInput.requestFocus()
                if (!mEmotionKeyboard!!.isSoftInputShown) {
                    mEmotionKeyboard!!.showSoftInput()
                }
            } else {
                showAudioButton()
                if (mEmotionKeyboard!!.isSoftInputShown) {
                    mEmotionKeyboard!!.hideSoftInput()
                }
                if (dataBinding.pageMenuLayout.visibility === View.VISIBLE) {
                    hideMoreLayout()
                }
            }
        }

        //设置录音成功回调
         dataBinding.layoutInput.btnVoiceRecord.setAudioFinishRecorderListener { seconds, filePath ->
                handleRecordAudioResult(
                    seconds,
                    filePath
                )
        }
    }


    /**
     * 音频回调处理
     */
    private fun handleRecordAudioResult(seconds: Float, filePath: String?) {
        val media = LocalMedia()
        media.path = filePath
        media.duration = seconds.toLong()
        media.isCompressed = false
        val audioList: MutableList<LocalMedia> =
            java.util.ArrayList()
        audioList.add(media)
        val messageBeans =
            generateMessageAndSend(audioList, IMessage.MessageType.SEND_VOICE)
//        sendAudioToServer(messageBeans)
    }

    private fun showAudioButton() {
        isAudioMode = true
         dataBinding.layoutInput.btnVoiceRecord.visibility = View.VISIBLE
         dataBinding.layoutInput.auroraEtChatInput.visibility = View.GONE
         dataBinding.layoutInput.ivSendSound.setImageResource(R.mipmap.cb_sound_check)
    }
    /**
     * 初始化文本输入框事件
     */
    private fun initEtContent() {
         dataBinding.layoutInput.auroraEtChatInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence,
                start: Int,
                before: Int,
                count: Int
            ) {
            }

            override fun afterTextChanged(s: Editable) {
                if (!TextUtils.isEmpty(s.toString())) {
                     dataBinding.layoutInput.ivMoreMenu.visibility = View.GONE
                     dataBinding.layoutInput.btnAuroraSend.visibility = View.VISIBLE
                } else {
                     dataBinding.layoutInput.ivMoreMenu.visibility = View.VISIBLE
                     dataBinding.layoutInput.btnAuroraSend.visibility = View.GONE
                }
            }
        })
    }


    /**
     * 初始化键盘管理
     */
    private fun initEmotionKeyboard() {
        mEmotionKeyboard = EmotionKeyboard.Builder(activity)
            .bindToEditText( dataBinding.layoutInput.auroraEtChatInput)
            .bindToContent(dataBinding.llContent)
            .bindToEmotionLayout(dataBinding.flEmotionView)
            .bindToEmotionButton(dataBinding.layoutInput.ivMoreMenu) 
            .bindInputTouchListener { scrollToBottom() }
            .bindEmotionButtonOnClickListener { view ->
                if (view.id == dataBinding.layoutInput.ivMoreMenu.id) {
                    if (dataBinding.pageMenuLayout.isShown) {
                        mEmotionKeyboard?.showSoftInput()
                         dataBinding.layoutInput.auroraEtChatInput.requestFocus()
                    } else {
                         dataBinding.layoutInput.auroraEtChatInput.clearFocus()
                        mEmotionKeyboard?.hideSoftInput()
                        showMoreLayout()
                    }
                    scrollToBottom()
                    hideAudioButton()
                }
                false
            }
            .build()
    }

    private fun hideAudioButton() {
        isAudioMode = false
         dataBinding.layoutInput.btnVoiceRecord.visibility = View.GONE
         dataBinding.layoutInput.auroraEtChatInput.visibility = View.VISIBLE
         dataBinding.layoutInput.ivSendSound.setImageResource(R.mipmap.cb_sound_normal)
    }

    private fun showMoreLayout() {
        dataBinding.pageMenuLayout.visibility = View.VISIBLE
    }

    private fun scrollToBottom() {
        Handler(Looper.getMainLooper()).post {
            dataBinding.msgList.scrollToPosition(
                msgListAdapter!!.data.size - 1
            )
        }
    }

    /**
     * 初始化更多菜单
     */
    private fun initMenu() {
        val menuBeanList: MutableList<AuroraMenuBean> =
            ArrayList<AuroraMenuBean>()
        for (i in menuNames.indices) {
            val auroraMenuBean = AuroraMenuBean(menuIcons[i], menuNames[i])
            auroraMenuBean.onClickListener = MenuClickListener(
                auroraMenuBean
            )
            menuBeanList.add(auroraMenuBean)
        }
        menuBeanList.addAll(generateBuzzMenu())
        dataBinding.pageMenuLayout.setData(menuBeanList, childFragmentManager)
    }

    /**
     * 更多菜单的点击事件
     */
    inner class MenuClickListener(private val menuBean: AuroraMenuBean) :
        View.OnClickListener {
        override fun onClick(v: View) {
            when (menuBean.menuName) {
                "图片" -> selectPictureFromGallery()
                "视频" -> addVideo()
                "文件" -> {
//                    addFile()
                    showToast("暂不支持")
                }
            }
        }

    }

    /**
     * 添加图片
     */
    private fun selectPictureFromGallery() {
        val pictureDir =
            File(MediaCacheUtils.getMediaCacheSavePath(DispatcherInfo.Type.IMAGE))
        if (!pictureDir.exists()) {
            pictureDir.mkdirs()
        }
        PictureSelector.create(this)
            .openGallery(PictureMimeType.ofImage())
            .imageEngine(GlideEngine.createGlideEngine())
            .maxSelectNum(3)
            .forResult(object: OnResultCallbackListener<LocalMedia> {
                override fun onResult(result: MutableList<LocalMedia>?) {
                    handleSelectImageResult(result)
                }

                override fun onCancel() {
                }

            })
    }

    /**
     * 添加新视频
     */
    private fun addVideo() {
        val videoDir =
            File(MediaCacheUtils.getMediaCacheSavePath(DispatcherInfo.Type.VIDEO))
        if (!videoDir.exists()) {
            videoDir.mkdirs()
        }
        PictureSelector.create(this)
            .openGallery(PictureMimeType.ofVideo())
            .imageEngine(GlideEngine.createGlideEngine())
            .setOutputCameraPath(MediaCacheUtils.getMediaCacheSavePath(DispatcherInfo.Type.VIDEO))
            .compressSavePath(MediaCacheUtils.getMediaCacheSavePath(DispatcherInfo.Type.VIDEO))
            .recordVideoSecond(15)
            .videoMaxSecond(15)
            .videoQuality(1)
            .forResult((object: OnResultCallbackListener<LocalMedia> {
                override fun onResult(result: MutableList<LocalMedia>?) {
                    handleRecordVideoResult(result)
                }

                override fun onCancel() {
                }

            }))

    }

    /**
     * 添加附件
     */
    private fun addFile() {
        PictureSelector.create(this)
            .openGallery(PictureMimeType.ofAll())
            .imageEngine(GlideEngine.createGlideEngine())
            .maxSelectNum(3)
            .forResult((object: OnResultCallbackListener<LocalMedia> {
                override fun onResult(result: MutableList<LocalMedia>?) {
                }

                override fun onCancel() {
                }

            }))
    }
    /**
     * 初始化消息列表适配器
     */
    private fun initRecyclerView() {
        moreLoadingView = layoutInflater.inflate(R.layout.dispatch_layout_more_loading, null)
        initMessageAdapter()
        dataBinding.msgList.setAdapter(msgListAdapter)

    }

    /**
     * 初始化回话消息适配器
     */
    private fun initMessageAdapter() {
        msgListAdapter = MessageListAdapter(
            null,
            object : ImageLoader<MessageBean?> {
                override fun loadAvatarImage(avatarImageView: ImageView?, avatarString: String?) {
                    Log.e("TEST", "AvatarImage")
                    if (avatarString!!.contains("R.mipmap")) {
                        val replace = avatarString.replace("R.mipmap.", "")
                        avatarImageView!!.setImageResource(getDrawableId(replace))
                    } else {
                        Glide.with(avatarImageView!!.context)
                            .load(avatarString)
                            .apply(RequestOptions().placeholder(R.mipmap.aurora_headicon_default))
                            .into(avatarImageView)
                    }

                }

                override fun loadImage(imageView: ImageView?, message: MessageBean?) {
                    GlideUtil.displayFullUrlImage(
                        mContext, message!!.mediaFilePath,
                        imageView!!
                    )
                    Log.e("TEST", "loadImage")
                }

                override fun loadVideo(imageCover: ImageView?, message: MessageBean?) {
                    loadVideoMessage(imageCover!!, message!!)
                }

            })
        msgListAdapter!!.peerSize = 10
        msgListAdapter!!.setVoiceLoader(VoiceLoader<MessageBean> { message ->
//            mViewModel.loadVoiceMessage(
//                message
//            )

        } as VoiceLoader<MessageBean?>?)
        msgListAdapter!!.setFileLoader(FileLoader<MessageBean> { message ->
//            mViewModel.loadFileMessage(
//                message
//            )

            var filePath = MediaCacheUtils.getMediaCacheSavePath(DispatcherInfo.Type.IMAGE)+message.text+".pdf"
            var file = File(filePath)
            if (file.exists()){
                message.messageStatus = IMessage.MessageStatus.RECEIVE_SUCCEED
                Handler(Looper.myLooper()!!).postDelayed(Runnable {
                    msgListAdapter!!.updateMessage(message)
                },300)
            }else{
                val taskId: Long = Aria.download(this)
                    .load(message.mediaFilePath) //读取下载地址
                    .setFilePath(filePath) //设置文件保存的完整路径
                    .create() //创建并启动下载
            }


        } as FileLoader<MessageBean?>?)
        msgListAdapter!!.setOnMsgClickListener {message ->
                if (message!!.type === IMessage.MessageType.RECEIVE_VIDEO.ordinal
                    || message!!.type === IMessage.MessageType.SEND_VIDEO.ordinal
                ) {
                    if (!TextUtils.isEmpty(message!!.mediaFilePath)) {
                        browserAttachment(DispatcherInfo.Type.VIDEO, message!!.mediaFilePath)
                    }
                } else if (message!!.type === IMessage.MessageType.RECEIVE_IMAGE.ordinal
                    || message!!.type === IMessage.MessageType.SEND_IMAGE.ordinal
                ) {
                    browserAttachment(DispatcherInfo.Type.IMAGE, message!!.mediaFilePath)
                } else if (message!!.type === IMessage.MessageType.RECEIVE_FILE.ordinal
                    || message!!.type === IMessage.MessageType.SEND_FILE.ordinal
                ) {
                    browserAttachment(DispatcherInfo.Type.FILE, message!!.mediaFilePath)
                }
        }
//        msgListAdapter!!.setMsgLongClickListener(this)
//        msgListAdapter!!.setMsgStatusViewClickListener(this)
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = RecyclerView.VERTICAL
        //布局反向
        layoutManager.reverseLayout = false
        dataBinding.msgList.layoutManager = layoutManager
        msgListAdapter!!.setLayoutManager(layoutManager)
    }


    /**
     * 加载视频
     *
     * @param imageCover
     * @param messageBean
     */
    fun loadVideoMessage(
        imageCover: ImageView,
        messageBean: MessageBean
    ) {
        if (messageBean.isLocal) {
            Glide.with(imageCover.context)
                .load(messageBean.mediaFilePath)
                .apply(
                    RequestOptions()
                        .transform(CenterCrop())
                        .override(400, 300)
                )
                .into(imageCover)
        } else {
//            downloadVideoMessage(messageBean)
        }
    }
    /**
     * 获取资源id
     *
     * @param name
     * @return
     */
    private fun getDrawableId(name: String): Int {
        return try {
            val field: Field = R.mipmap::class.java.getField(name)
            field.getInt(field.name)
        } catch (e: java.lang.Exception) {
            R.mipmap.dispatch_chat_right_icon
        }
    }

    override fun onTouch(view: View, motionEvent: MotionEvent): Boolean {
        when (motionEvent.action) {
            MotionEvent.ACTION_DOWN -> {
                eventY = motionEvent.y
                if (dataBinding.pageMenuLayout.visibility == View.VISIBLE) {
                    hideMoreLayout()
                }
                try {
                    val v = mContext.currentFocus
                    if (mEmotionKeyboard != null && v != null) {
                        mEmotionKeyboard!!.hideSoftInput()
                        view.clearFocus()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            MotionEvent.ACTION_MOVE -> if (dataBinding.pageMenuLayout.visibility === View.GONE && isListOnTop()
                && msgListAdapter != null && msgListAdapter!!.hasMoreData()
            ) {
                if (motionEvent.y > eventY) {
                    if (msgListAdapter!!.headerLayoutCount == 0) {
                        msgListAdapter!!.addHeaderView(moreLoadingView!!)
                        loadMoreMsgDelayed()
                    }
                }
            }
            MotionEvent.ACTION_UP -> view.performClick()
            else -> {
            }
        }
        return false
    }

    private fun loadMoreMsgDelayed() {
        dataBinding.msgList.postDelayed(Runnable {
//            val messageBeans: List<MessageBean> = messageManager.loadMoreHistoryMessage()
            val messageBeans = ArrayList<MessageBean>()
            for (i in 1..3) {
                var messageBean = MessageBean("test$i", IMessage.MessageType.SEND_TEXT.ordinal)
                messageBeans.add(messageBean)
            }
            setData(messageBeans, true)
        }, 1000)
    }

    /**
     * 设置数据
     *
     * @param data
     */
    open fun setData(
        data: List<MessageBean>?,
        isLoadMore: Boolean
    ) {
        currentPage++
        val size = data?.size ?: 0
        if (size > 0) {
            val fromUser: IUser = data!![0].fromUser
            if ("通告" == fromUser.displayName) {
                msgListAdapter!!.setHasMoreData(false)
            } else {
                msgListAdapter!!.setHasMoreData(size == msgListAdapter!!.peerSize)
            }
            if (isLoadMore) {
                msgListAdapter!!.loadMore(data)
            } else {
                msgListAdapter!!.addData(data)
                scrollToBottom()
            }
        }
        if (msgListAdapter!!.headerLayoutCount > 0) {
            msgListAdapter!!.removeHeaderView(moreLoadingView!!)
        }
    }

    /**
     * 面板中是否含有RecyclerView
     *
     * @return
     */
    private fun isListOnTop(): Boolean {
        if (dataBinding.msgList != null) {
            val layoutManager =
                dataBinding.msgList.layoutManager as LinearLayoutManager
            if (layoutManager != null) {
                val position = layoutManager.findFirstCompletelyVisibleItemPosition()
                if (position == 0) {
                    return true
                }
            }
        }
        return false
    }

    private fun hideMoreLayout(): Boolean {
        if (dataBinding.flEmotionView.visibility === View.VISIBLE) {
            dataBinding.flEmotionView.visibility = View.GONE
            dataBinding.pageMenuLayout.visibility = View.GONE
            return true
        }
        return false
    }

    override fun onStatusViewClick(message: IMessage?) {
    }


    override fun onMessageLongClick(view: View?, message: IMessage?) {
    }

    /**
     * 图片选择回调处理
     *
     * @param data
     */
    private fun handleSelectImageResult(result: List<LocalMedia>?) {
        val messageBeans: List<MessageBean> =
            generateMessageAndSend(result, IMessage.MessageType.SEND_IMAGE)!!
            messageBeans.forEach {
            viewModel.sendImageToServer(it)
            }
    }

    /**
     * 视频选择回调处理
     *
     * @param data
     */
    private fun handleRecordVideoResult(result: List<LocalMedia>?) {
        val messageBeans: List<MessageBean> =
            generateMessageAndSend(result, IMessage.MessageType.SEND_VIDEO)!!
        messageBeans.forEach {
            viewModel.sendImageToServer(it)
        }

    }

    /**
     * 生成消息并发送
     *
     * @param mSelectedList
     * @param messageType
     * @return
     */
    private fun generateMessageAndSend(
        mSelectedList: List<LocalMedia>?,
        messageType: IMessage.MessageType
    ): List<MessageBean>? {
        val messageBeans: MutableList<MessageBean> =
            ArrayList()
        if (mSelectedList != null) {
            for (media in mSelectedList) {
                val filePath =
                    if (media.isCompressed) media.compressPath else media.path
                val message =
                    MessageBean(StringUtils.getFileName(filePath), messageType.ordinal)

                message.setUserInfo(
                    PoliceUser(
                        "userSetInfo.getLoginId()", "XYZ-图片",
                        "userSetInfo.getDeptCode()", DSPConstants.CHAT_SEND_HEAD_PORTRAIT
                    )
                )
                if (messageType == IMessage.MessageType.SEND_VOICE
                    || messageType == IMessage.MessageType.SEND_IMAGE
                    || messageType == IMessage.MessageType.SEND_VIDEO
                    || messageType == IMessage.MessageType.SEND_FILE
                ) {
                    message.isLocal = true
                    message.mediaFilePath = filePath
                    if (messageType == IMessage.MessageType.SEND_VOICE || messageType == IMessage.MessageType.SEND_VIDEO
                    ) {
                        val extra = media.duration
                        if (extra != 0L) {
                            message.duration = extra.toFloat().roundToInt().toLong()
                        }
                    }
                }
                message.timeString = NiceDateUtils.dateToString(
                    Date(),
                    NiceDateUtils.FORMAT_ONE
                )
                message.messageStatus = IMessage.MessageStatus.SEND_GOING
                messageBeans.add(message)
                msgListAdapter!!.addData(message, true)
            }
        }
        return messageBeans
    }

    /**
     * 浏览附件详情
     *
     * @param browserType
     * @param path
     */
    private fun browserAttachment(
        browserType: String,
        path: String
    ) {
        when (browserType) {
            DispatcherInfo.Type.VIDEO -> PictureSelector.create(activity)
                .externalPictureVideo(path)
            DispatcherInfo.Type.IMAGE -> {
                val selectPicList: MutableList<LocalMedia> =
                    ArrayList()
                val pathList: ArrayList<String> = getImageList()
                var index = 0
                var i = 0
                while (i < pathList.size) {
                    val localMedia = LocalMedia()
                    localMedia.path = pathList[i]
                    localMedia.compressPath = pathList[i]
                    selectPicList.add(localMedia)
                    if (path == pathList[i]) {
                        index = i
                    }
                    i++
                }
                PictureSelector.create(mContext as Activity)
                    .themeStyle(R.style.picture_default_style)
                    .openExternalPreview(index, selectPicList)
            }
            DispatcherInfo.Type.FILE -> {
//                FileOpenUtils.queryFilePath(mViewModel, mContext, path)
                showToast("附件无法预览")
            }
            else -> {
            }
        }
    }

    //获取图片list
    private fun getImageList():ArrayList<String>{
      var imageList =  msgListAdapter!!.data as  MutableList<MessageBean>
       var paths = ArrayList<String>()
        imageList.forEach {
            if (it.type == IMessage.MessageType.SEND_IMAGE.ordinal){
                paths.add(it.mediaFilePath)
            }

        }
        return  paths
    }

    override fun onTaskCancel(task: DownloadTask?) {
    }

    override fun onPre(task: DownloadTask?) {
    }

    override fun onTaskStart(task: DownloadTask?) {
    }

    override fun onTaskPre(task: DownloadTask?) {
    }

    override fun onTaskRunning(task: DownloadTask?) {
          if(task!!.key== DOWNLOAD_URL){

            }
            var p = task!!.percent	//任务进度百分比
            var speed = task!!.convertSpeed;	//转换单位后的下载速度，单位转换需要在配置文件中打开
            var speed1 = task!!.speed; //原始byte长度速度
            Log.e("TEST","running"+task!!.key+p+speed1)
    }

    override fun onWait(task: DownloadTask?) {
    }

    override fun onTaskResume(task: DownloadTask?) {
    }

    override fun onNoSupportBreakPoint(task: DownloadTask?) {
    }

    override fun onTaskStop(task: DownloadTask?) {
    }

    override fun onTaskFail(task: DownloadTask?, e: java.lang.Exception?) {
    }

    override fun onTaskComplete(task: DownloadTask?) {
        if(task!!.key== DOWNLOAD_URL){

        }
        var messageList =  msgListAdapter!!.data as  MutableList<MessageBean>
      Log.e("TEST","taskComplete"+task.key)
        messageList.forEach {
            if (it.mediaFilePath == task!!.key){
                it.messageStatus = IMessage.MessageStatus.RECEIVE_SUCCEED
                Handler(Looper.myLooper()!!).postDelayed(Runnable {
                    msgListAdapter!!.updateMessage(it)
                },300)
            }

        }
    }

    /**
     * 生成业务菜单
     *
     * @return
     */
    protected abstract fun generateBuzzMenu(): ArrayList<AuroraMenuBean>
}