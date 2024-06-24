package org.firstinspires.ftc.teamcode.autonomous

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import org.firstinspires.ftc.teamcode.modules.hardware.Chassis

@Autonomous
class SimplePark: LinearOpMode() {
    override fun runOpMode() {
        var chassis =   Chassis(hardwareMap)
        var perTileTime:Long =1300



        waitForStart()
        chassis.Move(-1.0,-1.0)
        sleep(perTileTime*2)
        chassis.Move(1.0,-1.0)
        sleep(perTileTime)
        chassis.Move(-1.0,-1.0)
        sleep(perTileTime*4)
        chassis.Stop()

    }
}