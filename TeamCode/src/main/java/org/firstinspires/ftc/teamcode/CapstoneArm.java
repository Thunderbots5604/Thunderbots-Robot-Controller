package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;


public class CapstoneArm extends GodFatherOfAllTeleOp {

    // servo
    private Servo capstoneServo;

    // toggle
    private boolean armOut = false;

    //positions
    private double out = 0;
    private double in = 0;

    //previous and current right bumper values
    private boolean rightBumperPrevious = false;
    private boolean rightBumperCurrent = false;

    // constructor sets everything to what it should be by default and takes servo names
    public CapstoneArm(String servoName, double outPosition, double inPosition){
        //set servo
        this.capstoneServo = hardwareMap.get (Servo.class, servoName);
        //set positions
        this.out = outPosition;
        this.in = inPosition;
        //set previous to false
        rightBumperPrevious = false;
        //set current
        rightBumperCurrent = gamepad1.right_bumper || gamepad2.right_bumper;
        //set staring value for armOut
        armOut = false;
    }
    public CapstoneArm(String servoName) {
        this(servoName, 1, 0);
    }
    public void moveArmOut() {
        this.capstoneServo.setPosition(this.out);
        this.armOut = true;
    }
    public void moveArmIn() {
        this.capstoneServo.setPosition(this.in);
        this.armOut = false;
    }
    public void toggleArmPosition() {
        if (armOut == true){
            this.moveArmIn();
        }
        else {
            this.moveArmOut();
        }
    }
    public void updateCurrentValues() {
        this.rightBumperCurrent = gamepad1.right_bumper || gamepad2.right_bumper;
    }
    public void updatePreviousValues() {
        this.rightBumperPrevious = this.rightBumperCurrent;
    }
    public void checkRun() {
        this.updateCurrentValues();
        if (this.rightBumperCurrent != this.rightBumperPrevious && this.rightBumperCurrent == true){
            this.toggleArmPosition();
        }
        this.updatePreviousValues();
    }
    public boolean isOut() {
        return this.armOut;
    }
    public void runOpMode() {}
}