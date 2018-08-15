package net.ntlx.proximus.backend.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.PastOrPresent;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "\"UTILISATEUR\"")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Utilisateur implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -7562652296079622447L;

	/**
	 *
	 */

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	@Column(name = "\"ID\"")
	private Long id;

	// @NotBlank(message = "Le NOM doit etre rempli  !!")
	@Column(name = "\"NOM\"")
	private String nom;

	// @NotBlank(message = "Le PRENOM doit etre rempli  !!")
	@Column(name = "\"PRENOM\"")
	private String prenom;

	@Email
	@Column(name = "\"EMAIL\"")
	private String email;

	// @Size(min=5, max=5)
	@Column(name = "\"CODE_POSTAL\"")
	private Long codePostal;

	@Temporal(TemporalType.DATE)
	@PastOrPresent
	@Column(name = "\"DATE_NAISSANCE\"")
	private Date dateNaissance;

	@ManyToMany(cascade = { CascadeType.ALL })
	@JoinTable(name = "\"FAVORIS\"", joinColumns = { @JoinColumn(name = "\"ID_UTILISATEUR\"") }, inverseJoinColumns = {
			@JoinColumn(name = "\"ID_ENTREPRISE\"") })
	private Collection<Entreprise> favoris;
	
	@OneToMany(mappedBy = "utilisateur" , fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private Collection<Avis> avis;
	
	@OneToMany(mappedBy = "utilisateur" , fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private Collection<Question> questions;
	
	@OneToMany(mappedBy = "utilisateur" , fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private Collection<Reponse> reponses;

	/**
	 * Getters pour utiliser @JsonIgnore
	 */
	
	@JsonIgnore
	public Collection<Entreprise> getFavoris() {
		return favoris;
	}
	@JsonIgnore
	public Collection<Avis> getAvis() {
		return avis;
	}
	@JsonIgnore
	public Collection<Question> getQuestions() {
		return questions;
	}
	@JsonIgnore
	public Collection<Reponse> getReponses() {
		return reponses;
	}
}
