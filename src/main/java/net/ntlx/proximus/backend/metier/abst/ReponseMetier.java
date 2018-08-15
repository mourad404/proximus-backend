package net.ntlx.proximus.backend.metier.abst;

import java.util.List;

import org.springframework.http.ResponseEntity;

import net.ntlx.proximus.backend.model.Reponse;

public interface ReponseMetier {

	public List<Reponse> listReponses();

	public ResponseEntity<Reponse> ajouterReponse(Long idq, Reponse r);

	public List<Reponse> listReponsesParQuestion(Long id);

	public ResponseEntity<String> supprimerReponse(Long id);

	public ResponseEntity<Reponse> modifierReponse(Long idr, Reponse newReponse);
}
