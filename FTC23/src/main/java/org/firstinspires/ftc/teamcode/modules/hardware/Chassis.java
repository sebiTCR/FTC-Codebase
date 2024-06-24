package org.firstinspires.ftc.teamcode.modules.hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Chassis extends Module{

    DcMotorEx rightFront = null;
    DcMotorEx leftFront = null;
    DcMotorEx rightBack = null;
    DcMotorEx leftBack = null;

    public enum MovementType{
        SLIDELEFT,
        SLIDERIGHT,
        ROTATELEFT,
        ROTATERIGHT,
        FORWARD,
        BACKWARD,
        NONE
    }

    @Override
    public void initModule() {
        rightFront = hardwareMap.get(DcMotorEx.class, "M_RF_MVMT");
        leftFront  = hardwareMap.get(DcMotorEx.class, "M_LF_MVMT");
        rightBack  = hardwareMap.get(DcMotorEx.class, "M_RB_MVMT");
        leftBack   = hardwareMap.get(DcMotorEx.class, "M_LB_MVMT");

        rightBack.setDirection(DcMotor.Direction.REVERSE);
        rightFront.setDirection(DcMotor.Direction.REVERSE);

        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    @Override
    void updateModule() {

    }


    public Chassis(HardwareMap hMap) {
        super(hMap);
        initModule();
    }

    /**
     * Moves the chassis based on power given to the DcMotors.
     * @param leftPower Power applied for the left-sided motors
     * @param rightPower Power applied for the right-sided motors
     */
    public void Move(float leftPower, float rightPower){
        rightFront.setPower(rightPower);
        rightBack.setPower(rightPower);
        leftFront.setPower(leftPower);
        leftBack.setPower(leftPower);
    }

    /**
     * Moves the chassis depending on what type of movement the user wants to execute
     * @param power Power applied to the DcMotors
     * @param mvmType Movement to execute
     */
    public void Move(float power, MovementType mvmType){
        float lfPower = 0, rfPower = 0, lbPower = 0, rbPower = 0;


        switch (mvmType){

            case FORWARD:
                lfPower = power;
                rfPower = power;
                lbPower = power;
                rbPower = power;
                break;

            case BACKWARD:
                lfPower = -power;
                rfPower = -power;
                lbPower = -power;
                rbPower = -power;
                break;

            case ROTATELEFT:
                lfPower = -1;
                rfPower = 1;
                lbPower = 1;
                rbPower = -1;
                break;

            case ROTATERIGHT:
                lfPower = 1;
                rfPower = -1;
                lbPower = -1;
                rbPower = 1;
                break;

            case SLIDELEFT:
                lfPower = power;
                rfPower = -power;
                lbPower = -power;
                rbPower = power;
                break;

            case SLIDERIGHT:
                lfPower = -power;
                rfPower = power;
                lbPower = power;
                rbPower = -power;
                break;

            default:
                lfPower = 0;
                rfPower = 0;
                lbPower = 0;
                rbPower = 0;
                break;

        }

        rightFront.setPower(rfPower);
        rightBack.setPower(rbPower);
        leftFront.setPower(lfPower);
        leftBack.setPower(lbPower);
    }

    public void Slide(float power){
        leftFront.setPower(power);
        leftBack.setPower(-power);
        rightFront.setPower(power);
        rightBack.setPower(-power);
    }

//    PIDController pidController = new PIDController(0,0,0);
//    public void Move(float reference){
//        rightFront.setPower(pidController.GetPIDPower(reference, rightFront.getCurrentPosition()));
//        rightBack.setPower(pidController.GetPIDPower(reference, rightBack.getCurrentPosition()));
//        leftFront.setPower(pidController.GetPIDPower(reference, leftFront.getCurrentPosition()));
//        leftBack.setPower(pidController.GetPIDPower(reference, leftBack.getCurrentPosition()));
//    }
}
