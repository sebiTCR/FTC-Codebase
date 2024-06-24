package com.ltcdmnrobotics.FTC22.autonomous.timeBased;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.ltcdmnrobotics.FTC22.components.external.apriltags.AprilTagDetectionPipeline;
import com.ltcdmnrobotics.FTC22.components.hardware.BasicClaw;
import com.ltcdmnrobotics.FTC22.components.hardware.Chassis;
import com.ltcdmnrobotics.FTC22.components.hardware.Claw;
import com.qualcomm.ftccommon.SoundPlayer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.openftc.apriltag.AprilTagDetection;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;

// Merit omorât pentru asta:
@Config
@Autonomous(group = "drive", name = "Plasare Timp")
public class AutonomieTimpPlasare extends LinearOpMode {

    public static int x = 60;
    public static int y = 0;
    public static int TURN = -20;
    public static Boolean cameraToggled = false;

    public static int time_stepone = 900;
    public static int time_steptwo = 1000;
    public static int parktime = 1000;
    public static int cam_zoom = 2;


    public static boolean proceedtostalp = false;

    int spot = 0;
    Boolean detected = false;
    Boolean displaced = false;

    int cameraMonitorViewId = 0;
    OpenCvInternalCamera camera;
    AprilTagDetectionPipeline apriltagPipeline;
    Chassis chassis;
    DcMotor elevator_motor;
    DcMotor yAxisTr;
    BasicClaw claw;

    SimpleServo ClawC1;
    SimpleServo ClawC2;


    //
    @Override
    public void runOpMode() throws InterruptedException {

        cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        camera = OpenCvCameraFactory.getInstance().createInternalCamera(OpenCvInternalCamera.CameraDirection.BACK, cameraMonitorViewId);
        chassis = new Chassis(hardwareMap);
        claw = new BasicClaw(hardwareMap);

        ClawC1 = new SimpleServo(hardwareMap, "C1", 0, 180, AngleUnit.DEGREES);
        ClawC2 = new SimpleServo(hardwareMap, "C2", 0, 180, AngleUnit.DEGREES);
        yAxisTr = hardwareMap.dcMotor.get("Claw_y");
        yAxisTr.setDirection(DcMotorSimple.Direction.REVERSE);

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

        chassis.initModule();
        claw.initModule();

        elevator_motor = hardwareMap.dcMotor.get("M_Elevator");
        elevator_motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        elevator_motor.setDirection(DcMotorSimple.Direction.REVERSE);

        camera.setPipeline(apriltagPipeline);
        camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener(){
            @Override
            public void onOpened() {
                camera.startStreaming(640, 480, OpenCvCameraRotation.UPRIGHT);
                camera.setZoom(cam_zoom);
                camera.setFlashlightEnabled(true);

//                camera.startRecordingPipeline(new PipelineRecordingParameters(PipelineRecordingParameters.OutputFormat.MPEG_4, PipelineRecordingParameters.Encoder.H264, 30, 300, "FTC"));
            }

            @Override
            public void onError(int errorCode) {

            }
        });

        if(cameraToggled) { FtcDashboard.getInstance().startCameraStream(camera, 30); }
        //claw.toggleGrab();
        ClawC2.setPosition(0.6);
        ClawC1.setPosition(0.3);



        waitForStart();
        if(apriltagPipeline.getLatestDetections().size() != 0){
            detectedTag = apriltagPipeline.getLatestDetections().get(0);
            spot = detectedTag.id;
        }
        else
        {
            spot = 3;
        }
        camera.setFlashlightEnabled(false);



        while(opModeIsActive()) {

            telemetry.addData("Park Spot", spot);
            telemetry.update();


            if(!displaced){
//                chassis.Move(-1,-1);
//                sleep(time_stepone); //deplasare fata

                elevator_motor.setPower(1);
//                yAxisTr.setPower(1);
                chassis.Slide(-1);
                sleep(1800);
                chassis.Move(-1, -1);
                sleep(750);
                chassis.Move(0, 0);
                sleep(1500);
//                claw.setTiltPower(-0.8f);
                sleep(1000);
//                claw.setTiltPower(0);
                sleep(500);
                yAxisTr.setPower(0);
                elevator_motor.setPower(0);

                elevator_motor.setPower(1);
//                yAxisTr.setPower(1);
                sleep(3000);
//                claw.setTiltPower(-0.8f); //catre servo
                sleep(1500);
                elevator_motor.setPower(0);
                //WARN: Vezi ca Tiltu se lasa pe spate //nah


                claw.moveClawY(-1);
                sleep(2975);
                claw.moveClawY(0);

                claw.setTiltPower(1);
                sleep(250);
                claw.setTiltPower(0);



//                switch (spot){
//                    case 1:
//                        chassis.Slide(-1);
//                        sleep(1000);
//                        break;
//
//                    case 3:
//                        chassis.Slide(1);
//                        sleep(1300);
//                        break;
//
//                    case 2:
//                        chassis.Slide(0);
//                        break;
//                }

                chassis.Move(0,0);

                displaced = true;
            }
        }
    }
}
