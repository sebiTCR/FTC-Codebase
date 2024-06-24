//package com.ltcdmnrobotics.FTC22.autonomous.timeBased.atp;
//
//import com.acmerobotics.dashboard.FtcDashboard;
//import com.acmerobotics.dashboard.config.Config;
//import com.ltcdmnrobotics.FTC22.components.external.apriltags.AprilTagDetectionPipeline;
//import com.ltcdmnrobotics.FTC22.components.hardware.BasicClaw;
//import com.ltcdmnrobotics.FTC22.components.hardware.Chassis;
//import com.ltcdmnrobotics.FTC22.components.hardware.Claw;
////import com.ltcdmnrobotics.FTC22.components.hardware.Robot;
//import com.qualcomm.ftccommon.SoundPlayer;
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.hardware.DcMotor;
//import com.qualcomm.robotcore.hardware.DcMotorSimple;
//import com.qualcomm.robotcore.hardware.HardwareMap;
//
//import org.openftc.apriltag.AprilTagDetection;
//import org.openftc.easyopencv.OpenCvCamera;
//import org.openftc.easyopencv.OpenCvCameraFactory;
//import org.openftc.easyopencv.OpenCvCameraRotation;
//import org.openftc.easyopencv.OpenCvInternalCamera;
//
//// Merit omorât pentru asta:
//@Config
//@Autonomous(group = "drive")
//public class AutonomieTimpPlasare extends LinearOpMode {
//
//    public static int x = 60;
//    public static int y = 0;
//    public static int TURN = -20;
//    public static Boolean cameraToggled = false;
//
//    public static int time_stepone = 900;
//    public static int time_steptwo = 1000;
//    public static int parktime = 1000;
//    public static int cam_zoom = 2;
//
//
//    public static boolean proceedtostalp = false;
//
//    int spot = 0;
//    Boolean detected = false;
//    Boolean displaced = false;
//
//
//    Chassis chassis;
//    DcMotor elevatorMotor;
//    BasicClaw claw;
//
////    Robot robot;
//
//    public void setupSounds(){
//        int oneId = hardwareMap.appContext.getResources().getIdentifier("one", "raw", hardwareMap.appContext.getPackageName());
//        int twoId = hardwareMap.appContext.getResources().getIdentifier("two", "raw", hardwareMap.appContext.getPackageName());
//        int threeId = hardwareMap.appContext.getResources().getIdentifier("three", "raw", hardwareMap.appContext.getPackageName());
//        SoundPlayer.getInstance().preload(hardwareMap.appContext, oneId);
//        SoundPlayer.getInstance().preload(hardwareMap.appContext, twoId);
//        SoundPlayer.getInstance().preload(hardwareMap.appContext, threeId);
//    }
//
//    @Override
//    public void runOpMode() throws InterruptedException {
//
//        chassis = new Chassis(hardwareMap);
//        claw = new BasicClaw(hardwareMap);
//
//
//        setupSounds();
//
//
//
//        chassis.initModule();
//        claw.initModule();
//
//        elevatorMotor = hardwareMap.dcMotor.get("M_Elevator");
//        elevatorMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        elevatorMotor.setDirection(DcMotorSimple.Direction.REVERSE);
//
//
//
////        if(cameraToggled) { FtcDashboard.getInstance().startCameraStream(camera, 30); }
//        claw.toggleGrab();
//
//        waitForStart();
//
//        if(apriltagPipeline.getLatestDetections().size() != 0){
//            detectedTag = apriltagPipeline.getLatestDetections().get(0);
//            spot = detectedTag.id;
//        }
//        else
//        {
//            spot = 3;
//        }
//        camera.setFlashlightEnabled(false);
//
//
//
//        while(opModeIsActive()) {
//
//            telemetry.addData("Park Spot", spot);
//            telemetry.addData("Current State", null);
//            telemetry.update();
//
//
//            if(!displaced){
//                displaced = true;
//            }
//        }
//    }
//}
