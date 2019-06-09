package com.ubiquisoft.evaluation.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Car {

	private String year;
	private String make;
	private String model;
	private List<Part> parts;

	/**
	 * Check off missing parts that are currently on the car
	 * @return map of part types and quantities that are missing
	 */
	public Map<PartType, Integer> getMissingPartsMap() {

		Map<PartType, Integer> requiredParts = defineRequiredParts();

		if (this.getParts() == null || this.getParts().isEmpty()) {
			return requiredParts;
		} else {
			for (Part part : this.getParts()) {
				PartType partType = part.getType();

				if (requiredParts.get(partType) > 1) {
					requiredParts.put(partType, requiredParts.get(partType) - 1);
				} else {
					requiredParts.remove(partType);
				}
			}
		}

		return requiredParts;
	}

	private Map<PartType, Integer> defineRequiredParts() {
		Map<PartType, Integer> requiredParts = new HashMap<>();
		requiredParts.put(PartType.ENGINE, 1);
		requiredParts.put(PartType.ELECTRICAL, 1);
		requiredParts.put(PartType.FUEL_FILTER, 1);
		requiredParts.put(PartType.OIL_FILTER, 1);
		requiredParts.put(PartType.TIRE, 4);

		return requiredParts;
	}

	@Override
	public String toString() {
		return "Car{" +
				       "year='" + year + '\'' +
				       ", make='" + make + '\'' +
				       ", model='" + model + '\'' +
				       ", parts=" + parts +
				       '}';
	}

	/* --------------------------------------------------------------------------------------------------------------- */
	/*  Getters and Setters *///region
	/* --------------------------------------------------------------------------------------------------------------- */

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public List<Part> getParts() {
		return parts;
	}

	public void setParts(List<Part> parts) {
		this.parts = parts;
	}

	/* --------------------------------------------------------------------------------------------------------------- */
	/*  Getters and Setters End *///endregion
	/* --------------------------------------------------------------------------------------------------------------- */

}
