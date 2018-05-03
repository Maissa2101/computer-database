package com.excilys.java.formation.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class Users {
	@Id
	private String pseudo;
	private String password;
	private Boolean enabled;
	
	
	public Users(UserBuilder builder) {
		this.pseudo = builder.pseudo;
		this.password = builder.passeword;
		this.enabled = builder.enabled;
	}

	public Users() {
	}

	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String passeword) {
		this.password = passeword;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public String toString() {
		return "Users pseudo=" + pseudo + ", password=" + password + ", enabled=" + enabled;
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Users other = (Users) obj;
		if (enabled == null) {
			if (other.enabled != null)
				return false;
		} else if (!enabled.equals(other.enabled))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (pseudo == null) {
			if (other.pseudo != null)
				return false;
		} else if (!pseudo.equals(other.pseudo))
			return false;
		return true;
	}

	public static class UserBuilder {
		private String pseudo;
		private String passeword;
		private boolean enabled;

		public UserBuilder(String pseudo, String passeword, boolean enabled) {
			this.pseudo = pseudo;
			this.passeword = passeword;
			this.enabled = enabled;
		}

		public Users build() {
			return new Users(this);
		}
	}
}