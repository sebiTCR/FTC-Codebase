package org.firstinspires.ftc.teamcode.modules.hardware;

import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.TouchSensor;

public class Elevator extends Module{
    enum DisplacementDirection{
        FORWARD,
        BACKWADS,
        BOTH
    }

    Motor elevator;
    TouchSensor upperLimiter;
    TouchSensor lowerLimiter;

    DisplacementDirection displacementDirection;

    public Elevator(HardwareMap hardwareMap){
        super(hardwareMap);
    }

    @Override
    public void initModule() {
        elevator = new Motor(hardwareMap, "M_Elevator");
        upperLimiter = hardwareMap.touchSensor.get("ElevatorUpperLimit");
        lowerLimiter = hardwareMap.touchSensor.get("ElevatorLowerLimit");

        elevator.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
    }

    @Override
    public void updateModule() {
        if (upperLimiter.isPressed() == true){
            displacementDirection = DisplacementDirection.BACKWADS;
            elevator.set(0);
        }
        else if (lowerLimiter.isPressed() == true) {
            displacementDirection = DisplacementDirection.FORWARD;
            elevator.set(0);
        }
        else {
            displacementDirection = DisplacementDirection.BOTH;
        }
    }

    public void move(float power){
        switch (displacementDirection){
            case FORWARD:
                if (power < 0) {return;}
                break;

            case BACKWADS:
                if (power > 0) { return; }
                break;
        }
        elevator.set(power);
    }

    public DisplacementDirection get_possible_direction(){
        return displacementDirection;
    }
}
