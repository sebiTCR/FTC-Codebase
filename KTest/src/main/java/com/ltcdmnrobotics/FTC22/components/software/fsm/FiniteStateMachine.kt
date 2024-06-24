package com.ltcdmnrobotics.FTC22.components.software.fsm

import com.ltcdmnrobotics.FTC22.components.hardware.Robot
import com.ltcdmnrobotics.FTC22.components.software.fsm.states.State

class FiniteStateMachine(private val parent: Robot, defaultState: State?) {
    private var currentState: State? = null

    init {
        currentState = defaultState
        currentState!!.enter(parent)
    }

    fun update() {
        currentState!!.update(parent)
    }

    fun changeState(state: State?) {
        currentState!!.exit(parent)
        currentState = state
        currentState!!.enter(parent)
    }
}