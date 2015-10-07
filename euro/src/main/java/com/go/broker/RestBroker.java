package com.go.broker;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class RestBroker {

	private final Client client;
	public RestBroker() {
		client = ClientBuilder.newClient();
	}
	
	public Response returnJson(String resource) {
		Response response = getJson(resource);
		try {
			resolveResponse(response);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}
	
	private Response getJson(String resource) {
		WebTarget target = createWebTarget(resource);
		Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON_TYPE);
		Response response = invocationBuilder.get();
		
		return response;
	}

	private WebTarget createWebTarget(String resource) {
		String serviceUri = "http://api.goeuro.com/api/v2/position/suggest/en/";
		WebTarget target = client.target(serviceUri).path(resource);
		return target;
	}

	
	private Response resolveResponse(Response response) throws Exception {
		if (isResponseSuccessful(response)) {
			return response;
		} else {
			throw new Exception();
		}
	}
    
	private boolean isResponseSuccessful(Response response) {
		return Response.Status.OK.getStatusCode() == response.getStatus();
	}
}
