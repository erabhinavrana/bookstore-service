/**
 * 
 */
package com.smartdubai.bookstore.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author abhinav
 *
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "book")
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotBlank(message = "Name is mandatory")
	private String name;
	@NotBlank(message = "description is mandatory")
	private String description;
	@NotBlank(message = "author is mandatory")
	private String author;
	@NotBlank(message = "type/classification is mandatory")
	private String type;
	@NotNull(message = "price is mandatory")
	private Double price;
	private String ISBN;
}
