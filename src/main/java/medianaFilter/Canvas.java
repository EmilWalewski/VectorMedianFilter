package medianaFilter;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Canvas {//extends JPanel {

    public Canvas() {

        //repaint();
        getPixelArrayFromImage(generateImage());

    }

    public BufferedImage generateImage(){

        BufferedImage image = null;

        try {

            image = ImageIO.read(new File("D:\\Images\\ww_fullhd.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;
    }

    private void getPixelArrayFromImage(BufferedImage image){

        final byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        final int width = image.getWidth();
        final int height = image.getHeight();
        final boolean hasAlphaChannel = image.getAlphaRaster() != null;

        int[][] result = new int[height][width];

        if (hasAlphaChannel) {
            final int pixelLength = 4;
            for (int pixel = 0, row = 0, col = 0; pixel + 3 < pixels.length; pixel += pixelLength) {
                int argb = 0;
                argb += (((int) pixels[pixel] & 0xff) << 24); // alpha
                argb += ((int) pixels[pixel + 1] & 0xff); // blue
                argb += (((int) pixels[pixel + 2] & 0xff) << 8); // green
                argb += (((int) pixels[pixel + 3] & 0xff) << 16); // red
                result[row][col] = argb;
                col++;
                if (col == width) {
                    col = 0;
                    row++;
                }
            }
        } else {
            final int pixelLength = 3;
            for (int pixel = 0, row = 0, col = 0; pixel + 2 < pixels.length; pixel += pixelLength) {
                int argb = 0;
               argb += -16777216; // 255 alpha
                argb += ((int) pixels[pixel] & 0xff); // blue
                argb += (((int) pixels[pixel + 1] & 0xff) << 8); // green
                argb += (((int) pixels[pixel + 2] & 0xff) << 16); // red


                //System.out.println(((int) pixels[pixel] & 0xff)+" "+(((int) pixels[pixel + 1] & 0xff) << 8)+" "+(((int) pixels[pixel + 2] & 0xff) << 16)+" "+(int) pixels[pixel]);
                System.out.println((((int) pixels[pixel] & 0xff) << 24)+" "+((int) pixels[pixel] & 0xff)+" "+(((int) pixels[pixel + 1] & 0xff) << 8)+" "+(((int) pixels[pixel + 2] & 0xff) << 16)+" "+(int) pixels[pixel]);
                System.out.println();

                result[row][col] = argb;
                col++;
                if (col == width) {
                    col = 0;
                    row++;
                }
            }
        }

//        for (int i=0; i<height; i++)
//            for (int j=0; j <width; j++){
//
//                //if (result[i][j] == -13814977) result[i][j] = 0;
//                //System.out.print(result[i][j]+" ");
//                if (j == width-1) System.out.println("");
//            }

        BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        WritableRaster raster = newImage.getRaster();

        ArrayList<Integer> toReturn = new ArrayList<>();
        for (int[] sublist : Arrays.asList(result)) {
            for (int elem : sublist) {
                toReturn.add(elem);
            }
        }
        int[] arr = new int[toReturn.size()];

        for (int i=0; i<arr.length; i++) arr[i] = toReturn.get(i);

        raster.setDataElements(0,0, width, height, arr);
        File f = new File("");

        try {

            ImageIO.write(newImage, "jpg", f);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
