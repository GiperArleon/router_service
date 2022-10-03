package com.router.tools;

import lombok.extern.slf4j.Slf4j;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Slf4j
public class PropertyReader {
    public static final PropertyReader PROPERTIES = new PropertyReader();
    private final Map<String, String> properties = readProperties();

    private PropertyReader() {
    }

    private Map<String, String> readProperties() {
        Map<String, String> properties = new HashMap<>();
        String fileName = "application.properties";
        try(InputStream input = PropertyReader.class
                .getClassLoader()
                .getResourceAsStream(fileName)) {

            Properties prop = new Properties();
            if(input != null) {
                prop.load(input);
            } else {
                String tmp = "property file '" + fileName + "' not found";
                log.error(tmp);
                throw new FileNotFoundException(tmp);
            }

            properties.put("some.test.param1", prop.getProperty("some.test.param1"));
            properties.put("some.test.param2", prop.getProperty("some.test.param2"));
        } catch(IOException e) {
            log.error("Exception: " + e.toString());
        }
        return properties;
    }

    public Map<String, String> getProperties() {
        return properties;
    }
}
