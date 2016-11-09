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
	

	 public Result retrieveProducts(Integer page, Integer count) {
		 
		 List<Product> productList = cache.get("productList");
			if (productList ==null) {
				productList = Product.findPage(page, count);
				System.out.println(productList.get(0).getName());
				cache.set("productList", productList);
			}

			if (productList == null) {
				return notFound();
			}

			if (request().accepts("application/xml")) {
				return ok(views.xml.products.render(productList));
			}
			else if (request().accepts("application/json")) {
				JsonNode node = cache.get("productList-json");
				if (node == null) {
					node = Product.toJsonArray(productList);
					cache.set("productList-json", node, 60);
				}
				return ok(node);
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
			if (User.findById(userId) == null){
				return Results.badRequest("User id dont exist");
			}
			Product product = p.get();
			product.setUser(User.findById(userId));
			product.save();
			

			return Results.status(CREATED, product.toJson());
		}
}
