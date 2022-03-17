package com.electrogrid.config;

import com.electrogrid.controller.HomeController;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 * Created By lonewol7f
 * Date: 3/17/2022
 */

@ApplicationPath("/")
public class Config extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        final Set<Class<?>> classes = new HashSet<>();
        classes.add(HomeController.class);
        return classes;
    }
}
