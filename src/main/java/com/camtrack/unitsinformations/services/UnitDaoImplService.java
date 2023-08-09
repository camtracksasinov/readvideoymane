package com.camtrack.unitsinformations.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.camtrack.bean.ListAllIdUnitsBean;
import com.camtrack.entities.User;
import com.camtrack.unitsinformations.bean.MyPositions;
import com.camtrack.unitsinformations.bean.MyTrucks;
import com.camtrack.unitsinformations.dao.UnitDaoInterface;

@Service("unitdaoservices")
public class UnitDaoImplService implements UnitDaoInterface {

	@Override
	public ResponseEntity<?> mylastpositions(User user, ListAllIdUnitsBean listid) {
		List<MyPositions> listtrucks = new ArrayList<>();
		MyPositions trucks;
		trucks = new MyPositions();
		trucks.setDat(new Date());
		trucks.setOdom("500");
		trucks.setLat(6.235412);
		trucks.setLgs(6.965842);
		trucks.setSp(56.3625);
		trucks.setVehid(9685);
		listtrucks.add(trucks);
		trucks = new MyPositions();
		trucks.setDat(new Date());
		trucks.setOdom("700");
		trucks.setLat(9.235412);
		trucks.setLgs(9.965842);
		trucks.setSp(55.3625);
		trucks.setVehid(9696);

		return ResponseEntity.status(HttpStatus.OK).body(listtrucks);
	}

	@Override
	public ResponseEntity<?> mylastpositionsbyId(User user, Integer listid) {
		MyPositions trucks;
		trucks = new MyPositions();
		trucks.setDat(new Date());
		trucks.setOdom("500");
		trucks.setLat(6.235412);
		trucks.setLgs(6.965842);
		trucks.setSp(56.3625);
		trucks.setVehid(9685);
		return ResponseEntity.status(HttpStatus.OK).body(trucks);
	}

	@Override
	public ResponseEntity<?> mytrucks(User user, ListAllIdUnitsBean listid) {
		List<MyTrucks> listtrucks = new ArrayList<>();
		MyTrucks trucks;
		trucks = new MyTrucks();
		trucks.setDat(new Date());
		trucks.setOdom("500");
		trucks.setTrpid(88);
		trucks.setVehn("LT 161 AI");
		listtrucks.add(trucks);
		trucks = new MyTrucks();
		trucks.setDat(new Date());
		trucks.setOdom("3500");
		trucks.setTrpid(87);
		trucks.setVehn("LT 163 AI");
		listtrucks.add(trucks);

		return ResponseEntity.status(HttpStatus.OK).body(listtrucks);
	}

	@Override
	public ResponseEntity<?> mytrucksbyId(User user, Integer listid) {
		// List<MyTrucks> listtrucks=new ArrayList<>();
		MyTrucks trucks;
		trucks = new MyTrucks();
		trucks.setDat(new Date());
		trucks.setOdom("500");
		trucks.setTrpid(88);
		trucks.setVehn("LT 161 AI");

		return ResponseEntity.status(HttpStatus.OK).body(trucks);
	}

}
