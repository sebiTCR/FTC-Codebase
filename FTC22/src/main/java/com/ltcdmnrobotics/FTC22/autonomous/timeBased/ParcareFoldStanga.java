package com.ltcdmnrobotics.FTC22.autonomous.timeBased;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.ltcdmnrobotics.FTC22.components.external.apriltags.AprilTagDetectionPipeline;
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
@Autonomous(group = "drive")
public class ParcareFoldStanga extends LinearOpMode {

    public static int x = 60;
    public static int y = 0;
    public static int TURN = -20;
    public static int PARKING_TIME_OFFSET = 50;
    public static Boolean CAM_LIGHT = false;
    public static Boolean cameraToggled = false;

    public static int time_stepone = 900;
    public static int time_steptwo = 1000;
    public static int parktime = 1000;
    public static int cam_zoom = 2;

    public static int rotation_time = 760;

    public static boolean proceedtostalp = false;

    int spot = 0;
    Boolean detected = false;
    Boolean displaced = false;

    int cameraMonitorViewId = 0;
    OpenCvInternalCamera camera;
    AprilTagDetectionPipeline apriltagPipeline;
    Chassis chassis;
    DcMotor elevator_motor;
    Claw claw;

    SimpleServo ClawC1;
    SimpleServo ClawC2;


    //
    @Override
    public void runOpMode() throws InterruptedException {

        cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        camera = OpenCvCameraFactory.getInstance().createInternalCamera(OpenCvInternalCamera.CameraDirection.BACK, cameraMonitorViewId);
        chassis = new Chassis(hardwareMap);
        claw = new Claw(hardwareMap);

        ClawC1 = new SimpleServo(hardwareMap, "C1", 0, 180, AngleUnit.DEGREES);
        ClawC2 = new SimpleServo(hardwareMap, "C2", 0, 180, AngleUnit.DEGREES);


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
                telemetry.addData("Max Zoom: ",camera.getMaxSupportedZoom());
                camera.setZoom(cam_zoom);
                camera.setFlashlightEnabled(CAM_LIGHT);

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
                chassis.Move(-1,-1);
                sleep(time_stepone); //deplasare fata

                switch (spot){
                    case 1:
                        chassis.Slide(-1);
                        sleep(1000 + PARKING_TIME_OFFSET);
                        break;

                    case 3:
                        chassis.Slide(1);
                        sleep(1300 + PARKING_TIME_OFFSET);
//                        break.Slide;

                    case 2:
                        chassis.Slide(0);
                        break;
                }

                chassis.Move(0,0);


                sleep(100);
                chassis.Move((float) -1, (float) 1);
                sleep(rotation_time);
                chassis.Move(0,0);
                //Rotate

                claw.moveClawY( 1);
                sleep(2000);
                claw.moveClawY(0);

                displaced = true;
            }
        }
    }
}
