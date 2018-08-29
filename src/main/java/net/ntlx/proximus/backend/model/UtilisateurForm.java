package net.ntlx.proximus.backend.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UtilisateurForm implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 8172067593978411383L;

	// @NotBlank(message = "Le NOM doit etre rempli !!")
	private String nom;

	// @NotBlank(message = "Le PRENOM doit etre rempli !!")
	private String prenom;

	private String password;

	private String repassword;

	@Email
	private String email;

	// @Size(min=5, max=5, message="le code postal doit se composer de 5 chiffres
	// !!")
	private Long codePostal;

	@Temporal(TemporalType.DATE)
	private Date dateNaissance;

	// @Size(min = 9, max = 10, message = "Le numero du telephone doit etre de 9 ou
	// 10 chiffres")
	private String tel;
}
