package com.ltcdmnrobotics.FTC22.autonomous.timeBased;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.ltcdmnrobotics.FTC22.autonomous.timeBased.atp.ElevatorUnfolding;
import com.ltcdmnrobotics.FTC22.components.external.apriltags.AprilTagDetectionPipeline;
import com.ltcdmnrobotics.FTC22.components.hardware.BasicClaw;
import com.ltcdmnrobotics.FTC22.components.hardware.Chassis;
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
@Autonomous(group = "drive", name = "Plasare Stânga Lenta")
public class PlasareStanga extends LinearOpMode {

    public static int x = 60;
    public static int y = 0;
    public static int TURN = -20;
    public static Boolean cameraToggled = false;


    public static int PER_TILE_TIME = 820;
    public static double GLOBAL_DC_POWER = 1;
    public static int POLE_APROACHING_TIME_OFFSET = 100;
    public static int DEFAULT_SPOT = 1;
    public static int PARKING_OFFSET = 100;
    public static int ELEVATOR_FULL_ASC_TIME = 4000;
    public static int CLAW_X_TIME_OFFSET = 1000;
    public static int TURN_TIME = 50;
    public static int ROTATION_TIME = 750; // per 90 deg
    public static int PARK_TIME_OFFSET = 100;

    public static int cam_zoom = 2;
    public static boolean use_claw = true;


    int spot = 0;
    Boolean detected = false;
    Boolean displaced = false;

    int cameraMonitorViewId = 0;
    OpenCvInternalCamera camera;
    AprilTagDetectionPipeline apriltagPipeline;
    Chassis chassis;
    public DcMotor elevator_motor;
    DcMotor yAxisTr;
    public BasicClaw claw;

    SimpleServo ClawC1;
    SimpleServo ClawC2;

    ElevatorUnfolding elUnfold;

    //
    @Override
    public void runOpMode() throws InterruptedException {

        cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        camera = OpenCvCameraFactory.getInstance().createInternalCamera(OpenCvInternalCamera.CameraDirection.BACK, cameraMonitorViewId);
        chassis = new Chassis(hardwareMap);
        claw = new BasicClaw(hardwareMap);
        elUnfold = new ElevatorUnfolding(this);

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
            telemetry.addLine("Signal spotted; id=" + spot);

        }
        else
        {
            spot = DEFAULT_SPOT;
            telemetry.addLine("No signal detected!");
        }
        camera.setFlashlightEnabled(false);



        while(opModeIsActive()) {

            telemetry.addData("Park Spot", spot);
            telemetry.update();


            if(!displaced){
                // Displace to *pole position*

                chassis.Slide((float) -GLOBAL_DC_POWER);
                sleep((long) (PER_TILE_TIME * 2L));

                sleep(50);
                chassis.Move(0,0);
                sleep(50);
                chassis.Move(0.2f, 0.2f);
                sleep(1500);
                chassis.Slide(0);
                sleep(100);

                chassis.Move((float) -GLOBAL_DC_POWER, (float) -GLOBAL_DC_POWER);
                sleep(PER_TILE_TIME + POLE_APROACHING_TIME_OFFSET);
                chassis.Move(0,0);



                // Plasare
                if(use_claw) {
                    claw.moveClawY(-1);
                    elevator_motor.setPower(1);
                    sleep((ELEVATOR_FULL_ASC_TIME / 2) + CLAW_X_TIME_OFFSET);
                    claw.moveClawY(0);
                    sleep((ELEVATOR_FULL_ASC_TIME / 2) -CLAW_X_TIME_OFFSET) ;
                    elevator_motor.setPower(0);


                    claw.setTiltPower(-1);
                    sleep(1000);
                    claw.setTiltPower(0);
                    elevator_motor.setPower(-1);
                    sleep(500);
                    elevator_motor.setPower(1);
                    claw.toggleGrab();
                    sleep(500);
                    elevator_motor.setPower(0);

                    sleep(1500);
                    claw.setTiltPower(1);
                    sleep(600);
                    claw.setTiltPower(0);

                    claw.moveClawY(1);
                    elevator_motor.setPower(-1);
                    sleep((ELEVATOR_FULL_ASC_TIME - 1570) / 2);
                    claw.moveClawY(0);
                    sleep((ELEVATOR_FULL_ASC_TIME - 2100) / 2);
                    elevator_motor.setPower(0);

                }

                sleep(50);
//                chassis.Move((float) -GLOBAL_DC_POWER, (float) GLOBAL_DC_POWER);
//                sleep(TURN_TIME);
//
//                chassis.Slide((float) -GLOBAL_DC_POWER);
//                switch (spot){
//                    case 1:
//                        sleep(PER_TILE_TIME + PARKING_OFFSET);
//
//                    case 2:
//                        sleep((PER_TILE_TIME*2) + PARKING_OFFSET);
//                        break;
//
//                    case 3:
//                        sleep((PER_TILE_TIME*3) + PARKING_OFFSET);
//
//                }
//                chassis.Slide(0);

                sleep(POLE_APROACHING_TIME_OFFSET);
                chassis.Move((float) GLOBAL_DC_POWER, (float) GLOBAL_DC_POWER);
                sleep(POLE_APROACHING_TIME_OFFSET);
                chassis.Move((float) -GLOBAL_DC_POWER, (float) GLOBAL_DC_POWER);
                sleep(ROTATION_TIME);
                chassis.Move(0,0);

                // Dupa Plasare

                chassis.Move((float) -GLOBAL_DC_POWER, (float) -GLOBAL_DC_POWER);

                switch (spot){
                    case 1:
                        sleep((PER_TILE_TIME / 2)+PARK_TIME_OFFSET);
                        break;

                    case 2:
                        sleep((long) (PER_TILE_TIME * 2L) + PARK_TIME_OFFSET);
                        break;

                    case 3:
                        sleep((long) (PER_TILE_TIME * 3.5) + PARK_TIME_OFFSET);
                }

                chassis.Move(0,0);

                displaced = true;
            }
        }
    }
}

//todo: scadee din timpu de scadere a elevatorului