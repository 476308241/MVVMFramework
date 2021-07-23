package com.finest.comm_base.util

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.net.Uri
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.ToastUtils
import com.blankj.utilcode.util.UriUtils
import com.bumptech.glide.load.engine.GlideException
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.finest.comm_base.R
import com.finest.comm_base.config.ConstantConfig
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.listener.OnResultCallbackListener


/**
 * Created by liangjiangze on 2020/05/09.
 */
class PictureSelectorHelper(var mContext: Context) {
    private var displayImagesList = ArrayList<String>()  //用于展示的列表，多一个添加图片按钮
    lateinit var adapter : AddFileAdapter
    var isAddPicVisible = true  //是否展示添加图片按钮
    var fileList = ArrayList<String>()//真正的文件列表，去掉添加图片按钮
    var selectLocalMedias = ArrayList<LocalMedia>()

    fun initRecyclerView(recyclerView: RecyclerView){
        listAddPicImage(isAddPicVisible,fileList)
        adapter = AddFileAdapter(
            mContext, isAddPicVisible,
            displayImagesList,
            onAddFileClickListener
        )
        val gridLayoutManager = GridLayoutManager(mContext, 3)
        recyclerView.layoutManager = gridLayoutManager
        recyclerView.adapter = adapter
    }
    private var haha = "1"
    private fun haha(){
         this.isAddPicVisible = false
        Log.e("TEST","testhaha")
    }
    private fun listAddPicImage(isAddPicVisible: Boolean, data: ArrayList<String>): ArrayList<String> {
            displayImagesList.clear()
            for (str in data) {
                displayImagesList.add(str)
            }
            if (isAddPicVisible) {
                displayImagesList.add(ConstantConfig.ADD_PIC)
            }
            return displayImagesList
    }

    private val onAddFileClickListener = object : OnAddFileClickListener {
        override fun onAddFileClick() {
            PictureSelector.create(mContext as Activity).openGallery(PictureMimeType.ofImage())
                .maxSelectNum(6)
                .imageEngine(GlideEngine.createGlideEngine())
                .selectionData(selectLocalMedias)
                .forResult(
                    object:OnResultCallbackListener<LocalMedia>{
                        override fun onCancel() {
                        }
                        override fun onResult(result: MutableList<LocalMedia>?) {
                            fileList.clear()
                            selectLocalMedias.clear()
                            for (localMedia  in result!!){
                                var  path = localMedia.path
                                //PictureSelector框架在android 10 返回的是uri地址，需要转换成绝对录像
                                if (path.contains("content://")){
                                    val uri: Uri = Uri.parse(path)
                                    path =  UriUtils.uri2File(uri).absolutePath
                                }
                                fileList.add(path)
                                selectLocalMedias.add(localMedia)
                            }
                            listAddPicImage(true,fileList)
                            adapter.notifyDataSetChanged()
                        }

                    }
                )
        }

        override fun onOtherItemClick(position: Int) {
            val mediaList = ArrayList<LocalMedia>()
            for (url in fileList) {
                val media = LocalMedia()
                media.path =  url
                when {
                    MediaFileUtil.isImageFileType(media.path) -> media.mimeType = PictureMimeType.ofImage().toString()
                    MediaFileUtil.isAudioFileType(media.path) -> media.mimeType = PictureMimeType.ofAudio().toString()
                    MediaFileUtil.isVideoFileType(media.path) -> media.mimeType = PictureMimeType.ofVideo().toString()
                    else -> media.mimeType = PictureMimeType.ofAll().toString()
                }
                mediaList.add(media)
            }
            when {
                MediaFileUtil.isImageFileType(mediaList[position].path) -> {
                    //从所有附件中获取图片附件
                    val imageList = ArrayList<LocalMedia>()
                    for (media in mediaList) {
                        if (media.mimeType == PictureMimeType.ofImage().toString()) {
                            imageList.add(media)
                        }
                    }
                    //获取点击的图片在图片组中的位置
                    var pos = 0
                    imageList.forEachIndexed() { i: Int, localMedia: LocalMedia ->
                        if (localMedia.path.contains(mediaList[position].path)) {
                            pos = i
                        }
                    }

                    PictureSelector.create(mContext as Activity)
                        .themeStyle(R.style.picture_default_style)
                        .openExternalPreview(pos, imageList)
                }
                MediaFileUtil.isAudioFileType(mediaList[position].path) ->
                    PictureSelector.create(mContext as Activity).externalPictureAudio(
                        mediaList[position].path
                    )
                MediaFileUtil.isVideoFileType(mediaList[position].path) ->
                    PictureSelector.create(mContext as Activity).externalPictureVideo(mediaList[position].path)
                else -> {
                    ToastUtils.showShort("该文件无法预览！")
                }
            }
        }

        override fun onDelFileClick(position: Int, item: String) {
            AlertDialog.Builder(mContext as Activity)
                .setMessage("你确定删除附件吗")
                .setPositiveButton("是") { _: DialogInterface, _: Int ->
                    fileList.remove(item)
                    selectLocalMedias.removeAt(position)
                    listAddPicImage(isAddPicVisible,fileList)
                    adapter.notifyDataSetChanged()
                }
                .setNegativeButton("否", null)
                .show()
        }

    }


    interface OnAddFileClickListener {
        fun onAddFileClick()
        fun onOtherItemClick(position: Int)
        fun onDelFileClick(position: Int, item: String)
    }
   inner class AddFileAdapter(
        val mContext: Context,
        private val isAddPicVisible: Boolean,
        var list: ArrayList<String>,
        private val mOnAddFileClickListener: OnAddFileClickListener
    ) :
        BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_add_file, list) {
        override fun convert(helper: BaseViewHolder, item: String) {
            val ivFile = helper.getView<ImageView>(R.id.fiv)
            val llDel = helper.getView<LinearLayout>(R.id.ll_del)
            val tvDuration = helper.getView<TextView>(R.id.tv_duration)
            if (item.contains(ConstantConfig.ADD_PIC)) {
                ivFile.setImageResource(R.mipmap.addimg_1x)
                ivFile.setOnClickListener {
                    mOnAddFileClickListener.onAddFileClick()
                }
                llDel.visibility = View.INVISIBLE
                tvDuration.visibility = View.INVISIBLE
            } else {
                ivFile.setOnClickListener {
                    mOnAddFileClickListener.onOtherItemClick(helper.layoutPosition)
                }
                if (isAddPicVisible) {
                    llDel.visibility = View.VISIBLE
                } else {
                    llDel.visibility = View.GONE
                }
                llDel.setOnClickListener {

                    mOnAddFileClickListener.onDelFileClick(helper.layoutPosition, item)
                }
                when {
                    MediaFileUtil.isImageFileType(item) -> {
                        try {
                            GlideUtil.displayFullUrlImage(context, item, ivFile)
                        } catch (e: Exception) {
                            if (e is GlideException) {
                                e.logRootCauses("glide")
                            }
                        }
                        tvDuration.visibility = View.INVISIBLE
                    }
                    else -> {
                        ivFile.setImageResource(R.mipmap.ic_file_icon)
                        tvDuration.visibility = View.INVISIBLE
                    }
                }


            }
        }

    }

}