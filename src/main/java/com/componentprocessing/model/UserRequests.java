package com.componentprocessing.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@EqualsAndHashCode
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

}
