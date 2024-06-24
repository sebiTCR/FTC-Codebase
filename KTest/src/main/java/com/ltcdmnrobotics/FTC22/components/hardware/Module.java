package com.ltcdmnrobotics.FTC22.components.hardware;
import com.qualcomm.robotcore.hardware.*;


abstract class Module {
    protected HardwareMap hardwareMap = null;

    public Module(HardwareMap hMap){
        hardwareMap = hMap;
    }

    /**
     * Inits the module
     */
    abstract void initModule();

    /**
     * @brief updates the module
     * @abstract
     */
    abstract void updateModule();

}
 