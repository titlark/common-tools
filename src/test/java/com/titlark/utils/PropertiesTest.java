package com.titlark.utils;

import junit.framework.TestCase;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class PropertiesTest extends TestCase {

    public void testReadStringProp() {
        String driver = PropertiesUtil.readString("jdbc", "jdbc.driver");
        System.out.println(driver);
    }

    public void readHttpsFile() {
        try {
            InputStream inputStream = FileUtil.readRemote("https://testeseal.certca.cn:8088/api/previewImageByFileId/758cc89ef6a811ecbf50005056813149");
            BufferedImage bufferedImage = ImageIO.read(inputStream);
            System.out.println("bufferedImage.getWidth() = " + bufferedImage.getWidth());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
