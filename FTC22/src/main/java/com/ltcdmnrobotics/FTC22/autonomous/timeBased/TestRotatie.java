package com.ltcdmnrobotics.FTC22.autonomous.timeBased;

import com.acmerobotics.dashboard.config.Config;
import com.ltcdmnrobotics.FTC22.components.hardware.Chassis;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(group = "Tests")
@Config
public class TestRotatie extends LinearOpMode {
    public static int ninRotationTime = 100;
    Chassis chassis;


    @Override
    public void runOpMode() throws InterruptedException {
        chassis = new Chassis(hardwareMap);
        chassis.Move(1, -1);
        sleep(ninRotationTime);
        chassis.Move(0,0);
        waitForStart();
    }
}
