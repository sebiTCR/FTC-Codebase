package org.firstinspires.ftc.teamcode.TeleOp

import com.acmerobotics.dashboard.FtcDashboard
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.modules.hardware.Chassis
import org.firstinspires.ftc.teamcode.modules.hardware.Claw
import org.firstinspires.ftc.teamcode.modules.hardware.Elevator

@TeleOp
class TeleOpFinal : LinearOpMode() {
    var dashboard = FtcDashboard.getInstance()
    var dashboardTelemetry = dashboard.telemetry


    override fun runOpMode() {
        val chassis = Chassis(hardwareMap)
        val elevator = Elevator(hardwareMap)
        val grabber = Claw(hardwareMap)

        chassis.initModule()
        elevator.initModule()
        waitForStart()
        grabber.initServos(hardwareMap)


        while (opModeIsActive()) {
            //Gamepad 1
            if (gamepad1.left_stick_y != 0f || gamepad1.right_stick_y != 0f)
                chassis.Move(gamepad1.left_stick_y.toDouble(), gamepad1.right_stick_y.toDouble())
            else if (gamepad1.left_bumper)
                chassis.Slide(1.0)
            else if (gamepad1.right_bumper)
                chassis.Slide(-1.0)
            else if (gamepad1.dpad_down){
                chassis.Move(0.2, 0.2)
            }
            else if (gamepad1.dpad_up){
                chassis.Move(-0.2, -0.2)
            }
            else if(gamepad1.dpad_left){
                chassis.Slide(0.3)
            }
            else if(gamepad1.dpad_right){
                chassis.Slide(-0.3)
            }
            else
                chassis.Stop()


            //Gamepad 2
            if(gamepad2.left_bumper){
                grabber.toggleClaw(Claw.Orientation.LEFT)
            }
            if(gamepad2.right_bumper){
                grabber.toggleClaw(Claw.Orientation.RIGHT)
            }


            if(gamepad2.dpad_up){
                elevator.extendArm(-1.0f)
            }
            else if (gamepad2.dpad_down){
                elevator.extendArm(1.0f)
            }
            else{
                elevator.extendArm(0.0f)
            }

            if(gamepad2.left_trigger > 0){
                elevator.move(-gamepad2.left_trigger)
            } else if (gamepad2.right_trigger > 0) {
                elevator.move(gamepad2.right_trigger)
            } else {
                elevator.move(0.0f)
            }

            elevator.rotateModule( gamepad2.left_stick_y )
            grabber.setVerticalTranslationPower( -gamepad2.right_stick_y )
            grabber.updateModule()

            elevator.updateModule()

            dashboardTelemetry.addData("Possible displacement direction: ", elevator.get_possible_direction())
            dashboardTelemetry.update()
        }
    }
}