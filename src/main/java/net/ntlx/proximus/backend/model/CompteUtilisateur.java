package net.ntlx.proximus.backend.model;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "\"COMPTE_UTILISATEUR\"")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CompteUtilisateur implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2855045626255863424L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "\"ID\"")
	private Long id;

	@Column(name = "\"USERNAME\"", unique = true)
	private String username;

	@Column(name = "\"PASSWORD\"")
	private String password;

	@Column(name = "\"ENABLED\"")
	private boolean enabled;

	@OneToOne
	@JoinColumn(name = "ID_UTILISATEUR")
	private Utilisateur utilisateur;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "USERS_ROLES")
	private Collection<CompteRole> roles;

}
