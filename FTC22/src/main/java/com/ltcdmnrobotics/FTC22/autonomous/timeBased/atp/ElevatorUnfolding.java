package com.ltcdmnrobotics.FTC22.autonomous.timeBased.atp;

import com.ltcdmnrobotics.FTC22.autonomous.timeBased.AutonomieTimpPlasareLent;
import com.ltcdmnrobotics.FTC22.autonomous.timeBased.PlasareDreapta;
import com.ltcdmnrobotics.FTC22.autonomous.timeBased.PlasareStanga;
import com.ltcdmnrobotics.FTC22.autonomous.timeBased.UnoReverseCone;

public class ElevatorUnfolding implements Runnable {

    AutonomieTimpPlasareLent atpl = null;
    PlasareDreapta plD = null;
    PlasareStanga plS = null;
    UnoReverseCone URC = null;

    public ElevatorUnfolding(AutonomieTimpPlasareLent a){
        atpl = a;
    }

    public ElevatorUnfolding(PlasareDreapta a){
        plD = a;
    }

    public ElevatorUnfolding(PlasareStanga a){
        plS = a;
    }
    public ElevatorUnfolding(UnoReverseCone a){
        URC = a;
    }

    @Override
    public void run(){

        if(atpl != null){
            atpl.elevator_motor.setPower(1);
        }
        else if(plD != null){
            plS.elevator_motor.setPower(1);
        }
        else if(URC != null){
            URC.elevator_motor.setPower(1);
        }
        else{
            plD.elevator_motor.setPower(1);

        }

        try {
            wait(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


        if(atpl != null){
            atpl.elevator_motor.setPower(0);
        }
        else if(URC != null) {
            URC.elevator_motor.setPower(0);
        }
        else{
            plD.elevator_motor.setPower(0);

        }

    }
}
