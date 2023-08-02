package com.nagp.utils;

import java.io.*;
import java.util.Properties;

public class FileUtils {

    public static Properties properties = new Properties();

    public String readFile(String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        StringBuilder stringBuilder = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line).append("\n");
        }
        reader.close();
        return stringBuilder.toString();
    }

    public String getProperty(String propertyName) {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(System.getProperty("user.dir") + "\\src\\main\\java\\com\\nagp\\resources\\config.properties");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            properties.load(fis);
        } catch (IOException e) {
            e.getStackTrace();
        }
        return properties.getProperty(propertyName);
    }
}