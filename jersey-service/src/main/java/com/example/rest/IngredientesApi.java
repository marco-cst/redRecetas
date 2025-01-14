package com.example.rest;

import controller.Dao.servicies.IngredientesServicies;
import java.util.HashMap;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.Response.StatusType;

@Path("ingredientes")
public class IngredientesApi {

    @Path("/listType")

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getType() {
        HashMap map = new HashMap<>();
        IngredientesServicies ps = new IngredientesServicies();
        map.put("msg", "Ok");
        map.put("data", ps.getIngredientes());
        return Response.ok(map).build();
    }

    @Path("/list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listAllIngre() {
        HashMap map = new HashMap<>();
        IngredientesServicies ps = new IngredientesServicies();
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
    public Response getIngre(@PathParam("id") Integer id) {
        HashMap map = new HashMap<>();
        IngredientesServicies ps = new IngredientesServicies();
        try {
            ps.setIngredientes(ps.get(id));
        } catch (Exception e) {
            // TODO: handle exception
        }
        map.put("msg", "Ok");
        map.put("data", ps.getIngredientes());
        if (ps.getIngredientes().getIdIngrediente() == null) {
            map.put("data", "No existe el Ingrediente con ese identificador");
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
            IngredientesServicies ps = new IngredientesServicies();

            // Validación de campos obligatorios
            if (!map.containsKey("nombre") || map.get("nombre").toString().isEmpty()) {
                throw new IllegalArgumentException("El nombre del ingrediente es obligatorio.");
            }
            if (!map.containsKey("cantidad") || map.get("cantidad").toString().isEmpty()) {
                throw new IllegalArgumentException("La cantidad es obligatoria.");
            }

            // Formatear nombre
            String nombre = map.get("nombre").toString().trim();
            nombre = Character.toUpperCase(nombre.charAt(0)) + nombre.substring(1).toLowerCase();
            ps.getIngredientes().setNombre(nombre);

            // Validación de cantidad
            float cantidad = Float.parseFloat(map.get("cantidad").toString());
            if (cantidad <= 0) {
                throw new IllegalArgumentException("La cantidad debe ser mayor a cero.");
            }
            ps.getIngredientes().setCantidad(cantidad);

            // Asignar unidad de medida
            String unidadMedida = map.containsKey("unidadMedida") && !map.get("unidadMedida").toString().isEmpty()
                    ? map.get("unidadMedida").toString()
                    : "No especificado";
            ps.getIngredientes().setUnidadMedida(unidadMedida);

            ps.save();

            res.put("msg", "Ok");
            res.put("data", "Ingredientes guardados exitosamente");
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
            IngredientesServicies ps = new IngredientesServicies();
            ps.setIngredientes(ps.get(Integer.parseInt(map.get("idIngredientes").toString())));

            // Validación de campos obligatorios
            if (!map.containsKey("nombre") || map.get("nombre").toString().isEmpty()) {
                throw new IllegalArgumentException("El nombre del ingrediente es obligatorio.");
            }
            if (!map.containsKey("cantidad") || map.get("cantidad").toString().isEmpty()) {
                throw new IllegalArgumentException("La cantidad es obligatoria.");
            }

            // Formatear nombre
            String nombre = map.get("nombre").toString().trim();
            nombre = Character.toUpperCase(nombre.charAt(0)) + nombre.substring(1).toLowerCase();
            ps.getIngredientes().setNombre(nombre);

            // Validación de cantidad
            float cantidad = Float.parseFloat(map.get("cantidad").toString());
            if (cantidad <= 0) {
                throw new IllegalArgumentException("La cantidad debe ser mayor a cero.");
            }
            ps.getIngredientes().setCantidad(cantidad);

            // Asignar unidad de medida
            String unidadMedida = map.containsKey("unidadMedida") && !map.get("unidadMedida").toString().isEmpty()
                    ? map.get("unidadMedida").toString()
                    : "No especificado";
            ps.getIngredientes().setUnidadMedida(unidadMedida);

            ps.update();

            res.put("msg", "Ok");
            res.put("data", "Ingredientes actualizados exitosamente");
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
            IngredientesServicies fs = new IngredientesServicies();

            // Intentar eliminar la reseña por ID
            boolean IngreDeleted = fs.delete(id);

            if (IngreDeleted) {
                res.put("message", "Ingredientes eliminados exitosamente");
                return Response.ok(res).build();
            } else {
                // Si no se eliminó, enviar un error 404
                res.put("message", "Ingrediente no encontrado");
                return Response.status(Response.Status.NOT_FOUND).entity(res).build();
            }
        } catch (Exception e) {
            // En caso de error, devolver una respuesta de error interno
            res.put("message", "Error al intentar eliminar el ingrediente");
            res.put("error", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
    }
}
