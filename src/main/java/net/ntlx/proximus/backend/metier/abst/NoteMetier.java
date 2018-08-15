package net.ntlx.proximus.backend.metier.abst;

import java.util.List;

import org.springframework.http.ResponseEntity;

import net.ntlx.proximus.backend.model.Note;


public interface NoteMetier {

	public List<Note> listNotes();
	
	public Note chercherNoteParUtilisateur(Long idu, Long ide);
	
	public ResponseEntity<Note> ajouterNote(Long ide, Note n);
	
	public List<Note> listNotesParEntreprise(Long id);
	
	public ResponseEntity<Note> modifierNote(Long idn, Note newNote);
}
