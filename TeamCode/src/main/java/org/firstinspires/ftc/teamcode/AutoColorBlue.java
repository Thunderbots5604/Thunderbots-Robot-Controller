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


@Autonomous(name="AutoColorBlue", group="Block side")
public class AutoColorBlue extends GodFatherOfAllAutonomous {
    private String color = null;
    private int blockNumber = 6;
    private boolean wall = false;
    @Override
    public void runOpMode() {

        initialization();

        waitForStart();

        runTo(colorRun1, allPower, slowPower);
        adjustToInitialAngle();
        runUntil(colorRunUntil1, allPower * .9);
        color = senseColor();
        if (color == "Yellow") {
            strafeRight(strafeToNextBlock, allPower, slowPower);
            blockNumber -= 1;
            senseColor();
            if (color == "Yellow") {
                blockNumber -= 1;
                strafeRight(strafeToNextBlock, allPower, slowPower);
            }
        }
        runTo(colorRun2, allPower * .8, slowPower * .7);
        sleep(1000);
        //pickUpBlock();
        runTo(-colorRun3, allPower, slowPower);
        turnLeft(70, allPower, slowPower);
        accurateTurnLeft(90, allPower);
        runTo(colorRun4 + colorRunMultiplier1 * (6 - blockNumber), allPower, slowPower);
        //dropBlock();
        sleep(1000);
        runTo(-colorRun5 - colorRunMultiplier1 * (6 - blockNumber), allPower, slowPower);
        adjustToInitialAngle();
        sleep(500);
        adjustToInitialAngle();
        runTo(colorRun3, allPower * .8, slowPower * .7);
        //pickUpBlock();
        sleep(1000);
        runTo(-colorRun3, allPower, slowPower);
        turnLeft(70, allPower, slowPower);
        accurateTurnLeft(90, allPower);
        runTo(colorRun6 + 12 * (6 - blockNumber), allPower, slowPower);
        //dropBlock();
        sleep(1000);
    }
}