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


@Autonomous(name="AutoColorRed", group="Autonomous Competition")
public class AutoColorRed extends GodFatherOfAllAutonomous {
    private String color = null;
    private int blockNumber = 6;
    @Override
    public void runOpMode() {

        initialization();

        waitForStart();

        armUp(); spinnyBoyUp();

        //get to the first block, might need editiing depending on color sensor placement
        runTo(19, allPower);
        runUntil(3, allPower);
        sleep(500);
        turnLeft(90, allPower);
        runTo(-16, allPower);
        accurateTurnRight(90, allPower);
        runTo(4, allPower);
        color = senseColor();
        if (color.equals("Yellow")) {
            runTo(8, allPower);
            color = senseColor();
            blockNumber -= 1;
            if (color.equals("Yellow")) {
                runTo(6, allPower);
                blockNumber -= 1;
            }
        }
        //grab block
        turnRight(30, allPower);
        runTo(-2, allPower);
        accurateTurnRight(0, allPower);
        if (blockNumber == 6) {
            turnLeft(15, allPower);
        }
        runTo(12, allPower / 2);
        armDown();
        //back up and turn towards other side
        runTo(-14, allPower);
        turnRight(70, allPower);
        accurateTurnRight(-90, allPower);
        //move (distance depending on block location) to other side
        runTo(10*(6-blockNumber)+17, allPower);
        runTo(6, allPower);
        //drop in block
        armUp();
        runTo(-6, allPower);
        //back to original place + 3 block lengths to go to next block
        turnLeft(5, allPower);
        if (blockNumber != 4){
            runTo(-48, allPower + .25);
            if (blockNumber == 5){
                runTo(-8, allPower);
            }
            turnLeft(75, allPower);
            accurateTurnLeft(0, allPower);
            runTo(16, allPower / 2);
            armDown();
            runTo(-12, allPower);
            turnRight(45, allPower);
            accurateTurnRight(-90, allPower);
            turnRight(10, allPower);
            runTo(55, allPower);
            if (blockNumber == 5){
                runTo(8, allPower);
            }
        } else {
            runTo(-54, allPower + .25);
            turnLeft(90, allPower + .25);
            accurateTurnLeft(25, allPower + .25);
            runTo(10, allPower);
            runTo(6, allPower);
            armDown();
            runTo(-8, allPower);
            turnRight(90, allPower);
            accurateTurnRight(-90, allPower);
            turnRight(10, allPower);
            runTo(58, allPower + .25);
        }
    }
}