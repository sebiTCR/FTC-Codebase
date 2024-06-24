package org.firstinspires.ftc.teamcode.TeleOp;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.modules.hardware.Chassis;
import org.firstinspires.ftc.teamcode.modules.hardware.Elevator;

@TeleOp
public class TeleOpFinal extends LinearOpMode {
    FtcDashboard dashboard = FtcDashboard.getInstance();
    Telemetry dashboardTelemetry = dashboard.getTelemetry();

    @Override
    public void runOpMode() throws InterruptedException {

        Chassis chassis = new Chassis(hardwareMap);
        Elevator elevator = new Elevator(hardwareMap);

        chassis.initModule();
        elevator.initModule();

        waitForStart();
        while(opModeIsActive()){
            if(gamepad1.left_stick_y != 0 || gamepad1.right_stick_y != 0)
                chassis.Move(gamepad1.left_stick_y, gamepad1.right_stick_y);
            else if(gamepad1.left_trigger != 0)
                chassis.Move(1, Chassis.MovementType.SLIDELEFT);
            else if(gamepad1.right_trigger != 0)
                chassis.Move(1, Chassis.MovementType.SLIDERIGHT);
            else
                chassis.Move(0, 0);

            elevator.updateModule();
            elevator.move(gamepad2.left_stick_y);

            dashboardTelemetry.addData("Possible displacement direction: ", elevator.get_possible_direction());
            dashboardTelemetry.update();

        }

    }
}
