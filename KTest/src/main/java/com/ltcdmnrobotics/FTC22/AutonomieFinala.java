package com.ltcdmnrobotics.FTC22;

import android.graphics.Color;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.control.PIDFController;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.ltcdmnrobotics.FTC22.components.external.drive.SampleMecanumDrive;
//import com.ltcdmnrobotics.FTC22.components.hardware.Robot;
//import com.ltcdmnrobotics.FTC22.components.software.fsm.FiniteStateMachine;
//import com.ltcdmnrobotics.FTC22.components.software.fsm.states.MovingState;
//import com.ltcdmnrobotics.FTC22.components.software.fsm.states.State;
import com.qualcomm.ftccommon.SoundPlayer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;

@Config
@Autonomous(group = "drive")
public class AutonomieFinala extends LinearOpMode {

    public static int x = 30;
    public static int y = 0;
    public static int TURN = -20;
    public static Boolean LEDEnabled = true;

    ColorSensor csens;
    int spot = 0;
    Boolean detected = false;
    Boolean displaced = false;


    int getHighestNumber(int[] nums){
        int biggestIndex = 0;
//        int lastIndex = 0;

        for(int i = 0; i < nums.length; i++){
            if( nums[biggestIndex] < nums[i]){
                biggestIndex = i;
            }
        }

        return biggestIndex;
    }

    @Override
    public void runOpMode() throws InterruptedException {

        int oneId = hardwareMap.appContext.getResources().getIdentifier("one", "raw", hardwareMap.appContext.getPackageName());
        int twoId = hardwareMap.appContext.getResources().getIdentifier("two", "raw", hardwareMap.appContext.getPackageName());
        int threeId = hardwareMap.appContext.getResources().getIdentifier("three", "raw", hardwareMap.appContext.getPackageName());

        SoundPlayer.getInstance().preload(hardwareMap.appContext, oneId);
        SoundPlayer.getInstance().preload(hardwareMap.appContext, twoId);
        SoundPlayer.getInstance().preload(hardwareMap.appContext, threeId);


        csens = hardwareMap.get(ColorSensor.class, "csens");
        SampleMecanumDrive chassis = new SampleMecanumDrive(hardwareMap);
        csens.enableLed(true);
        Trajectory trj = chassis.trajectoryBuilder(new Pose2d())
                .lineTo(new Vector2d(x,y))
                .build();

        Trajectory forwd = chassis.trajectoryBuilder(new Pose2d())
                .forward(100)
                .build();

        Trajectory sfBk = chassis.trajectoryBuilder(new Pose2d())
                .back(25)
                .build();

        chassis.turn(Math.toRadians(TURN));
        chassis.followTrajectory(trj);

        if(!detected) {
            int colors[] = {csens.red(), csens.green(), csens.blue()};
            csens.enableLed(LEDEnabled);
//
//                if (csens.blue() > csens.green()) {
//                    spot = 3;
//                    SoundPlayer.getInstance().startPlaying(hardwareMap.appContext, threeId);
//                    detected = true;
//                }
//                else if (csens.blue() > csens.red()){
//                    spot = 1;
//                    telemetry.addData("Blue", csens.blue());
//                    telemetry.addData("Green", csens.green());
//                    SoundPlayer.getInstance().startPlaying(hardwareMap.appContext, twoId);
//                    detected = true;
//                }
//                else {
//
//                }

            spot = getHighestNumber(colors) + 1;
            csens.enableLed(false);
            detected = false;
        }




//        if(spot == 1){
//            chassis.turn(15);
//            chassis.followTrajectory(forwd);
//
//        }
//        else if(spot == 3){
//            chassis.turn(-15);
//            chassis.followTrajectory(forwd);
//        }
        waitForStart();


        while(opModeIsActive()) {
//            fsm.update();
            telemetry.addData("Displaced", displaced);
            telemetry.addData("Park Spot", spot);
            telemetry.update();

            if(!displaced) {
                chassis.followTrajectory(sfBk);
                switch (spot) {
                    case 1:
                        SoundPlayer.getInstance().startPlaying(hardwareMap.appContext, oneId);
                        chassis.turn(Math.toRadians(90));
                        chassis.followTrajectory(forwd);
                        displaced = true;
                        break;

                    case 2:
                        SoundPlayer.getInstance().startPlaying(hardwareMap.appContext, twoId);
                        displaced = true;
                        break;

                    case 3:
                        SoundPlayer.getInstance().startPlaying(hardwareMap.appContext, threeId);
                        chassis.turn(Math.toRadians(-90));
                        chassis.followTrajectory(forwd);
                        displaced = true;
                        break;
                }
                break;
            }
        }

    }
}
