package org.firstinspires.ftc.teamcode.TeleOp

import com.acmerobotics.dashboard.FtcDashboard
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotor.RunMode


@TeleOp
class EncoderTest : LinearOpMode() {
    override fun runOpMode() {
        val motor: Motor = Motor(hardwareMap, "Encore")
        val dash: FtcDashboard = FtcDashboard.getInstance()

        motor.setRunMode(Motor.RunMode.PositionControl)
        motor.setTargetPosition(5)

        waitForStart()

        while(opModeIsActive()){
            motor.set(1.0)
            dash.telemetry.addData("Position", motor.encoder.position)
            dash.telemetry.addData("Distance", motor.encoder.distance)
            dash.telemetry.update()
        }



    }
}