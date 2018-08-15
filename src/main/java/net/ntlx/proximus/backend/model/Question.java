package net.ntlx.proximus.backend.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "\"QUESTION\"")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Question implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 920597018747598880L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "\"ID\"")
	private Long id;
	
	@Column(name = "\"CONTENU\"",columnDefinition = "text")
	//@NotBlank(message = "Le contenu doit etre rempli !!")
	private String contenu;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "\"DATE_AJOUT\"")
	private Date dateAjout;
	
	@ManyToOne
	@JoinColumn(name = "ID_UTILISATEUR")
	private Utilisateur utilisateur;
	
	@ManyToOne
	@JoinColumn(name = "ID_ENTREPRISE")
	private Entreprise entreprise;
	
	@OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
	private Collection<Reponse> reponses;
	
	/**
	 * Getter pour utiliser @JsonIgnore
	 */
	@JsonIgnore
	public Collection<Reponse> getReponses() {
		return reponses;
	}
}
