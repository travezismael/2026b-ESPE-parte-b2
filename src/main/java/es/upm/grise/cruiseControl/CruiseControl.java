package es.upm.grise.cruiseControl;

import java.security.PrivateKey;

import javax.smartcardio.CommandAPDU;

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
	private Response response = new Response();

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

		if (speedLimit == null) {

			if (speedSet > 0) {

				this.speedSet = speedSet;
				this.enabled = true;

			} else {

				throw new IncorrectSpeedSetException();

			}

		} else {

			if (speedSet > speedLimit) {

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

		if (speedSet == null) {

			if (speedLimit > 0) {

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

	public Response NextCommand() {
		if (speedSet == null) {
			response.command = response.command.IDLE;
		}
		if (enabled == false) {
			response.command = response.command.IDLE;
		} else {
			if (speedometer.getCurrentSpeed() > speedSet) {
				response.command = response.command.REDUCE;
			}
			if (speedometer.getCurrentSpeed() <= roadInformation.getMinSpeed()){
				response.command = response.command.INCREASE;
			}
			if (speedometer.getCurrentSpeed() >= roadInformation.getMinSpeed()){
				response.command = response.command.REDUCE;
			}
			if (speedometer.getCurrentSpeed() == speedSet) {
				response.command = response.command.KEEP;
			}
		}
		return response;
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
