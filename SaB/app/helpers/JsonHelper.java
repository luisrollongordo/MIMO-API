package helpers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import play.libs.Json;
import play.mvc.Result;
import play.mvc.Results;

public class JsonHelper {

	public static JsonNode getErrorJson(int errorCode) {
		ObjectNode errorNode = Json.newObject();
		errorNode.put("error_code", errorCode);
		errorNode.put("description", "xxxx");
		return errorNode;
	}

	public static Result getErrorResult(int errorCode) {
		switch (errorCode) {
		case 1:
			JsonNode node = getErrorJson(errorCode);
			return Results.notFound(node);
		case 2:
			break;
		case 3:
			break;
		}

		return null;
	}

}
