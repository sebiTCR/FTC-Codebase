package org.firstinspires.ftc.teamcode.TeleOp

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.Disabled
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.DistanceSensor
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit
import org.firstinspires.ftc.teamcode.modules.hardware.Chassis

@TeleOp
@Disabled
class MotorTest : LinearOpMode() {
    override fun runOpMode() {
        val chassis = Chassis(hardwareMap)
        var speed = 0.5
        val distSens = hardwareMap.get(DistanceSensor::class.java, "dist")

        chassis.initModule()

        waitForStart()

        if (distSens.getDistance(DistanceUnit.CM) < 50) {
            chassis.Stop()
        }
        

    }
}