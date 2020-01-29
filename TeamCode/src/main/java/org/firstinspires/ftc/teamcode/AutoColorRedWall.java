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


@Autonomous(name="AutoColorRed_Wall", group="Block side")
public class AutoColorRedWall extends GodFatherOfAllAutonomous {
    private String color = null;
    private boolean red = true;
    @Override
    public void runOpMode() {

        initialization();

        waitForStart();
        
        autoTime.reset();

        resetArm();
        
        //Get first block and point towards foundation side
        startBlock(red);
        //Move to foundation and drop block at stack level 1
        
        //Go to wall
        strafeRight(22, allPower, slowPower);
        
        //Move to foundation and drop block at stack level 1
        runTo(10 * (blockNumber), allPower, slowPower);
        runTo(45, .9, slowPower * 1.2);
        
        turnTo(-90, allPower, slowPower);
        dropBlock();
        //Move back under bridge to get other blocks
        runTo(-45, allPower, slowPower);
        turnTo(-90, allPower * .8, slowPower);
        runTo(-8 * (blockNumber), allPower, slowPower);
        turnTo(-90, allPower, slowPower);
        if (blockNumber == 0) {
            runTo(-98, .9, slowPower);
            turnTo(0, allPower, slowPower);
            runTo(16, allPower, slowPower);
            mmAway = getDistance();
            if (mmAway > 800) {
                runTo(12, allPower, slowPower);
            }
            runUntil(80, slowPower);
            runTo(4, slowPower * .7, slowPower * .6);
            pickUpBlock();
            runTo(-22, allPower, slowPower);
            turnRight(80, allPower, slowPower);
            turnTo(-90, allPower, slowPower);
            runTo(8 * (blockNumber), allPower, slowPower);
            runTo(60, .9, slowPower);
            turnTo(-90, allPower, slowPower);
            dropBlock();
            runTo(-20, allPower, slowPower);
        }
        if (blockNumber == 1) {
            runTo(-88, .9, slowPower);
            turnTo(0, allPower, slowPower);
            runTo(16, allPower, slowPower);
            mmAway = getDistance();
            if (mmAway > 800) {
                runTo(12, allPower, slowPower);
            }
            runUntil(80, slowPower);
            runTo(4, slowPower * .7, slowPower * .6);
            pickUpBlock();
            runTo(-22, allPower, slowPower);
            turnRight(80, allPower, slowPower);
            turnTo(-90, allPower, slowPower);
            runTo(8 * (blockNumber), allPower, slowPower);
            runTo(60, .9, slowPower);
            turnTo(-90, allPower, slowPower);
            dropBlock();
            runTo(-20, allPower, slowPower);
        }
        else {
            runTo(-85, .9, slowPower);
            turnLeft(80, allPower, slowPower);
            turnTo(20, allPower, slowPower);
            runTo(16, allPower, slowPower);
            mmAway = getDistance();
            if (mmAway > 800) {
                runTo(12, allPower, slowPower);
            }
            runUntil(80, slowPower);
            runTo(4, slowPower * .7, slowPower * .6);
            pickUpBlock();
            runTo(-22, allPower, slowPower);
            turnRight(100, allPower, slowPower);
            turnTo(-90, allPower, slowPower);
            runTo(8 * (blockNumber), allPower, slowPower);
            runTo(50, .9, slowPower);
            turnTo(-90, allPower, slowPower);
            dropBlock();
            runTo(-20, allPower, slowPower);
        }
    }
}