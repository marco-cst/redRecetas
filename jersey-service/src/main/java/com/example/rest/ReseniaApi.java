package com.example.rest;

import java.util.Date;
import java.util.HashMap;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.google.gson.Gson;

import controller.Dao.servicies.ReseniaServices;
import models.Receta;
import models.Resenia;


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
            rs.getResenia().setCalificacion(2);
            rs.getResenia().setIdPersona(2);
            rs.getResenia().setIdReceta(1);
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
            
            rs.getResenia().setComentario(map.get("comentario").toString());
            rs.getResenia().setCalificacion(Integer.parseInt(map.get("calificacion").toString()));
            rs.getResenia().setFecha(new Date());
            rs.getResenia().getCalificacion();

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
            Resenia reseniaExistente = rs.get(Integer.parseInt(map.get("idResenia").toString()));
            rs.setResenia(reseniaExistente);
            rs.setResenia(rs.get(Integer.parseInt(map.get("idResenia").toString())));
            rs.getResenia().setComentario(map.get("comentario").toString());
            rs.getResenia().setCalificacion(Integer.parseInt(map.get("calificacion").toString()));

            int calificacion = Integer.parseInt(map.get("calificacion").toString());
            if (calificacion < 1 || calificacion > 5) {
                res.put("msg", "Error");
                res.put("data", "La calificación debe estar entre 1.0 y 5.0");
                throw new Exception("Las calificacion debe ser: mayor a uno y maximo 5.");
            }
            rs.getResenia().setCalificacion(calificacion);

            rs.update();

            res.put("msg", "Ok");
            res.put("data", "Resenia actualizada exitosamente");
            return Response.ok(res).build();
        } catch (Exception e) {
            res.put("msg", "Error");
            res.put("data", e.getMessage());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
    }

    @Path("/get/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getResenia(@PathParam("id") Integer id) {
        HashMap map = new HashMap<>();
        ReseniaServices rs = new ReseniaServices();
        try {
            rs.setResenia(rs.get(id));
        } catch (Exception e) {
        }

        map.put("msg", "Ok");
        map.put("data", rs.getResenia());


        if (rs.getResenia().getIdResenia() == null) {
            map.put("data", "No existe la resenia con ese identificador");
            return Response.status(Status.BAD_REQUEST).entity(map).build();
        }
        return Response.ok(map).build();
    }
    
    @Path("/listType")

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getType() {
        HashMap map = new HashMap<>();
        ReseniaServices rs = new ReseniaServices();
        map.put("msg", "Ok");
        map.put("data", rs.getResenia());
        return Response.ok(map).build();
    }
}