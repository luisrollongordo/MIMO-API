package validators;

import play.data.validation.Constraints;
import play.libs.F;
import play.libs.F.Tuple;

public class PaswordValidator extends Constraints.Validator<String> {

	@Override
	public boolean isValid(String object) {
		return (object.length() > 5);
	}

	@Override
	public Tuple<String, Object[]> getErrorMessageKey() {
		return new F.Tuple<String, Object[]>("Password is too short", new Object[] { "" });
	}

}
