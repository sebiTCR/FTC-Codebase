package org.firstinspires.ftc.teamcode.misc

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import org.firstinspires.ftc.teamcode.modules.hardware.Module
import java.util.Vector


abstract class ModularOpMode : LinearOpMode() {
    var modules: Vector<Module> = Vector<Module>()


    open fun registerModule(mod: Module){
        modules.add(mod)
    }


    open fun initModules(){
        for(module: Module in modules){
            module.initModule()
        }
    }


    open fun updateModules(){
        for(module: Module in modules){
            module.updateModule();
        }
    }


    open abstract fun run()

    override fun runOpMode() {
        run()
    }
}