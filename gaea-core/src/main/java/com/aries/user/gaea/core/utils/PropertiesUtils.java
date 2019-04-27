package com.aries.user.gaea.core.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;
import java.util.Properties;

@Slf4j
public class PropertiesUtils {
    private String propertiesName;

    public PropertiesUtils(String propertiesName) {
        this.propertiesName = propertiesName;
    }

    public String readProperty(String key) {
        String value = "";
        try (InputStream is = PropertiesUtils.class.getClassLoader().getResourceAsStream(propertiesName)) {
            Properties p = new Properties();
            p.load(is);
            value = p.getProperty(key);
        } catch (IOException e) {
            log.error("PropertiesUtils error:{}", e.getMessage(), e);
        }
        return value;
    }

    public Properties getProperties() {
        Properties p = new Properties();
        try (InputStream is = PropertiesUtils.class.getClassLoader().getResourceAsStream(propertiesName)) {
            p.load(is);
        } catch (IOException e) {
            log.error("PropertiesUtils error:{}", e.getMessage(), e);
        }
        return p;
    }

    public void writeProperty(String key, String value) {
        Properties properties = new Properties();
        String file = Objects.requireNonNull(PropertiesUtils.class.getClassLoader().getResource(propertiesName)).getFile();
        try (InputStream is = new FileInputStream(propertiesName);
             OutputStream os = new FileOutputStream(file)) {
            properties.load(is);
            properties.setProperty(key, value);
            properties.store(os, key);
            os.flush();
        } catch (Exception e) {
            log.error("PropertiesUtils error:{}", e.getMessage(), e);
        }
    }
}