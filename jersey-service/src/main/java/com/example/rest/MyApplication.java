package com.example.rest;

import org.glassfish.jersey.server.ResourceConfig;
import controller.security.JwtFilter;
import javax.ws.rs.ApplicationPath;

@ApplicationPath("api")
public class MyApplication extends ResourceConfig {
    public MyApplication() {
        packages("com.example.rest");
        register(JwtFilter.class); // Registrar el filtro JWT
    }
}
