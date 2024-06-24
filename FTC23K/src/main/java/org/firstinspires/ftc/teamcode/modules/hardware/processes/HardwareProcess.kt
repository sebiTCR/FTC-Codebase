package org.firstinspires.ftc.teamcode.modules.hardware.processes

import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.teamcode.modules.hardware.Module

abstract class HardwareProcess(hardwareMap: HardwareMap, module: Module) : Thread() {
    var isRunning = false

    abstract fun onStart()
    abstract fun onUpdate()

    fun kill(){
        isRunning = false
    }

    override fun run() {
        super.run()
        isRunning = true
        onStart()
        while(isRunning){
            onUpdate()
        }
    }
}