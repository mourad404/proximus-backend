package net.ntlx.proximus.backend.metier.abst;

import java.util.List;

import org.springframework.http.ResponseEntity;

import net.ntlx.proximus.backend.model.BusinessOwner;
import net.ntlx.proximus.backend.model.UtilisateurForm;

public interface BusinessOwnerMetier {

	public List<BusinessOwner> listBO();

	public ResponseEntity<BusinessOwner> ajouterBO(UtilisateurForm uf);

	public BusinessOwner rechercherBO(Long id);

	public ResponseEntity<String> supprimerBO(Long id);

	public ResponseEntity<BusinessOwner> modifierBO(Long id, BusinessOwner bo);
}
