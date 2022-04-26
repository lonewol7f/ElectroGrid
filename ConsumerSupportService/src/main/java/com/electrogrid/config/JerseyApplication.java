package com.electrogrid.config;

import com.electrogrid.security.SecurityFilter;
import org.glassfish.jersey.server.ResourceConfig;

/**
 * Created By lonewol7f
 * Date: 4/25/2022
 */
public class JerseyApplication extends ResourceConfig {

    public JerseyApplication() {

        packages("com.electrogrid");
        register(SecurityFilter.class);

    }
}
