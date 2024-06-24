package com.ltcdmnrobotics.FTC22.components.hardware;


import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.arcrobotics.ftclib.hardware.motors.CRServo;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

/**
 * @brief Modul prehensiune cu sistem de clești
 *
 */
public class BasicClaw extends Module{
    DcMotor yAxisTransformer = null;
    CRServo xAxisTransformer = null;
    CRServo tiltShift = null;
    boolean claw_closed = false;

    SimpleServo ClawC1;
    SimpleServo ClawC2;


    public BasicClaw(HardwareMap hMap) {
        super(hMap);
    }

    @Override
        public void initModule() {
        xAxisTransformer = new CRServo(hardwareMap, "Claw_x");
        yAxisTransformer = hardwareMap.dcMotor.get("Claw_y");
//        yAxisTransformer.setRunMode(Motor.RunMode.RawPower);
        yAxisTransformer.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        tiltShift = new CRServo(hardwareMap, "Claw_Tilt");
        ClawC1 = new SimpleServo(hardwareMap, "C1", 0, 180, AngleUnit.DEGREES);
        ClawC2 = new SimpleServo(hardwareMap, "C2", 0, 180, AngleUnit.DEGREES);
    }

    @Override
    void updateModule() {

    }

    /**
     * Deplasează sistemul de prehensiune pe axa X
     * @param value Valoare putere motor [-1.0 -> 1.0]
     */
    public void moveClawX(float value){
        xAxisTransformer.set(value);
    }

    /**
     * Rotește sistemul de prehensiune pe axa Y
     * @param value Valoare putere motor
     */
    public void moveClawY(float value){
        yAxisTransformer.setPower(value);
    }

    /**
     * Activează sau dezactivează ghiara, în funcție de status
     */
    public void toggleGrab(){
        ClawC2.setPosition(claw_closed ? 0.6 : 0.3);
        ClawC1.setPosition(claw_closed ? 0.3 : 0.6);
        claw_closed = !claw_closed;
    }

    public void setInverseClawY(boolean val) {yAxisTransformer.setDirection(val ? DcMotorSimple.Direction.REVERSE : DcMotorSimple.Direction.FORWARD);}

    public void setTiltPower(float power) { tiltShift.set(power);}
}
