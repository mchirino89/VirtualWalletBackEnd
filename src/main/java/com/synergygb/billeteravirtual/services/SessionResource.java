package com.synergygb.billeteravirtual.services;

import java.net.HttpURLConnection;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
//import com.synergygb.asegura.core;

/**
 *
 * @author mauriciochirino
 */

@Path("/session")
public class SessionResource {
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registrarUsuario(User log) {
        
        return Response.status(HttpURLConnection.HTTP_OK).entity(log.toString()).build();
    }
}
