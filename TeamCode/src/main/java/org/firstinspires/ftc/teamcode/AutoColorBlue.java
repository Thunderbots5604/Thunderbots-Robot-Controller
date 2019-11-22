package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
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

/*
Still Needs Testing. This should be the same as AutoColorRed, but for the blue side.
Detects Skystone and brings it to the other side, then gets the next skystone and brings it to the other side.
 */


@Autonomous(name="AutoColorBlue", group="Test")
public class AutoColorBlue extends GodFatherOfAllAutonomous {
    private String color = null;
    private int blockNumber = 6;
    @Override
    public void runOpMode() {

        initialization();

        waitForStart();

        //get to the first block, might need editiing depending on color sensor placement
        runTo(18, allPower);
        turnLeft(40, allPower);
        runTo(3, allPower);
        turnLeft(20, allPower);
        runTo(2, allPower);
        accurateTurnLeft(85, allPower);
        runTo(-6, allPower);
        //start testing blocks
        while (blockNumber > 4){
            //refresh color sensor
            sleep(500);
            color = senseColor();
            //if it's not the skystone, move on. Otherwise, exit loop
            if (color.equals("Yellow")){

                runTo(-8, allPower);
                blockNumber -= 1;
            }
            else {
                break;
            }
            telemetry.addData("Scanning block", blockNumber);
            telemetry.update();
        }
        //grab block
        turnRight(60, allPower);
        runTo(10, allPower / 2);
        armDown();
        //back up and turn towards other side
        runTo(-15, allPower);
        turnLeft(75, allPower);
        //move (distance depending on block location) to base
        runTo(12*(6-blockNumber)+60, allPower);
        //drop in block
        armUp();
        //back to original place + 3 block lengths to go to next block
        runTo(-(12*(6-blockNumber)+60), allPower);
        runTo(-12, allPower);
        //orient towards next skystone
        turnRight(90, allPower);
        //grab next skystone
        runTo(10, allPower);
        runTo(5, allPower / 2);
        armDown();
        //head to base again
        runTo(-15, allPower);
        accurateTurnLeft(80, allPower);
        runTo(12*(6-blockNumber)+96, allPower);
        //drop in next skystone
        armUp();
        runTo(-20, allPower);
    }
}