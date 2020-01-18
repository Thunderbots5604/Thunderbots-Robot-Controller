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


@Autonomous(name="AutoEverythingRed", group="All")
public class AutoEverythingRed extends GodFatherOfAllAutonomous {
    private String color = null;
    private boolean red = true;
    @Override
    public void runOpMode() {

        initialization();

        waitForStart();

        startBlock(red);
        //Branch off based off blockNumber
        if (blockNumber == 0) {
            runTo(60, allPower, slowPower);
            //raiseAndRun(1, 30, allPower, slowPower);
            turnLeft(60, allPower, slowPower);
            turnTo(0, allPower, slowPower);
            /**/resetArm();
            strafeRight(30, allPower, slowPower);
            dropBlock();/**/
            runTo(20, allPower * .6, slowPower * .7);
            moveFoundation(red);
            runTo(-5, allPower, slowPower);
            strafeLeft(30, allPower, slowPower);
            runTo(-110, allPower * 1.2, slowPower);
            resetArm();
            turnLeft(80, allPower, slowPower);
            turnTo(0, allPower, slowPower);
            runUntil(1.5, allPower);
            pickUpBlock();
            runTo(-3, allPower, slowPower);
            turnRight(80, allPower, slowPower);
            turnTo(-90, allPower, slowPower);
            runTo(80, allPower, slowPower);
            dropBlock();
            runTo(-30, allPower, slowPower);
        }
        else if (blockNumber == 1) {
            runTo(45 + 8 * (blockNumber), allPower, slowPower);
            turnLeft(60, allPower, slowPower);
            turnTo(0, allPower, slowPower);
            resetArm();
            strafeRight(30, allPower, slowPower);
            dropBlock();
            runTo(5, allPower * .6, slowPower * .7);
            moveFoundation(red);

            runTo(-5, allPower, slowPower);
            strafeLeft(10, allPower, slowPower);
            runTo(-84 - 8 * (blockNumber), allPower * 1.2, slowPower);
            turnLeft(80, allPower, slowPower);
            turnTo(0, allPower, slowPower);
            resetArm();
            runUntil(1.5, allPower);
            pickUpBlock();
            runTo(-3, allPower, slowPower);
            turnRight(80, allPower, slowPower);
            turnTo(-90, allPower, slowPower);
            runTo(80 + 8 * (blockNumber), allPower, slowPower);
            dropBlock();
            runTo(-30, allPower, slowPower);
        }
        else {
            runTo(45 + 8 * (blockNumber), allPower, slowPower);
            turnLeft(60, allPower, slowPower);
            turnTo(0, allPower, slowPower);
            resetArm();
            strafeRight(30, allPower, slowPower);
            dropBlock();
            runTo(5, allPower * .6, slowPower * .7);
            moveFoundation(red);

            runTo(-5, allPower, slowPower);
            strafeLeft(10, allPower, slowPower);
            runTo(-84 - 8 * (blockNumber), allPower * 1.2, slowPower);
            turnLeft(80, allPower, slowPower);
            turnTo(0, allPower, slowPower);
            resetArm();
            runUntil(1.5, allPower);
            pickUpBlock();
            runTo(-3, allPower, slowPower);
            turnRight(80, allPower, slowPower);
            turnTo(-90, allPower, slowPower);
            runTo(80 + 8 * (blockNumber), allPower, slowPower);
            dropBlock();
            runTo(-30, allPower, slowPower);
        }
    }
}



