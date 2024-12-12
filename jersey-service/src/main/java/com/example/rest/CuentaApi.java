package com.example.rest;


import java.util.HashMap;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import controller.Dao.servicies.CuentaServices;


@Path("account")
public class CuentaApi {
    @Path("/list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllaccounts() {
        HashMap<String, Object> map = new HashMap<>();
        CuentaServices ps = new CuentaServices();
        map.put("msg", "Ok");
        map.put("data", ps.listAll().toArray());
        if (ps.listAll().isEmpty()) {
            map.put("data", new Object[] {});
            return Response.status(Status.BAD_REQUEST).entity(map).build();
        }
        return Response.ok(map).build();
    }

    @Path("/save")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response save(HashMap<String, Object> map) {
        //todo
        //Validation
       
            CuentaServices ps = new CuentaServices();
            ps.getCuenta().setCorreo(map.get("correo").toString());
            ps.getCuenta().setClave(map.get("clave").toString());
            ps.getCuenta().setEstado(true);
            HashMap<String, Object> res = new HashMap<>();
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
    

}
