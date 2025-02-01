package com.example.rest;

import java.util.HashMap;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import controller.Dao.servicies.PersonaServicies;

@Path("person")
public class PersonaApi {
    @Path("/listType")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getType() {
        HashMap<String, Object> map = new HashMap<>();
        PersonaServicies ps = new PersonaServicies();
        map.put("msg", "Ok");
        map.put("data", ps.getPersona());
        return Response.ok(map).build();
    }

    @Path("/list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPersons() {
        HashMap<String, Object> map = new HashMap<>();
        PersonaServicies ps = new PersonaServicies();
        map.put("msg", "Ok");
        map.put("data", ps.listAll().toArray());
        if (ps.listAll().isEmpty()) {
            map.put("data", new Object[] {});
        }
        return Response.ok(map).build();
    }

    @GET
    @Path("/get/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    
    public Response getPerson(@PathParam("id") Integer id) {
        HashMap<String, Object> map = new HashMap<>();
        PersonaServicies ps = new PersonaServicies();
        try {
            ps.setPersona(ps.get(id));
        } catch (Exception e) {
            map.put("msg", "Error");
            map.put("data", e.toString());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(map).build();
        }
        map.put("msg", "Ok");
        map.put("data", ps.getPersona());
        if (ps.getPersona().getIdPersona() == 0) {
            map.put("data", "No existe la persona con ese identificador");
            return Response.status(Status.BAD_REQUEST).entity(map).build();
        }
        return Response.ok(map)
    .header("Access-Control-Allow-Origin", "*") // Permitir cualquier origen
    .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS")
    .header("Access-Control-Allow-Headers", "Origin, Content-Type, Accept, Authorization")
    .build();
    }

    @Path("/list/search/ident/{texto}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPersn(@PathParam("texto") String texto) {
        HashMap<String, Object> map = new HashMap<>();
        PersonaServicies ps = new PersonaServicies();
        System.out.println("List size: " + ps.listAll().getSize()); // Imprimir tamaño de la lista

        map.put("msg", "OK");
        ps.setPersona(ps.buscar_identificacion(texto));
        map.put("data", ps.getPersona());

        if (ps.getPersona().getIdPersona() == null) {
            map.put("data", "No existe la persona con ese identificador");
            return Response.status(Status.BAD_REQUEST).header("Acces-Control-Allow-Origin", "+").entity(map).build();
        }
        return Response.ok(map)
    .header("Access-Control-Allow-Origin", "*") // Permitir cualquier origen
    .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS")
    .header("Access-Control-Allow-Headers", "Origin, Content-Type, Accept, Authorization")
    .build();


    }

    @Path("/save")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response save(HashMap<String, Object> map) {
        PersonaServicies ps = new PersonaServicies();
        HashMap<String, Object> res = new HashMap<>();
        try {
            ps.getPersona().setNombre(map.get("nombre").toString());
            ps.getPersona().setApellido(map.get("apellido").toString());
            ps.getPersona().setDNI(map.get("dni").toString());
            ps.getPersona().setApodo(map.get("apodo").toString());
            
            ps.save(); // Este método ya valida duplicados y lanza excepciones
            
            res.put("msg", "Ok");
            res.put("data", "Guardado correctamente");
            return Response.ok(res).build();
        } catch (Exception e) {
            res.put("msg", "Error");
            res.put("data", e.getMessage()); // Devolver el mensaje de error de la excepción
            return Response.status(Response.Status.BAD_REQUEST).entity(res).build(); // Usar un código de estado adecuado
        }
    }
    

    @Path("/update")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(HashMap<String, Object> map) {
        // todo
        // Validation
        PersonaServicies ps = new PersonaServicies();
        ps.getPersona().setIdPersona(Integer.parseInt(map.get("idPersona").toString()));
        ps.getPersona().setNombre(map.get("nombre").toString());
        ps.getPersona().setApellido(map.get("apellido").toString());
        // ps.getPersona().setDNI(map.get("dni").toString());
        ps.getPersona().setApodo(map.get("apodo").toString());
        HashMap<String, Object> res = new HashMap<>();
        try {
            ps.update();
            res.put("msg", "Ok");
            res.put("data", "Actualizado correctamente");
            return Response.ok(res).build();
        } catch (Exception e) {
            res.put("msg", "Error");
            res.put("data", e.toString());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
    }

    @Path("/delete/{id}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id") int id) {
        // todo
        // Validation
        PersonaServicies ps = new PersonaServicies();
        HashMap<String, Object> res = new HashMap<>();
        try {
            if (ps.delete(id)) {
                res.put("msg", "Ok");
                res.put("data", "Eliminado correctamente");
                return Response.ok(res).build();
            } else {
                res.put("msg", "Error");
                res.put("data", "No se puede eliminar la persona");
                return Response.status(Status.INTERNAL_SERVER_ERROR).entity(res).build();
            }
        } catch (Exception e) {
            res.put("msg", "Error");
            res.put("data", e.toString());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
    }

}
