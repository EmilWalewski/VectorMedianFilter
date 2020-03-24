package medianaFilter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Canvas2 {

    public Canvas2() {
        getRGB();
    }

    public void getRGB(){

        BufferedImage img = null;
        File f;


        try{
            f = new File("Put path to your noisy picture");
            img = ImageIO.read(f);
        }catch(IOException e){
            System.out.println(e);
        }

        //get image width and height
        int width = img.getWidth();
        int height = img.getHeight();


        BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        int[][] result = new int[height][width];

        int[] mask = new int[9];
        for (int y=0; y < height; y++) {

            for (int x = 0; x < width; x++) {
//
                int p = img.getRGB(x,y);


//                int a = (p>>24) & 0xff;
//
//                int r = (p>>16) & 0xff;
//
//                int g = (p>>8) & 0xff;
//
//                int b = p & 0xff;
//
//               // avg = (r+g+b)/3;
//
//                p = (a<<24) | (r<<16) | (g<<8) | b;

//                if (Math.random() <= 0.01)
//                   p = Color.yellow.getRGB();

                result[y][x] = p;

//                newImage.setRGB(x, y, p);

                if ((y > 0 && y < height - 1) && (x > 0 && x < width - 1)){

                    mask[0] = img.getRGB(x-1, y-1);
                    mask[1] = img.getRGB(x, y-1);
                    mask[2] = img.getRGB(x+1, y-1);
                    mask[3] = img.getRGB(x-1, y);
                    mask[4] = img.getRGB(x, y);
                    mask[5] = img.getRGB(x+1, y);
                    mask[6] = img.getRGB(x-1, y+1);
                    mask[7] = img.getRGB(x, y+1);
                    mask[8] = img.getRGB(x+1, y+1);

                    int[] sortedMask = sort(mask);

                    newImage.setRGB(x, y, sortedMask[sortedMask.length/2]);
                }


            }
        }


        File newF = new File("Put path to your denoised picture");

        try {

            ImageIO.write(newImage, "png", newF);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    /**
     *
     *      Merge sort
     *
     */

    public static int[] sort(int[] numbers) {

        if (numbers.length <= 1) {
            return numbers;
        }

        int[] first = new int[numbers.length / 2];
        int[] second = new int[numbers.length - first.length];

        for (int i = 0; i < first.length; i++) {
            first[i] = numbers[i];
        }

        for (int i = 0; i < second.length; i++) {
            second[i] = numbers[first.length + i];
        }

        return merge(sort(first), sort(second));
    }

    private static int[] merge(int[] first, int[] second) {

        int[] merged = new int[first.length + second.length];

        for (int indexFirst = 0, indexSecond = 0, indexMerged = 0; indexMerged < merged.length; indexMerged++) {

            if (indexFirst >= first.length) {
                merged[indexMerged] = second[indexSecond++];
            }
            else if (indexSecond >= second.length) {
                merged[indexMerged] = first[indexFirst++];
            }
            else if (first[indexFirst] <= second[indexSecond]) {
                merged[indexMerged] = first[indexFirst++];
            }
            else {
                merged[indexMerged] = second[indexSecond++];
            }
        }

        return merged;
    }
}
