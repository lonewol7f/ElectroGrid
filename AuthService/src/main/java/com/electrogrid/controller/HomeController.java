package com.electrogrid.controller;

import javax.annotation.security.PermitAll;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created By lonewol7f
 * Date: 3/17/2022
 */

@Path("/")
public class HomeController {

    @PermitAll
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String showHome() {
        return "This is home built by lonewol7f";
    }

}
