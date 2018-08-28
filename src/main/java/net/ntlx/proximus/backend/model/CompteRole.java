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
@Table(name = "\"COMPTE_ROLE\"")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CompteRole implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4628371309612693188L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "\"ID\"")
	private Long id;

	@Column(name = "\"ROLENAME\"", unique = true)
	private String rolename;
}
