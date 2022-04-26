package com.electrogrid.controller;

import com.electrogrid.entity.Credentials;
import com.electrogrid.entity.User;
import com.electrogrid.service.AuthServiceI;
import com.electrogrid.service.AuthServiceImpl;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

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
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateRole(User user) {
        return authService.updateUserRole(user);
    }

    @RolesAllowed("Admin")
    @DELETE
    @Path("/delete/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteUser(@PathParam("id") int id) {
        return authService.deleteUser(id);
    }

    @PermitAll
    @GET
    @Path("/user/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Object getUserById(@PathParam("id") int id) {
        return authService.getUserById(id);
    }

    @PermitAll
    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> listUsers() {
        return authService.listUsers();
    }

    @PermitAll
    @GET
    @Path("/validate")
    @Produces({MediaType.APPLICATION_JSON})
    public Response test(ContainerRequestContext containerRequestContext) {
        return authService.validate(containerRequestContext);
    }

}
