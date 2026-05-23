package com.ems.backend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {

    private String message;
    private boolean success;
    private int statusCode;
    private Integer count;
    private String role;
    private Object data;
    private String token;
    private String name;
    private String email;


    public static Response success(String message){
        Response response = new Response();
        response.setMessage(message);
        response.setSuccess(true);
        response.setStatusCode(200);
        return response;
    }

    public static Response error(String message,int statusCode){
        Response response = new Response();
        response.setMessage(message);
        response.setSuccess(false);
        response.setStatusCode(statusCode);
        return response;
    }


    public static Response auth(String token, String role, String name, String email) {
        Response response = new Response();
        response.setMessage("Authentication successful");
        response.setSuccess(true);
        response.setStatusCode(200);
        response.setToken(token);
        response.setRole(role);
        response.setName(name);
        response.setEmail(email);
        return response;
    }

    public Response withCount(int count) {
        this.count = count;
        return this;
    }


    public Response withData(Object data) {
        this.data = data;
        return this;
    }

    public Response withRole(String role) {
        this.role = role;
        return this;
    }

    public Response withToken(String token) {
        this.token = token;
        return this;
    }

}
