package com.finest.chatlibrary.util;

import android.media.MediaRecorder;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class AudioManager {
    private static final String TAG = AudioManager.class.getSimpleName();
    private MediaRecorder mRecorder;
    private String mDirString;
    private String mCurrentFilePathString;
    // 是否准备好了
    private boolean isPrepared;

    private static AudioManager mInstance;

    public AudioManager(String dir) {
        mDirString = dir;
    }

    /**
     * 回调函数，准备完毕，准备好后，button才会开始显示录音框
     *
     * @author nickming
     */
    public interface AudioStageListener {
        void wellPrepared();
    }

    private AudioStageListener mListener;

    public void setOnAudioStageListener(AudioStageListener listener) {
        mListener = listener;
    }

    // 准备方法
    public void prepareAudio() {
        try {
            // 一开始应该是false的
            isPrepared = false;

            File dir = new File(mDirString);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            String fileNameString = generalFileName();
            File file = new File(dir, fileNameString);

            mCurrentFilePathString = file.getAbsolutePath();

            mRecorder = new MediaRecorder();
            // 设置输出文件
            mRecorder.setOutputFile(file.getAbsolutePath());
            // 设置meidaRecorder的音频源是麦克风
            mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            // 设置文件音频的输出格式为amr
            mRecorder.setOutputFormat(MediaRecorder.OutputFormat.RAW_AMR);
            // 设置音频的编码格式为amr
            mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

            // 严格遵守google官方api给出的mediaRecorder的状态流程图
            mRecorder.prepare();

            mRecorder.start();
            // 准备结束
            isPrepared = true;
            // 已经准备好了，可以录制了
            if (mListener != null) {
                mListener.wellPrepared();
            }

        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 随机生成文件的名称
     *
     * @return
     */
    private String generalFileName() {
        return UUID.randomUUID().toString() + ".amr";
    }

    // 获得声音的level
    public int getVoiceLevel(int maxLevel) {
        // mRecorder.getMaxAmplitude()这个是音频的振幅范围，值域是1-32767
        if (isPrepared) {
            try {
                // 取正+1，否则去不到7
                return maxLevel * mRecorder.getMaxAmplitude() / 32768 + 1;
            } catch (Exception e) {
                if (e != null) {
                    ;
                }
            }
        }

        return 1;
    }

    // 释放资源
    public void release() {
        if (mRecorder == null) {
            return;
        }
        // 严格按照api流程进行
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
    }

    // 取消,因为prepare时产生了一个文件，所以cancel方法应该要删除这个文件，
    // 这是与release的方法的区别
    public void cancel() {
        release();
        if (mCurrentFilePathString != null) {
            File file = new File(mCurrentFilePathString);
            file.delete();
            mCurrentFilePathString = null;
        }
    }

    public String getCurrentFilePath() {
        return mCurrentFilePathString;
    }

}
