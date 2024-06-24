package com.ltcdmnrobotics.FTC22;

import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(group = "idk")
public class tests extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        boolean run = true;

        int position = 0;
        Motor hexcore = new Motor(hardwareMap, "mot");

        hexcore.setRunMode(Motor.RunMode.PositionControl);
        hexcore.setPositionCoefficient(0.05);


        waitForStart();
        while (opModeIsActive()) {


            position += gamepad2.left_stick_y;
            hexcore.setTargetPosition(position);


            while (!hexcore.atTargetPosition()) {
                hexcore.set(position);
                telemetry.addData("CurrentPos: ", hexcore.getCurrentPosition());
                telemetry.addData("Target Pos:", position);
                telemetry.update();
            }
            telemetry.addData("Target Pos:", position);
            telemetry.update();
            hexcore.stopMotor();
        }

    }
}

