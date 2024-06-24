package org.firstinspires.ftc.teamcode.modules.hardware

import com.qualcomm.robotcore.hardware.HardwareMap

abstract class Module(hMap: HardwareMap?) {
    @JvmField
    protected var hardwareMap: HardwareMap? = null

    init {
        if (hMap != null) {
            hardwareMap = hMap
        }
    }

    abstract fun initModule()
    abstract fun updateModule()
}