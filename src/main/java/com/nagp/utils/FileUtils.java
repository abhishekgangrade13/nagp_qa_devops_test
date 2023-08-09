package com.nagp.utils;

import java.io.*;
import java.util.Properties;

public class FileUtils {

    private static final String CONFIG_FILE_PATH = "src/main/java/com/nagp/resources/config.properties";

    private static Properties properties = new Properties();

    public String readFile(String filePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            StringBuilder stringBuilder = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append(System.lineSeparator());
            }
            return stringBuilder.toString();
        } catch (FileNotFoundException e) {
            return null;
        }
    }

    public String getProperty(String propertyName) {
        try (FileInputStream fis = new FileInputStream(CONFIG_FILE_PATH)) {
            properties.load(fis);
            return properties.getProperty(propertyName);
        } catch (IOException e) {
            return null;
        }
    }
}
