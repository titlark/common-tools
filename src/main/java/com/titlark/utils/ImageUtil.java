package com.titlark.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * 图片工具类
 */
public class ImageUtil {

    private ImageUtil() {
    }

    /**
     * 切割图片
     *
     * @param imageFilePath 图片路径
     * @param outDir        切割后图片的输出目录
     * @param xCopies       横向分隔个数
     * @param yCopies       纵向分隔个数
     */
    public static boolean cutPictures(String imageFilePath, String outDir, int xCopies, int yCopies) throws IOException {
        File file = new File(imageFilePath);
        if (!file.exists() || !file.isFile()) {
            return false;
        }
        BufferedImage image = ImageIO.read(file);
        int totalWidth = image.getWidth();
        int totalHeight = image.getHeight();
        int width = totalWidth / (xCopies <= 0 ? 1 : xCopies);
        int height = totalHeight / (yCopies <= 0 ? 1 : yCopies);

        // 图片的宽度必须大于切割的份数
        if (totalWidth < xCopies) {
            throw new RuntimeException("图片的宽度必须大于横向切割的份数!");
        }
        if (totalHeight < yCopies) {
            throw new RuntimeException("图片的高度必须大于纵向切割的份数!");
        }

        File dirFile = new File(outDir);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }

        // 获取文件名
        String filename = FileUtil.getPrefixName(file.getName());
        String suffix = FileUtil.getSuffixName(file.getName());
        String formatName = suffix.replace(".", "");

        for (int y = 0, j = 1; y <= totalHeight - height; y += height, j++) {
            for (int x = 0, i = 1; x <= totalWidth - width; x += width, i++) {
                File targetFile = new File(dirFile, filename + "_" + j + "_" + i + suffix);
                BufferedImage subimage = image.getSubimage(x, y, width, height);
                ImageIO.write(subimage, formatName, targetFile);
            }
        }
        return true;
    }

    public static boolean mergePictures(String imageFileDir, String outImageFilePath, int xCopies, int yCopies) throws IOException {
        File[] files = new File(imageFileDir).listFiles();
        if (files.length != xCopies * yCopies) {
            throw new RuntimeException("切割的份数和文件的个数不匹配！");
        }
        // 存放所有的图片
        BufferedImage[] images = new BufferedImage[files.length];
        int totalWidth = 0;
        int totalHeight = 0;
        // 计算宽高
        for (int i = 0; i < files.length; i++) {
            BufferedImage image = ImageIO.read(files[i]);
            images[i] = image;
            if (i < xCopies) {
                totalWidth += image.getWidth();
            }
            if (i < yCopies) {
                totalHeight += image.getHeight();
            }
        }
        int imageType = images[0].getType();
        BufferedImage bi = new BufferedImage(totalWidth, totalHeight, imageType);
        Graphics2D g2d = bi.createGraphics();
        for (int i = 0, z = 0; i < yCopies; i++) {
            for (int j = 0; j < xCopies; j++) {
                BufferedImage image = images[z];
                g2d.drawImage(image, j * image.getWidth(), i * image.getHeight(), image.getWidth(), image.getHeight(), null);
                z++;
            }
        }
        g2d.dispose();
        String suffix = FileUtil.getSuffixName(outImageFilePath);
        String formatName = suffix.replace(".", "");
        ImageIO.write(bi, formatName, new File(outImageFilePath));
        return true;
    }

}
