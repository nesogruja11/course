package com.course.movieapp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "content_type")
public class ContentType {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NotNull(message = "Id tipa sadržaja je obavezan!")
	private int contentTypeId;

	@Column(name = "name", nullable = false)
	@NotNull(message = "Naziv tipa sadržaja je obavezan!")
	private String name;
}
