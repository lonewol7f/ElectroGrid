package com.electrogrid.config;

import com.electrogrid.util.AuthUser;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * Created By lonewol7f
 * Date: 4/5/2022
 */

@Provider
public class SecurityFilter implements ContainerRequestFilter {

    public static final String AUTHENTICATION_HEADER_KEY = "Authorization";
    public static final String AUTHENTICATION_HEADER_PREFIX = "Basic ";

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

            final MultivaluedMap<String, String> headers = containerRequestContext.getHeaders();
            final List<String> authorization = headers.get(AUTHENTICATION_HEADER_KEY);

            if (authorization == null || authorization.isEmpty()) {
                containerRequestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                        .entity("You can not access this resource 1 !!!").build());
                return;
            }

            final String authToken = authorization.get(0).replaceFirst(AUTHENTICATION_HEADER_PREFIX, "");
            final String decodedAuthToken = new String(Base64.getDecoder().decode(authToken.getBytes(StandardCharsets.UTF_8)));

            final StringTokenizer tokenizer = new StringTokenizer(decodedAuthToken, ":");
            final String username = tokenizer.nextToken();
            final String password = tokenizer.nextToken();

            if (method.isAnnotationPresent(RolesAllowed.class)) {
                RolesAllowed rolesAnnotations = method.getAnnotation(RolesAllowed.class);
                Set<String> roleSet = new HashSet<>(Arrays.asList(rolesAnnotations.value()));

                if (!AuthUser.isUserAllowed(username, password, roleSet)) {
                    containerRequestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                            .entity("You can not access this resource 2 !!!").build());

                }
            }
        }

    }
}
