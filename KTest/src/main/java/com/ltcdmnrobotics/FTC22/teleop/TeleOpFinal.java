package com.ltcdmnrobotics.FTC22.teleop;

import com.ltcdmnrobotics.FTC22.components.hardware.Chassis;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(group = "Final")
public class TeleOpFinal extends LinearOpMode {
    Boolean is_claw_closed = false;
    Servo srv_handle_claw;

    @Override
    public void runOpMode() throws InterruptedException {
        Chassis chassis = new Chassis(hardwareMap);
        DcMotor antebrat = hardwareMap.dcMotor.get("M_ANTEBRAT");
        DcMotor brat = hardwareMap.dcMotor.get("M_BRAT");

        srv_handle_claw = hardwareMap.servo.get("Prindere");


        waitForStart();
        while (opModeIsActive()){
            chassis.Move(gamepad1.left_stick_y, gamepad1.right_stick_y);
            brat.setPower(gamepad2.left_stick_y);
            antebrat.setPower(gamepad2.right_stick_y);

            if(gamepad2.a)
            {
                is_claw_closed = !is_claw_closed;
                srv_handle_claw.setPosition(is_claw_closed ? 0.0 : 1.0);
                sleep(200);

            }

            if(gamepad1.left_trigger != 0){
                float val = gamepad1.left_trigger;
                chassis.Slide(1);
            }
        }
    }
}
