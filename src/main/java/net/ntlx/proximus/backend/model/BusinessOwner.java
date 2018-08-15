package net.ntlx.proximus.backend.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "\"BUSINESS_OWNER\"")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BusinessOwner extends Utilisateur implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -7048940957735094521L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	@Column(name = "\"ID\"")
	private Long id;

	// @Size(min = 9, max = 10, message = "Le numero du telephone doit etre de 9 ou
	// 10 chiffres")
	@Column(name = "\"TEL\"")
	private String tel;

	@OneToOne
	@JoinColumn(name = "ID_ENTREPRISE")
	private Entreprise entreprise;

}
