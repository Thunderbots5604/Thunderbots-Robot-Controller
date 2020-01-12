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
    private int method = 2;
    @Override
    public void runOpMode() {

        initialization();

        waitForStart();

        //Go to block
        runTo(colorRun1, allPower, slowPower);
        adjustToInitialAngle();
        runUntil(colorRunUntil1, allPower * .7);
        runTo(colorRun2, allPower, slowPower);
        strafeLeft(colorRunExtra1, allPower, slowPower);
        color = senseColor();
        sleep(1000);
        //Strafe left until in front of correct block
        if (color == "Yellow") {
            strafeRight(strafeToNextBlock, allPower, slowPower);
            blockNumber -= 1;
            color = senseColor();
            sleep(500);
            if (color == "Yellow") {
                blockNumber -= 1;
                strafeRight(strafeToNextBlock, allPower, slowPower);
            }
        }
        telemetry.addData("Block number: ", blockNumber);
        telemetry.update();
        sleep(1000);
        strafeRight(colorRunExtra2, allPower, slowPower);
        //Goes to pick up block
        runTo(colorRun2, allPower * .8, slowPower * .7);
        pickUpBlock();
        //Goes backwards a little. Wall backs up a little more
        runTo(-colorRun3, allPower, slowPower);
        sleep(300);
        adjustToInitialAngle();
        sleep(1000);
        angle = getAngle();
        if (Math.abs(angle) > 40) {
            adjustToInitialAngle();
        }
        //Suckier method
        if (method == 1) {
            turnRight(70, allPower, slowPower);
            accurateTurnRight(-90, allPower);
            runTo(colorRun4 + colorRunMultiplier1 * (6 - blockNumber), allPower, slowPower);
            resetArm();
            sleep(500);
            runTo(-colorRun5 - colorRunMultiplier1 * (6 - blockNumber), allPower, slowPower);
            adjustToInitialAngle();
            sleep(200);
            adjustToInitialAngle();
            runTo(colorRun3, allPower * .8, slowPower * .7);
            pickUpBlock();
            sleep(200);
            runTo(-colorRun3, allPower, slowPower);
            turnRight(70, allPower, slowPower);
            accurateTurnRight(-90, allPower);
            runTo(colorRun6 + colorRunMultiplier1 * (6 - blockNumber), allPower, slowPower);
            resetArm();
            sleep(500);
        }
        //Better Method
        else if (method == 2) {
            //Strafes right until tile #4 from left wall. Foundation side
            strafeRight(colorRun4 + colorRunMultiplier1 * (6 - blockNumber), .8, slowPower);
            dropBlock();
            sleep(200);
            //Adjusts back to angle for picking up block
            adjustToInitialAngle();
            //Go to next skystone halfway
            strafeRight((colorRun5 + colorRunMultiplier1 * (6 - blockNumber)) / 2, .8, slowPower);
            //Adjusts back to angle for picking up block
            resetArm();
            //finish moving to next skystone
            strafeRight((colorRun5 + colorRunMultiplier1 * (6 - blockNumber)) / 2, .8, slowPower);
            //Pick up next block
            adjustToInitialAngle();
            if (blockNumber < 5) {
                strafeRight(colorRunExtra4, allPower, slowPower);
                turnRight(colorRun2 * colorRunMultiplier3 + colorRunMultiplier2 * (6 - blockNumber), allPower, slowPower);
            }
            sleep(500);
            runUntil(colorRunUntil1, allPower * .7);
            resetArm();
            runTo(colorRun2 + colorRunMultiplier3 * (blockNumber - 4), allPower, slowPower);
            sleep(200);
            runTo(colorRun2, allPower, slowPower);
            pickUpBlock();
            sleep(200);
            runTo(-colorRunExtra3, allPower, slowPower);
            //Drop next block
            adjustToInitialAngle();
            turnLeft(colorRunMultiplier2 * (6 - blockNumber), allPower, slowPower);
            if (blockNumber > 4) {
                runTo(-colorRun2 / 2, allPower, slowPower);
            }
            strafeLeft(colorRun6 + colorRunMultiplier1 * (6 - blockNumber), .9, allPower);
            dropBlock();
            strafeRight(colorRun7, allPower, slowPower);
        }
    }
}