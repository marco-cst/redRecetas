package com.example.rest;

import java.util.HashMap;

import controller.Dao.servicies.RecetaServicies;
import models.Receta;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.DELETE;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("receta")
public class RecetaApi {

    @Path("/listType")

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getType() {
        HashMap map = new HashMap<>();
        RecetaServicies ps = new RecetaServicies();
        map.put("msg", "Ok");
        map.put("data", ps.getReceta());
        return Response.ok(map).build();
    }

    @Path("/list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listAllRecetas() {
        HashMap map = new HashMap<>();
        RecetaServicies ps = new RecetaServicies();
        map.put("msg", "Ok");
        map.put("data", ps.listAll().toArray());
        if (ps.listAll().isEmpty()) {
            map.put("data", new Object[] {});

        }
        return Response.ok(map).build();
    }

    @Path("/get/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getReceta(@PathParam("id") Integer id) {
        HashMap map = new HashMap<>();
        RecetaServicies ps = new RecetaServicies();
        try {
            ps.setReceta(ps.get(id));
        } catch (Exception e) {
            // TODO: handle exception
        }
        map.put("msg", "Ok");
        map.put("data", ps.getReceta());
        if (ps.getReceta().getidReceta() == null) {
            map.put("data", "No existe la receta con ese identificador");
            return Response.status(Status.BAD_REQUEST).entity(map).build();
        }

        return Response.ok(map).build();
    }

    @Path("/save")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response save(HashMap map) {

        HashMap res = new HashMap<>();

        try {
            RecetaServicies ps = new RecetaServicies();
            ps.getReceta().setNombre(map.get("nombre").toString());
            ps.getReceta().setPreparacion(map.get("preparacion").toString());
            ps.getReceta().setPorciones(Integer.parseInt(map.get("porciones").toString()));

            ps.validarReceta(ps.getReceta());
            ps.save();

            res.put("msg", "Ok");
            res.put("data", "Receta guardada exitosamente");
            res.put("fecha", ps.getReceta().getFechaPublicacion());
            return Response.ok(res).build();

        } catch (Exception e) {
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
            RecetaServicies ps = new RecetaServicies();
            Receta recetaExistente = ps.get(Integer.parseInt(map.get("idReceta").toString()));
            ps.setReceta(recetaExistente);
            ps.setReceta(ps.get(Integer.parseInt(map.get("idReceta").toString())));
            ps.getReceta().setNombre(map.get("nombre").toString());
            ps.getReceta().setPreparacion(map.get("preparacion").toString());
            ps.getReceta().setPorciones(Integer.parseInt(map.get("porciones").toString()));
            int porciones = Integer.parseInt(map.get("porciones").toString());
            if (porciones <= 0) {
                throw new Exception("Las porciones deben ser un número entero mayor a cero.");
            }
            ps.getReceta().setPorciones(porciones);
            ps.getReceta().setFavoritos(Boolean.parseBoolean(map.get("favoritos").toString()));

            ps.validarReceta(ps.getReceta());
            ps.update();

            res.put("msg", "Ok");
            res.put("data", "Receta actualizada exitosamente");
            return Response.ok(res).build();
        } catch (Exception e) {
            res.put("msg", "Error");
            res.put("data", e.getMessage());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
    }

    @Path("/delete/{id}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteRe(@PathParam("id") int id) {
        HashMap<String, Object> res = new HashMap<>();

        try {
            RecetaServicies fs = new RecetaServicies();

            // Intentar eliminar la reseña por ID
            boolean recetaDeleted = fs.delete(id);

            if (recetaDeleted) {
                res.put("message", "Receta eliminada exitosamente");
                return Response.ok(res).build();
            } else {
                // Si no se eliminó, enviar un error 404
                res.put("message", "Receta no encontrada o no eliminada");
                return Response.status(Response.Status.NOT_FOUND).entity(res).build();
            }
        } catch (Exception e) {
            // En caso de error, devolver una respuesta de error interno
            res.put("message", "Error al intentar eliminar la receta");
            res.put("error", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
    }
}