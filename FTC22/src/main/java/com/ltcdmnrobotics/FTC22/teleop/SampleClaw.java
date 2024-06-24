package com.ltcdmnrobotics.FTC22.teleop;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;


@Config
@TeleOp(group = "we")
public class SampleClaw extends LinearOpMode {
    public static double GR = 0.5;

    @Override
    public void runOpMode() throws InterruptedException {
        Servo servClaw1 = hardwareMap.servo.get("C1");
        Servo servClaw2 = hardwareMap.servo.get("C2");
        boolean isToggled = false;
        servClaw1.setPosition(0);
        servClaw2.setPosition(GR);

        waitForStart();
        servClaw1.setPosition(GR);
        servClaw2.setPosition(0);

        while(opModeIsActive()){
            if(gamepad1.a) {
                if(!isToggled){
                    servClaw1.setPosition(90);
                    servClaw2.setPosition(90);
                }
                else{
                    servClaw1.setPosition(0);
                    servClaw2.setPosition(0);
                }
                isToggled = !isToggled;
            }
        }

    }
}
