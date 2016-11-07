package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import play.data.validation.Constraints.Min;
import play.data.validation.Constraints.Required;
import play.libs.Json;

@Entity
public class Usuario extends Model {

	@Id
	private Long id;

	@Required
	private String nickname;

	@Required
	private String edad;

	@OneToOne(cascade = CascadeType.ALL)
	@Valid
	private UsuarioPassword password;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "usuario")
	@Valid
	private List<UsuarioTelefonos> telefonos;

	private static final Find<Long, Usuario> find = new Find<Long, Usuario>() {
	};

	public static Usuario findById(Long id) {
		return find.byId(id);
	}

	public static List<Usuario> findByNickname(String nickname) {
		return find.where().eq("nickname", nickname).findList();
	}

	public static List<Usuario> findPage(Integer page, Integer count) {
		return find.setFirstRow(page * count).setMaxRows(count).findList();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getEdad() {
		return edad;
	}

	public void setEdad(String edad) {
		this.edad = edad;
	}

	public JsonNode toJson() {
		return Json.toJson(this);
	}

	public UsuarioPassword getPassword() {
		return password;
	}

	public void setPassword(UsuarioPassword password) {
		this.password = password;
	}

	public List<UsuarioTelefonos> getTelefonos() {
		return telefonos;
	}

	public void setTelefonos(List<UsuarioTelefonos> telefonos) {
		this.telefonos = telefonos;
	}

	public String getTelefonoPrincipal() {
		return telefonos.get(0).getTelefono();
	}

	public void setTelefonoPrincipal(String tlf) {
		telefonos = new ArrayList<>();
		UsuarioTelefonos usuTlf = new UsuarioTelefonos();
		usuTlf.setTelefono(tlf);
		telefonos.add(usuTlf);
	}

}
