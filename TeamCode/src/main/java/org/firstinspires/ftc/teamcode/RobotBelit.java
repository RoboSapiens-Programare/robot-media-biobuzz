package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "Brat", group = "TeleOp")
public class RobotBelit extends OpMode {

    private Servo clawServo;
    private Servo gleznaServo;
    private Servo armServo;
    private Servo leftServo;
    private Servo rightServo;
    private DcMotor rightDrive;
    private DcMotor leftDrive;

    private static final double GLEZNA_SPEED = 0.01;
    private static final double ARM_SPEED   = 0.01;
    private static final double BASE_SPEED  = 0.01;

    private static final double DEADZONE = 0.1;
    private static final double TRIGGER_THRESHOLD = 0.1;
    private static final double Drive_power = 0.8;
    private static final double Turn_power = 0.8;

    private double gleznaPos = 0.5;
    private double armPos = 0.5;
    private double basePos = 0.5;
    private double clawPos = 0.5;

    @Override
    public void init() {
        rightDrive = hardwareMap.get(DcMotor.class, "rightDrive");
        leftDrive = hardwareMap.get(DcMotor.class, "leftDrive");

        rightDrive.setDirection(DcMotorSimple.Direction.FORWARD);
        leftDrive.setDirection(DcMotorSimple.Direction.REVERSE);

        clawServo = hardwareMap.get(Servo.class, "claw");
        gleznaServo = hardwareMap.get(Servo.class, "gleznaServo");
        armServo = hardwareMap.get(Servo.class, "armServo");
        leftServo = hardwareMap.get(Servo.class, "leftServo");
        rightServo = hardwareMap.get(Servo.class, "rightServo");

        gleznaServo.setPosition(gleznaPos);
        armServo.setPosition(armPos);
        leftServo.setPosition(basePos);
        rightServo.setPosition(1.0 - basePos);
        clawServo.setPosition(clawPos);

        telemetry.addData("Status", "Initializat");
        telemetry.update();
    }

    @Override
    public void loop() {
        double rightPower = 0;
        double leftPower = 0;
        String directie = "OPRIT";

        if(gamepad1.dpad_up){
            leftPower = Drive_power;
            rightPower = Drive_power;
            directie = "INAINTE";
        } else if(gamepad1.dpad_down){
            leftPower = -Drive_power;
            rightPower = -Drive_power;
            directie = "INAPOI";
        } else if(gamepad1.dpad_right){
            leftPower = Turn_power;
            rightPower = -Turn_power;
            directie = "DREAPTA";
        } else if(gamepad1.dpad_left){
            leftPower = -Turn_power;
            rightPower = Turn_power;
            directie = "STANGA";
        }
        leftDrive.setPower(leftPower);
        rightDrive.setPower(rightPower);

        clawPos = gamepad1.cross ? 1.0 : 0.5;
        clawServo.setPosition(clawPos);

        double input = -gamepad1.left_stick_y;
        if (Math.abs(input) > DEADZONE) {
            gleznaPos += input * GLEZNA_SPEED;
        }
        gleznaPos = Range.clip(gleznaPos, 0.0, 1.0);
        gleznaServo.setPosition(gleznaPos);

        if (gamepad1.left_bumper) {
            armPos += ARM_SPEED;
        }
        if (gamepad1.left_trigger > TRIGGER_THRESHOLD) {
            armPos -= ARM_SPEED;
        }
        armPos = Range.clip(armPos, 0.0, 1.0);
        armServo.setPosition(armPos);

        if (gamepad1.right_bumper) {
            basePos += BASE_SPEED;
        }
        if (gamepad1.right_trigger > TRIGGER_THRESHOLD) {
            basePos -= BASE_SPEED;
        }
        basePos = Range.clip(basePos, 0.0, 1.0);
        leftServo.setPosition(basePos);
        rightServo.setPosition(1.0 - basePos);

        if (clawPos == 1.0) {
            telemetry.addData("Gheara", "DESCHISA");
        } else if (clawPos == 0.5) {
            telemetry.addData("Gheara", "INCHISA");
        }
        telemetry.addData("Glezna", "%.2f", gleznaPos);
        telemetry.addData("Brat", "%.2f", armPos);
        telemetry.addData("Baza", "%.2f", basePos);
        telemetry.addData("Directie:", directie);
        telemetry.addData("Putere stanga:", "%.2f", leftPower);
        telemetry.addData("Putere dreapta:", "%.2f", rightPower);
        telemetry.update();
    }
}