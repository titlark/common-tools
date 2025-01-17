package com.titlark.utils;

import java.io.*;
import java.nio.file.Files;
import java.util.Enumeration;
import java.util.zip.*;

/**
 * zip 压缩
 */
public class ZipUtil {

    private ZipUtil() {
    }

    /**
     * 文件压缩
     *
     * @param srcPath 源文件路径
     * @param dstPath 压缩后的文件路径
     * @return true：压缩成功 false：压缩失败
     */
    public static boolean compress(String srcPath, String dstPath) {
        File srcFile = new File(srcPath);
        File dstFile = new File(dstPath);
        if (!srcFile.exists()) return false;

        try (FileOutputStream out = new FileOutputStream(dstFile); CheckedOutputStream cos = new CheckedOutputStream(out, new CRC32()); ZipOutputStream zipOut = new ZipOutputStream(cos)) {
            return compress(srcFile, zipOut, "");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 文件压缩
     *
     * @param file    待压缩文件
     * @param zipOut  输出流
     * @param baseDir 输出路径
     * @return
     */
    private static boolean compress(File file, ZipOutputStream zipOut, String baseDir) {
        if (file.isDirectory()) {
            return compressDirectory(file, zipOut, baseDir);
        } else {
            return compressFile(file, zipOut, baseDir);
        }
    }

    /**
     * 压缩一个目录
     *
     * @param dir     待压缩目录
     * @param zipOut  输出流
     * @param baseDir 输出路径
     * @return true：压缩成功 false：压缩失败
     * @throws IOException 异常
     */
    private static boolean compressDirectory(File dir, ZipOutputStream zipOut, String baseDir) {
        File[] files = dir.listFiles();
        assert files != null;
        for (File file : files) {
            if (!compress(file, zipOut, baseDir + dir.getName() + "/")) return false;
        }
        return true;
    }

    /**
     * 压缩一个文件
     *
     * @param file    待压缩文件
     * @param zipOut  输出zip流
     * @param baseDir 输出路径
     * @return true：压缩成功 false：压缩失败
     */
    private static boolean compressFile(File file, ZipOutputStream zipOut, String baseDir) {
        if (!file.exists()) return false;
        try (BufferedInputStream bis = new BufferedInputStream(Files.newInputStream(file.toPath()))) {
            ZipEntry entry = new ZipEntry(baseDir + file.getName());
            zipOut.putNextEntry(entry);
            // zipOut不能关闭
            return write(bis, zipOut);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 写数据
     *
     * @param inputStream  输入流
     * @param outputStream 输出流
     * @return true：写入成功 false：写入失败
     */
    private static boolean write(InputStream inputStream, OutputStream outputStream) {
        try {
            int len = 0;
            byte[] buffer = new byte[1024];
            while ((len = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, len);
            }
            inputStream.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将Zip文件加压缩出来，包含所有文件和文件夹到目标目录
     *
     * @param zipFilePath 压缩包文件路径
     * @param dstPath     解压路径
     * @return true：解压成功 false：解压失败
     */
    public static boolean decompress(String zipFilePath, String dstPath) {
        try {
            FileUtil.makeDir(dstPath, true);
            ZipFile zip = new ZipFile(zipFilePath);
            for (Enumeration<? extends ZipEntry> entries = zip.entries(); entries.hasMoreElements(); ) {
                ZipEntry entry = entries.nextElement();
                String zipEntryName = entry.getName();
                String outPath = (dstPath + "/" + zipEntryName).replaceAll("\\*", "/");
                //判断路径是否存在,不存在则创建文件路径
                if (!FileUtil.makeDir(outPath, false)) return false;
                //判断文件全路径是否为文件夹,如果是上面已经上传,不需要解压
                if (new File(outPath).isDirectory()) {
                    continue;
                }

                if (!FileUtil.write(zip.getInputStream(entry), outPath)) return false;
            }
            zip.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

}
