package hu.bme.mit.train.controller;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import hu.bme.mit.train.interfaces.TrainController;

import java.time.LocalDateTime;


public class TrainControllerImpl implements TrainController {

	private int step = 0;
	private int referenceSpeed = 0;
	private int speedLimit = 0;
	// 	R	Now
	//	C	Joystick position
	//	V	Reference speed
	private Table<LocalDateTime, Integer, Integer> tachograph = HashBasedTable.create();

	@Override
	public void followSpeed() {
		if (referenceSpeed < 0) {
			referenceSpeed = 0;
		} else if(referenceSpeed + step < 0) {
			referenceSpeed = 0;
		} else {
			referenceSpeed+=step;
		}

		enforceSpeedLimit();

		tachograph.put(LocalDateTime.now(), step, referenceSpeed);
	}

	@Override
	public int getReferenceSpeed() {
		return referenceSpeed;
	}

	@Override
	public void setSpeedLimit(int speedLimit) {
		this.speedLimit = speedLimit;
		enforceSpeedLimit();
		
	}

	private void enforceSpeedLimit() {
		if (referenceSpeed > speedLimit) {
			referenceSpeed = speedLimit;
		}
	}

	@Override
	public void setJoystickPosition(int joystickPosition) {
		this.step = joystickPosition;
		tachograph.put(LocalDateTime.now(), step, referenceSpeed);
	}

	public Table<LocalDateTime, Integer, Integer> getTachograph() {
		return tachograph;
	}

}
