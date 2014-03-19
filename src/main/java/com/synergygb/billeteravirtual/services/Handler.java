package com.synergygb.billeteravirtual.services;

import java.util.ArrayList;
import java.util.Random;
import javax.ws.rs.*;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
/**
 *
 * @author mauriciochirino
 * http://localhost:8080/BilleteraVirtual/resources/user/recurso/1
 */
@Path("/recurso")
public class Handler {

    private ArrayList inventario;
    static String[] colores = {"amarillo", "azul", "rojo", "verde", "negro", "blanco"};

    public Handler() {
        inventario = new ArrayList();
        for (int i = 0; i < 10; i++) {
            inventario.add(creaPar(i+1, new Random().nextInt(colores.length)));
        }
    }
    
    private Zapato creaPar(int codigo, int posColor){
        return new Zapato(codigo, colores[posColor]);
    }

    @GET
    @Path("/{param}")
    public Response getMsg(@PathParam("param") String parametro) {
        String output = "";
        try {
            switch (Integer.parseInt(parametro)) {
                case 1:
                    output += "<blockquote><strong>Zapatos en el sistema</strong></blockquote>";
                    for (int i = 0; i < inventario.size(); i++) {
                        output += ((Zapato) inventario.get(i)).toString() + "<br>";
                    }
                    break;
                default:
                    output = "Opcion no disponible";
                    break;
            }
        } catch (Exception e) {
            output = "Parametro incorrecto";
            System.out.println("pasando por aqui");
        }
        return Response.status(200).entity(output).build();
    }

    @PUT
    @Path("/crearZapato")
    @Produces(MediaType.APPLICATION_JSON)
    public Zapato produceJSON( @PathParam("name") String name ) {
        return new Zapato(new Random().nextInt(colores.length), colores[new Random().nextInt(colores.length)]);
    }

    
    
}
