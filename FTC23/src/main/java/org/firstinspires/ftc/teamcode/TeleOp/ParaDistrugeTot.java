package org.firstinspires.ftc.teamcode.TeleOp;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.modules.hardware.Chassis;

@TeleOp
public class ParaDistrugeTot  extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

            Chassis doriel = new Chassis(hardwareMap);
            doriel.initModule();
            doriel.Move(1,1);

            sleep(10000);


    }
}
