package com.componentprocessing.model;

import java.util.Objects;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class DefectiveComponentDetail {
	@NotNull(message = "componentName cannot be null")
	private String componentName;
	@NotNull(message = "componentType cannot be null")
	private String componentType;
	@NotNull(message = "quantity cannot be null")
	private Integer quantity;

	public DefectiveComponentDetail(String componentName, String componentType, Integer quantity) {
		super();
		this.componentName = componentName;
		this.componentType = componentType;
		this.quantity = quantity;
	}

	@Override
	public int hashCode() {
		return Objects.hash(componentName, componentType, quantity);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DefectiveComponentDetail other = (DefectiveComponentDetail) obj;
		return Objects.equals(componentName, other.componentName) && Objects.equals(componentType, other.componentType)
				&& Objects.equals(quantity, other.quantity);
	}

}
