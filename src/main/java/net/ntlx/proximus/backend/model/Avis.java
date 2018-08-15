package net.ntlx.proximus.backend.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "\"AVIS\"")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Avis implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4807991436215487090L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "\"ID\"")
	private Long id;

	// @NotBlank(message = "Le type doit etre rempli !!")
	@Column(name = "\"TYPE\"")
	private String type;

	// @NotBlank(message = "Le type doit etre rempli !!")
	@Column(name = "\"CONTENU\"", columnDefinition = "text")
	private String contenu;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "\"DATE_AJOUT\"")
	private Date dateAjout;

	@OneToOne(cascade = CascadeType.REMOVE)
	@JoinColumn(name = "ID_NOTE")
	private Note note;

	@ManyToOne
	@JoinColumn(name = "ID_UTILISATEUR")
	private Utilisateur utilisateur;

	@ManyToOne
	@JoinColumn(name = "ID_ENTREPRISE")
	private Entreprise entreprise;

}
