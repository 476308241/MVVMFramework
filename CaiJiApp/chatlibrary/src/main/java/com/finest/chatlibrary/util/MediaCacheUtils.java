package com.finest.chatlibrary.util;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.text.TextUtils;


import com.finest.chatlibrary.entity.DispatcherInfo;
import com.finest.chatlibrary.entity.MessageBean;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;


/**
 * Created on 2018/8/7
 *
 * @author BigZhou
 * @Company: * 公司网址：www.finest.com *
 * @Description: 多媒体缓存工具
 */
public class MediaCacheUtils {
    /**
     * 可识别的最小路径长度
     */
    private final static int MIN_PATH_LENGTH_LIMIT = 3;

    /**
     * 检测消息附件是否存在
     *
     * @param bean
     * @param type
     * @return
     */
    public static boolean exists(MessageBean bean, String type) {
        if (bean == null || TextUtils.isEmpty(bean.getMediaFilePath())) {
            return false;
        }

        String[] split = bean.getMediaFilePath().split("/");
        if (split.length < MIN_PATH_LENGTH_LIMIT) {
            return false;
        }

        File file = new File(getMediaCacheSavePath(type) + split[split.length - 1]);
        return file.exists();
    }

    /**
     * 保存Bitmap
     *
     * @param bitmap
     * @param type
     * @param picName
     */
    public static void saveBitmap(Bitmap bitmap, String type, String picName) {
        File f = getMediaCacheDir(type);
        if (!f.exists()) {
            f.mkdirs();
        }

        f = new File(getMediaCacheSavePath(type), picName);

        if (f.exists()) {
            f.delete();
        }

        try {
            FileOutputStream out = new FileOutputStream(f);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取附件存放路径
     *
     * @param type
     * @return
     */
    public static String getMediaCacheSavePath(String type) {
//        UserSetInfo userSetInfo = BaseApp.getInstance().getUserSetInfo();
//        String userExt = "";
//        if (userSetInfo != null && !TextUtils.isEmpty(userSetInfo.getLoginId())) {
//            userExt = userSetInfo.getLoginId();
//        }
        String userExt = "LoginId";
        String savePath = "";
        if (DispatcherInfo.Type.IMAGE.equals(type)) {
            savePath = DSPConstants.PICTURE_FILE_PATH + (TextUtils.isEmpty(userExt) ? "" : (userExt + "/"));
        } else if (DispatcherInfo.Type.VOICE.equals(type)) {
            savePath = DSPConstants.AUDIO_FILE_PATH + (TextUtils.isEmpty(userExt) ? "" : (userExt + "/"));
        } else if (DispatcherInfo.Type.VIDEO.equals(type)) {
            savePath = DSPConstants.VIDEO_FILE_PATH + (TextUtils.isEmpty(userExt) ? "" : (userExt + "/"));
        } else if (DispatcherInfo.Type.FILE.equals(type)) {
            savePath = DSPConstants.FILE_PATH + (TextUtils.isEmpty(userExt) ? "" : (userExt + "/"));
        }
        File file = new File(savePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        return savePath;
    }

    /**
     * 获取附件存放地址
     *
     * @param type
     * @return
     */
    private static File getMediaCacheDir(String type) {
        File mediaDir = null;

        String cachePath = getMediaCacheSavePath(type);
        if (!TextUtils.isEmpty(cachePath)) {
            mediaDir = new File(cachePath);
        }
        return mediaDir;
    }

    /**
     * 通过消息实体获取文件名称
     *
     * @param bean
     * @return
     */
    public static String getFileName(MessageBean bean) {
        if (bean == null || TextUtils.isEmpty(bean.getMediaFilePath())) {
            return "";
        }

        String[] split = bean.getMediaFilePath().split("/");
        if (split.length < MIN_PATH_LENGTH_LIMIT) {
            return "";
        }

        return split[split.length - 1];
    }

    /**
     * 服务器返回url，通过url去获取视频的第一帧
     * Android 原生给我们提供了一个MediaMetadataRetriever类
     * 提供了获取url视频第一帧的方法,返回Bitmap对象
     *
     * @param videoUrl
     * @return
     */
    public static Bitmap getWebVideoThumbnail(String videoUrl, Map<String, String> map) {
        Bitmap bitmap = null;

        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            //根据url获取缩略图
            retriever.setDataSource(videoUrl, map);
            //获得第一帧图片
            bitmap = retriever.getFrameAtTime();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } finally {
            retriever.release();
        }
        return bitmap;
    }

    /**
     * 获取本地视频的第一帧
     *
     * @param localPath
     * @return
     */
    public static Bitmap getLocalVideoThumbnail(String localPath) {
        Bitmap bitmap = null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            //根据文件路径获取缩略图
            retriever.setDataSource(localPath);
            //获得第一帧图片
            bitmap = retriever.getFrameAtTime();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } finally {
            retriever.release();
        }
        return bitmap;
    }

    /**
     * 获取调度消息默认缓存目录
     *
     * @param info
     * @return
     */
    private static File getMessageCacheDir(DispatcherInfo info) {
        if (info == null) {
            return null;
        }

        File fileDir = getMediaCacheDir(info.getInfoType());
        if (fileDir == null || !fileDir.exists()) {
            // 不存在该目录，创建
            fileDir.mkdirs();
        }

        return fileDir;
    }

    /**
     * 检查消息多媒体文件,存在返回文件，不存在返回null
     *
     * @param info
     */
    public static File getCacheFile(DispatcherInfo info) {
        if (info == null) {
            return null;
        }
        if (!TextUtils.isEmpty(info.getFilePath())) {
            File file = new File(info.getFilePath());
            if (file.exists()) {
                return file;
            }
        }
        if (DispatcherInfo.Type.FILE.equals(info.getInfoType())) {
            if (!TextUtils.isEmpty(info.getInfoName())) {
                // 得到多媒体消息对应缓存目录
                File file = getMessageCacheDir(info);
                if (file != null && file.exists()) {
                    // 构建文件路径，判断文件是否存在
                    file = new File(file, info.getInfoName());
                    if (file.exists()) {
                        return file;
                    }
                }
            }
        }

        String content = info.getContent();
//        if (!TextUtils.isEmpty(content)) {
//            // 得到多媒体消息对应缓存目录
//            File file = getMessageCacheDir(info);
//            if (file != null && file.exists()) {
//                // 构建文件路径，判断文件是否存在
//                file = new File(file, Objects.requireNonNull(StringUtils.getFileName(content)));
//                if (file.exists()) {
//                    return file;
//                }
//            }
//        }

        return null;
    }
}
