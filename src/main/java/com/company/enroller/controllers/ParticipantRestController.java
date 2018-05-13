package com.company.enroller.controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.company.enroller.model.Participant;
import com.company.enroller.persistence.ParticipantService;

@RestController
@RequestMapping("/participants")
public class ParticipantRestController {

	@Autowired
	ParticipantService participantService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<?> getParticipants() {
		Collection<Participant> participants = participantService.getAll();
		return new ResponseEntity<Collection<Participant>>(participants, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getParticipant(@PathVariable("id") String login) {
	     Participant participant = participantService.findByLogin(login);
	     if (participant == null) {
	         return new ResponseEntity(HttpStatus.NOT_FOUND);
	     }
	     return new ResponseEntity<Participant>(participant, HttpStatus.OK);
	 }
	
	@RequestMapping(value = "", method = RequestMethod.POST)
	 public ResponseEntity<?> registerParticipant(@RequestBody Participant participant){
		// sprawdzic czy istnieje
		if (participantService.findByLogin(participant.getLogin()) != null) {
			return new ResponseEntity (
				"Unable to create. A participant with login "+ participant.getLogin() + " alredy exists.", 
				HttpStatus.CONFLICT);
		}
		// zapisac
		participantService.create(participant);
		
		// zwrocic
		return new ResponseEntity<Participant>(participant, HttpStatus.CREATED);
		
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteParticipant(@PathVariable("id") String login) {
	     Participant participant = participantService.findByLogin(login);
	     if (participant == null) {
	         return new ResponseEntity(HttpStatus.NOT_FOUND);
	     }
	     // wykasować usera
	     participantService.delete(participant);
	     
	     // zwróć
	     return new ResponseEntity (
					"A participant with login "+ participant.getLogin() + " has been removed.", 
					 HttpStatus.OK);
	     //  curl -i -s -X DELETE http://localhost:8080/participants/somelogin5    - w cmd/bash 

	     
	 }
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateParticipant(@PathVariable("id") String login, @RequestBody Participant newParticipant){
	//public ResponseEntity<?> updateParticipant(@PathVariable("id") String login) {
	     Participant participant = participantService.findByLogin(login);
		//Participant participant2 = participantService.findByLogin(login);
	     if (participant == null) {
	         return new ResponseEntity(HttpStatus.NOT_FOUND);
	     }
	     // zaktualizować usera
	     participant.setPassword(newParticipant.getPassword());
	     participantService.update(participant);
	     
	     // zwróć
	     return new ResponseEntity("Participant with login"+participant+"has been update", HttpStatus.OK);
	     //
	     // jak uruchomić: w postmanie dajemy: put
	     // adress: http://localhost:8080/participants/user5
	     // ustawiamy na JSON (application/json)
	     // zaznaczamy body: raw
	     // wklejamy:
	     //{
	    //	    "login": "user5",
	    //	    "password": "password5"
	    //	}

	     
	 }

}
