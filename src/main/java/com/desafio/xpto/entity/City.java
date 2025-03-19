package com.desafio.xpto.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class City {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;
	private String state;
	private String noAccents;
	private String alternativeNames;
	private String microregion;
	private String mesoregion;
	private boolean isCapital;
	private double latitude;
	private double longitude;
	private int ibgeId;

	public boolean isCapital() {
		return isCapital;
	}

	public void setIsCapital(boolean isCapital) {
		this.isCapital = isCapital;
	}
}
