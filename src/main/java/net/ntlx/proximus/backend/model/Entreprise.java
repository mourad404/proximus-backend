package net.ntlx.proximus.backend.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;

import org.hibernate.validator.constraints.URL;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "\"ENTREPRISE\"")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Entreprise implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -8524667375720041876L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "\"ID\"")
	private Long id;

	/**
	 * raison sociale
	 */
	@Column(name = "\"RS\"", length = 100)
	// @NotBlank(message = "C'est obligatoire de remplir ce champs")
	// @Size(max = 100, message = "La raison sociale ne doit dépasser 100
	// caractères")
	private String rs;

	/**
	 * identifiant fiscal
	 */
	@Column(name = "\"IF\"", length = 50)
	private String idFiscal;

	/**
	 * registre de commerce
	 */
	@Column(name = "\"RC\"", length = 50)
	private String rc;

	/**
	 * code ice
	 */
	@Column(name = "\"ICE\"", length = 50)
	private String ice;

	/**
	 * cnss
	 */
	@Column(name = "\"CNSS\"", length = 50)
	private String cnss;

	@ManyToOne
	@JoinColumn(name = "\"ID_CATEGORIE\"")
	private Categorie categorie;

	@Column(name = "\"NOTATION\"", columnDefinition = "Decimal(2,1) default 0")
	private Double notation = (double) 0;

	@Column(name = "\"NB_AVIS\"", columnDefinition = "INT default 0")
	private Integer nombreAvis = 0;

	@Column(name = "\"DESCRIPTION\"", columnDefinition = "text")
	private String description;

	@Temporal(value = TemporalType.DATE)
	@Column(name = "\"DATE_CREATION\"")
	private Date dateCreation;

	// @NotBlank(message="L'adresse doit etre renseignée")
	@Column(name = "\"ADRESSE\"")
	private String adresse;

	// @Size(min=5, max=5, message = "Le code postale doit etre de 5 chiffres")
	@Column(name = "\"CODE_POSTALE\"")
	private Long codePostal;

	@Email
	@Column(name = "\"EMAIL\"")
	private String email;

	@URL
	@Column(name = "\"SITE_WEB\"")
	private String siteWeb;

	// @Size(min=9, max=10, message = "Le numero du telephone doit etre de 9 ou 10
	// chiffres")
	@Column(name = "\"TEL\"")
	private String tel;

	// @Size(min=9, max=10, message = "Le numero du FAX doit etre de 9 ou 10
	// chiffres")
	@Column(name = "\"FAX\"")
	private String fax;

	@URL
	@Column(name = "\"URL_FACEBOOK\"")
	private String urlFacebook;

	@URL
	@Column(name = "\"URL_TWITTER\"")
	private String urlTwitter;

	@URL
	@Column(name = "\"URL_YOUTUBE\"")
	private String urlYoutube;

	@ManyToMany(mappedBy = "favoris")
	private Collection<Utilisateur> funs = new HashSet<>();

	@OneToMany(mappedBy = "entreprise", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private Collection<ImageEse> images;

	@OneToMany(mappedBy = "entreprise", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private Collection<Note> notes;

	@OneToMany(mappedBy = "entreprise", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private Collection<Avis> avis;

	@OneToMany(mappedBy = "entreprise", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private Collection<Question> questions;

	/**
	 * Getters pour utiliser @JsonIgnore
	 */
	@JsonIgnore
	public Collection<Utilisateur> getFuns() {
		return funs;
	}

	@JsonIgnore
	public Collection<ImageEse> getImages() {
		return images;
	}

	@JsonIgnore
	public Collection<Note> getNotes() {
		return notes;
	}

	@JsonIgnore
	public Collection<Avis> getAvis() {
		return avis;
	}

	@JsonIgnore
	public Collection<Question> getQuestions() {
		return questions;
	}
}
