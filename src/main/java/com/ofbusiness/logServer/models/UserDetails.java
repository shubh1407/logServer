package com.ofbusiness.logServer.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown= true)
public class UserDetails {

	@Column
	@Id
	@GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
	private String userId;
	@Column
	private String userName;
}
