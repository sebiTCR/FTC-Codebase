package org.firstinspires.ftc.teamcode.TeleOp

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.CRServo
import com.qualcomm.robotcore.hardware.Servo

@TeleOp
class ClawTest : LinearOpMode() {
    override fun runOpMode() {
        var position = 0.0;

        //WARN: NU PUNETI VALORI MAI MARI DE 0.1!
        var step_ratio = 0.01; //TODO: Adjusteaza pasu pe care-l face Servou la deplasare astfel incat deplasarea sa fie precisa


        val servo: CRServo = hardwareMap.crservo.get("Claw 1")

        //TODO: Pune pozitia intiala la initializare sa fie la inchis

        waitForStart()
        while ( opModeIsActive() ){
            var stick_y = gamepad1.left_stick_y;

            if( stick_y != 0.0f){
                position += step_ratio * stick_y;
            }

            servo.power = position
        }
    }
}