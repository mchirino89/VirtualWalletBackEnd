/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.synergygb.billeteravirtual.services;

import java.net.HttpURLConnection;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author mauriciochirino
 */

@Path("/user")
public class UserResource {
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response registrarUsuario(User registrado) {
        
        return Response.status(HttpURLConnection.HTTP_CREATED).entity(registrado.toString()).build();
    }
}
