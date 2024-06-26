package com.example.democfx.apis;

import com.example.democfx.apis.dto.UsuarioResponse;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.cxf.jaxrs.client.WebClient;

import javax.ws.rs.core.Response;

public class GithubApiCalls {

    // Ejemplo de get de arrays
    public void loguearUsuariosId() throws Exception {
        WebClient clientUsers = WebClient.create("https://api.github.com/users?page=2&per_page=10");

        ObjectMapper objectMapper = new ObjectMapper(); 
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); // Solamente trae lo que me interesa, el resto ignoralo

        Response response = clientUsers.header("Content-Type", "application/json").get(); // Aca hago la llamada

        int status = response.getStatus();
        System.out.println("Status: " + status); // Lo imprimo por consola

        String responseBody = response.readEntity(String.class); // Interpreto la respuesta
        
        if (status == 200) { // Camino feliz

            System.out.println("response = " + responseBody);
            UsuarioResponse[] usuarios = objectMapper.readValue(responseBody, UsuarioResponse[].class); // Sirve para deseñalizar

            for (int i = 0; i < usuarios.length; i++) { System.out.println("ID: " + usuarios[i].getId()); }

        } else {

            System.out.println("Error response = " + responseBody);
            throw new Exception("Error en la llamada a /api/user");
            
        }
    }
}
