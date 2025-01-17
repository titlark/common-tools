package com.titlark.utils;

import javax.net.ssl.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.DecimalFormat;
import java.util.Base64;
import java.util.List;

/**
 * 文件工具类
 */
public class FileUtil {
    private FileUtil() {
    }

    /**
     * 默认缓冲区大小
     */
    private static final int DEFAULT_BUFFER_SIZE = 1024;

    /**
     * 读取文件
     *
     * @param inFilePath 输入文件
     * @return 文件字节数组
     * @throws IOException 异常
     */
    public static byte[] read(String inFilePath) {
        return read(inFilePath, DEFAULT_BUFFER_SIZE);
    }

    /**
     * 读取文件
     *
     * @param inFilePath 输入文件
     * @param bufferSize 缓冲区大小
     * @return 文件字节数组
     * @throws IOException 异常
     */
    public static byte[] read(String inFilePath, int bufferSize) {
        try (FileInputStream fis = new FileInputStream(inFilePath)) {
            return convert(fis, bufferSize);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 读取文件
     *
     * @param inFile 输入文件
     * @return 文件字节数组
     * @throws IOException 异常
     */
    public static byte[] read(File inFile) {
        return read(inFile, DEFAULT_BUFFER_SIZE);
    }

    /**
     * 读取文件
     *
     * @param inFile     输入文件
     * @param bufferSize 缓冲区大小
     * @return 文件字节数组
     * @throws IOException 异常
     */
    public static byte[] read(File inFile, int bufferSize) {
        try (FileInputStream fis = new FileInputStream(inFile)) {
            return convert(fis, bufferSize);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 读取流
     *
     * @param inputStream 输入流
     * @return 文件字节数组
     * @throws IOException 异常
     */
    public static byte[] read(InputStream inputStream) {
        return convert(inputStream, DEFAULT_BUFFER_SIZE);
    }

    /**
     * 读取流
     *
     * @param inputStream 输入流
     * @return 文件字节数组
     * @throws IOException 异常
     */
    public static byte[] read(InputStream inputStream, int bufferSize) {
        return convert(inputStream, bufferSize);
    }

    /**
     * 读取指定长度的byte
     *
     * @param src    源数据
     * @param offset 偏移量
     * @param len    读取的长度
     * @return 文件字节数组
     */
    public static byte[] read(byte[] src, int offset, int len) {
        byte[] dest = new byte[len];
        System.arraycopy(src, offset, dest, 0, len);
        return dest;
    }

    /**
     * 读取远程文件
     *
     * @param remoteUrl 文件url
     * @return 文件字节数组
     */
    public static InputStream readRemote(String remoteUrl) {
        return isHttps(remoteUrl) ? readHttps(remoteUrl) : readHttp(remoteUrl);
    }

    /**
     * 读取http请求
     *
     * @param remoteUrl 文件url
     * @return 文件字节数组
     */
    private static InputStream readHttp(String remoteUrl) {
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(remoteUrl).openConnection();
            conn.setConnectTimeout(60000);
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                return conn.getInputStream();
            }
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 读取https请求
     *
     * @param remoteUrl 文件url
     * @return 文件字节数组
     */
    private static InputStream readHttps(String remoteUrl) {
        try {
            HttpsURLConnection conn = (HttpsURLConnection) new URL(remoteUrl).openConnection();
            conn.setSSLSocketFactory(getSSLSocketFactory());
            conn.setConnectTimeout(60000);
            if (conn.getResponseCode() == HttpsURLConnection.HTTP_OK) {
                return conn.getInputStream();
            }
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取SSLSocketFactory对象
     *
     * @return
     */
    private static SSLSocketFactory getSSLSocketFactory() {
        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

            }

            @Override
            public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        }};
        // Install the all-trusting trust manager
        try {
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            return sslContext.getSocketFactory();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 是否是https
     *
     * @param remoteUrl 文件url
     * @return
     */
    private static boolean isHttps(String remoteUrl) {
        return remoteUrl.toLowerCase().startsWith("https");
    }

    /**
     * 写文件
     *
     * @param data        文件数据
     * @param outFilePath 输出文件路径
     * @throws IOException 异常
     */
    public static boolean write(byte[] data, String outFilePath) {
        try (FileOutputStream fos = new FileOutputStream(outFilePath)) {
            fos.write(data);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 写文件
     *
     * @param data    文件数据
     * @param outFile 输出文件路径
     * @throws IOException 异常
     */
    public static boolean write(byte[] data, File outFile) {
        try (FileOutputStream fos = new FileOutputStream(outFile)) {
            fos.write(data);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 写文件
     *
     * @param inputStream 输入流
     * @param outFilePath 输出文件路径
     */
    public static boolean write(InputStream inputStream, String outFilePath) {
        return write(inputStream, outFilePath, DEFAULT_BUFFER_SIZE);
    }

    /**
     * 写文件
     *
     * @param inputStream 输入流
     * @param outFilePath 输出文件路径
     * @param bufferSize  缓冲区大小
     */
    public static boolean write(InputStream inputStream, String outFilePath, int bufferSize) {
        return write(inputStream, new File(outFilePath), bufferSize);
    }

    /**
     * 写文件
     *
     * @param inputStream 输入流
     * @param outFile     输出文件
     */
    public static boolean write(InputStream inputStream, File outFile) {
        return write(inputStream, outFile, DEFAULT_BUFFER_SIZE);
    }

    /**
     * 写文件
     *
     * @param inputStream 输入流
     * @param outFile     输出文件
     * @param bufferSize  缓冲区大小
     */
    public static boolean write(InputStream inputStream, File outFile, int bufferSize) {
        try (BufferedOutputStream bos = new BufferedOutputStream(Files.newOutputStream(outFile.toPath()))) {
            int len = 0;
            byte[] buffer = new byte[bufferSize];
            while ((len = inputStream.read(buffer)) != -1) {
                bos.write(buffer, 0, len);
            }
            bos.flush();
            inputStream.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 写文件
     *
     * @param inputStream  输入流
     * @param outputStream 输出流
     */
    public static boolean write(InputStream inputStream, OutputStream outputStream) {
        return write(inputStream, outputStream, DEFAULT_BUFFER_SIZE);
    }

    /**
     * 写文件
     *
     * @param inputStream  输入流
     * @param outputStream 输出流
     * @param bufferSize   缓冲区大小
     */
    public static boolean write(InputStream inputStream, OutputStream outputStream, int bufferSize) {
        try {
            int len = 0;
            byte[] buffer = new byte[bufferSize];
            while ((len = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, len);
            }
            inputStream.close();
            outputStream.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 写文件
     *
     * @param data         文件数据
     * @param outputStream 输出流
     */
    public static boolean write(byte[] data, OutputStream outputStream) {
        return write(data, outputStream, DEFAULT_BUFFER_SIZE);
    }

    /**
     * 写文件
     *
     * @param data         文件数据
     * @param outputStream 输出流
     * @param bufferSize   缓冲区大小
     */
    public static boolean write(byte[] data, OutputStream outputStream, int bufferSize) {
        try (ByteArrayInputStream bais = new ByteArrayInputStream(data)) {
            int len = 0;
            byte[] buffer = new byte[bufferSize];
            while ((len = bais.read(buffer)) != -1) {
                outputStream.write(buffer, 0, len);
            }
            outputStream.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * InputStream 转换为 byte[]
     *
     * @param inputStream 输入流
     * @return 字节数组
     * @throws IOException 异常
     */
    public static byte[] convert(InputStream inputStream) {
        return convert(inputStream, DEFAULT_BUFFER_SIZE);
    }

    /**
     * InputStream 转换为 byte[]
     *
     * @param inputStream 输入流
     * @param bufferSize  缓存区大小
     * @return 字节数组
     * @throws IOException 异常
     */
    public static byte[] convert(InputStream inputStream, int bufferSize) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[bufferSize];
            int len = 0;
            while ((len = inputStream.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }
            return baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * byte[] 转换为 InputStream
     *
     * @param data 字节数组
     * @return 输入流
     */
    public static InputStream convert(byte[] data) {
        return new ByteArrayInputStream(data);
    }

    /**
     * 拷贝文件
     *
     * @param inFilePath  输入文件路径
     * @param outFilePath 输出文件路径
     * @return true：拷贝成功 false：拷贝失败
     */
    public static boolean copyFile(String inFilePath, String outFilePath) {
        return copyFile(inFilePath, outFilePath, DEFAULT_BUFFER_SIZE);
    }

    /**
     * 拷贝文件
     *
     * @param inFilePath  输入文件路径
     * @param outFilePath 输出文件路径
     * @param bufferSize  缓冲区大小
     * @return true：拷贝成功 false：拷贝失败
     */
    public static boolean copyFile(String inFilePath, String outFilePath, int bufferSize) {
        return copyFile(new File(inFilePath), new File(outFilePath), bufferSize);
    }

    /**
     * 拷贝文件
     *
     * @param inFile  输入文件
     * @param outFile 输出文件
     * @return true：拷贝成功 false：拷贝失败
     */
    public static boolean copyFile(File inFile, File outFile) {
        return copyFile(inFile, outFile, DEFAULT_BUFFER_SIZE);
    }

    /**
     * 拷贝文件
     *
     * @param inFile     输入文件
     * @param outFile    输出文件
     * @param bufferSize 缓冲区大小
     * @return true：拷贝成功 false：拷贝失败
     */
    public static boolean copyFile(File inFile, File outFile, int bufferSize) {
        try (FileInputStream fis = new FileInputStream(inFile); FileOutputStream fos = new FileOutputStream(outFile)) {
            int len = 0;
            byte[] buffer = new byte[bufferSize];
            while ((len = fis.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * InputStream 转换为 Base64
     *
     * @param inputStream 输入流
     * @return base64字符串
     */
    public static String getBase64FromInputStream(InputStream inputStream) {
        return Base64.getEncoder().encodeToString(convert(inputStream));
    }

    /**
     * File 转换为 Base64
     *
     * @param inFile 输入文件
     * @return base64字符串
     */
    public static String getBase64FromFile(File inFile) {
        return Base64.getEncoder().encodeToString(read(inFile));
    }

    /**
     * Base64 转换为文件
     *
     * @param base64String base64字符串
     * @param outFilePath  输出文件路径
     * @return true：写入文件成功 false：写入文件失败
     */
    public static boolean convertFile(String base64String, String outFilePath) {
        return write(Base64.getDecoder().decode(base64String), outFilePath);
    }

    /**
     * Base64 转换为文件
     *
     * @param base64String base64字符串
     * @param outFile      输出文件路径
     * @return true：写入文件成功 false：写入文件失败
     */
    public static boolean convertFile(String base64String, File outFile) {
        return write(Base64.getDecoder().decode(base64String), outFile);
    }

    /**
     * 删除文件
     *
     * @param filePath 待文件路径
     * @return true：删除成功 false：删除失败
     */
    public static boolean deleteFile(String filePath) {
        return deleteFile(new File(filePath));
    }

    /**
     * 删除文件
     *
     * @param targetFile 待删除文件
     * @return true：删除成功 false：删除失败
     */
    public static boolean deleteFile(File targetFile) {
        return targetFile.delete();
    }

    /**
     * 获取文件大小
     *
     * @param filePath 文件路径
     * @return 文件大小
     */
    public static long getFileSize(String filePath) {
        return getFileSize(new File(filePath));
    }

    /**
     * 获取文件大小
     *
     * @param targetFile 目标文件
     * @return 文件大小
     */
    public static long getFileSize(File targetFile) {
        return targetFile.exists() ? targetFile.length() : 0;
    }

    /**
     * 格式化文件大小
     *
     * @param fileSize 文件大小
     * @return 格式化后的文件大小
     */
    public static String formatFileSize(long fileSize) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        if (fileSize < 1024) {
            fileSizeString = df.format((double) fileSize) + "B";
        } else if (fileSize < (1024 * 1024)) {
            fileSizeString = df.format((double) fileSize / 1024) + "K";
        } else if (fileSize < (1024 * 1024 * 1024)) {
            fileSizeString = df.format((double) fileSize / (1024 * 1024)) + "M";
        } else {
            fileSizeString = df.format((double) fileSize / (1024 * 1024 * 1024)) + "G";
        }
        return fileSizeString;
    }

    /**
     * 创建目录
     *
     * @param path  文件路径
     * @param isDir 是否是目录
     * @return true：创建成功 false：创建失败
     */
    public static boolean makeDir(String path, boolean isDir) {
        if (isDir) {
            new File(path).mkdirs();
        } else {
            new File(path).getParentFile().mkdirs();
        }
        return true;
    }

    /**
     * 获取文件前缀名
     *
     * @param filename 文件名
     * @return 文件前缀
     */
    public static String getPrefixName(String filename) {
        int index = filename.lastIndexOf(".");
        if (-1 == index) return filename;
        return filename.substring(0, index);
    }

    /**
     * 获取后缀名
     *
     * @param filename 文件名
     * @return 文件后缀名
     */
    public static String getSuffixName(String filename) {
        int index = filename.lastIndexOf(".");
        if (-1 == index) return "";
        return filename.substring(index);
    }

    /**
     * 把文件分割成指定大小的文件
     *
     * @param filePath       源文件
     * @param targetFilePath 分割后的文件路径
     * @param targetFileSize 分割后每个文件的大小，单位：B(字节)
     * @return
     */
    public static boolean split(String filePath, String targetFilePath, int targetFileSize) {
        try (BufferedInputStream bis = new BufferedInputStream(Files.newInputStream(Paths.get(filePath)))) {
            if (!makeDir(targetFilePath, true)) return false;
            int fileSize = bis.available();
            String filename = new File(filePath).getName();
            if (targetFileSize >= fileSize) {
                copyFile(filePath, targetFilePath + "/" + filename);
            }
            int times = fileSize / targetFileSize;
            int mod = fileSize % targetFileSize;
            if (0 != mod) times += 1;

            for (int i = 0; i < times; i++) {
                byte[] buffer = new byte[i != times - 1 ? targetFileSize : mod];
                bis.read(buffer);
                BufferedOutputStream bos = new BufferedOutputStream(Files.newOutputStream(Paths.get(targetFilePath + "/" + getPrefixName(filename) + i + getSuffixName(filename))));
                bos.write(buffer);
                bos.flush();
                bos.close();
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 合并文件（把分割后的文件合并成一个文件）
     *
     * @param filePathList   分割后的文件路径集合
     * @param targetFilePath 合并后的输入路径
     * @return
     */
    public static boolean merge(List<String> filePathList, String targetFilePath) {
        try (BufferedOutputStream bos = new BufferedOutputStream(Files.newOutputStream(Paths.get(targetFilePath)))) {
            for (String filePath : filePathList) {
                BufferedInputStream bis = new BufferedInputStream(Files.newInputStream(Paths.get(filePath)));
                int len = 0;
                byte[] buffer = new byte[1024 * 1024];
                while (-1 != (len = bis.read(buffer))) {
                    bos.write(buffer, 0, len);
                }
                bis.close();
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
