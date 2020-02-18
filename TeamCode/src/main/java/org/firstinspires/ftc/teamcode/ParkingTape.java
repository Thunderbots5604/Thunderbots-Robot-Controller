package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

public class ParkingTape extends GodFatherOfAllAutonomous {

    //CRServo
    private CRServo tapeServo = null;

    //previous and current left and right trigger values
    private double rightTriggerCurrent = 0;
    private double leftTriggerCurrent = 0;
    private double rightTriggerPrevious = 0;
    private double leftTriggerPrevious = 0;

    //forwards and backwards values
    private double forwardsPower = 0;
    private double backwardsPower = 0;

    //power multiplier
    private double powerMultiplier = 1;

    //actual power to set to
    private double actualPower = 0;

    public ParkingTape(String servoName, double multiplier) {
        //declare servo name
        this.tapeServo = HardwareMap.get (CRServo.class, servoName);
        //set multiplier
        this.powerMultiplier = multiplier;
        //set up current and previous values
        this.leftTriggerCurrent = gamepad1.left_trigger;
        this.rightTriggerCurrent = gamepad1.right_trigger;

        this.leftTriggerPrevious = 0;
        this.rightTriggerPrevious = 0;
        //backwards and forwards powers
        this.forwardsPower = 0;
        this.backwardsPower = 0;
        //actual power
        this.actualPower = 0;
    }
    //calculates actual power
    public void CalculatePowers() {
        //get powers
        this.actualPower = (this.forwardsPower - this.backwardsPower) * this.powerMultiplier;
    }
    //sets the powers to a specificed value
    public void SetPowers(double forward, double backward) {
        this.forwardsPower = forward;
        this.backwardsPower = backward;
    }
    //updates current values from controller
    public void UpdateCurrentValues() {
        this.rightTriggerCurrent = gamepad1.right_trigger;
        this.leftTriggerCurrent = gamepad1.left_trigger;
    }
    //updates previous values from current values
    public void UpdatePreviousValues() {
        this.rightTriggerPrevious = this.rightTriggerCurrent;
        this.leftTriggerCurrent = this.leftTriggerPrevious;
    }
    //puts it together to set powers given a forward and backward power
    public void UpdatePowers(double forward, double backward) {
        this.SetPowers(forward, backward);
        this.CalculatePowers();
    }
    //pushes power to crservo
    public void PushPower() {
        this.tapeServo.setPower(this.actualPower);
    }
    //updates and pushes power
    public void UpdateAndPushPower(double forward, double backward) {
        this.UpdatePowers(forward, backward);
        this.pushPower();
    }
    public void checkRun() {
        this.UpdateCurrentValues();
        if (this.rightTriggerCurrent != this.rightTriggerPrevious || this.leftTriggerCurrent != this.leftTriggerPrevious) {
            this.UpdateAndPushPower(this.rightTriggerCurrent, this.leftTriggerCurrent);
        }
        this.UpdatePreviousValues();
    }
}