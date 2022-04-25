package com.electrogrid.security;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created By lonewol7f
 * Date: 4/25/2022
 */
public class SecurityFilter implements ContainerRequestFilter {

    @Context
    private ResourceInfo resourceInfo;

    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {

        Method method = resourceInfo.getResourceMethod();

        if (!method.isAnnotationPresent(PermitAll.class)) {

            if (method.isAnnotationPresent(DenyAll.class)) {

                containerRequestContext.abortWith(Response.status(Response.Status.FORBIDDEN)
                        .entity("Access denied for all users !!!").build());
                return;

            }

            if (method.isAnnotationPresent(RolesAllowed.class)) {

                RolesAllowed rolesAnnotations = method.getAnnotation(RolesAllowed.class);
                // Set<String> roleSet = new HashSet<>(Arrays.asList(rolesAnnotations.value()));
                List<String> roleSet = new ArrayList<>(Arrays.asList(rolesAnnotations.value()));

                if (!Auth.validateUser(containerRequestContext, roleSet)) {
                    containerRequestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                            .entity("You can not access this resource !!!").build());
                }

            }
        }

    }

}
