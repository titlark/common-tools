package com.titlark.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 多线程下载
 * 1. 获取文件大小
 * 2. 在客户端创建一个大小和服务器一模一样的文件提前申请好空间
 * 2.1 每个线程下载的开始位置和
 * 3. 开多个线程去下载文件
 * 4. 知道每个线程什么时候下载完毕了
 */
public class MultiThreadDownload {


    /**
     * 多线程下载
     *
     * @param url         下载路径
     * @param destPath    输出路径
     * @param threadCount 下载线程的个数
     */
    public static boolean download(String url, String destPath, int threadCount) {
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(60000);
            if (conn.getResponseCode() == 200) {
                int contentLength = conn.getContentLength();
                if (!FileUtil.makeDir(destPath, false)) return false;
                RandomAccessFile raf = new RandomAccessFile(destPath, "rw");
                raf.setLength(contentLength);
                int blockSize = contentLength / threadCount;
                for (int i = 0; i < threadCount; i++) {
                    //每个现成下载的开始位置
                    int startIndex = i * blockSize;
                    // 每个线程的结束位置
                    int endIndex = (i + 1) * blockSize - 1;
                    //最后一个线程
                    if (i == threadCount - 1) {
                        endIndex = contentLength - 1;
                    }
                    new DownloadThread(url, startIndex, endIndex, destPath).start();
                }
                return true;
            }
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static class DownloadThread extends Thread {
        private String url;
        private int startIndex;
        private int endIndex;
        private String destPath;

        DownloadThread(String url, int startIndex, int endIndex, String destPath) {
            this.url = url;
            this.startIndex = startIndex;
            this.endIndex = endIndex;
            this.destPath = destPath;
        }

        @Override
        public void run() {
            try {
                HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(60000);
                // 固定写法，请求部分资源
                conn.setRequestProperty("Range", "bytes=" + startIndex + "-" + endIndex);
                // 206表示请求部分资源
                if (conn.getResponseCode() == 206) {
                    RandomAccessFile raf = new RandomAccessFile(destPath, "rw");
                    raf.seek(startIndex);
                    int len = 0;
                    byte[] buffer = new byte[1024];
                    InputStream is = conn.getInputStream();
                    while (-1 != (len = is.read(buffer))) {
                        raf.write(buffer, 0, len);
                    }
                    raf.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}