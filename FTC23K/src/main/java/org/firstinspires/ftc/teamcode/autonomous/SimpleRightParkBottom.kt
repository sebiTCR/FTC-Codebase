package org.firstinspires.ftc.teamcode.autonomous

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import org.firstinspires.ftc.teamcode.modules.hardware.Chassis

@Autonomous
class SimpleRightParkBottom : LinearOpMode() {
    override fun runOpMode() {
        val chassis = Chassis(hardwareMap)
        chassis.initModule()

        waitForStart()
        chassis.Slide(-1.0)
        sleep(2300)
        chassis.Move(-0.2,-0.2)
        sleep(1000)
        chassis.Stop()

        while (opModeIsActive()){

        }

    }
}