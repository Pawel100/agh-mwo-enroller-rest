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

import com.company.enroller.model.Meeting;
import com.company.enroller.persistence.MeetingService;

@RestController
@RequestMapping("/meetings")
public class MeetingRestController {

	@Autowired
	MeetingService meetingService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<?> getMeetings() {
		Collection<Meeting> meetings = meetingService.getAll();
		return new ResponseEntity<Collection<Meeting>>(meetings, HttpStatus.OK);
		//  curl -s localhost:8080/participants
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getMeeting(@PathVariable("id") int id) {
	     Meeting meeting = meetingService.findById(id);
	     if (meeting == null) {
	         return new ResponseEntity(HttpStatus.NOT_FOUND);
	     }
	     return new ResponseEntity<Meeting>(meeting, HttpStatus.OK);
	 }
	
	@RequestMapping(value = "", method = RequestMethod.POST)
	 public ResponseEntity<?> registerMeeting(@RequestBody Meeting meeting){
		// sprawdzic czy istnieje
		if (meetingService.findById(meeting.getId()) != null) {
			return new ResponseEntity (
				"Unable to create. A meeting with title "+ meeting.getId() + " alredy exists.", 
				HttpStatus.CONFLICT);
		}
		// zapisac
		meetingService.create(meeting);
		
		// zwrocic
		return new ResponseEntity<Meeting>(meeting, HttpStatus.CREATED);
		
	}
	

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteMeeting(@PathVariable("id") int id) {
	     Meeting meeting = meetingService.findById(id);
	     if (meeting == null) {
	         return new ResponseEntity(HttpStatus.NOT_FOUND);
	     }
	     // wykasować meeting
	     meetingService.delete(meeting);
	     
	     // zwróć
	     return new ResponseEntity (
					"A meeting with id "+ meeting.getId() + " has been removed.", 
					 HttpStatus.OK);
	     //   curl -i -s -X DELETE http://localhost:8080/meetings/2    - w cmd/bash 

	     
	 }
	
	
	

}
