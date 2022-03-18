package com.electrogrid.config;

import com.electrogrid.controller.HomeController;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

/**
 * Created By lonewol7f
 * Date: 3/17/2022
 */

@ApplicationPath("/")
public class Config extends Application {

    public static final String DB_PROPERTIES_FILE = "db.properties";
    public static Properties properties = new Properties();

    @Override
    public Set<Class<?>> getClasses() {
        final Set<Class<?>> classes = new HashSet<>();
        classes.add(HomeController.class);
        return classes;
    }

    public void readProperties() {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(DB_PROPERTIES_FILE);
        if (inputStream != null) {
            try {
                properties.load(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
