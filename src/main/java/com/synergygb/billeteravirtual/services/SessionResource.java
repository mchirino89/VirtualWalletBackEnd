package com.synergygb.billeteravirtual.services;

import com.synergygb.billeteravirtual.core.ServiceUtils;
import com.synergygb.billeteravirtual.handlers.sessionHandler;
import com.synergygb.webAPI.handlers.WebServiceHandler;
import com.synergygb.webAPI.handlers.WebServiceStatus;
import com.synergygb.webAPI.handlers.WebServiceStatusType;
import com.synergygb.webAPI.layerCommunication.WebServiceRequest;
import com.synergygb.webAPI.layerCommunication.WebServiceResponse;
import com.synergygb.webAPI.parameters.ParametersMediaType;
import com.synergygb.webAPI.parameters.exceptions.InvalidParametersFormatException;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author mauriciochirino
 */
@Path("/session")
public class SessionResource {

    private static int divisor = 80;
    @Context
    UriInfo uriInfo;
    @Context
    Request request;
    @Context
    HttpHeaders headers;
    String id;

    public SessionResource(UriInfo uriInfo, Request request, String id, HttpHeaders headers) {
        this.uriInfo = uriInfo;
        this.request = request;
        this.headers = headers;
        this.id = id;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response logUser(String content) {
        WebServiceStatus status = null;
        ParametersMediaType mediaType = ParametersMediaType.APPLICATION_JSON;
        //---------------------------------------------------------------------
        // Building a WebServiceRequest from the service request and an empty
        // response to be filled through the handler.
        //---------------------------------------------------------------------
        WebServiceResponse webResponse = WebServiceResponse.buildDefault(mediaType);
        WebServiceRequest webRequest = null;
        try {
            webRequest = WebServiceRequest.build(request, headers, content, mediaType);
        } catch (InvalidParametersFormatException ex) {
            ServiceUtils.addErrorStatus(WebServiceStatus.buildStatus(ex), webResponse);
            return WebServiceHandler.okResponseFromStatus(WebServiceStatus.buildStatus(ex), mediaType);
        }
        return getResponse(webResponse, webRequest, status, new sessionHandler(null,true));
    }
    
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{cookie}")
    public Response logoutUser(String content,@PathParam("cookie") String cookie) {
        WebServiceStatus status = null;
        ParametersMediaType mediaType = ParametersMediaType.APPLICATION_JSON;
        //---------------------------------------------------------------------
        // Building a WebServiceRequest from the service request and an empty
        // response to be filled through the handler.
        //---------------------------------------------------------------------
        WebServiceResponse webResponse = WebServiceResponse.buildDefault(mediaType);
        WebServiceRequest webRequest = null;
        try {
            webRequest = WebServiceRequest.build(request, headers, content, mediaType);
        } catch (InvalidParametersFormatException ex) {
            ServiceUtils.addErrorStatus(WebServiceStatus.buildStatus(ex), webResponse);
            return WebServiceHandler.okResponseFromStatus(WebServiceStatus.buildStatus(ex), mediaType);
        }
        return getResponse(webResponse, webRequest, status, new sessionHandler(cookie,false));
    }
    
    private Response getResponse(WebServiceResponse webResponse,WebServiceRequest webRequest, WebServiceStatus status, WebServiceHandler handler){
        try {
            status = handler.run(webRequest, webResponse);
        } catch (Exception e) {
            // catching any unexpected exception
            status = WebServiceStatus.buildStatus(WebServiceStatusType.UNEXPECTED_ERROR);
            System.err.println("Excepcion: " + e.getLocalizedMessage());
            StackTraceElement[] aux = e.getStackTrace();
            separation(divisor);
            for (int i = 0; i < aux.length; i++) {
                System.out.println("Traza nº " + (i + 1));
                System.out.println("Clase: " + aux[i].getClassName());
                System.out.println("Metodo: " + aux[i].getMethodName());
                System.out.println("Linea: " + aux[i].getLineNumber());
                separation(divisor);
            }
            return WebServiceHandler.okResponseFromStatus(status, ParametersMediaType.APPLICATION_JSON);
        }
        //---------------------------------------------------------------------
        // Enconding response in UTF-8.
        //---------------------------------------------------------------------
        webResponse.setUTF8Encoding(ParametersMediaType.APPLICATION_JSON);
        //---------------------------------------------------------------------
        // Returning the appropriate response according to the status
        //---------------------------------------------------------------------
        if (status.ok()) {
            WebServiceHandler.addStatusToWSResponse(webResponse, status, ParametersMediaType.APPLICATION_JSON);
            return WebServiceHandler.okResponse(webResponse);
        } else {
            return WebServiceHandler.okResponseFromStatus(status, ParametersMediaType.APPLICATION_JSON);
        }
    }

    private void separation(int limit) {
        for (int j = 0; j < limit; j++) {
            System.out.print("-");
        }
        System.out.println("");
    }
}
