package controllers;



import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.inject.Inject;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import helpers.JsonHelper;
import models.Password;
import models.User;
import play.mvc.*;
import play.mvc.Results;
import play.twirl.api.Content;
import views.html.*;
import play.cache.CacheApi;
import play.cache.Cached;
import play.data.Form;
import play.data.FormFactory;
import play.db.ebean.Transactional;
import play.libs.*;

public class UserController extends Controller {

	@Inject
	private FormFactory formFactory;

	@Inject
	private CacheApi cache;

	public Result retrieve(Long id) {
		User user = cache.get("usuario-" + id);
		if (user == null) {
			user = user.findById(id);
			cache.set("usuario-" + id, user);
		}

		if (user == null) {
			return notFound();
		}

		if (request().accepts("application/xml")) {
			return ok(views.xml.user.render(user));
		}
		else if (request().accepts("application/json")) {
			JsonNode node = cache.get("usuario-" + id + "-json");
			if (node == null) {
				node = user.toJson();
				cache.set("usuario-" + id + "-json", node, 60);
			}
			return ok(node);
		}

		return Results.status(406);
	}

	@Transactional
	public Result create() {
		Form<User> f = formFactory.form(User.class).bindFromRequest();

		if (f.hasErrors()) {
			return Results.badRequest(f.errorsAsJson());
		}

		Password pwd = new Password();
		pwd.setPasswordHash(String.valueOf(System.currentTimeMillis()));
		pwd.save();

		User usu = f.get();

		usu.setPassword(pwd);
		pwd.setUser(usu);

		usu.save();
		pwd.save();

		return Results.status(CREATED, usu.toJson());
	}

	public Result update(Integer id) {
		// TODO
		return ok();
	}

	public Result remove(Integer id) {
		User usuario = User.findById(Long.valueOf(id));
		if (usuario == null) {
			return notFound();
		}

		if (usuario.delete()) {
			cache.remove("usuario-" + id);
			return ok();
		}
		else {
			return internalServerError();
		}
	}

}
