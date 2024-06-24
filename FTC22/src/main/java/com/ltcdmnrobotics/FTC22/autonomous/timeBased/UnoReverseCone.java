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
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.openftc.apriltag.AprilTagDetection;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;

// Merit omorât pentru asta:
@Config
@Autonomous(group = "drive", name = "Uno Reverse Cone")
public class UnoReverseCone extends LinearOpMode {

    public static int x = 60;
    public static int y = 0;
    public static int TURN = -20;
    public static Boolean cameraToggled = false;


    public static int PER_TILE_TIME = 820;
    public static double GLOBAL_DC_POWER = 1;
    public static int POLE_APROACHING_TIME_OFFSET = -800;
    public static int INITIAL_SLIDE_OFFSET = 600;
    public static int INITAL_ELEVATOR_CONE_TIME = 1000;
    public static int SLIDE_TIME = 3000;
    public static int ELEVATOR_FULL_ASC_TIME = 4000;
    public static int CLAW_X_TIME_OFFSET = 1000;
    public static int PER_CONE_DELTA_TIME = 100;
    public static int POST_CLAW_Y_TIME = 2000; // Când revine înapoi în pos. de prindere
//    public static int TURN_TIME = 50;
//    public static int ROTATION_TIME = 750; // per 90 deg
    public static int CLAW_TILT_TIME = 200;

    public static int cam_zoom = 2;
    public static boolean use_claw = true;


    int spot = 0;
    Boolean detected = false;
    Boolean displaced = false;

    int cameraMonitorViewId = 0;
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


        chassis = new Chassis(hardwareMap);
        claw = new BasicClaw(hardwareMap);
        elUnfold = new ElevatorUnfolding(this);

        ClawC1 = new SimpleServo(hardwareMap, "C1", 0, 180, AngleUnit.DEGREES);
        ClawC2 = new SimpleServo(hardwareMap, "C2", 0, 180, AngleUnit.DEGREES);
        yAxisTr = hardwareMap.dcMotor.get("Claw_y");
        yAxisTr.setDirection(DcMotorSimple.Direction.REVERSE);

        chassis.initModule();
        claw.initModule();

        elevator_motor = hardwareMap.dcMotor.get("M_Elevator");
        elevator_motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        elevator_motor.setDirection(DcMotorSimple.Direction.REVERSE);


        //claw.toggleGrab();
        ClawC2.setPosition(0.3);
        ClawC1.setPosition(0.6);


        waitForStart();


        while(opModeIsActive()) {

            telemetry.addData("Park Spot", spot);
            telemetry.update();


            if(!displaced){
                // Displace to *pole position*


                if(use_claw) {
                    claw.moveClawY(-1);
                    elevator_motor.setPower(1);
                    sleep((ELEVATOR_FULL_ASC_TIME / 2) + CLAW_X_TIME_OFFSET);
                    claw.moveClawY(0);
                    sleep((ELEVATOR_FULL_ASC_TIME / 2) - CLAW_X_TIME_OFFSET);
                    elevator_motor.setPower(0);


                    claw.setTiltPower(-1);
                    sleep(CLAW_TILT_TIME);
                    claw.setTiltPower(-0);


//                    claw.setTiltPower(0);
//                    elevator_motor.setPower(-1);
//                    sleep(500);
//                    elevator_motor.setPower(1);
//                    sleep(500);
//                    elevator_motor.setPower(0);
                }


                chassis.Slide((float) -GLOBAL_DC_POWER);
                sleep((long) (PER_TILE_TIME * 3L + INITIAL_SLIDE_OFFSET));

                chassis.Move((float) -GLOBAL_DC_POWER, (float) -GLOBAL_DC_POWER);
                sleep((long) (PER_TILE_TIME * 2L + POLE_APROACHING_TIME_OFFSET));

                chassis.Move(0, 0);

//                for(int i = )


//
//                // Plasare

//
//                    sleep(1500);
//                    claw.setTiltPower(1);
//                    sleep(600);
//                    claw.setTiltPower(0);
//
//                    claw.moveClawY(1);
//                    elevator_motor.setPower(-1);
//                    sleep((ELEVATOR_FULL_ASC_TIME - 1570) / 2);
//                    claw.moveClawY(0);
//                    sleep((ELEVATOR_FULL_ASC_TIME - 2100) / 2);
//                    elevator_motor.setPower(0);
//
//                }

                // Dupa Plasare
                sleep(100);


                for(int i = 1; i < 5; i++){
                    elevator_motor.setPower(-1);
                    sleep(INITAL_ELEVATOR_CONE_TIME);
                    elevator_motor.setPower(0);
                    sleep(50);

                    // Plasare pe spate
                    telemetry.addLine("Plasare pe spate");
                    telemetry.update();

                    //Închidere
                    ClawC2.setPosition(0.6);
                    ClawC1.setPosition(0.3);

                    elevator_motor.setPower(1);
                    sleep((INITAL_ELEVATOR_CONE_TIME - PER_CONE_DELTA_TIME) * i);
                    elevator_motor.setPower(0);


                    sleep(100);
                    elevator_motor.setPower(1);
                    sleep((INITAL_ELEVATOR_CONE_TIME - PER_CONE_DELTA_TIME) * i);
                    elevator_motor.setPower(0);
//                    sleep(50);

                    ClawC2.setPosition(0.3);
                    ClawC1.setPosition(0.6);

                    // Plasare pe spate
                    telemetry.addLine("Plasare pe spate");
                    telemetry.update();

    //                claw.moveClawY(-1);
    //                sleep(POST_CLAW_Y_TIME);
    //                claw.moveClawY(0);

                    claw.moveClawX(-0.5f);
                    sleep(100);
                    claw.moveClawX(0);

                    elevator_motor.setPower(-1);
                    sleep((INITAL_ELEVATOR_CONE_TIME - PER_CONE_DELTA_TIME) * i);
                    elevator_motor.setPower(0);

                    //Deschidere
                    telemetry.addLine("Deschidere ghiară");
                    telemetry.update();
                }
                displaced = true;
            }
        }
    }
}

//todo: scadee din timpu de scadere a elevatorului