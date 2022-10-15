package sample.compress;

import java.io.*;

/**
 * @author author
 * @version 1.0
 * @className MyCompress
 * @descriptioz TODO
 * @date 2021/4/22 19:41
 */
public class MyCompress {


    public static PixImage readFrom(String fileName){
        ObjectInputStream inObj = null;
        try {
            FileInputStream inStr = new FileInputStream(fileName);
            inObj = new ObjectInputStream(inStr);
            PixImage pixImage = (PixImage) inObj.readObject();
            return pixImage;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
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
        PixImage pixImage = new PixImage(5,5);
        writeTo(pixImage,"123.tiff");
        System.out.println(readFrom("123.tiff").getWidth());;
    }

    public static void writeTo(PixImage pixImage,String fileName){
        ObjectOutputStream outObj=null;
        try {
            FileOutputStream outStr=new FileOutputStream(fileName);
            outObj=new ObjectOutputStream(outStr);
            outObj.writeObject(pixImage);
            outObj.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            try {
                if (outObj != null) {
                    outObj.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
