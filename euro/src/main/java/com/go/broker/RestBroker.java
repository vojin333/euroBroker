package com.go.broker;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.ws.WebServiceException;

public class RestBroker {

	private final Client client;
	public RestBroker() {
		client = ClientBuilder.newClient();
	}
	
	public String returnResponse(String resource) throws WebServiceException{
		String response = null;
		try {
			String serviceUri = "http://localhost:8080/fterm-microservices/esma/registers/services/fterm/finalTermNotifications/";
			WebTarget target = client.target(serviceUri).path(resource);

			Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON_TYPE);
			Response responseJson = invocationBuilder.get();

			if (!isResponseSuccessful(responseJson)) {
				throw new WebServiceException("Failed : HTTP error code : " + responseJson.getStatus());
			}

			response = responseJson.readEntity(String.class);
		} catch (WebServiceException e) {
			e.printStackTrace();
			throw e;

		}
		return response;
	}
	
	
	private boolean isResponseSuccessful(Response response) {
		return Response.Status.OK.getStatusCode() == response.getStatus();
	}
}
