package com.ltcdmnrobotics.FTC22.components.software.fsm.states

import com.acmerobotics.roadrunner.trajectory.Trajectory
import com.ltcdmnrobotics.FTC22.components.hardware.Robot
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode

var trajectoryBackward: Trajectory = drive.trajectoryBuilder(trajectoryForward.end())
    .back(73)
    .build()

class MovingState : State(){
    override fun enter(parent: Robot?) {
        parent.chassis.followTrajectory(trajectoryBackward);
    }



//    waitForStart()
//
//    while (opModeIsActive() && !isStopRequested())
//    {
//        drive.followTrajectory(trajectoryForward)
//        drive.followTrajectory(trajectoryBackward)
//    }

    override fun update(parent: Robot?) {
        TODO("Not yet implemented")
    }

    override fun exit(parent: Robot?) {
        TODO("Not yet implemented")
    }

}