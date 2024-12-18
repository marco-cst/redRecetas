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

import controller.Dao.servicies.CuentaServices;
import controller.Dao.servicies.PersonaServicies;


@Path("account")
public class CuentaApi {
    @Path("/list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllaccounts() {
        HashMap<String, Object> map = new HashMap<>();
        CuentaServices ps = new CuentaServices();
        map.put("msg", "Ok");
        try {
            map.put("data", ps.listShowall());
            if (ps.listAll().isEmpty()) {
                map.put("data", new Object[] {});
                return Response.status(Status.BAD_REQUEST).entity(map).build();
            }

        } catch (Exception e) {

        }
        return Response.ok(map).build();
    }


      @Path("/get/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getReceta(@PathParam("id") Integer id) {
        HashMap<String, Object> map = new HashMap<>();
        CuentaServices ps = new CuentaServices();
        try {
            ps.setCuenta(ps.get(id));
        } catch (Exception e) {
            map.put("msg", "Error");
            map.put("data", e.toString());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(map).build();
        }
        map.put("msg", "Ok");
        map.put("data", ps.getCuenta());
        if (ps.getCuenta().getIdCuenta() == 0) {
            map.put("data", "No existe la receta con ese identificador");
            return Response.status(Status.BAD_REQUEST).entity(map).build();
        }

        return Response.ok(map).build();
    }

    @Path("/save")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response save(HashMap<String, Object> map) {
        // todo
        // Validation
        HashMap<String, Object> res = new HashMap<>();
        try {

            if (map.get("person") != null) {
                PersonaServicies fs = new PersonaServicies();
                fs.setPersona(fs.get(Integer.parseInt(map.get("person").toString())));
                if (fs.getPersona().getIdPersona() != 0) {
                    CuentaServices ps = new CuentaServices();
                    ps.getCuenta().setCorreo(map.get("correo").toString());
                    ps.getCuenta().setClave(map.get("clave").toString());
                    ps.getCuenta().setEstado(true);
                    ps.getCuenta().setIdPersona(fs.getPersona().getIdPersona());
                    ps.save();
                    res.put("msg", "Ok");
                    res.put("data", "Guardado correctamente");
                    return Response.ok(res).build();
                } else {
                    res.put("msg", "Error");
                    res.put("data", "No se puede guardar la receta, la persona no existe");
                    return Response.status(Status.INTERNAL_SERVER_ERROR).entity(res).build();
                }

            } else {
                res.put("msg", "ERROR");
                res.put("data", "No se puede guardar la receta");
                return Response.status(Status.BAD_REQUEST).entity(res).build();
            }

        } catch (Exception e) {
            res.put("msg", "Error");
            res.put("data", e.toString());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(res).build();

        }
    }

    @Path("/update")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(HashMap<String, Object> map) {
        // todo
        // Validation
        HashMap<String, Object> res = new HashMap<>();
        try {

            if (map.get("person") != null) {
                PersonaServicies fs = new PersonaServicies();
                fs.setPersona(fs.get(Integer.parseInt(map.get("person").toString())));
                if (fs.getPersona().getIdPersona() != 0) {
                    CuentaServices ps = new CuentaServices();
                    ps.getCuenta().setIdCuenta(Integer.parseInt(map.get("id").toString()));
                    ps.getCuenta().setCorreo(map.get("correo").toString());
                    ps.getCuenta().setClave(map.get("clave").toString());
                    ps.getCuenta().setEstado(true);
                    ps.getCuenta().setIdPersona(fs.getPersona().getIdPersona());
                    ps.update();
                    res.put("msg", "Ok");
                    res.put("data", "Actualizado correctamente");
                    return Response.ok(res).build();
                } else {
                    res.put("msg", "Error");
                    res.put("data", "No se puede actualizar la receta, la persona no existe");
                    return Response.status(Status.INTERNAL_SERVER_ERROR).entity(res).build();
                }

            } else {
                res.put("msg", "ERROR");
                res.put("data", "No se puede actualizar la receta");
                return Response.status(Status.BAD_REQUEST).entity(res).build();
            }

        } catch (Exception e) {
            res.put("msg", "Error");
            res.put("data", e.toString());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(res).build();

        }
    }


    @Path("/delete/{id}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteRe(@PathParam("id") int id) {
        HashMap<String, Object> res = new HashMap<>();
    
        try {
            CuentaServices fs = new CuentaServices();
            
            // Intentar eliminar la reseña por ID
            boolean recetaDeleted = fs.delete(id);
    
            if (recetaDeleted) {
                res.put("message", "Cuenta eliminada exitosamente");
                return Response.ok(res).build();
            } else {
                // Si no se eliminó, enviar un error 404
                res.put("message", "Cuenta no encontrada o no eliminada");
                return Response.status(Response.Status.NOT_FOUND).entity(res).build();
            }
        } catch (Exception e) {
            // En caso de error, devolver una respuesta de error interno
            res.put("message", "Error al intentar eliminar la cuenta");
            res.put("error", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
    }



}
