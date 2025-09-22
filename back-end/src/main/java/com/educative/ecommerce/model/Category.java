package com.educative.ecommerce.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Entity
@Table(name = "categories", schema = "esame")
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	@Column(name = "id", nullable = false)
	private Integer id;

	@NotBlank
	@Column(name = "category_name", nullable = false)
	private String categoryName;

	@NotBlank
	@Column(name = "description", nullable = false)
	private String description;

	@Column(name = "image_url")
	private String imageUrl;

	// Relazione uno-a-molti con Product (se vuoi vedere i prodotti della categoria)
	@OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@ToString.Exclude
	private Set<Product> products;

	public Category() {}

	public Category(String categoryName, String description) {
		this.categoryName = categoryName;
		this.description = description;
	}

	public Category(String categoryName, String description, String imageUrl) {
		this.categoryName = categoryName;
		this.description = description;
		this.imageUrl = imageUrl;
	}
}
