package com.ltcdmnrobotics.FTC22.teleop;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.button.GamepadButton;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.hardware.ServoEx;
import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.arcrobotics.ftclib.hardware.motors.CRServo;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.ltcdmnrobotics.FTC22.components.hardware.BasicClaw;
import com.ltcdmnrobotics.FTC22.components.hardware.Chassis;
import com.ltcdmnrobotics.FTC22.components.hardware.Claw;
import com.ltcdmnrobotics.FTC22.components.hardware.Elevator;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.PIDCoefficients;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

@Config
@TeleOp(group = "Final")
public class TeleOpFinal extends LinearOpMode {
    Boolean is_claw_closed = false;
    Servo srv_handle_claw;
    SimpleServo grabber;
    DcMotor elevator_motor;
    com.qualcomm.robotcore.hardware.CRServo claw_tilt;
    DcMotor claw_y;
    com.qualcomm.robotcore.hardware.CRServo claw_x;
//    Claw claw;
    BasicClaw claw = null;

    SimpleServo ClawC1;
    SimpleServo ClawC2;

    GamepadEx ElevatorController;
    public static float tilt_limiter = 10;
//    GamepadButton claw_grab_btn = new GamepadButton(gamepad2, GamepadKeys.Button.A);

    @Override
    public void runOpMode() throws InterruptedException {
        ElevatorController = new GamepadEx(gamepad2);

//        GamepadButton grab_btn = new GamepadButton(gamepad2, GamepadKeys.Button.A);

        claw = new BasicClaw(hardwareMap);

        Chassis chassis = new Chassis(hardwareMap);
        claw_x = hardwareMap.crservo.get("Claw_x");
        claw_y = hardwareMap.dcMotor.get("Claw_y");
        claw_y.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //        Claw claw = new Claw(hardwareMap);
        grabber = new SimpleServo(hardwareMap, "Grab", 0, 180);
        elevator_motor = hardwareMap.dcMotor.get("M_Elevator");
        elevator_motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        claw_tilt = hardwareMap.crservo.get("Claw_Tilt");

        ClawC1 = new SimpleServo(hardwareMap, "C1", 0, 180, AngleUnit.DEGREES);
        ClawC2 = new SimpleServo(hardwareMap, "C2", 0, 180, AngleUnit.DEGREES);

        if(grabber == null){
            telemetry.addData("SERVO NOT CONNECETD", 0);
            telemetry.update();
        }

        waitForStart();
        while (opModeIsActive()){

            elevator_motor.setPower(-gamepad2.right_stick_y);
            claw_tilt.setPower(gamepad2.left_stick_y / 2);
            claw_x.setPower(gamepad2.left_stick_x / 2);


            telemetry.addData("claw_tilt power", claw_tilt.getPower());
            telemetry.addData("claw_tilt_Gamepad power", gamepad2.left_stick_y);

            telemetry.addData("claw_x power", claw_x.getPower());
            telemetry.addData("claw_x_Gamepad power", gamepad2.left_stick_x);
            telemetry.update();

            if(gamepad2.a){
                ClawC2.setPosition(is_claw_closed ? 0.6 : 0.3);
                ClawC1.setPosition(is_claw_closed ? 0.3 : 0.6);
                is_claw_closed = !is_claw_closed;
                sleep(100);
            }

            if(gamepad1.left_stick_y != 0 || gamepad1.right_stick_y != 0){
                chassis.Move (gamepad1.left_stick_y, gamepad1.right_stick_y);
            }
            else if(gamepad1.left_trigger != 0){
                chassis.Slide(-gamepad1.left_trigger);
            }
            else if(gamepad1.right_trigger != 0) {
                chassis.Slide(gamepad1.right_trigger);
            }
            else{
                chassis.Move(0, 0);
            }


            if(gamepad2.left_trigger != 0){
                claw_y.setPower(-gamepad2.left_trigger);
            }
            else if(gamepad2.right_trigger != 0){
                claw_y.setPower(gamepad2.right_trigger);
            }
            else{
                claw_y.setPower(0);

            }
        }
    }
}
