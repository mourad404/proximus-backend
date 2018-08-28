package net.ntlx.proximus.backend.metier.abst;

import java.util.List;

import org.springframework.http.ResponseEntity;

import net.ntlx.proximus.backend.model.CompteUtilisateur;

public interface CompteUtilisateurMetier {

	public ResponseEntity<CompteUtilisateur> ajtUser(CompteUtilisateur cu);

	public List<CompteUtilisateur> listUsers();

	public ResponseEntity<String> supprimerUser(String username);

	public void roleToUser(String username, String rolename);

	public CompteUtilisateur findByUsername(String username);
}
