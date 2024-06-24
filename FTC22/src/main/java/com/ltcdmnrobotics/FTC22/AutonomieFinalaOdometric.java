package com.ltcdmnrobotics.FTC22;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.ltcdmnrobotics.FTC22.components.external.apriltags.AprilTagDetectionPipeline;
import com.ltcdmnrobotics.FTC22.components.external.drive.SampleMecanumDrive;
//import com.ltcdmnrobotics.FTC22.components.hardware.Robot;
//import com.ltcdmnrobotics.FTC22.components.software.fsm.FiniteStateMachine;
//import com.ltcdmnrobotics.FTC22.components.software.fsm.states.MovingState;
//import com.ltcdmnrobotics.FTC22.components.software.fsm.states.State;
import com.qualcomm.ftccommon.SoundPlayer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.openftc.apriltag.AprilTagDetection;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;

// Merit omorât pentru asta:
@Config
@Autonomous(group = "drive")
public class AutonomieFinalaOdometric extends LinearOpMode {

    public static int x = 60;
    public static int y = 0;
    public static int TURN = -20;
    public static Boolean cameraToggled = false;

    int spot = 0;
    Boolean detected = false;
    Boolean displaced = false;

    int cameraMonitorViewId = 0;
    OpenCvInternalCamera camera;
    AprilTagDetectionPipeline apriltagPipeline;

    SimpleServo ClawC1;
    SimpleServo ClawC2;


    @Override
    public void runOpMode() throws InterruptedException {

        ClawC1 = new SimpleServo(hardwareMap, "C1", 0, 180, AngleUnit.DEGREES);
        ClawC2 = new SimpleServo(hardwareMap, "C2", 0, 180, AngleUnit.DEGREES);

        cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        camera = OpenCvCameraFactory.getInstance().createInternalCamera(OpenCvInternalCamera.CameraDirection.BACK, cameraMonitorViewId);

        double fx = 578.272;
        double fy = 578.272;
        double cx = 402.145;
        double cy = 221.506;
        apriltagPipeline = new AprilTagDetectionPipeline(0.166, fx, fy,cx, cy );

        int oneId = hardwareMap.appContext.getResources().getIdentifier("one", "raw", hardwareMap.appContext.getPackageName());
        int twoId = hardwareMap.appContext.getResources().getIdentifier("two", "raw", hardwareMap.appContext.getPackageName());
        int threeId = hardwareMap.appContext.getResources().getIdentifier("three", "raw", hardwareMap.appContext.getPackageName());

        SoundPlayer.getInstance().preload(hardwareMap.appContext, oneId);
        SoundPlayer.getInstance().preload(hardwareMap.appContext, twoId);
        SoundPlayer.getInstance().preload(hardwareMap.appContext, threeId);


        AprilTagDetection detectedTag = null;
        SampleMecanumDrive chassis = new SampleMecanumDrive(hardwareMap);

        Trajectory forwd = chassis.trajectoryBuilder(new Pose2d())
                .forward(50)
                .strafeRight(20)
                .build();


        Trajectory goToSpotOne = chassis.trajectoryBuilder(new Pose2d())
                .splineTo(new Vector2d(x,y), Math.toRadians(0))
                .build();

        camera.setPipeline(apriltagPipeline);
        camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener(){
            @Override
            public void onOpened() {
                camera.startStreaming(640, 480, OpenCvCameraRotation.UPRIGHT);
                camera.setZoom(2);
                camera.setFlashlightEnabled(true);

//                camera.startRecordingPipeline(new PipelineRecordingParameters(PipelineRecordingParameters.OutputFormat.MPEG_4, PipelineRecordingParameters.Encoder.H264, 30, 300, "FTC"));
            }

            @Override
            public void onError(int errorCode) {

            }
        });

        if(cameraToggled) { FtcDashboard.getInstance().startCameraStream(camera, 30); }

        ClawC2.setPosition(0.3);
        ClawC1.setPosition(0.6);

        waitForStart();
        detectedTag = apriltagPipeline.getLatestDetections().get(0);
        spot = detectedTag.id;
        camera.setFlashlightEnabled(false);



        while(opModeIsActive()) {

            telemetry.addData("Park Spot", spot);
            telemetry.update();

//            chassis.turn(120);

            chassis.followTrajectoryAsync(forwd);

//            if(!displaced) {
//                if (spot == 3) {
//                    SoundPlayer.getInstance().startPlaying(hardwareMap.appContext, oneId);
////                    chassis.turn(Math.toRadians(-60));
////                    chassis.followTrajectory(forwd);
//                    chassis.followTrajectory(goToSpotOne);
//                } else if (spot == 1) {
//                    SoundPlayer.getInstance().startPlaying(hardwareMap.appContext, threeId);
//                    chassis.turn(60);
//                    chassis.followTrajectory(forwd);
//                } else {
//                    SoundPlayer.getInstance().startPlaying(hardwareMap.appContext, twoId);
//                }
//
////                chassis.turn(-60);
//                displaced = true;
//            }
//            if(!displaced) {
////              chassis.turn(Math.toRadians(TURN));
//                chassis.followTrajectory(trj);
//                chassis.followTrajectory(forwd_nx);
//                displaceds = false;
//            }

//
//            if(!displaced) {
////                chassis.followTrajectory(sfBk);
//                switch (detectedTag.id) {
//                    case 1:
//                        SoundPlayer.getInstance().startPlaying(hardwareMap.appContext, oneId);
//                        chassis.turn(Math.toRadians(60));
//                        chassis.followTrajectory(forwd);
////                        displaced = true;
//                        break;
//
//                    case 2:
//                        SoundPlayer.getInstance().startPlaying(hardwareMap.appContext, twoId);
////                        displaced = true;
//                        break;
//
//                    case 3:
//                        SoundPlayer.getInstance().startPlaying(hardwareMap.appContext, threeId);
//                        chassis.turn(Math.toRadians(-60));
//                        chassis.followTrajectory(forwd);
////                        displaced = true;
//                        break;
//
//                }
//                break;
//            }


        }
    }
}
