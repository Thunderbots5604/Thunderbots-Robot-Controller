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


@Autonomous(name="AutoColorRed", group="All")
public class AutoColorRed extends GodFatherOfAllAutonomous {
    private String color = null;
    private int blockNumber = 0;
    @Override
    public void runOpMode() {

        initialization();

        waitForStart();

        runUntil(2, allPower);
        color = senseColor();
        if (color == "Yellow") {
            blockNumber += 1;
            strafeLeft(8, allPower, slowPower);
            color = senseColor();
            if (color == "Yellow") {
                blockNumber += 1;
                strafeLeft(8, allPower, slowPower);
            }
        }
        runTo(1, allPower * .6, slowPower * .8);
        pickUpBlock();
        runTo(-3, allPower, slowPower);
        turnRight(80, allPower, slowPower);
        turnTo(-90, allPower, slowPower);
        //Branch off based off blockNumber
        if (blockNumber == 0) {
            runTo(45, allPower, slowPower);
            turnLeft(60, allPower, slowPower);
            turnTo(0, allPower, slowPower);
            resetArm();
            strafeRight(30, allPower, slowPower);
            dropBlock();
            runTo(5, allPower * .6, slowPower * .7);
            spinnyBoyDown();

            //Bring block to corner
            runTo(-24, allPower * 1.1, slowPower * 1.4);
            turnRight(90, allPower * 1.1, slowPower * 1.4);
            runTo(5, allPower * 1.1, slowPower * 1.4);
            spinnyBoyUp();

            runTo(-5, allPower, slowPower);
            turnLeft(80, allPower, slowPower);
            turnTo(0, allPower, slowPower);
            runTo(10, allPower, slowPower);
            strafeLeft(84, allPower * 1.2, slowPower);
            resetArm();
            runUntil(1.5, allPower, slowPower);
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
            spinnyBoyDown();

            //Bring block to corner
            runTo(-24, allPower * 1.1, slowPower * 1.4);
            turnRight(90, allPower * 1.1, slowPower * 1.4);
            runTo(5, allPower * 1.1, slowPower * 1.4);
            spinnyBoyUp();

            runTo(-5, allPower, slowPower);
            turnLeft(80, allPower, slowPower);
            turnTo(0, allPower, slowPower);
            runTo(10, allPower, slowPower);
            strafeLeft(84 + 8 * (blockNumber), allPower * 1.2, slowPower);
            resetArm();
            runUntil(1.5, allPower, slowPower);
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
            spinnyBoyDown();

            //Bring block to corner
            runTo(-24, allPower * 1.1, slowPower * 1.4);
            turnRight(90, allPower * 1.1, slowPower * 1.4);
            runTo(5, allPower * 1.1, slowPower * 1.4);
            spinnyBoyUp();

            runTo(-5, allPower, slowPower);
            turnLeft(80, allPower, slowPower);
            turnTo(0, allPower, slowPower);
            runTo(10, allPower, slowPower);
            strafeLeft(84 + 8 * (blockNumber), allPower * 1.2, slowPower);
            resetArm();
            runUntil(1.5, allPower, slowPower);
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



