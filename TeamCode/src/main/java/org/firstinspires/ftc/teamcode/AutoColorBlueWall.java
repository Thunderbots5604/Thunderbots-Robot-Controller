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


//Basically Pseudo code at this point. We need to have the robot arm methods and make some other stuff before something like this will work


@Autonomous(name="AutoColorBlueWall", group="Block side")
public class AutoColorBlueWall extends GodFatherOfAllAutonomous {
    private String color = null;
    private boolean red = false;
    @Override
    public void runOpMode() {

        initialization();

        waitForStart();
        
        autoTime.reset();

        //Get first block and point towards foundation side
        startBlock(red);
        //Move to foundation and drop block at stack level 1
        runTo(10 * (blockNumber), allPower, slowPower);
        runTo(32, .9, slowPower * 1.2);
        
        raiseAndRun(1, 35, allPower, slowPower);
        turnTo(90, allPower, slowPower);
        runUntil(150, slowPower);
        runTo(5, allPower * .9, allPower * .9);
        dropBlock();
        //Move back under bridge to get other blocks
        runTo(-4, allPower, slowPower);
        turnTo(90, allPower * .8, slowPower);
        raiseAndRun(0, -45, allPower, slowPower);
        runTo(-8 * (blockNumber), allPower, slowPower);
        turnTo(90, allPower, slowPower);
        if (blockNumber == 0) {
            runTo(-88, .9, slowPower);
            turnTo(0, allPower, slowPower);
            mmAway = getDistance();
            if (mmAway > 800) {
                runTo(12, allPower, slowPower);
            }
            runUntil(80, slowPower);
            runTo(4, slowPower * .7, slowPower * .6);
            pickUpBlock();
            runTo(-4, allPower, slowPower);
            turnLeft(80, allPower, slowPower);
            turnTo(90, allPower, slowPower);
            runTo(8 * (blockNumber), allPower, slowPower);
            runTo(50, .9, slowPower);
            raiseAndRun(2, 10, allPower, slowPower);
            turnTo(90, allPower, slowPower);
            runUntil(150, slowPower);
            runTo(5, allPower * .9, allPower * .9);
            dropBlock();
            runTo(-2, allPower, slowPower);
            raiseAndRun(0, -11, allPower, slowPower);
            runTo(-11, allPower, slowPower);
        }
        if (blockNumber == 1) {
            runTo(-98, .9, slowPower);
            turnTo(0, allPower, slowPower);
            mmAway = getDistance();
            if (mmAway > 800) {
                runTo(12, allPower, slowPower);
            }
            runUntil(80, slowPower);
            runTo(4, slowPower * .7, slowPower * .6);
            pickUpBlock();
            runTo(-4, allPower, slowPower);
            turnLeft(80, allPower, slowPower);
            turnTo(90, allPower, slowPower);
            runTo(8 * (blockNumber), allPower, slowPower);
            runTo(50, .9, slowPower);
            raiseAndRun(2, 10, allPower, slowPower);
            turnTo(90, allPower, slowPower);
            runUntil(150, slowPower);
            runTo(5, allPower * .9, allPower * .9);
            dropBlock();
            runTo(-2, allPower, slowPower);
            raiseAndRun(0, -11, allPower, slowPower);
            runTo(-11, allPower, slowPower);
        }
        else {
            runTo(-85, .9, slowPower);
            turnRight(80, allPower, slowPower);
            turnTo(-20, allPower, slowPower);
            mmAway = getDistance();
            if (mmAway > 800) {
                runTo(12, allPower, slowPower);
            }
            runUntil(80, slowPower);
            runTo(4, slowPower * .7, slowPower * .6);
            pickUpBlock();
            runTo(-4, allPower, slowPower);
            turnLeft(30, allPower, slowPower);
            turnTo(0, allPower, slowPower);
            turnLeft(70, allPower, slowPower);
            turnTo(90, allPower, slowPower);
            runTo(50, allPower, slowPower);
            runTo(8 * (blockNumber), allPower, slowPower);
            raiseAndRun(2, 10, allPower, slowPower);
            dropBlock();
            turnTo(90, allPower, slowPower);
            runTo(-2, allPower, slowPower);
            raiseAndRun(0, -11, allPower, slowPower);
            runTo(-11, allPower, slowPower);
            strafeLeft(20, allPower, slowPower);
        }
    }
}