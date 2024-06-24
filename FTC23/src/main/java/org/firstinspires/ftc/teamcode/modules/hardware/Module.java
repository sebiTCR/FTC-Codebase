package org.firstinspires.ftc.teamcode.modules.hardware;

import com.qualcomm.robotcore.hardware.HardwareMap;


abstract class Module {
    protected HardwareMap hardwareMap = null;

    public Module(HardwareMap hMap){
        hardwareMap = hMap;
    }

    /**
     * Initțializează modulul
     */
    abstract void initModule();

    abstract void updateModule();
}
 