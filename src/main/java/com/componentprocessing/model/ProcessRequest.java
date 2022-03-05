package com.componentprocessing.model;

import java.util.Objects;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@RequiredArgsConstructor
@Valid
public class ProcessRequest {

	@NotNull(message = "Name cannot be null")
	@Setter
	private String name;
	@NotNull(message = "Contact Number cannot be null")
	private Long contactNumber;
	@NotNull(message = "Defective component detail cannot be null")
	@Valid
	private DefectiveComponentDetail defectiveComponentDetail;

	@Override
	public int hashCode() {
		return Objects.hash(contactNumber, defectiveComponentDetail, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProcessRequest other = (ProcessRequest) obj;
		return Objects.equals(contactNumber, other.contactNumber)
				&& Objects.equals(defectiveComponentDetail, other.defectiveComponentDetail)
				&& Objects.equals(name, other.name);
	}

	public ProcessRequest(@NotNull String name, @NotNull Long contactNumber,
			@NotNull @Valid DefectiveComponentDetail defectiveComponentDetail) {
		super();
		this.name = name;
		this.contactNumber = contactNumber;
		this.defectiveComponentDetail = defectiveComponentDetail;
	}

}
