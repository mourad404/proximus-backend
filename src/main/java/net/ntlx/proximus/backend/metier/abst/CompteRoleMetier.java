package net.ntlx.proximus.backend.metier.abst;

import java.util.List;

import org.springframework.http.ResponseEntity;

import net.ntlx.proximus.backend.model.CompteRole;

public interface CompteRoleMetier {

	public ResponseEntity<CompteRole> ajtRole(CompteRole cr);

	public List<CompteRole> listRoles();

	public ResponseEntity<String> supprimerRole(String rolename);

	public CompteRole findByRolename(String rolename);
}
