package com.example.myapplication;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtility {

    public static void writeToFile(String path, String content) {
        File file = new File(path);
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(content.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}