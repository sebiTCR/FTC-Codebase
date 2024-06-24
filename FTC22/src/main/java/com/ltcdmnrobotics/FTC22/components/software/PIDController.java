package com.ltcdmnrobotics.FTC22.components.software;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;

public class PIDController {
    private float error = 0.0f;
    public static float kp = 0.0f;
    private float ki = 0.0f;
    private float kd = 0.0f;

    private float lastError;
    private float derivate;
    float integralSum = 0;
    ElapsedTime time = new ElapsedTime();


    public PIDController(float Kp, float Ki, float Kd){
        kp = Kp;
        ki = Ki;
        kd = Kd;
    }

    public float getPower(float reference, float endpoint){
        error = endpoint - reference;
        derivate = (float) ((error - lastError) / time.seconds());
        integralSum += (error * time.seconds());

        lastError = error;
        time.reset();

        return (kp * error) + (ki * integralSum) + (derivate * kd);
    }



}
