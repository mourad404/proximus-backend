package net.ntlx.proximus.backend.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "\"NOTE\"")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Note implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8117881477045149189L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "\"ID\"")
	private Long id;

	// @NotBlank(message = "La valeur doit etre rempli !!")
	// @Range(min=0, max=5, message = "La valeur doit etre entre 0 et 5 !!")
	@Column(name = "\"VALEUR\"")
	private Integer valeur;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "\"DATE_AJOUT\"")
	private Date dateAjout;

	@ManyToOne
	@JoinColumn(name = "ID_UTILISATEUR")
	private Utilisateur utilisateur;

	@ManyToOne
	@JoinColumn(name = "ID_ENTREPRISE")
	private Entreprise entreprise;

}
