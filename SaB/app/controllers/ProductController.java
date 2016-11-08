package controllers;


import javax.inject.Inject;

import play.cache.CacheApi;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;


public class ProductController extends Controller {

	@Inject
	private FormFactory formFactory;

	@Inject
	private CacheApi cache;

	public Result list() {
		// TODO
		return ok();
	}

}
