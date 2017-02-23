package org.usfirst.frc2408.robot;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Timer;

/**
 * This sample program shows how to control a motor using a joystick. In the
 * operator control part of the program, the joystick is read and the value is
 * written to the motor.
 *
 * Joystick analog values range from -1 to 1 and speed controller inputs also
 * range from -1 to 1 making it easy to work together. The program also delays a
 * short time in the loop to allow other threads to run. This is generally a
 * good idea, especially since the joystick values are only transmitted from the
 * Driver Station once every 20ms.
 */
public class Robot extends SampleRobot {

	private SpeedController FrontRightTurn = new Spark(1);
	private SpeedController FrontLeftTurn = new Spark(0);
	private SpeedController BackRightTurn = new Spark(17);
	private SpeedController BackLeftTurn = new Spark(2);
	
	private SpeedController FrontRightDrive = new Spark(5);
	private SpeedController FrontLeftDrive = new Spark(4);
	private SpeedController BackRightDrive = new Spark(7);
	private SpeedController BackLeftDrive = new Spark(6);
	
	private AnalogInput EncoderFrontRight = new AnalogInput(1);
	private AnalogInput EncoderFrontLeft = new AnalogInput(0);
	private AnalogInput EncoderBackRight = new AnalogInput(3);
	private AnalogInput EncoderBackLeft = new AnalogInput(2);
	
	private Servo DoorLeft = new Servo(10);
	private Servo DoorRight = new Servo(11);
	private Servo DoorShooter = new Servo(12);
	private Servo Agitator = new Servo(13);
	
	private SpeedController Shooter = new Talon(9);
	private SpeedController Winch = new Talon(8);
	
	private Joystick stick = new Joystick(0); 
	private Joystick OpStick = new Joystick(1);

	
	
	
	public Robot() {
	}

	/**
	 * Runs the motor from a joystick.
	 */
	@Override
	public void operatorControl() {
		while (isOperatorControl() && isEnabled()) {
				
			double LeftYstick = stick.getRawAxis(1);
			double LeftXstick = stick.getRawAxis(0);
			double RightXstick = stick.getRawAxis(3);
			
			double FrontLeftEq;
			double FrontRightEq;
			double BackLeftEq;
			double BackRightEq;
			
			double FrontRightAverage = EncoderFrontRight.getAverageVoltage();
			double FrontLeftAverage = EncoderFrontLeft.getAverageVoltage();
			double BackRightAverage = EncoderBackRight.getAverageVoltage();
			double BackLeftAverage = EncoderBackLeft.getAverageVoltage();
			
				if (OpStick.getRawAxis(3) > 0) {
					Winch.set(1);
				} else {
					Winch.stopMotor();
				}
				
				if (OpStick.getRawAxis(2) > 0) {
					Shooter.set(1);
				} else {
					Shooter.stopMotor();
				}
			
				if (OpStick.getRawButton(5)) {
					Agitator.setAngle(75);
				} else {
					Agitator.setAngle(170);
				}
				
				if (OpStick.getRawButton(6)) {
					DoorShooter.setAngle(0);
				} else {
					DoorShooter.setAngle(90);
				}
				
				if (OpStick.getRawButton(1)) {
					DoorLeft.setAngle(90);
					DoorRight.setAngle(-90);
				} else {
					DoorLeft.setAngle(0);
					DoorRight.setAngle(0);
				}
				
				FrontLeftEq = 1.5*(RightXstick)+2.94;
				FrontRightEq = 1.5*(RightXstick)+1.44;
				BackLeftEq = 1.5*(RightXstick)+0.73;
				BackRightEq = 1.5*(RightXstick)+1.94;
				
				FrontRightTurn.set((FrontRightEq - FrontRightAverage)*1.5);
				FrontLeftTurn.set((FrontLeftEq - FrontLeftAverage)*1.5);
				BackRightTurn.set((BackRightEq - BackRightAverage)*1.5);
				BackLeftTurn.set((BackLeftEq - BackLeftAverage)*1.5);
				
				FrontLeftDrive.set(Math.max (-1, Math.min(1, (LeftYstick + LeftXstick))));
				BackLeftDrive.set(Math.max (-1, Math.min(1, (LeftYstick + LeftXstick))));
				FrontRightDrive.set(Math.max (-1, Math.min(1, (LeftYstick - LeftXstick))));
				BackRightDrive.set(Math.max (-1, Math.min(1, (LeftYstick - LeftXstick))));

		}
	}
}
