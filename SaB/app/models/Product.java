package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.avaje.ebean.Model;
import com.avaje.ebean.Model.Find;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.JsonNode;

import play.data.validation.Constraints.Required;
import play.libs.Json;
@Entity
public class Product extends Model{

	@Id
	private Long id;
	@Required
	private String name;
	@Required
	private Integer amount;
	@Required
	private Double price;
	@ManyToOne
	@JsonIgnore
	private User user;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public JsonNode toJson(){
		return Json.toJson(this);
	}
	private static final Find<Long,Product> find = new Find<Long,Product>(){};
	public static Find<Long, Product> getFind() {
		return find;
	}
	public static Product findById(Long id){
		return find.byId(id);
	}
	public static List<Product> findByName(String name){
		return find.where().eq("name", name).findList();
	}
	
	public static List<Product> findPage(Integer page, Integer count){
		return find.setFirstRow(page * count).setMaxRows(count).findList();
	}
	
}
