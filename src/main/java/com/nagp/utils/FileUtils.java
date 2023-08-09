package com.nagp.utils;

import java.io.*;
import java.util.Properties;

public class FileUtils {

    public static Properties properties = new Properties();

    public String readFile(String filePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            StringBuilder stringBuilder = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append(System.lineSeparator());
            }
            return stringBuilder.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getProperty(String propertyName) {
        try (FileInputStream fis = new FileInputStream(CONFIG_FILE_PATH)) {
            properties.load(fis);
            return properties.getProperty(propertyName);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
