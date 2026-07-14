package es.upm.grise.cruiseControl;

import es.upm.grise.cruiseControl.exceptions.CannotSetSpeedLimitException;
import es.upm.grise.cruiseControl.exceptions.IncorrectSpeedLimitException;
import es.upm.grise.cruiseControl.exceptions.IncorrectSpeedSetException;
import es.upm.grise.cruiseControl.exceptions.SpeedSetAboveSpeedLimitException;

public class CruiseControl {
	
	private RoadInformation roadInformation;
	private Speedometer speedometer;
	private Integer speedLimit;
	private Integer speedSet;
	private boolean enabled = false;
	
	/*
	 * Constructor
	 */
	
	public CruiseControl(RoadInformation roadInformation, Speedometer speedometer) {
		
		this.roadInformation = roadInformation;
		this.speedometer = speedometer;
		
		this.speedSet = null;
		this.speedLimit = null;
		
	}
	
	/*
	 * Method to test
	 */
	
	public void setSpeedSet(int speedSet) throws SpeedSetAboveSpeedLimitException, IncorrectSpeedSetException {
		
		if(speedLimit == null) {

			if(speedSet > 0 ) {
				
				this.speedSet = speedSet;
				this.enabled = true;
				
			} else {
				
				throw new IncorrectSpeedSetException();
				
			}

		} else {
			
			if(speedSet > speedLimit) {

				throw new SpeedSetAboveSpeedLimitException();
				
			} else {

				this.speedSet = speedSet;
				this.enabled = true;
				
			}			
		}
	}
	
	/* 
	 * Method to test
	 */
	
	public void setSpeedLimit(int speedLimit) throws CannotSetSpeedLimitException, IncorrectSpeedLimitException {
			
		if(speedSet == null ) {
			
			if(speedLimit > 0) {

				this.speedLimit = speedLimit;
				
			} else {
				
				throw new IncorrectSpeedLimitException();
				
			}

		} else {

			throw new CannotSetSpeedLimitException();
			
		}
	}
	
	/* 
	 * Method to test
	 */
	
	public void disable() {
		
		enabled = false;
		speedSet = null;
		
	}
	
	/* 
	 * Others getters and setters
	 */
	
	public boolean isEnabled() {
		return enabled;
	}

	public Integer getSpeedLimit() {
		return speedLimit;
	}

	public Integer getSpeedSet() {
		return speedSet;
	}

}
