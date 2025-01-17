package com.titlark.utils;

import junit.framework.TestCase;

import java.io.IOException;

public class ImageUtilTest extends TestCase {

    public void testCutPictures() {
        try {
            String imageFilePath = "C:\\Users\\lark\\Desktop\\1.jpg";
            String outFileDir = "C:\\Users\\lark\\Desktop\\test";
            //横向分隔个数
            int xCopies = 3;
            //纵向分隔个数
            int yCopies = 3;

            ImageUtil.cutPictures(imageFilePath, outFileDir, 3, 9);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void testMergePictures() {
        try {
            String imageFileDir = "C:\\Users\\lark\\Desktop\\test";
            String outFilePath = "C:\\Users\\lark\\Desktop\\merge.png";
            ImageUtil.mergePictures(imageFileDir, outFilePath, 3, 9);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
