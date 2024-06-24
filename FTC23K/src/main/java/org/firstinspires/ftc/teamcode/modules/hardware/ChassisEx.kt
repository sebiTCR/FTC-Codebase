package org.firstinspires.ftc.teamcode.modules.hardware

import com.arcrobotics.ftclib.controller.PIDController
import com.acmerobotics.roadrunner.control.PIDFController
import com.qualcomm.hardware.lynx.LynxModule
import com.qualcomm.robotcore.hardware.HardwareMap
import com.acmerobotics.roadrunner.control.PIDCoefficients

class ChassisEx(hMap: HardwareMap) : Chassis(hMap) {
    companion object{
        @JvmStatic
        var kP: Double = 0.0

        @JvmStatic
        var kI: Double = 0.0

        @JvmStatic
        var kD: Double = 0.0

        @JvmStatic
        var kF: Double = 0.0
    }

    var pidCoff = PIDCoefficients(kP, kI, kD)
    var pidController: PIDFController = PIDFController(pidCoff)
    var correction_val = 0.0

    init {
        initModule();
    }


    open override fun updateModule() {
        super.updateModule()
        correction_val = pidController.update(pidController.targetPosition)
    }


    open fun displace(units: Double){
        pidController.targetPosition = units
    }

}