package com.ltcdmnrobotics.FTC22.teleop

import com.acmerobotics.dashboard.config.Config
import com.qualcomm.robotcore.eventloop.opmode.Disabled
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.ltcdmnrobotics.FTC22.components.hardware.Chassis
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.Gamepad
import kotlin.Throws

@Config
@Disabled
@TeleOp(name = "Final TeleOp 2022", group = "Final")
class FinalTeleOp : LinearOpMode() {


    @Throws(InterruptedException::class)
    override fun runOpMode() {
        val chassis = Chassis(hardwareMap)
        val arm: DcMotor = hardwareMap.dcMotor.get("M_BRAT")
        waitForStart()
        while (opModeIsActive()) {
            chassis.Move(gamepad1.left_stick_y, gamepad1.right_stick_y)

            chassis.Move(gamepad1.left_stick_y, gamepad.right_left_y);
        }
    }
}