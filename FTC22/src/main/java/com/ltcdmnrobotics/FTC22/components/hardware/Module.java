package com.ltcdmnrobotics.FTC22.components.hardware;
import com.qualcomm.robotcore.hardware.*;


abstract class Module {
    protected HardwareMap hardwareMap = null;

    public Module(HardwareMap hMap){
        hardwareMap = hMap;
    }

    /**
     * Initțializează modulul
     */
    abstract void initModule();

    /**
     * @brief Actualizează modulul
     * @abstract
     */
    abstract void updateModule();

}
 