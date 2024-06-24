package org.firstinspires.ftc.teamcode.modules.hardware

import com.arcrobotics.ftclib.hardware.ServoEx
import com.qualcomm.robotcore.hardware.HardwareMap
import com.qualcomm.robotcore.hardware.Servo
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit
import com.arcrobotics.ftclib.hardware.SimpleServo
import com.qualcomm.robotcore.hardware.CRServo

class Claw(hardwareMap: HardwareMap) : Module(hardwareMap){
    enum class Orientation{
        RIGHT,
        LEFT,
    }

    var hMap: HardwareMap
    var verticalTranslation: CRServo? = null
    var grabbers: ArrayList<Servo>? = null
    var status = arrayListOf<Boolean>(false, false)
    var positions = arrayListOf<Double>(0.0, 0.0)
    var stepSize: Float = 0.5f


    init{
        hMap = hardwareMap
    }


    override fun initModule() {

    }

    fun initServos(hardwareMap: HardwareMap){
        verticalTranslation = hardwareMap.crservo.get("RotB4")
        val leftGrab = hardwareMap.servo.get("ApucareStanga")
        val rightGrab = hardwareMap.servo.get("ApucareDreapta")
        grabbers = arrayListOf<Servo>(leftGrab, rightGrab)
    }


    override fun updateModule() {

    }


    fun toggleClaw(orientation: Orientation){
        var orient: Int = orientation.ordinal
        var position = if(status[orient]) 0.3 else 0.9

        if(orientation == Orientation.RIGHT){
            position = if(status[orient]) 0.0 else 1.0
        }

        status[orient] = !status[orient]
        grabbers!![orient].position = position
    }


    fun setVerticalTranslationPower(power: Float){
        verticalTranslation!!.power = power.toDouble()
    }

}