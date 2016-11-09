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

	public Result retrieveUser(Long id) {
		User user = cache.get("user-" + id);
		if (user == null) {
			user = user.findById(id);
			cache.set("user-" + id, user);
		}

		if (user == null) {
			return notFound();
		}

		if (request().accepts("application/xml")) {
			return ok(views.xml.user.render(user));
		}
		else if (request().accepts("application/json")) {
			JsonNode node = cache.get("user-" + id + "-json");
			if (node == null) {
				node = user.toJson();
				cache.set("user-" + id + "-json", node, 60);
			}
			return ok(node);
		}

		return Results.status(406);
	}

	@Transactional
	public Result createUser() {
		Form<User> f = formFactory.form(User.class).bindFromRequest();
		Form<Password> p = formFactory.form(Password.class).bindFromRequest();

		if (f.hasErrors()) {
			return Results.badRequest(f.errorsAsJson());
		}
		if (p.hasErrors()) {
			return Results.badRequest(p.errorsAsJson());
		}

		User usu = f.get();
		Password ps = p.get();

		usu.setPassword(ps);
		ps.setUser(usu);

		usu.save();
		ps.save();

		return Results.status(CREATED, usu.toJson());
	}

}
