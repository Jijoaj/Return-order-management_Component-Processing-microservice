package com.componentprocessing.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
public class UserRequests {
	@NotNull(message = "Name cannot be null")
	private String name;

	@Id
	@NotNull
	@Setter
	@Column(name = "requestId")
	private long requestId;

	public UserRequests(@NotNull(message = "Name cannot be null") String name, @NotNull long requestId) {
		super();
		this.name = name;
		this.requestId = requestId;
	}

	public UserRequests() {
		super();
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, requestId);
	}

	@Override
	public boolean equals(Object obj) {
		UserRequests other = (UserRequests) obj;
		return Objects.equals(name, other.name) && requestId == other.requestId;
	}

}
