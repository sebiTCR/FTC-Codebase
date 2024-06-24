package com.ltcdmnrobotics.FTC22.components.software.fsm.states

import com.ltcdmnrobotics.FTC22.components.hardware.Robot
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode

abstract class State {

    abstract fun enter(parent: Robot?)
    abstract fun update(parent: Robot?)
    abstract fun exit(parent: Robot?)
}