package com.example.rest;


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

import controller.Dao.servicies.CategoriaServices;
import controller.tda.list.LinkedList;
import models.Categoria;
import models.enumedores.TipoCategoria;

@Path("categoria")
public class CategoriaApi {

    @GET
    @Path("/establecerCategorias")
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveCategoria() {
        HashMap<String, String> mapa = new HashMap<>();
        CategoriaServices cs = new CategoriaServices();
        String aux = "";

        try {
            //Definir la categoria por defecto
            cs.getCategoria().setIdCategoria(1);
            cs.getCategoria().setTipo(TipoCategoria.SALADO);
            cs.save();

            cs.getCategoria().setIdCategoria(2);
            cs.getCategoria().setTipo(TipoCategoria.DULCE);
            cs.save();

            aux = "Nueva Categoria guardada: ";

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
    public Response getAllCategoria() {
        HashMap<String, Object> map = new HashMap<>();
        CategoriaServices cs = new CategoriaServices();
        map.put("msg", "Ok");
        map.put("data", cs.listAll().toArray());
        if (cs.listAll().isEmpty()) {
            map.put("data", new Object[]{});
        }
        return Response.ok(map).build();
    }

    @Path("/delete")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteCategoria(HashMap<String, Object> map) {
        HashMap<String, Object> res = new HashMap<>();
        try {
            CategoriaServices cs = new CategoriaServices();
            Integer id = Integer.parseInt(map.get("idCategoria").toString());
            
            Boolean success = cs.delete(id);
            if (success) {
                res.put("msg", "Ok");
                res.put("data", "Eliminado exitosamente");
                return Response.ok(res).build();
            } else {
                res.put("msg", "Error");
                res.put("data", "Categoria no encontrada");
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
    public Response update(HashMap<String,Object> map) {
        HashMap<String,Object> res = new HashMap<>();

        try {
            CategoriaServices cs = new CategoriaServices();
            cs.setCategoria(cs.get(Integer.parseInt(map.get("idCategoria").toString())));
            // cs.getCategoria().setTipo(map.get("tipo").toString());
            // cs.getCategoria().setEstado(Boolean.parseBoolean(map.get("estado").toString()));

            cs.update();

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

    @Path("/list/search/lineal/categoria/{texto}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCategoria(@PathParam("texto") String texto) {
        HashMap<String, Object> map = new HashMap<>();
        CategoriaServices cs = new CategoriaServices();

        // Validar que el texto no esté vacío
        if (texto == null || texto.trim().isEmpty()) {
            map.put("msg", "Error");
            map.put("data", "El texto de búsqueda no puede estar vacío");
            return Response.status(Status.BAD_REQUEST).entity(map).build();
        }

        LinkedList<Categoria> lista = cs.busquedaLinCategoria(texto);

        map.put("msg", "Ok");
        map.put("data", lista.toArray());

        if (lista.isEmpty()) {
            map.put("data", new Object[] {}); // Devolver un array vacío si no hay resultados
        }

        return Response.ok(map).build();
    }
}