package models;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.avaje.ebean.Model;
import com.avaje.ebean.annotation.CreatedTimestamp;
import com.fasterxml.jackson.annotation.JsonIgnore;

import helpers.EncryptHelper;
import play.data.validation.Constraints.Required;
import play.data.validation.Constraints.ValidateWith;
import validators.PaswordValidator;

@Entity
public class Password extends Model{

	@Id
	private Long id;
	//@ValidateWith(PaswordValidator.class)
	@Required
	private String passwordHash;
	@CreatedTimestamp
	private Timestamp upDate;	
	@OneToOne (mappedBy = "password")
	@JsonIgnore
	private User user;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPasswordHash() {
		return passwordHash;
	}
	public void setPasswordHash(String passwordHash){

		this.passwordHash = EncryptHelper.sha_encrypt(passwordHash);
	}
	public Timestamp getUpDate() {
		return upDate;
	}
	public void setUpDate(Timestamp upDate) {
		this.upDate = upDate;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	
	
}
