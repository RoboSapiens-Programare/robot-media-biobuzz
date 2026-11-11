package org.firstinspires.ftc.teamcode;

import android.content.OperationApplicationException;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous(name = "Autonom", group = "Competition")
    public class RobotBelit extends OpMode{
    Servo clawServo;

    @Override
    public void init() {
        clawServo = hardwareMap.get(Servo.class, "claw");

        clawServo.setPosition(0.5);
    }

    @Override
    public void loop(){
    }
}
