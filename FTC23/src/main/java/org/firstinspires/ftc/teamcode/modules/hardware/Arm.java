package org.firstinspires.ftc.teamcode.modules.hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Arm extends Module{

    DcMotor m_brat = null;
    DcMotor m_antebrat = null;

    enum Component {
            ARM,
            FOREARM
    };

    enum Direction {
            FORWARD,
            BACKWARDS
    };

    public Arm(HardwareMap hMap) {
        super(hMap);
    }

    @Override
    void initModule() {
        m_brat  = hardwareMap.dcMotor.get("M_ANTEBRAT");
        m_antebrat  = hardwareMap.dcMotor.get("M_BRAT");
    }

    @Override
    void updateModule() {

    }


    /**
    Rotates either the arm or the forearm
    @param component Component to rotate
    @param power Applied power to the specific component
    */
    public void Rotate(Component component, double power){
        DcMotor compToRotate = component == Component.ARM ? m_brat : m_antebrat;
        compToRotate.setPower(power);
    }
}
