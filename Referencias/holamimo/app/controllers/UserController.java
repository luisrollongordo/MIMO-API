package controllers;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import helpers.JsonHelper;
import play.mvc.*;

import play.libs.*;

public class UserController extends Controller {
	
	private List<String> users = new ArrayList<>();

    public Result create() {
    	//Recuperamos el valor del parámetro name que nos han pasado
    	String name = request().getQueryString("name");
    	//validación: el name debe existir
    	if(name == null || name.length() < 2){
    		System.out.println("No ha pasado parámetro name");
    		return Results.status(PRECONDITION_FAILED);
    	}
    	
    	//validación: no permitimos repetidos
    	if(users.contains(name)){
    		System.out.println("Name repetido");
    		return Results.status(CONFLICT);
    	}
    	
    	//En el caso de que nos hayan pasado el parámetro name correctamente, lo añadimos al array
    	users.add(name);
    	System.out.println("Usuario creado");
    	return Results.status(CREATED, String.valueOf(users.size() - 1));
    }
    
    public Result list() {
    	ArrayNode usersArray = Json.newArray();
    	
    	for(int i = 0; i<users.size();i++){
    		String name = users.get(i);
    		if(!name.isEmpty()){
        		ObjectNode userNode = Json.newObject();

        		userNode.put("id", i);
        		userNode.put("name", name);	
        		
        		usersArray.add(userNode);
    		}
    	}
    	
    	//TODO No vale porque le van a llegar los dummies
    	response().setHeader("X-User-Count", String.valueOf(users.size()));
    	
    	return ok(usersArray);
    }
    
    public Result retrieve(Integer id) {
    	//validación: id válido
    	if (id >= users.size()){
    		return JsonHelper.getErrorResult(1);
    	}
    	
    	String name = users.get(id);
    	
    	//validación: El objeto no puede ser dummy
    	if(name.isEmpty()){
    		return Results.notFound();
    	}
    	
    	return ok(name);
    }
    
    public Result update(Integer id) {
    	
    	//Recuperamos el valor del parámetro name que nos han pasado    	
    	String name = request().getQueryString("name");
    	
    	//validación: id válido    	
    	if (id >= users.size()){
    		System.out.println("Id incorrecto");
        	return Results.notFound();	
    	}  
    	//validación: el name debe existir
    	if(name == null || name.length() < 2){
    		System.out.println("No ha pasado parámetro name");
    		return Results.status(PRECONDITION_FAILED);
    	}    	
    	
    	//TODO: Comprobar que no sea dummy
    	
    	users.set(id, name);
    	
    	System.out.println("Usuario actualizado");
    	return ok();
    }
    
    public Result remove(Integer id) {
    	//validación: id válido
    	if (id >= users.size()){
    		System.out.println("Id incorrecto");
        	return Results.notFound();	
    	}
    	
    	//No podemos borrar el objeto de la lista
    	//porque estaríamos cambiando ids de todos los objetos.
    	//En lugar de borrarlo, lo sustituimos por un valor
    	//tipo dummy
    	
    	users.set(id, "");
    	System.out.println("Usuario Eliminado");
    	return ok();
    }
	
}
