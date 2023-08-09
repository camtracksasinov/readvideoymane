package com.camtrack.unitsinformations.dao;

import org.springframework.http.ResponseEntity;

import com.camtrack.bean.ListAllIdUnitsBean;
import com.camtrack.entities.User;

public interface UnitDaoInterface {
	public ResponseEntity<?> mylastpositions(User user, ListAllIdUnitsBean listid);

	public ResponseEntity<?> mylastpositionsbyId(User user, Integer listid);

	public ResponseEntity<?> mytrucks(User user, ListAllIdUnitsBean listid);

	public ResponseEntity<?> mytrucksbyId(User user, Integer listid);
}
