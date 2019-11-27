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


//Still Needs Testing.


@Autonomous(name="AutoColorRed", group="Autonomous Competition")
public class AutoColorRed extends GodFatherOfAllAutonomous {
    private String color = null;
    private int blockNumber = 6;
    @Override
    public void runOpMode() {

        initialization();

        waitForStart();

        //get to the first block, might need editiing depending on color sensor placement
        runTo(19, allPower);
        runUntil(90, allPower);
        sleep(500);
        turnLeft(45, allPower);
        runTo(3, allPower);
        sleep(500);
        accurateTurnLeft(90, allPower);
        runTo(-10, allPower);
        turnRight(10, allPower);
        color = senseColor();
        if (color.equals("Yellow")) {
            runTo(6, allPower);
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
        runUntil(20, allPower);
        armDown();
        //back up and turn towards other side
        runTo(-10, allPower);
        turnRight(70, allPower);
        accurateTurnRight(-88, allPower);
        //move (distance depending on block location) to other side
        runTo(8*(6-blockNumber)+17, allPower);
        //drop in block
        armUp();
        //back to original place + 3 block lengths to go to next block

        /*if (blockNumber != 4) {
            runTo(-(12*(6-blockNumber)+20), allPower);
            runTo(-24, allPower);
            turnLeft(120, allPower);
            runTo(10, allPower);
        }
        else {
            runTo(-48, allPower);
            turnLeft(100, allPower);
            turnLeft(60, allPower);
            runTo(5, allPower);
            accurateTurnRight(10, allPower);
            runTo(5, allPower);
        }
        //orient towards next skystone

        //grab next skystone
        runUntil(20, allPower);
        armDown();
        //head to base again
        runTo(-18, allPower);
        turnRight(70, allPower);
        accurateTurnRight(-90, allPower);
        runTo(8*(6-blockNumber)+17, allPower);
        runTo(24, allPower);
        //drop in next skystone
        armUp();
        //Run to park below bridge
        runTo(2, allPower);*/
    }
}