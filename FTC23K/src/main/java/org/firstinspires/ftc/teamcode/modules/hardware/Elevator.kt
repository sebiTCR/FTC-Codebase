package org.firstinspires.ftc.teamcode.modules.hardware

import com.arcrobotics.ftclib.hardware.motors.Motor
import com.qualcomm.robotcore.hardware.CRServo
import com.qualcomm.robotcore.hardware.HardwareMap
import com.qualcomm.robotcore.hardware.TouchSensor

class Elevator(hardwareMap: HardwareMap?) : Module(hardwareMap) {
    enum class DisplacementDirection {
        FORWARD, BACKWADS, BOTH
    }

    var elevator: Motor? = null
    var vExtend: Motor? = null
    var rotationServo: CRServo? = null
    var upperLimiter: TouchSensor? = null
    var lowerLimiter: TouchSensor? = null
    var displacementDirection: DisplacementDirection? = null

    public override fun initModule() {
        elevator = Motor(hardwareMap!!, "M_Elevator")
        vExtend = Motor(hardwareMap!!, "TranslatieBrat")
        rotationServo = hardwareMap!!.crservo.get("RotireB3")
        upperLimiter = hardwareMap!!.touchSensor["ElevatorUpperLimit"]
        lowerLimiter = hardwareMap!!.touchSensor["ElevatorLowerLimit"]
        elevator!!.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE)
    }

    public override fun updateModule() {
        if (upperLimiter!!.isPressed == true) {
            displacementDirection = DisplacementDirection.BACKWADS
            elevator!!.set(0.0)
        } else if (lowerLimiter!!.isPressed == true) {
            displacementDirection = DisplacementDirection.FORWARD
            elevator!!.set(0.0)
        } else {
            displacementDirection = DisplacementDirection.BOTH
        }
    }

    fun move(power: Float) {
        when (displacementDirection) {
            DisplacementDirection.FORWARD -> if (power < 0) {
                return
            }

            DisplacementDirection.BACKWADS -> if (power > 0) {
                return
            }
        }
        elevator!!.set(power.toDouble())
    }

    fun rotateModule(power: Float){
        rotationServo!!.power = power.toDouble()
    }

    fun extendArm(power: Float){
        vExtend!!.set(power.toDouble())
    }

    fun get_possible_direction(): DisplacementDirection? {
        return displacementDirection
    }
}