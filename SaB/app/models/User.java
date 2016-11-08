package models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.databind.JsonNode;

import play.data.validation.Constraints.Required;
import play.libs.Json;
@Entity
public class User extends Model{
	
	@Id
	private Long id;
	@Required
	private String name;
	@Required
	private String email;
	@OneToOne (cascade = CascadeType.ALL)
	private Password password;
	@OneToMany (cascade = CascadeType.ALL, mappedBy = "user")
	private List<Product> products;	


	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public static Find<Long, User> getFind() {
		return find;
	}
	public void setId(Long id) {
		this.id = id;
	}
	private static final Find<Long,User> find = new Find<Long,User>(){};
	
	public static User findById(Long id){
		return find.byId(id);
	}
	public static List<User> findByEmail(String email){
		return find.where().eq("email", email).findList();
	}
	
	public static List<User> findPage(Integer page, Integer count){
		return find.setFirstRow(page * count).setMaxRows(count).findList();
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}

	public JsonNode toJson(){
		return Json.toJson(this);
	}
	public Password getPassword() {
		return password;
	}
	public void setPassword(Password password) {
		this.password = password;
	}
	public List<Product> getProducts() {
		return products;
	}
	public void setProducts(List<Product> products) {
		this.products = products;
	}


	

}
