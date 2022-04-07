package com.electrogrid.config;

import com.electrogrid.controller.HomeController;
import com.electrogrid.controller.UserController;

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

    private final Set<Class<?>> classes;
    private final Set<Object> singletons;


    public Config() {
        readProperties();

        this.classes = new HashSet<>();
        this.classes.add(HomeController.class);
        this.classes.add(UserController.class);

        this.singletons = new HashSet<>();
        this.singletons.add(new SecurityFilter());
    }

    @Override
    public Set<Class<?>> getClasses() {
        return this.classes;
    }

    @Override
    public Set<Object> getSingletons() {
        return this.singletons;
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
