package org.firstinspires.ftc.teamcode.modules.hardware

import com.acmerobotics.dashboard.FtcDashboard
import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.robotcore.external.Telemetry
import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection
import org.firstinspires.ftc.vision.VisionPortal
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor

class Camera (hMap: HardwareMap) : Module(hMap) {
    lateinit var vision: VisionPortal
    lateinit var aprilTag: AprilTagProcessor
    private var currentDetection: AprilTagDetection? = null
    private lateinit var telemetry: Telemetry
    private var dashboard: FtcDashboard = FtcDashboard.getInstance()

    override fun initModule() {
        aprilTag = AprilTagProcessor.easyCreateWithDefaults();
        vision = VisionPortal.easyCreateWithDefaults(BuiltinCameraDirection.BACK, aprilTag)
        telemetry = dashboard.telemetry

    }

    override fun updateModule() {
        if(aprilTag.detections.size <= 1)
            return

        try {


            currentDetection = aprilTag.detections[0];
            telemetry.addData("Aruco detections", aprilTag.detections.size)

            for (i in 0 until aprilTag.detections.size) {
                telemetry.addData("Aruco $i ID", aprilTag.detections[i].id)
            }

            telemetry.update()
        }
        catch (e: Exception){
            telemetry.addLine(e.message)
            telemetry.update()
        }
    }
}