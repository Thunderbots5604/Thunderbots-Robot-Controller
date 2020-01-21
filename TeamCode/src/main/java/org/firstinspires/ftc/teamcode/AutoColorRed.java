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


//Still Needs Testing. Could probably use some more sensors, particularly if blockNumber == 2


@Autonomous(name="AutoColorRed", group="Block side")
public class AutoColorRed extends GodFatherOfAllAutonomous {
    private String color = null;
    private boolean red = true;
    @Override
    public void runOpMode() {

        initialization();

        waitForStart();
        //Get first block and point towards foundation side
        startBlock(red);
        //Move to foundation and drop block at stack level 1
        runTo(8 * (blockNumber), allPower, slowPower);
        raiseAndRun(1, 45, allPower, slowPower);
        dropBlock();
        //Move back under bridge to get other blocks
        runTo(-6, allPower, slowPower);
        raiseAndRun(0, -39, allPower, slowPower);
        runTo(-8 * (blockNumber), allPower, slowPower);
        turnLeft(70, allPower, slowPower);
        turnTo(0, allPower, slowPower);
        if (blockNumber == 0){
            strafeLeft(24, allPower, slowPower);
            strafeLeft(8 * (blockNumber), allPower, slowPower);
            turnTo(0, allPower, slowPower);
            runUntil(1.5, allPower);
            pickUpBlock();
            runTo(-2, allPower, slowPower);
            turnRight(70, allPower, slowPower);
            turnTo(-90, allPower, slowPower);
            raiseAndRun(2, 45, allPower, slowPower);
            runTo(8 * (blockNumber), allPower, slowPower);
            dropBlock();
            runTo(-2, allPower, slowPower);
            raiseAndRun(0, -22, allPower, slowPower);
        }
        if (blockNumber == 1){
            strafeLeft(24, allPower, slowPower);
            strafeLeft(8 * (blockNumber), allPower, slowPower);
            turnTo(0, allPower, slowPower);
            runUntil(1.5, allPower);
            pickUpBlock();
            runTo(-2, allPower, slowPower);
            turnRight(70, allPower, slowPower);
            turnTo(-90, allPower, slowPower);
            raiseAndRun(2, 45, allPower, slowPower);
            runTo(8 * (blockNumber), allPower, slowPower);
            dropBlock();
            runTo(-2, allPower, slowPower);
            raiseAndRun(0, -22, allPower, slowPower);
        }
        else{
            strafeLeft(32, allPower, slowPower);
            turnLeft(30, allPower, slowPower);
            turnTo(45, allPower, slowPower);
            runTo(5, allPower, slowPower);
            pickUpBlock();
            runTo(-5, allPower, slowPower);
            turnRight(-30, allPower, slowPower);
            turnTo(0, allPower, slowPower);
            turnRight(70, allPower, slowPower);
            turnTo(-90, allPower, slowPower);
            runTo(77, allPower, slowPower);
            dropBlock();
            runTo(-2, allPower, slowPower);
            raiseAndRun(0, -22, allPower, slowPower);
        }
    }
}