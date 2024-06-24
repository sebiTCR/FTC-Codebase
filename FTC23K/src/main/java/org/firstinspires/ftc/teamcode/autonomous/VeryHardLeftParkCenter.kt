package org.firstinspires.ftc.teamcode.autonomous

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import org.firstinspires.ftc.teamcode.modules.hardware.Chassis

@Autonomous
class VeryHardLeftParkCenter : LinearOpMode() {
    override fun runOpMode() {
        val chassis = Chassis(hardwareMap)
        chassis.initModule()

        waitForStart()
        chassis.Move(-1.0,-1.0)
        sleep(1600)
        chassis.Slide(1.0)
        sleep(4600)
        chassis.Stop()

        while (opModeIsActive()){
            
        }
    }
}