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


@Autonomous(name="AutoColorRed", group="Block side")
public class AutoColorRed extends GodFatherOfAllAutonomous {
    private String color = null;
    private int blockNumber = 6;
    private boolean wall = false;
    //2 color & distance sensor. side determines which one to use.
    //1 = Right side of robot, 2 = Left side of robot
    private int side = 1;
    @Override
    public void runOpMode() {

        initialization();

        waitForStart();

        //Go to blocks to start sensing
        strafeRight(20, allPower, slowPower);
        adjustToInitialAngle();
        strafeRightUntil(4, slowPower);

        //Sense the blocks
        color = senseColor(side);
        if (color.equals("Yellow")) {
            blockNumber -= 1;
            runTo(7, allPower, slowPower);
            color = senseColor(side);
            if (color.equals("Yellow")) {
                blockNumber -= 1;
                runTo(-5, allPower, slowPower);
                sleep(500);
            }
        }
        if (blockNumber > 4) {
            runTo(2*(blockNumber) - 21, allPower, slowPower);
        }

        //Pick up the block once color is sensed
        strafeRight(12, allPower, slowPower);
        runTo(3, allPower, slowPower);
        //pickUpBlock();

        //Bring block to foundation side and place it faced towards bridge
        strafeLeft(24, allPower, slowPower);
        runTo(-20 - 12*(6-blockNumber), allPower, slowPower);
        turnRight(90, allPower, slowPower);
        sleep(500);
        //spitOutBlock();

        //Readjust to face to go back for second block
        turnLeft(70, allPower, slowPower);
        adjustToInitialAngle();
        sleep(500);
        adjustToInitialAngle();
        runTo(46 + 12*(6-blockNumber), allPower, slowPower);

        //Pick up second block
        strafeRight(18, allPower, slowPower);
        runTo(3, allPower, slowPower);
        sleep(500);
        //pickUpBlock();
        adjustToInitialAngle();
        sleep(500);
        adjustToInitialAngle();

        //Bring block to foundation side
        strafeLeft(22, allPower, slowPower);
        runTo(-60 - 12*(6-blockNumber), allPower, slowPower);
        turnRight(90, allPower, slowPower);
        //spitOutBlock();

        //Readjust to face to go back for third block
        adjustToInitialAngle();
        sleep(500);
        adjustToInitialAngle();
        runTo(36, allPower, slowPower);
        if (blockNumber == 6) {
            runTo(8, allPower, slowPower);
        }

        //Pick up third block (Haven't tested yet)
        strafeRight(20, allPower, slowPower);
        runTo(3, allPower, slowPower);
        sleep(500);
        //pickUpBlock();
        adjustToInitialAngle();
        sleep(500);
        adjustToInitialAngle();

        //Run back to foundation
        strafeLeft(24, allPower, slowPower);
        runTo(-30, allPower + .15, allPower);
    }
}