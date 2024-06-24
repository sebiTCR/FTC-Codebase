package com.ltcdmnrobotics.FTC22

import com.acmerobotics.dashboard.config.Config
import com.ltcdmnrobotics.FTC22.components.hardware.Robot
import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.ltcdmnrobotics.FTC22.components.software.fsm.FiniteStateMachine
import com.ltcdmnrobotics.FTC22.components.software.fsm.states.IdentifyingState
import com.ltcdmnrobotics.FTC22.components.software.fsm.states.MovingState
import com.ltcdmnrobotics.FTC22.components.software.fsm.states.State
import kotlin.Throws

@Config
@Autonomous(name = "Final Autonomy 2022", group = "Final")
class FinalAutonomieKT : LinearOpMode() {
    public enum class parkingSpots {
        A,
        B,
        C
    }

    private var identifyingState: State = IdentifyingState()
    private var movingState: State = MovingState()
    private var parkingDistrict: parkingSpots = parkingSpots.A
    private     val robot = Robot(hardwareMap)
    private val fsm: FiniteStateMachine = FiniteStateMachine(robot, identifyingState)


    fun setParkingSpot(spot: parkingSpots){
        parkingDistrict = spot
    }


    @Throws(InterruptedException::class)
    override fun runOpMode() {
        waitForStart()
        while (opModeIsActive()) {
            fsm.update()
        }
    }
}