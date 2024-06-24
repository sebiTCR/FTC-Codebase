package org.firstinspires.ftc.teamcode.modules.hardware.processes

import com.qualcomm.robotcore.hardware.Gamepad
import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.teamcode.modules.hardware.Claw
import org.firstinspires.ftc.teamcode.modules.hardware.Module

class ClawProcess(hardwareMap: HardwareMap, claw: Claw, gamepad: Gamepad) : HardwareProcess(hardwareMap, claw) {
    lateinit var claw: Claw
    lateinit var hardwareMap: HardwareMap
    lateinit var gamepad: Gamepad

    init {
        this.claw = claw
        this.hardwareMap = hardwareMap
        this.gamepad = gamepad
    }

    override fun onStart() {
        claw.initModule()
        claw.initServos(hardwareMap)
    }

    override fun onUpdate() {
        if(gamepad.left_bumper){
            claw.toggleClaw(Claw.Orientation.LEFT)
            sleep(250)
        }

        if(gamepad.right_bumper){
            claw.toggleClaw(Claw.Orientation.RIGHT)
            sleep(250)
        }

    }


}