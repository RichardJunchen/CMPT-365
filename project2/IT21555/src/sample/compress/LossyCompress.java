package sample.compress;

import java.io.*;

/**
 * @author author
 * @version 1.0
 * @className MyCompress
 * @descriptioz TODO
 * @date 2021/4/22 19:41
 */
public class LossyCompress {


    public static PixImage readFrom(String fileName) {
        ObjectInputStream inObj = null;
        try {
            FileInputStream inStr = new FileInputStream(fileName);
            byte[] buffer = new byte[4];
            inStr.read(buffer);
            int width = toInt(buffer);
            inStr.read(buffer);
            int height = toInt(buffer);
            PixImage pixImage = new PixImage(width, height);
            buffer = new byte[2];
            for (int index = 0; index < pixImage.getWidth(); index++) {
                for (int j = 0; j < pixImage.getHeight(); j++) {
                    inStr.read(buffer);
                    short rgb16 = toShort(buffer);
                    pixImage.setPixel(index, j, (short) (((rgb16 >> 10) & 0x1F )<<3), (short) (((rgb16 >> 5) & 0x1F )<<3),
                            (short) (((rgb16 & 0x1F ))<<3));
                }
            }
            return pixImage;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inObj != null) {
                try {
                    inObj.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println(toInt(toLH(20)));
        System.out.println(toInt(toLH(20)));
        PixImage pixImage = new PixImage(5, 5);
        pixImage.setPixel(0, 0, (short) 127, (short) 5, (short) 254);
        writeTo(pixImage, "123.tiff");
        PixImage pixImage1 = readFrom("123.tiff");
        System.out.println(pixImage1.getRed(0, 0));
        System.out.println(pixImage1.getBlue(0, 0));
        System.out.println(pixImage1.getGreen(0, 0));

//        short rgb16 = toShort(toLH((short) 15391));
//        System.out.println(rgb16);
//        System.out.println((short) (((rgb16 >> 10) & 0x1F) <<3));
//        System.out.println((short) (((rgb16 >> 5) & 0x1F) <<3));
//        System.out.println((short) ((rgb16 & 0x1F)) <<3);
    }

    public static byte[] toLH(short n) {
        byte[] b = new byte[2];
        b[0] = (byte) (n & 0xff);
        b[1] = (byte) (n >> 8 & 0xff);
        return b;
    }

    public static short toShort(byte[] b) {
        short res = 0;
        for (int i = 0; i < b.length; i++) {
            res += (b[i] & 0xff) << (i * 8);
        }
        return res;
    }

    public static byte[] toLH(int n) {
        byte[] b = new byte[4];
        b[0] = (byte) (n & 0xff);
        b[1] = (byte) (n >> 8 & 0xff);
        b[2] = (byte) (n >> 16 & 0xff);
        b[3] = (byte) (n >> 24 & 0xff);
        return b;
    }

    public static int toInt(byte[] b) {
        int res = 0;
        for (int i = 0; i < b.length; i++) {
            res += (b[i] & 0xff) << (i * 8);
        }
        return res;
    }

    public static void writeTo(PixImage pixImage, String fileName) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(fileName);
            byte[] data = toLH(pixImage.getWidth());
            fos.write(data);
            data = toLH(pixImage.getHeight());
            fos.write(data);
            for (int index = 0; index < pixImage.getWidth(); index++) {
                for (int j = 0; j < pixImage.getHeight(); j++) {
                    short r = pixImage.getRed(index, j);
                    short g = pixImage.getGreen(index, j);
                    short b = pixImage.getBlue(index, j);
                    int rgb16 = ((r >> 3) << 10) | ((g >> 3) << 5) | (b >> 3);
                    fos.write(toLH((short) rgb16));
                }
            }
            fos.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
