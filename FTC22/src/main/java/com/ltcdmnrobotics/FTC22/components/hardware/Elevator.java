package com.ltcdmnrobotics.FTC22.components.hardware;

import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Elevator extends Module{
    Telemetry telemetry = null;
    Motor elevator = new Motor(hardwareMap, "M_Elevator");

    public Elevator(HardwareMap hMap, Telemetry telemetry){
        super(hMap);
        elevator.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        elevator.setInverted(true);
        this.telemetry = telemetry;
    }


    @Override
    public void initModule() {
        elevator.setTargetPosition(50);
        SetPosition(100);
    }

    @Override
    public void updateModule() {
        while (!elevator.atTargetPosition()){
            telemetry.addData("Encoder Position Val: ", elevator.getCurrentPosition());
            telemetry.update();

            elevator.set(0.1);
        }
        elevator.stopMotor();

        telemetry.addData("Encoder Position Val: ", elevator.getCurrentPosition());
//        telemetry.addData("Encoder Velocity Val: ", ());
        telemetry.update();
    }

    public void SetPosition(int pos){
        while (!elevator.atTargetPosition()){
            elevator.set(0.1);
        }
        elevator.stopMotor();
    }

}