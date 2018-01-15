/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team2478.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class Robot extends IterativeRobot {

	public WPI_TalonSRX leftFront = new WPI_TalonSRX(RobotMap.LEFT_FRONT);
	public WPI_TalonSRX leftBack = new WPI_TalonSRX(RobotMap.LEFT_BACK);
	public SpeedControllerGroup leftGroup = new SpeedControllerGroup(leftFront, leftBack);
	
	public WPI_TalonSRX rightFront = new WPI_TalonSRX(RobotMap.RIGHT_FRONT);
	public WPI_TalonSRX rightBack = new WPI_TalonSRX(RobotMap.RIGHT_BACK);
	public SpeedControllerGroup rightGroup = new SpeedControllerGroup(rightFront, rightBack);
	
	public DifferentialDrive differentialDrive = new DifferentialDrive(leftGroup, rightGroup);
	
	public XboxController xbox = new XboxController(RobotMap.XBOX_ID);
	public SynchronousPIDF pidLoop;
	public AHRS navx = new AHRS(I2C.Port.kMXP);
	public Timer timer = new Timer();
	
	public double angle;
	
	@Override
	public void robotInit() {
		pidLoop = new SynchronousPIDF(RobotMap.ANGULAR_P, RobotMap.ANGULAR_I, RobotMap.ANGULAR_D);
		navx.zeroYaw();
		
	}

	@Override
	public void autonomousInit() {
		
	}

	@Override
	public void autonomousPeriodic() {
		
	}
	
	@Override
	public void teleopInit() {
		
	}
	
	@Override
	public void disabledInit() {
		navx.zeroYaw();
		angle = navx.getAngle();
		pidLoop.setSetpoint(RobotMap.ANGULAR_SETPOINT);
	}
	
	@Override
	public void teleopPeriodic() {
		double leftAxis = xbox.getRawAxis(1) * -0.8;
		double rightAxis = xbox.getRawAxis(5) * -0.8;
		differentialDrive.tankDrive(leftAxis, rightAxis);
		
	}

	@Override
	public void testInit() {
		timer.reset();
		timer.start();
		navx.zeroYaw();
		pidLoop.reset();
//		pidLoop.resetIntegrator();
	}
	
	@Override
	public void testPeriodic() {
		
		double pidOutput = pidLoop.calculate(angle, timer.get());
		differentialDrive.tankDrive(RobotMap.DRIVE_SPEED + (pidOutput * RobotMap.MOTOR_GAINS),
									RobotMap.DRIVE_SPEED + (pidOutput * -1 * RobotMap.MOTOR_GAINS));
		
		angle = navx.getAngle();
		
		System.out.println("Time: " + Double.toString(timer.get()));
		System.out.println("Angle: " + Double.toString(angle));
		System.out.println("PID output: " + Double.toString(pidOutput));
		
	}
}
