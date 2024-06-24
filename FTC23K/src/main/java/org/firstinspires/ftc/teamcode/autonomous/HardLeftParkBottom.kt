package org.firstinspires.ftc.teamcode.autonomous

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import org.firstinspires.ftc.teamcode.modules.hardware.Chassis

@Autonomous
class HardLeftParkBottom : LinearOpMode() {
    override fun runOpMode() {
        val chassis = Chassis(hardwareMap)
        chassis.initModule()

        waitForStart()
        chassis.Move(-0.2,-0.2)
        sleep(800)
        chassis.Slide(1.0)
        sleep(4500)
        chassis.Stop()

        while (opModeIsActive()){

        }
    }

}