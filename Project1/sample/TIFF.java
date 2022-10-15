// The follow code from the code resource online website : https://blog.csdn.net/qq_16555407/article/details/88365151?ops_request_misc=
// %257B%2522request%255Fid%2522%253A%2522161542620316780271573087%2522%252C%2522scm%2522%253A%252220140713.130102334..%
// 2522%257D&request_id=161542620316780271573087&biz_id=0&utm_medium=distribute.pc_search_result.none-task-blog-2~all~
// first_rank_v2~rank_v29-1-88365151.pc_search_result_hbase_insert&utm_term=


package sample;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class TIFF {
    byte[] data;

    boolean ByteOrder;
    public int ImageWidth = 0;
    public int ImageLength = 0;
    public ArrayList<Integer> BitsPerSample = new ArrayList<Integer>();
    public int PixelBytes = 0;
    public int Compression = 0;
    public int PhotometricInterpretation = 0;
    public ArrayList<Integer> StripOffsets = new ArrayList<Integer>();
    public int RowsPerStrip = 0;
    public ArrayList<Integer> StripByteCounts = new ArrayList<Integer>();
    public float XResolution = 0f;
    public float YResolution = 0f;
    public int ResolutionUnit = 0;
    public int Predictor = 0;
    public ArrayList<Integer> SampleFormat = new ArrayList<Integer>();
    public String DateTime = "";
    public String Software = "";
    public ArrayList<Color> colorarray = new ArrayList<Color>();



    private int GetInt(int startPos, int Length)
    {
        int value = 0;
        if (ByteOrder) {
            for (int i = 0; i < Length; i++)
                value |= Byte.toUnsignedInt(data[startPos + i]) << i * 8;
        }
        else {
            for (int i = 0; i < Length; i++)
                value |= Byte.toUnsignedInt(data[startPos + Length - 1 - i]) << i * 8;
        }
        return value;
    }

    private float GetRational(int startPos)
    {
        int A = GetInt(startPos,4);
        int B = GetInt(startPos+4,4);
        return (float)(A/B);
    }
    private String GetString(int startPos, int Length)
    {
        StringBuilder tmp = new StringBuilder();
        for (int i = 0; i < Length; i++)
            tmp.append((char) data[startPos]);
        return tmp.toString();
    }


    private int DecodeIFH(){

        String byteOrder = GetString (0,2);
        if (byteOrder.equals("II"))
            ByteOrder = true;
        else
            ByteOrder = false;
        int Version = GetInt (2,2);
        if (Version != 42)
            throw new IllegalArgumentException("Not TIFF.");
        return GetInt (4,4);

    }

    public void Decode(File selectfile) throws IOException {
        data = Files.readAllBytes(Path.of(selectfile.getPath()));
        int pIFD = DecodeIFH();

        while (pIFD != 0) {
            pIFD = DecodeIFD(pIFD);
        }
    }
    private float GetFloat(byte[] b, int startPos)
    {
        byte[] byteTemp;
        if (ByteOrder)// "II")
            byteTemp =new byte[]{b[startPos],b[startPos+1],b[startPos+2],b[startPos+3]};
        else
            byteTemp =new byte[]{b[startPos+3],b[startPos+2],b[startPos+1],b[startPos]};
        return BitConverter.ToFloat(byteTemp,0);
    }
    // QUESTION PART
    private void DecodeStrips()
    {
        Color temp;
        int index = 0;
        for(int x = 0; x < ImageWidth; x ++)
            {
                int R = GetInt(0, x * PixelBytes   );
                int G = GetInt(x * PixelBytes, x * PixelBytes+4 );
                int B = GetInt(x * PixelBytes+4, x * PixelBytes+8 );
                //int A = GetInt(x * PixelBytes+8, x * PixelBytes+12);
                temp = new Color(R,G,B);
                colorarray.add(temp);
            }
    }
    public int DecodeIFD(int Pos)
    {
        int n = Pos;
        int DECount = GetInt(n, 2);
        n += 2;
        for (int i = 0; i < DECount; i++)
        {
            DecodeDE(n);
            n += 12;
        }
        DecodeStrips();
        return GetInt(n, 4);
    }

    private void GetDEValue(int TagIndex, int TypeIndex, int Count, int pdata,Dtype[] TypeArray)
    {
        int typesize = TypeArray[TypeIndex].size;
        switch (TagIndex)
        {
            case 254: break;//NewSubfileType
            case 255: break;//SubfileType
            case 256://ImageWidth
                ImageWidth = GetInt(pdata,typesize);break;
            case 257://ImageLength
                if (TypeIndex == 3)//short
                    ImageLength = GetInt(pdata,typesize);break;
            case 258://BitsPerSample
                for (int i = 0; i < Count; i++)
                {
                    int v = GetInt(pdata+i*typesize,typesize);
                    BitsPerSample.add(v);
                    PixelBytes += v/8;
                }break;
            case 259: //Compression
                Compression = GetInt(pdata,typesize);break;
            case 262: //PhotometricInterpretation
                PhotometricInterpretation = GetInt(pdata,typesize);break;
                // QUESTION PART
            case 273://StripOffsets
                for (int i = 0; i < Count; i++)
                {
                    int v = GetInt(pdata+i*typesize,typesize);
                    StripOffsets.add(v);
                }break;
            case 274: break;//Orientation
            case 277: break;//SamplesPerPixel
            case 278://RowsPerStrip
                RowsPerStrip = GetInt(pdata,typesize);break;
            case 279://StripByteCounts
                for (int i = 0; i < Count; i++)
                {
                    int v = GetInt(pdata+i*typesize,typesize);
                    StripByteCounts.add(v);
                }break;
            case 282: //XResolution
                XResolution = GetRational(pdata); break;
            case 283://YResolution
                YResolution = GetRational(pdata); break;
            case 284: break;//PlanarConfig
            case 296://ResolutionUnit
                ResolutionUnit = GetInt(pdata,typesize);break;
            case 305://Software
                Software = GetString(pdata,typesize); break;
            case 306://DateTime
                DateTime = GetString(pdata,typesize); break;
            case 315: break;//Artist
            case 317: //Differencing Predictor
                Predictor = GetInt(pdata,typesize);break;
            case 320: break;//ColorDistributionTable
            case 338: break;//ExtraSamples
            case 339: //SampleFormat
                for (int i = 0; i < Count; i++)
                {
                    int v = GetInt(pdata+i*typesize,typesize);
                    SampleFormat.add(v);
                } break;

            default: break;
        }
    }

    public void DecodeDE(int Pos)
    {
        int TagIndex = GetInt(Pos, 2);
        int TypeIndex = GetInt(Pos + 2, 2);
        int Count = GetInt(Pos + 4, 4);

        int pData = Pos + 8;
        Dtype temp = new Dtype(null,0);
        Dtype[] TypeArray = temp.getArray();
        int totalSize = TypeArray[TypeIndex].size * Count;
        if (totalSize > 4)
            pData = GetInt(pData, 4);

        GetDEValue(TagIndex, TypeIndex, Count, pData,TypeArray);

    }
}