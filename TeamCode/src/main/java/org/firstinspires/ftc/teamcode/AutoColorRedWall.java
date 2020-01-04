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
    private int blockNumber = 6;
    private boolean wall = true;
    //2 color & distance sensor. side determines which one to use.
    //1 = Right side of robot, 2 = Left side of robot
    private int side = 1;
    @Override
    public void runOpMode() {

        initialization();

        waitForStart();

        runTo(colorRun1, allPower, slowPower);
        adjustToInitialAngle();
        runUntil(colorRunUntil1, allPower * .9);
        color = senseColor();
        if (color == "Yellow") {
            strafeLeft(strafeToNextBlock, allPower, slowPower);
            blockNumber -= 1;
            senseColor();
            if (color == "Yellow") {
                blockNumber -= 1;
                strafeLeft(strafeToNextBlock, allPower, slowPower);
            }
        }
        runTo(colorRun2, allPower * .8, slowPower * .7);
        sleep(1000);
        //pickUpBlock();
        runTo(-colorRunToWall, allPower, slowPower);
        turnRight(70, allPower, slowPower);
        accurateTurnRight(-90, allPower);
        runTo(colorRun4 + colorRunMultiplier1 * (6 - blockNumber), allPower, slowPower);
        //dropBlock();
        sleep(1000);
        runTo(-colorRun5 - colorRunMultiplier1 * (6 - blockNumber), allPower, slowPower);
        adjustToInitialAngle();
        sleep(500);
        adjustToInitialAngle();
        runTo(colorRunToWall, allPower * .8, slowPower * .7);
        //pickUpBlock();
        sleep(1000);
        runTo(-colorRunToWall, allPower, slowPower);
        turnRight(70, allPower, slowPower);
        accurateTurnRight(-90, allPower);
        runTo(colorRun6 + colorRunMultiplier1 * (6 - blockNumber), allPower, slowPower);
        //dropBlock();
        sleep(1000);
    }
}