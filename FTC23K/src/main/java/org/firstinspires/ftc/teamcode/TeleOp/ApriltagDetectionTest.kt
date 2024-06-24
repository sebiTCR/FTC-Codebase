package org.firstinspires.ftc.teamcode.TeleOp

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.modules.hardware.Camera
import org.openftc.apriltag.AprilTagDetection

@TeleOp
class ApriltagDetectionTest : LinearOpMode() {
    lateinit var camera: Camera

    override fun runOpMode() {
        camera = Camera(hardwareMap)
        camera.initModule();

        waitForStart();
        while(opModeIsActive()){
            camera.updateModule();
        }
    }
}