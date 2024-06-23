package com.example.myapplication;

import android.os.Environment;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class StorageHelper {

    public static File getAppSpecificFile(String fileName) {
        File appDir = new File(Environment.getExternalStorageDirectory(), "MyApp");
        if (!appDir.exists()) {
            appDir.mkdirs();
        }
        return new File(appDir, fileName);
    }

    public static void writeToFile(String fileName, String content) {
        File file = getAppSpecificFile(fileName);
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(content.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}