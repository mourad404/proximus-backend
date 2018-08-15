package net.ntlx.proximus.backend.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "\"CATEGORIE\"")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Categorie implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -5293405303438036955L;

	/**
	 *
	 */

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "\"ID\"")
	private Long id;

	// @NotBlank(message="La catégorie doit etre renseignée")
	// @Size(max=50)
	@Column(name = "\"LABEL\"", length = 50)
	private String label;

}
