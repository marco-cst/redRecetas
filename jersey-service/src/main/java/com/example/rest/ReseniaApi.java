package com.example.rest;

import java.util.Date;
import java.util.HashMap;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import controller.Dao.servicies.ReseniaServices;


@Path("resenia")
public class ReseniaApi {

    @GET
    @Path("/saveResenia")
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveResenia() {
        HashMap<String, String> mapa = new HashMap<>();
        ReseniaServices rs = new ReseniaServices();
        String aux = "";

        try {
            rs.getResenia().setComentario("Perfecto");
            rs.getResenia().setCalificacion(5f);
            rs.getResenia().setIdPersona(2);
            rs.getResenia().setIdReceta(1);
            // rs.getResenia().setIdResenia(2);
            rs.getResenia().setFecha(new Date());
            rs.save();

            aux = "Nueva Resenia guardada: ";

        } catch (Exception e) {
            System.out.println("Error al guardar: " + e);
            mapa.put("msg", "error");
            mapa.put("data", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(mapa).build();
        }

        mapa.put("msg", "ok");
        mapa.put("data", aux);
        return Response.ok(mapa).build();
    }

    @Path("/list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllResenia() {
        HashMap<String, Object> map = new HashMap<>();
        ReseniaServices rs = new ReseniaServices();
        map.put("msg", "Ok");
        map.put("data", rs.listAll().toArray());
        if (rs.listAll().isEmpty()) {
            map.put("data", new Object[]{});
        }
        return Response.ok(map).build();
    }

/*
    @Path("/save")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response save(HashMap map) {
        HashMap res = new HashMap<>();
        Gson g = new Gson();
        String a = g.toJson(map);
        System.out.println("  " + a);
        try {
            ReseniaServices rs = new ReseniaServices();

            // rs.getResenia().setIdResenia(1);
            rs.getResenia().getComentario(map.get("Resenia").toString());
            rs.getResenia().getCalificacion();

            // ps.getInversionista().setNombre(map.get("nombre").toString());
            // ps.getInversionista().setApellido(map.get("apellido").toString());
            // ps.getInversionista().setDNI(map.get("dni").toString());

            rs.save();
            res.put("msg", "Ok");
            res.put("data", "Guardado correctamente");
            return Response.ok(res).build();

        } catch (Exception e) {
            System.out.println("Error save " + e.toString());
            res.put("msg", "Error");
            res.put("data", e.toString());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
    }
 */

    @Path("/delete")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteResenia(HashMap<String, Object> map) {
        HashMap<String, Object> res = new HashMap<>();
        try {
            ReseniaServices rs = new ReseniaServices();
            Integer id = Integer.parseInt(map.get("idResenia").toString());
            
            Boolean success = rs.delete(id);
            if (success) {
                res.put("msg", "Ok");
                res.put("data", "Eliminado exitosamente");
                return Response.ok(res).build();
            } else {
                res.put("msg", "Error");
                res.put("data", "Resenia no encontrada");
                return Response.status(Status.NOT_FOUND).entity(res).build();
            }
        } catch (Exception e) {
            System.out.println("Error al borrar " + e.toString());
            res.put("msg", "Error");
            res.put("data", e.toString());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
    }

    @Path("/update")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(HashMap map) {
        HashMap res = new HashMap<>();

        try {
            ReseniaServices rs = new ReseniaServices();
            rs.setResenia(rs.get(Integer.parseInt(map.get("idResenia").toString())));
            rs.getResenia().setComentario(map.get("comentario").toString());
            rs.getResenia().setIdResenia(1);
            
            // rs.getResenia().setIdPersona(1);
            // rs.getResenia().setCalificacion(2.0f);


            // rs.setInversionista(rs.get(Integer.parseInt(map.get("idInversionista").toString())));
            // rs.getInversionista().setNombre(map.get("nombre").toString());
            // rs.getInversionista().setApellido(map.get("apellido").toString());
            // rs.getInversionista().setDNI(map.get("dni").toString());
            rs.update();
            res.put("msg", "Ok");
            res.put("data", "Guardado correctamente");
            return Response.ok(res).build();
        } catch (Exception e) {
            System.out.println("Error en save data" + e.toString());
            res.put("msg", "Error");
            res.put("data", e.toString());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
    }
}
