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
Still Needs Testing. This should be the same as AutoColorBlue, but for the red side.
Detects Skystone and brings it to the other side, then gets the next skystone and brings it to the other side.
 */

@Autonomous(name="AutoColorRed", group="Test")
public class AutoColorRed extends GodFatherOfAllAutonomous {
    private String color = null;
    private int blockNumber = 6;
    @Override
    public void runOpMode() {

        //get to the first block, might need editiing depending on color sensor placement
        runTo(36, allPower);
        turnLeft(90, allPower);
        //start testing blocks
        while (blockNumber > 4){
            //refresh color sensor
            color = senseColor();
            //if it's not the skystone, move on. Otherwise, exit loop
            if (color.equals("Yellow")){
                runTo(12, allPower);
                blockNumber -= 1;
            }
            else {
                break;
            }
        }
        //grab block
        turnRight(90, allPower);
        armDown();
        //back up and turn towards other side
        runTo(-24, allPower);
        turnRight(90, allPower);
        //move (distance depending on block location) to base
        runTo(12*(6-blockNumber)+60, allPower);
        //drop in block
        armUp();
        //back to original place + 3 block lengths to go to next block
        runTo(-(12*(6-blockNumber)+60), allPower);
        runTo(-36, allPower);
        //orient towards next skystone
        turnLeft(90, allPower);
        //grab next skystone
        runTo(24, allPower);
        armDown();
        //head to base again
        runTo(-24, allPower);
        turnRight(90, allPower);
        runTo(12*(6-blockNumber)+96, allPower);
        //drop in next skystone
        armUp();
    }
}