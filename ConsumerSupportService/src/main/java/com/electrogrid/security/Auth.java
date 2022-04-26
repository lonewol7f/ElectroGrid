package com.electrogrid.security;

import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

/**
 * Created By lonewol7f Date: 4/25/2022
 */
public class Auth {

	public static boolean validateUser(ContainerRequestContext requestContext, List<String> roleSet) {

		Client client = ClientBuilder.newClient();
		WebTarget webTarget = client.target("http://127.0.0.1:8080/auth/users/validate");

		System.out.println(roleSet);
		System.out.println(requestContext.getHeaders().get("Authorization").get(0));

		Invocation.Builder invocation = webTarget.request()
				.header("Authorization", requestContext.getHeaders().get("Authorization").get(0))
				.header("Roles", roleSet);

		Response response = invocation.get();

		final MultivaluedMap<String, Object> headers = response.getHeaders();
		System.out.println(headers);
		final List<Object> allowed = headers.get("Allowed");

		System.out.println(allowed);

		return Boolean.parseBoolean(allowed.get(0).toString());

	}

}
