package net.ntlx.proximus.backend.metier.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.ntlx.proximus.backend.exception.UnAuthorizedException;
import net.ntlx.proximus.backend.metier.abst.NoteMetier;
import net.ntlx.proximus.backend.model.Entreprise;
import net.ntlx.proximus.backend.model.Note;
import net.ntlx.proximus.backend.model.Utilisateur;
import net.ntlx.proximus.backend.repository.BusinessOwnerRepository;
import net.ntlx.proximus.backend.repository.EntrepriseRepository;
import net.ntlx.proximus.backend.repository.NoteRepository;

@Service
public class NoteMetierImpl implements NoteMetier {

	@Autowired
	private NoteRepository noteRepository;

	@Autowired
	private EntrepriseRepository entrepriseRepository;

	@Autowired
	private BusinessOwnerRepository boRepository;

	@Override
	public List<Note> listNotes() {
		return noteRepository.findAll();
	}

	@Override
	public Note chercherNoteParUtilisateur(Long idu, Long ide) {
		return noteRepository.getNoteOnEseByUtilis(ide, idu);
	}

	@Override
	@Transactional
	public ResponseEntity<Note> ajouterNote(Long ide, Note n) {

		final Utilisateur utiliBo = boRepository.getBoByEntreprise(ide);
		final Utilisateur utiliSession = n.getUtilisateur();

		// REGLES METIERS
		if (utiliBo.getId() == utiliSession.getId())
			throw new UnAuthorizedException("Vous ne pouvez pas noter votre entreprise !!");
		if (noteRepository.nombreNotesOnEseByUtilis(ide, utiliSession.getId()) != 0)
			throw new UnAuthorizedException("Vous ne pouvez pas noter plusieurs fois !!");
		//
		final Entreprise entreprise = entrepriseRepository.findById(ide).get();
		n.setEntreprise(entreprise);
		n.setDateAjout(new Date());

		final Note newNote = noteRepository.save(n);
		if (newNote == null) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} else {
			entreprise.setNotation(noteRepository.notationOfEse(ide));
			return ResponseEntity.status(HttpStatus.CREATED).body(newNote);
		}
	}

	@Override
	public List<Note> listNotesParEntreprise(Long ide) {
		return noteRepository.getNotesByEse(ide);
	}

	@Override
	@Transactional
	public ResponseEntity<Note> modifierNote(Long idn, Note newNote) {
		final Note oldNote = noteRepository.findById(idn).get();
		if (oldNote.getUtilisateur().getId() != newNote.getUtilisateur().getId())
			throw new UnAuthorizedException("Vous ne pouvez modifier que votre note !!");

		Entreprise entreprise = oldNote.getEntreprise();
		newNote.setId(idn);
		newNote.setEntreprise(entreprise);
		newNote.setDateAjout(new Date());

		final Note noteAdded = noteRepository.save(newNote);
		if (noteAdded == null) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} else {
			entreprise.setNotation(noteRepository.notationOfEse(entreprise.getId()));
			return ResponseEntity.status(HttpStatus.CREATED).body(noteAdded);
		}
	}

}
