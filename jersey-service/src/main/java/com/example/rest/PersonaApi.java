package com.example.rest;

import java.util.HashMap;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import controller.Dao.servicies.PersonaServicies;

@Path("person")
public class PersonaApi {
    @Path("/list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPersons() {
        HashMap map = new HashMap<>();
        PersonaServicies ps = new PersonaServicies();
        map.put("msg", "Ok");
        map.put("data", ps.listAll().toArray());
        if (ps.listAll().isEmpty()) {
            map.put("data", new Object[]{});
        }
        return Response.ok(map).build();
    }

    @Path("/save")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response save(HashMap map) {
        //todo
        //Validation
       
            PersonaServicies ps = new PersonaServicies();
            ps.getPersona().setNombre(map.get("nombre").toString());
            ps.getPersona().setApellido(map.get("apellido").toString());
            // ps.getPersona().setDNI(map.get("dni").toString());
            ps.getPersona().setApodo(map.get("apodo").toString());
            HashMap res = new HashMap<>();
            try {
                ps.save();
                    res.put("msg", "Ok");
                    res.put("data", "Guardado correctamente");
                    return Response.ok(res).build();
           
        } catch (Exception e) {
            res.put("msg", "Error");
            res.put("data", e.toString());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
    }
    
    @Path("/listType")

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getType() {
        HashMap map = new HashMap<>();
        PersonaServicies ps = new PersonaServicies();
        map.put("msg", "Ok");
        map.put("data", ps.getPersona());
        return Response.ok(map).build();
    }

}
