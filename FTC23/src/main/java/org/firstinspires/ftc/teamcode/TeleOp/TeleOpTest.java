package org.firstinspires.ftc.teamcode.TeleOp;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.modules.hardware.Chassis;
import org.firstinspires.ftc.teamcode.modules.hardware.Elevator;

@Autonomous
public class TeleOpTest extends LinearOpMode {
    FtcDashboard dashboard = FtcDashboard.getInstance();
    Telemetry dashboardTelemetry = dashboard.getTelemetry();

    @Override
    public void runOpMode() throws InterruptedException {
        Elevator elevator = new Elevator(hardwareMap);
        Chassis chassis = new Chassis(hardwareMap);
        chassis.initModule();


        waitForStart();
        chassis.Move(1, 1);
        while (opModeIsActive()){
            chassis.Move(0.5F, Chassis.MovementType.SLIDELEFT);
        }

    }
}
