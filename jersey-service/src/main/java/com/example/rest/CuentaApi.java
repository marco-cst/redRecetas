package com.example.rest;

import java.util.HashMap;

import javax.ws.rs.Consumes;
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
import controller.security.JwtUtil;
import models.Cuenta;
import models.Persona;


@Path("account")
public class CuentaApi {

@POST
@Path("/login")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public Response login(HashMap<String, String> credentials) throws Exception {
    String correo = credentials.get("correo");
    String clave = credentials.get("clave");
    
    CuentaServices cuentaServices = new CuentaServices();
    PersonaServicies personaServices = new PersonaServicies(); // Para obtener los datos de la persona

    // Buscar la cuenta
    Cuenta cuenta = cuentaServices.listAll().stream()
            .filter(c -> c.getCorreo().equals(correo) && c.getClave().equals(clave) && c.getEstado())
            .findFirst()
            .orElse(null);

    HashMap<String, Object> response = new HashMap<>();
    if (cuenta != null) {
        // Obtener datos de la persona asociada
        Persona persona = personaServices.get(cuenta.getIdPersona());

        // Generar un token
        String token = JwtUtil.generateToken(correo);

        // Construir la respuesta con datos de la cuenta y persona
        response.put("msg", "Login exitoso");
        response.put("token", token);
        response.put("correo", cuenta.getCorreo());
        System.out.println("Correo: " + cuenta.getCorreo());
        response.put("idCuenta", cuenta.getIdCuenta());
        System.out.println("idCuenta: " + cuenta.getIdCuenta());
        response.put("nombre", persona.getNombre());
        response.put("apellido", persona.getApellido());
        response.put("correo", persona.getApellido());
        response.put("identificacion", persona.getIdPersona());
        response.put("Apodo", persona.getApodo());
        response.put("estado", cuenta.getEstado());
        return Response.ok(response).build();
    } else {
        response.put("msg", "Credenciales incorrectas o cuenta inactiva");
        return Response.status(Response.Status.UNAUTHORIZED).entity(response).build();
    }
}


@POST
@Path("/getPersona")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public Response getPersona(HashMap<String, Integer> data) throws Exception {
    int idPersona = data.get("idPersona");
    PersonaServicies personaServicies = new PersonaServicies();
    
    Persona persona = personaServicies.get(idPersona);
    if (persona != null) {
        return Response.ok(persona).build();
    } else {
        HashMap<String, String> errorResponse = new HashMap<>();
        errorResponse.put("msg", "Persona no encontrada");
        System.out.println("Persona no encontrada");
        return Response.status(Response.Status.NOT_FOUND).entity(errorResponse).build();
    }
}


    




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
    public Response getCuentaById(@PathParam("id") Integer id) {
        HashMap<String, Object> response = new HashMap<>();
        CuentaServices cuentaService = new CuentaServices();
    
        try {
            // Busca la cuenta por ID
            Cuenta cuenta = cuentaService.get(id);
    
            if (cuenta == null || cuenta.getIdCuenta() == 0) {
                // Respuesta si no existe la cuenta
                response.put("msg", "No existe la cuenta con ese identificador");
                response.put("data", null);
                return Response.status(Status.NOT_FOUND).entity(response).build();
            }
    
            // Si la cuenta existe, retorna los datos
            response.put("msg", "Ok");
            response.put("data", cuenta);
            return Response.ok(response).build();
    
        } catch (Exception e) {
            // Manejo de errores
            response.put("msg", "Error interno del servidor");
            response.put("data", e.getMessage());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(response).build();
        }
    }
    

    @Path("/save")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response save(HashMap<String, Object> map) {
        HashMap<String, Object> res = new HashMap<>();
    
        // Validar que los parámetros requeridos estén presentes
        if (map.get("person") == null || map.get("correo") == null || map.get("clave") == null) {
            res.put("msg", "Error");
            res.put("data", "Faltan parámetros requeridos: person, correo o clave.");
            return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
        }
    
        try {
            // Buscar persona con el ID recibido
            PersonaServicies fs = new PersonaServicies();
            fs.setPersona(fs.get(Integer.parseInt(map.get("person").toString())));
    
            if (fs.getPersona() != null && fs.getPersona().getIdPersona() != null) {
                // Persona encontrada, crear y guardar cuenta
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
                // Si la persona no existe
                res.put("msg", "Error");
                res.put("data", "No se puede guardar la cuenta, la persona no existe");
                return Response.status(Response.Status.NOT_FOUND).entity(res).build();
            }
        } catch (Exception e) {
            // Manejar cualquier excepción durante el proceso
            res.put("msg", "Error");
            res.put("data", "Error al guardar la cuenta: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
    }
    
    @Path("/update")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(HashMap<String, Object> map) {
        // todo
        // Validation
        HashMap<String, Object> res = new HashMap<>();
        try {

            if (map.get("person") != null) {
                PersonaServicies fs = new PersonaServicies();
                fs.setPersona(fs.get(Integer.parseInt(map.get("person").toString())));
                if (fs.getPersona().getIdPersona() != null) {
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
    public Response deletePerson(@PathParam("id") int id) {
        HashMap<String, Object> res = new HashMap<>();
    
        try {
            CuentaServices fs = new CuentaServices();
            
            if (fs.delete(id)) {
                res.put("message", "Cuenta eliminada exitosamente");
                return Response.ok(res).build();
            } else {
                // Si no se eliminó, enviar un error 404
                res.put("message", "Cuenta no encontrada");
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
