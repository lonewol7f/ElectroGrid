package com.electrogrid.config;

import com.electrogrid.controller.HomeController;
import com.electrogrid.controller.UserController;
import com.electrogrid.service.AuthServiceImpl;
import com.electrogrid.util.AuthUser;
import com.electrogrid.util.DBUtil;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * Created By lonewol7f
 * Date: 3/17/2022
 */

@ApplicationPath("/auth")
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
        this.classes.add(AuthServiceImpl.class);
        this.classes.add(AuthUser.class);
        this.classes.add(DBUtil.class);

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
