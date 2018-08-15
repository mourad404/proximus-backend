package net.ntlx.proximus.backend.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import net.ntlx.proximus.backend.metier.abst.NoteMetier;
import net.ntlx.proximus.backend.model.Note;

@RestController
public class NoteController {

	@Autowired
	private NoteMetier noteMetier;
	
	@GetMapping("/notes")
	public List<Note> listNotes(){
		return noteMetier.listNotes();
	}
	
	@GetMapping("/entreprises/{ide}/notes")
	public List<Note> listNotesParEntreprise(@PathVariable("ide") Long id){
		return noteMetier.listNotesParEntreprise(id);
	}
	
	@GetMapping("/utilisateurs/{idu}/entreprises/{ide}/notes")
	public Note chercherNoteEntrepriseParUtilis(@PathVariable("idu") Long idu, @PathVariable("ide") Long ide){
		return noteMetier.chercherNoteParUtilisateur(idu, ide);
	}
	
	@PostMapping("/entreprises/{ide}/notes")
	public ResponseEntity<Note> ajouterNote(@PathVariable("ide") Long ide,@Valid @RequestBody Note n){
		return noteMetier.ajouterNote(ide,n);
	}
	
	@PutMapping("/notes/{idn}")
	public ResponseEntity<Note> modifierNote(@PathVariable("idn") Long idn,@Valid @RequestBody Note n){
		return noteMetier.modifierNote(idn,n);
	}
}
