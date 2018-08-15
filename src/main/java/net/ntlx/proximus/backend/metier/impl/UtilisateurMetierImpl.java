package net.ntlx.proximus.backend.metier.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.ntlx.proximus.backend.exception.ResourceNotFoundException;
import net.ntlx.proximus.backend.metier.abst.UtilisateurMetier;
import net.ntlx.proximus.backend.model.Entreprise;
import net.ntlx.proximus.backend.model.Utilisateur;
import net.ntlx.proximus.backend.repository.EntrepriseRepository;
import net.ntlx.proximus.backend.repository.NoteRepository;
import net.ntlx.proximus.backend.repository.UtilisateurRepository;

@Service
public class UtilisateurMetierImpl implements UtilisateurMetier {

	@Autowired
	private UtilisateurRepository utilisateurRepository;

	@Autowired
	private EntrepriseRepository entrepriseRepository;

	@Autowired
	private NoteRepository noteRepository;

	@Override
	public ResponseEntity<Page<Utilisateur>> listUtilisateurs(Specification<Utilisateur> utiliSpec, List<String> dirAsc,
			List<String> dirDesc, Integer page, Integer size) {

		List<Order> ords = new ArrayList<Order>();
		if (dirDesc != null) {
			if (dirDesc.size() != 0) {
				for (int i = 0; i < dirAsc.size(); i++)
					ords.add(new Order(Direction.ASC, dirAsc.get(i)));

				for (int i = 0; i < dirDesc.size(); i++)
					ords.add(new Order(Direction.DESC, dirDesc.get(i)));

			} else {
				for (int i = 0; i < dirAsc.size(); i++)
					ords.add(new Order(Direction.DESC, dirAsc.get(i)));
			}

		} else {
			if (dirAsc != null)
				for (int i = 0; i < dirAsc.size(); i++)
					ords.add(new Order(Direction.ASC, dirAsc.get(i)));
		}
		Pageable p = PageRequest.of(page, size, Sort.by(ords));
		return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).body(utilisateurRepository.findAll(utiliSpec, p));
	}

	@Override
	public ResponseEntity<Utilisateur> ajouterUtilisateur(Utilisateur u) {
		final Utilisateur newUtilisateur = utilisateurRepository.save(u);
		if (newUtilisateur == null) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} else {
			return ResponseEntity.status(HttpStatus.CREATED).body(newUtilisateur);
		}
	}

	@Override
	public Utilisateur rechercherUtilisateur(Long id) {
		final Utilisateur newUtilisateur = utilisateurRepository.findById(id).get();
		if (newUtilisateur == null)
			throw new ResourceNotFoundException("Utilisateur (id: " + id + ") est introuvable !!");
		return newUtilisateur;
	}

	@Override
	@Transactional
	public ResponseEntity<String> supprimerUtilisateur(Long id) {
		final Utilisateur ut = utilisateurRepository.findById(id).get();
		ut.getAvis().forEach(a -> {
			a.getEntreprise().setNombreAvis(a.getEntreprise().getNombreAvis() - 1);
			a.getEntreprise().setNotation(noteRepository.notationOfEse(a.getEntreprise().getId()));
		});

		utilisateurRepository.deleteById(id);
		if (utilisateurRepository.existsById(id)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("L'utilisateur (" + id + ") n'est pas supprimée");
		} else {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
	}

	@Override
	public ResponseEntity<Utilisateur> modifierUtilisateur(Long id, Utilisateur u) {
		u.setId(id);
		final Utilisateur newUtilisateur = utilisateurRepository.save(u);
		if (newUtilisateur == null) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} else {
			return ResponseEntity.status(HttpStatus.CREATED).body(newUtilisateur);
		}
	}

	/**
	 * BOOKMARK
	 */

	@Override
	@Transactional
	public Utilisateur ajouterOrSupprimerFavoris(Long idu, Long ide) {

		final Entreprise entreprise = entrepriseRepository.findById(ide).get();
		final Utilisateur utilisateur = utilisateurRepository.findById(idu).get();
		Collection<Entreprise> colFavoris = utilisateur.getFavoris();
		if (colFavoris.contains(entreprise)) {
			colFavoris.remove(entreprise);
		} else {
			colFavoris.add(entreprise);
		}
		return utilisateurRepository.save(utilisateur);
	}

	@Override
	public List<Entreprise> listFavoris(Long id) {

		final Utilisateur utilisateur = utilisateurRepository.findById(id).get();
		if (utilisateur == null)
			throw new ResourceNotFoundException("Utilisateur (id: " + id + ") est introuvable !!");
		List<Entreprise> listFavoris = (List<Entreprise>) utilisateur.getFavoris();
		return listFavoris;
	}

}
