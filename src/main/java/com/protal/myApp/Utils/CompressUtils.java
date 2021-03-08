package com.protal.myApp.Utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class CompressUtils {
    // compress τις εικόνες πριν την αποθήκευση στη βάση
    public static byte[] compressBytes(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.finish();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        try {
            outputStream.close();
        } catch (IOException e) {
        }
        return outputStream.toByteArray();
    }


    // uncompress πριν σταλούν στο front end
    public static byte[] decompressBytes(byte[] data) {
        Inflater inflater = new Inflater();
        ByteArrayOutputStream outputStream = null;
        try {
            inflater.setInput(data);
        } catch (NullPointerException ne) {
            ne.printStackTrace();
        }
        try {
            outputStream = new ByteArrayOutputStream(data.length);
            byte[] buffer = new byte[1024];
            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                outputStream.write(buffer, 0, count);
            }
            outputStream.close();
        } catch (IOException | DataFormatException | NullPointerException ioe) {
          ioe.printStackTrace();
        }
        return outputStream.toByteArray();
    }
}
