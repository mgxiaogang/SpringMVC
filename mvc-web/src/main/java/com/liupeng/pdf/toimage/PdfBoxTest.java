package com.liupeng.pdf.toimage;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;

/**
 * PDF转image
 *
 * @author fengdao.lp
 * @date 2018/7/11
 */
public class PdfBoxTest {
    public static List<BufferedImage> convertToImage(File file) throws IOException {
        PDDocument document = PDDocument.load(file);
        PDFRenderer pdfRenderer = new PDFRenderer(document);
        List<BufferedImage> bufferedImageList = new ArrayList<>();

        for (int page = 0; page < document.getNumberOfPages(); page++) {
            BufferedImage img = pdfRenderer.renderImageWithDPI(page, 300, ImageType.RGB);
            bufferedImageList.add(img);
        }
        document.close();
        return bufferedImageList;
    }

    public static BufferedImage concat(BufferedImage[] images) throws IOException {
        int heightTotal = 0;
        for (int j = 0; j < images.length; j++) {
            heightTotal += images[j].getHeight();
        }

        int heightCurr = 0;
        BufferedImage concatImage = new BufferedImage(images[0].getWidth(), heightTotal, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = concatImage.createGraphics();
        for (int j = 0; j < images.length; j++) {
            g2d.drawImage(images[j], 0, heightCurr, null);
            heightCurr += images[j].getHeight();
        }
        g2d.dispose();

        return concatImage;
    }

    /**
     * 将目标图片缩小成256*256并保存
     */
    public static void targetZoomOut(BufferedImage bufferedImage) {

        try {
            //Image big = bufferedImage.getScaledInstance(256, 256, Image.SCALE_DEFAULT);
            //BufferedImage inputbig = new BufferedImage(256, 256, BufferedImage.TYPE_INT_BGR);
            //画图
            //inputbig.getGraphics().drawImage(bufferedImage, 0, 0, 256, 256, null);
            bufferedImage.getGraphics().drawImage(bufferedImage, 0, 0, 256, 256, null);
            //此目录保存缩小后的关键图
            File file2 = new File("/Users/liupeng/pdftest");
            if (file2.exists()) {
                System.out.println("多级目录已经存在不需要创建！！");
            } else {
                //如果要创建的多级目录不存在才需要创建。
                file2.mkdirs();
            }
            //将其保存在C:/imageSort/targetPIC/下
            ImageIO.write(bufferedImage, "jpg",
                new File("/Users/liupeng/pdftest/" + "aaa"));
        } catch (Exception ex) {ex.printStackTrace();}
    }

    public static void main(String[] args) throws IOException {
        List<BufferedImage> list = convertToImage(new File("/Users/liupeng/children.pdf"));
        BufferedImage[] bufferedImages = new BufferedImage[list.size()];
        Object[] objects = list.toArray();
        for (int i = 0; i < objects.length; i++) {
            BufferedImage bufferedImage = (BufferedImage)objects[i];
            bufferedImages[i] = bufferedImage;
        }
        BufferedImage bufferedImage = concat(bufferedImages);
        targetZoomOut(bufferedImage);
        System.out.println("success");
    }
}
