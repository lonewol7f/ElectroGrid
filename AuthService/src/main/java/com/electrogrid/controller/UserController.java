package com.electrogrid.controller;

import com.electrogrid.entity.Credentials;
import com.electrogrid.entity.User;
import com.electrogrid.service.AuthServiceI;
import com.electrogrid.service.AuthServiceImpl;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created By lonewol7f
 * Date: 4/6/2022
 */

@Path("/users")
public class UserController {

    private final AuthServiceI authService = new AuthServiceImpl();

    @PermitAll
    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response insertUser(User user) {
        return authService.insertUser(user);
    }

    @PermitAll
    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(Credentials credentials) {
        return authService.login(credentials);
    }

    @RolesAllowed("Admin")
    @PUT
    @Path("/update/{id}/{newRole}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateRole(@PathParam("id") int id, @PathParam("newRole") String newRole) {
        return authService.updateUserRole(id, newRole);
    }

    @RolesAllowed("Admin")
    @DELETE
    @Path("/delete/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteUser(@PathParam("id") int id) {
        return authService.deleteUser(id);
    }

}
