package com.ltcdmnrobotics.FTC22.components.hardware

import com.qualcomm.robotcore.hardware.HardwareMap
import com.ltcdmnrobotics.FTC22.components.external.drive.SampleMecanumDrive

data class Robot(val hwMap: HardwareMap){
    val chassis: SampleMecanumDrive = SampleMecanumDrive(hwMap);
    public val parkingSpot = 3;
}
