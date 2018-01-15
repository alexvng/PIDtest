/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team2478.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
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
	
	public XboxController xbox = new XboxController(0);
	public SynchronousPIDF pidLoop;
	
	@Override
	public void robotInit() {
		
		
	}

	@Override
	public void autonomousInit() {
		
	}

	@Override
	public void autonomousPeriodic() {
		
	}

	@Override
	public void teleopPeriodic() {
		double leftAxis = xbox.getRawAxis(1) * -0.8;
		double rightAxis = xbox.getRawAxis(5) * -0.8;
		
		differentialDrive.tankDrive(leftAxis, rightAxis);
		
	}

	@Override
	public void testPeriodic() {
		
	}
}
