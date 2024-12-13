package com.example.rest;

import java.util.HashMap;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;


import controller.Dao.servicies.ImagenServices;

import controller.Dao.servicies.RecetaServicies;



@Path("picture")
public class ImagenApi {
    @Path("/list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllpictures() {
        HashMap<String, Object> map = new HashMap<>();
        ImagenServices ps = new ImagenServices();
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



     @Path("/save")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response save(HashMap<String, Object> map) {
        // todo
        // Validation
        HashMap<String, Object> res = new HashMap<>();
        try {

            if (map.get("receta") != null) {
                RecetaServicies fs = new RecetaServicies();
                fs.setReceta(fs.get(Integer.parseInt(map.get("receta").toString())));
                if (fs.getReceta().getidReceta() != 0) {
                    ImagenServices ps = new ImagenServices();
                    ps.getImagen().setImagen(map.get("url").toString());
                    ps.getImagen().setIdReceta(fs.getReceta().getidReceta());
                    ps.save();
                    res.put("msg", "Ok");
                    res.put("data", "Imagen subida correctamente");
                    return Response.ok(res).build();
                } else {
                    res.put("msg", "Error");
                    res.put("data", "No se puede subir la imagen, la receta no existe");
                    return Response.status(Status.INTERNAL_SERVER_ERROR).entity(res).build();
                }

            } else {
                res.put("msg", "ERROR");
                res.put("data", "No se puede subir la imagen");
                return Response.status(Status.BAD_REQUEST).entity(res).build();
            }

        } catch (Exception e) {
            res.put("msg", "Error");
            res.put("data", e.toString());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(res).build();

        }
    }

    
}
