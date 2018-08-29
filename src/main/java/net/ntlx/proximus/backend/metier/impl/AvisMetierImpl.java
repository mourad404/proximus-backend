package net.ntlx.proximus.backend.metier.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.ntlx.proximus.backend.exception.UnAuthorizedException;
import net.ntlx.proximus.backend.metier.abst.AvisMetier;
import net.ntlx.proximus.backend.model.Avis;
import net.ntlx.proximus.backend.model.Entreprise;
import net.ntlx.proximus.backend.model.Utilisateur;
import net.ntlx.proximus.backend.repository.AvisRepository;
import net.ntlx.proximus.backend.repository.BusinessOwnerRepository;
import net.ntlx.proximus.backend.repository.EntrepriseRepository;
import net.ntlx.proximus.backend.repository.NoteRepository;

@Service
public class AvisMetierImpl implements AvisMetier {

	@Autowired
	private AvisRepository avisRepository;

	@Autowired
	private NoteRepository noteRepository;

	@Autowired
	private EntrepriseRepository entrepriseRepository;

	@Autowired
	private BusinessOwnerRepository boRepository;

	@Override
	public List<Avis> listAvis() {
		return avisRepository.findAll();
	}

	@Override
	public Avis chercherAvisParUtilisateur(Long idu, Long ide) {
		return avisRepository.getAvisOnEseByUtilis(ide, idu);
	}

	@Override
	@Transactional
	public ResponseEntity<Avis> ajouterAvis(Long ide, Avis a) {

		final Utilisateur utiliBo = boRepository.getBoByEntreprise(ide);
		final Utilisateur utiliSession = a.getUtilisateur();

		// REGLES METIERS
		if (utiliBo.getId() == utiliSession.getId())
			throw new UnAuthorizedException("Vous ne pouvez pas postez un avis sur votre entreprise !!");
		if (avisRepository.nombreAvisOnEseByUtilis(ide, utiliSession.getId()) != 0)
			throw new UnAuthorizedException("Vous ne pouvez pas postez plus qu'un avis !!");
		//
		final Entreprise entreprise = entrepriseRepository.findById(ide).get();
		a.setEntreprise(entreprise);
		a.setDateAjout(new Date());
		a.setNote(noteRepository.getNoteOnEseByUtilis(ide, utiliSession.getId()));

		final Avis newAvis = avisRepository.save(a);
		if (newAvis == null) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} else {
			Integer nbAvis = entreprise.getNombreAvis();
			if (nbAvis == null)
				nbAvis = 0;
			nbAvis++;
			entreprise.setNombreAvis(nbAvis);
			return ResponseEntity.status(HttpStatus.CREATED).body(newAvis);
		}
	}

	@Override
	public List<Avis> listAvisParEntreprise(Long id) {
		return avisRepository.getAvisByEse(id);
	}

	@Override
	@Transactional
	public ResponseEntity<String> supprimerAvis(Long idu, Long ida) {
		final Avis avis = avisRepository.findById(ida).get();
		if (avis.getUtilisateur().getId() != idu)
			throw new UnAuthorizedException("Vous ne pouvez supprimer que votre avis !!");
		final Entreprise entreprise = avisRepository.findById(ida).get().getEntreprise();
		avisRepository.deleteById(ida);
		if (avisRepository.existsById(ida)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("L'avis (" + ida + ") n'est pas supprimée");
		} else {
			Integer nbAvis = entreprise.getNombreAvis();
			entreprise.setNombreAvis(--nbAvis);
			entreprise.setNotation(noteRepository.notationOfEse(entreprise.getId()));
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
	}

	@Override
	@Transactional
	public ResponseEntity<String> supprimerAvisAdmin(Long ida) {
		final Entreprise entreprise = avisRepository.findById(ida).get().getEntreprise();
		avisRepository.deleteById(ida);
		if (avisRepository.existsById(ida)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("L'avis (" + ida + ") n'est pas supprimée");
		} else {
			Integer nbAvis = entreprise.getNombreAvis();
			entreprise.setNombreAvis(--nbAvis);
			entreprise.setNotation(noteRepository.notationOfEse(entreprise.getId()));
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
	}

	@Override
	@Transactional
	public ResponseEntity<Avis> modifierAvis(Long ida, Avis newAvis) {

		final Avis oldAvis = avisRepository.findById(ida).get();
		if (oldAvis.getUtilisateur().getId() != newAvis.getUtilisateur().getId())
			throw new UnAuthorizedException("Vous ne pouvez modifier que votre avis !!");

		final Entreprise entreprise = oldAvis.getEntreprise();
		newAvis.setId(ida);
		newAvis.setEntreprise(entreprise);
		newAvis.setDateAjout(new Date());
		newAvis.setNote(oldAvis.getNote());

		final Avis avisAdded = avisRepository.save(newAvis);
		if (avisAdded == null) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} else {
			return ResponseEntity.status(HttpStatus.CREATED).body(avisAdded);
		}
	}

}
