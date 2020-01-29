package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.VoltageSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import java.util.List;


//Still Needs Testing.


@Autonomous(name="AutoEverythingBlue", group="All")
public class AutoEverythingBlue extends GodFatherOfAllAutonomous {
    private String color = null;
    private boolean red = false;
    @Override
    public void runOpMode() {

        initialization();

        waitForStart();
        
        autoTime.reset();

        spinnyBoyUp();
        resetArm();
        
        startBlock(red);
        runTo(32 + 8 * (blockNumber), .9, allPower * .8);
        raiseAndRun(1, 50, allPower * 1.1, slowPower);
        turnRight(80, allPower, slowPower);
        turnTo(0, allPower, slowPower);
        runUntil(80, allPower);
        runTo(3, allPower * .8, slowPower * .9);
        dropBlock();
        moveFoundation(red);
        
        strafeRight(11, allPower, slowPower);
        raiseAndRun(0, -40, allPower * 1.2, slowPower);
        turnTo(-90, allPower, slowPower);
        runTo(-50 - 8 * (blockNumber), .9, allPower * .8);
        resetArm();
        turnRight(80, allPower, slowPower);
        turnTo(0, allPower, slowPower);
        runTo(-3, allPower, slowPower);
        //Branch off based on blockNumber
        if (blockNumber == 0) {
            strafeRight(10, allPower, slowPower);
            runUntil(80, slowPower * .9);
            runTo(2, allPower * .7, slowPower);
            turnTo(0, allPower, slowPower);
            pickUpBlock();
            runTo(-3, allPower, slowPower);
            turnLeft(80, allPower * 1.1, slowPower);
            turnTo(90, allPower, slowPower);
            runTo(70 + 8 * (blockNumber), .9, allPower * .8);
            raiseAndRun(2, 50, allPower, slowPower);
            dropBlock();
            raiseAndRun(0, -30, allPower, slowPower);
        }
        else if (blockNumber == 1) {
            strafeRight(10, allPower, slowPower);
            runUntil(80, slowPower * .9);
            runTo(2, allPower * .7, slowPower);
            turnTo(0, allPower, slowPower);
            pickUpBlock();
            runTo(-3, allPower, slowPower);
            turnLeft(80, allPower * 1.1, slowPower);
            turnTo(90, allPower, slowPower);
            runTo(70 + 8 * (blockNumber), .9, allPower * .8);
            raiseAndRun(2, 50, allPower, slowPower);
            dropBlock();
            raiseAndRun(0, -30, allPower, slowPower);
        }
        else {
            turnTo(20, allPower, slowPower);
            runUntil(80, slowPower * .9);
            runTo(2, allPower * .7, slowPower);
            pickUpBlock();
            runTo(-3, allPower, slowPower);
            turnLeft(80, allPower * 1.1, slowPower);
            turnTo(90, allPower, slowPower);
            runTo(70 + 8 * (blockNumber), .9, allPower * .8);
            raiseAndRun(2, 50, allPower, slowPower);
            dropBlock();
            raiseAndRun(0, -30, allPower, slowPower);
        }
    }
}