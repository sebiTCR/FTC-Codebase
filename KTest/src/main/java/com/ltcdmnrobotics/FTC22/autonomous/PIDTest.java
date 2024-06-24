package com.ltcdmnrobotics.FTC22.autonomous;

import com.acmerobotics.dashboard.config.Config;
import com.ltcdmnrobotics.FTC22.components.software.PIDController;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@Config
@Autonomous(group = "tst")
public class PIDTest extends LinearOpMode {
    public float Kp = 0;
    public float Ki = 0;
    public float Kd = 0;

    @Override
    public void runOpMode() throws InterruptedException {

        DcMotorEx lf = hardwareMap.get(DcMotorEx.class, "M_LF_MVMT");
        DcMotorEx rf = hardwareMap.get(DcMotorEx.class, "M_RF_MVMT");
        DcMotorEx lb = hardwareMap.get(DcMotorEx.class, "M_LB_MVMT");
        DcMotorEx rb = hardwareMap.get(DcMotorEx.class, "M_RB_MVMT");
        PIDController pid = new PIDController(Kp, Ki, Kd);

        waitForStart();
        while(opModeIsActive()){

            lf.setPower(pid.getPower(lf.getCurrentPosition(), 10));
            rf.setPower(pid.getPower(rf.getCurrentPosition(), 10));
            telemetry.addData("Position", lf.getCurrentPosition());
            telemetry.addData("Position RF", rf.getCurrentPosition());
            telemetry.update();
        }
    }
}

