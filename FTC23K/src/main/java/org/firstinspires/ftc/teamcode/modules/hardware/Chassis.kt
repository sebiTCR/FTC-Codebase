package org.firstinspires.ftc.teamcode.modules.hardware


import com.arcrobotics.ftclib.hardware.motors.MotorEx
import com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior
import com.arcrobotics.ftclib.hardware.motors.Motor;

import com.qualcomm.robotcore.hardware.HardwareMap

open class Chassis(hMap: HardwareMap?) : Module(hMap) {
    protected val rightFront: MotorEx = MotorEx(hardwareMap!!, "M_RF_MVMT")
    protected val leftFront:  MotorEx = MotorEx(hardwareMap!!, "M_LF_MVMT")
    protected val rightBack:  MotorEx = MotorEx(hardwareMap!!, "M_RB_MVMT")
    protected val leftBack:   MotorEx = MotorEx(hardwareMap!!, "M_LB_MVMT")
    enum class MovementType {
        SLIDELEFT, SLIDERIGHT, ROTATELEFT, ROTATERIGHT, FORWARD, BACKWARD, NONE
    }

    public override fun initModule() {
        rightBack.inverted = true
        rightFront.inverted = true

        rightFront.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE)
        rightBack.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE)
        leftFront.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE)
        leftBack.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE)

    }

    public override fun updateModule() {}

    init {
        initModule()
    }

    /**
     * Moves the chassis based on power given to the DcMotors.
     * @param leftPower Power applied for the left-sided motors
     * @param rightPower Power applied for the right-sided motors
     */
    fun Move(leftPower: Double, rightPower: Double) {
        rightFront.set(rightPower)
        rightBack.set(rightPower)
        leftFront.set(leftPower)
        leftBack.set(leftPower)
    }

    /**
     * Moves the chassis depending on what type of movement the user wants to execute
     * @param power Power applied to the DcMotors
     * @param mvmType Movement to execute
     */
    fun Move(power: Double, mvmType: MovementType?) {
        var lfPower: Double = 0.0
        var rfPower: Double = 0.0
        var lbPower: Double = 0.0
        var rbPower: Double = 0.0
        
        when (mvmType) {
            MovementType.FORWARD -> {
                lfPower = power
                rfPower = power
                lbPower = power
                rbPower = power
            }

            MovementType.BACKWARD -> {
                lfPower = -power
                rfPower = -power
                lbPower = -power
                rbPower = -power
            }

            MovementType.ROTATELEFT -> {
                lfPower = power
                rfPower = -power
                lbPower = power
                rbPower = -power
            }

            MovementType.ROTATERIGHT -> {
                lfPower = -power
                rfPower = power
                lbPower = -power
                rbPower = power
            }

            else -> {
                lfPower = 0.0
                rfPower = 0.0
                lbPower = 0.0
                rbPower = 0.0
            }
        }

        rightFront.set(rfPower)
        rightBack.set(rbPower)
        leftFront.set(lfPower)
        leftBack.set(lbPower)
    }
    
    fun Slide(power: Double) {
        leftFront.set(power)
        leftBack.set(-power)
        rightFront.set(power)
        rightBack.set(-power)
    }

    /**
     * Stop the robot's displacement
     */
    fun Stop(){
        leftFront.set(0.0)
        leftBack.set(0.0)
        rightFront.set(0.0)
        rightBack.set(0.0)
    }
}