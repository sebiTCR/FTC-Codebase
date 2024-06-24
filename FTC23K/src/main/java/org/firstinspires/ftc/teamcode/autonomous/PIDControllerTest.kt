package org.firstinspires.ftc.teamcode.autonomous

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import org.firstinspires.ftc.teamcode.misc.ModularOpMode
import org.firstinspires.ftc.teamcode.modules.hardware.Chassis
import org.firstinspires.ftc.teamcode.modules.hardware.ChassisEx

@Autonomous(name = "PID Controller Test")
class PIDControllerTest : LinearOpMode() {
    override fun runOpMode()
    {
        val chassis: ChassisEx = ChassisEx(hardwareMap)

        chassis.initModule()
        waitForStart();

        while(opModeIsActive()){
            chassis.displace(1.5);
            sleep(1000)
            chassis.displace(-1.5)

            telemetry.addData("RunMode", "Running")
            telemetry.update()
        }
    }

}