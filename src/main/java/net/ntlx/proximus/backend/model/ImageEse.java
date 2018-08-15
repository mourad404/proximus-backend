package net.ntlx.proximus.backend.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "\"IMAGE_ESE\"")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ImageEse implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -8964712375244344571L;

	/**
	 *
	 */

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "\"ID\"")
	private Long id;

	@Column(name = "\"IMAGE\"")
	private String image;

	@ManyToOne
	@JoinColumn(name = "ID_ENTREPRISE")
	private Entreprise entreprise;

	/**
	 * Getter/Setter pour utiliser @JsonIgnore et @JsonSetter
	 */
	@JsonIgnore
	public Entreprise getEntreprise() {
		return entreprise;
	}
	@JsonSetter
	public void setEntreprise(Entreprise entreprise) {
		this.entreprise = entreprise;
	}
}
