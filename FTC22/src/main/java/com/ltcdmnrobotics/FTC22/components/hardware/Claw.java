package com.ltcdmnrobotics.FTC22.components.hardware;


import com.arcrobotics.ftclib.hardware.ServoEx;
import com.arcrobotics.ftclib.hardware.motors.CRServo;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * @brief Modul prehensiune cu transformare bi-axială
 *
 */
public class Claw extends Module{
    Motor yAxisTransformer = null;
    Servo grab = null;
    CRServo xAxisTransformer = null;
    boolean isGrabbed = false;

    public Claw(HardwareMap hMap) {
        super(hMap);
    }

    @Override
        public void initModule() {
        xAxisTransformer = new CRServo(hardwareMap, "Claw_x");
        grab = hardwareMap.servo.get("Grab");

        yAxisTransformer = new Motor(hardwareMap,"Claw_y");
        yAxisTransformer.setRunMode(Motor.RunMode.RawPower);
        yAxisTransformer.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
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
        yAxisTransformer.set(value);
    }

    /**
     * Activează sau dezactivează ghiara, în funcție de status
     */
    public void toggleGrab(){
        grab.setPosition(isGrabbed ? 0.0 : 1.0);
        isGrabbed = !isGrabbed;
    }
}
