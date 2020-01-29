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


@Autonomous(name="AutoColorBlueOneBlock", group="Block side")
public class AutoColorBlueOneBlock extends GodFatherOfAllAutonomous {
    private String color = null;
    private boolean red = false;
    @Override
    public void runOpMode() {

        initialization();

        waitForStart();

        //Get first block and point towards foundation side
        startBlock(red);
        turnLeft(10, allPower, slowPower);
        strafeLeft(3, allPower, slowPower);
        //Move to foundation and drop block at stack level 1
        runTo(10 * (blockNumber), allPower, slowPower);
        runTo(32, .9, slowPower * 1.2);

        raiseAndRun(1, 20, allPower, slowPower);
        turnTo(90, allPower, slowPower);
        runTo(5, allPower * .9, allPower * .9);
        dropBlock();
        raiseAndRun(0, -20, allPower, slowPower);
        //Move back under bridge to get other blocks
        runTo(-10, allPower, slowPower);
        turnTo(90, allPower, slowPower);
        if (blockNumber == 0) {
            strafeLeft(3, allPower, slowPower);
        }
        else if (blockNumber == 2) {
            strafeRight(7, allPower, slowPower);
        }
        
        runTo(-11, allPower, slowPower);
    }
}