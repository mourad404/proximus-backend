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
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "\"REPONSE\"")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Reponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -508776418281509841L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "\"ID\"")
	private Long id;
	
	@NotBlank(message = "Le contenu doit etre rempli !!")
	@Column(name = "\"CONTENU\"",columnDefinition = "text")
	private String contenu;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "\"DATE_AJOUT\"")
	private Date dateAjout;
	
	@ManyToOne
	@JoinColumn(name = "ID_QUESTION")
	private Question question;
	
	@ManyToOne
	@JoinColumn(name = "ID_UTILISATEUR")
	private Utilisateur utilisateur;

}
