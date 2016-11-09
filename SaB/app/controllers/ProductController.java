package controllers;


import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import helpers.JsonHelper;
import models.Password;
import models.Product;
import models.User;
import play.cache.CacheApi;
import play.data.Form;
import play.data.FormFactory;
import play.db.ebean.Transactional;
import play.libs.Json;
import play.mvc.*;
import play.twirl.api.Content;
import views.html.*;

public class ProductController extends Controller {

	@Inject
	private FormFactory formFactory;

	@Inject
	private CacheApi cache;
	
	private List<Product> productList = new ArrayList<>();

	 public Result retrieveProducts() {
		 	response().setHeader("X-Product-Count", String.valueOf(productList.size()));
		 	
		 	if(request().accepts("application/xml")){
				  Content content = views.xml.products.render(productList);
				 return ok(content);
			 }
			 else if(request().accepts("application/json")){
			 	ArrayNode arrayJSON = play.libs.Json.newArray();
			 	
			 	for(int i=0;i<productList.size();i++){
			 		 Product product = productList.get(i);
					 ObjectNode product_node = play.libs.Json.newObject();
					 product_node.put("id", product.getId());
					 product_node.put("name",product.getName());
					 product_node.put("amount",product.getAmount());	
					 product_node.put("price",product.getPrice());
					 arrayJSON.add(product_node);
				 }
				 
				 return ok(arrayJSON);
			 }

			 return Results.status(406);
	    }
	 
		public Result retrieveProduct(Long id) {
			Product product = cache.get("product-" + id);
			if (product == null) {
				product = product.findById(id);
				cache.set("product-" + id, product);
			}

			if (product == null) {
				return notFound();
			}

			if (request().accepts("application/xml")) {
				return ok(views.xml.product.render(product));
			}
			else if (request().accepts("application/json")) {
				JsonNode node = cache.get("product-" + id + "-json");
				if (node == null) {
					node = product.toJson();
					cache.set("product-" + id + "-json", node, 60);
				}
				return ok(node);
			}

			return Results.status(406);
		}	 
		
		@Transactional
		public Result createProduct(Long userId) {
			Form<Product> p = formFactory.form(Product.class).bindFromRequest();
			
			if (p.hasErrors()) {
				return Results.badRequest(p.errorsAsJson());
			}
			Product product = p.get();
			product.setUser(User.findById(userId));
			product.save();
			

			return Results.status(CREATED, product.toJson());
		}
}
