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

        strafeRight(20, allPower, slowPower);
        adjustToInitialAngle();
        strafeRightUntil(4.5, slowPower);
        strafeRight(1, allPower, slowPower);
        sleep(500);
        color = senseColor(side);
        if (color.equals("Yellow")) {
            blockNumber -= 1;
            runTo(8, allPower, slowPower);
            sleep(500);
            color = senseColor(side);
            if (color.equals("Yellow")) {
                blockNumber -= 1;
                runTo(-4, allPower, slowPower);
                sleep(500);
            }
        }
        if (blockNumber > 4) {
            runTo(2*(blockNumber) - 20, allPower, slowPower);
        }
        strafeRight(12, allPower, slowPower);
        runTo(3, allPower, slowPower);
        //pickUpBlock();
        strafeLeft(44, allPower, slowPower);
        runTo(-20 - 12*(6-blockNumber), allPower, slowPower);
        turnRight(90, allPower, slowPower);
        sleep(500);
        //spitOutBlock();
        turnLeft(70, allPower, slowPower);
        adjustToInitialAngle();
        sleep(500);
        adjustToInitialAngle();
        runTo(48 + 12*(6-blockNumber), allPower, slowPower);
        strafeRight(36, allPower, slowPower);
        runTo(3, allPower, slowPower);
        sleep(500);
        //pickUpBlock();
        adjustToInitialAngle();
        sleep(500);
        adjustToInitialAngle();
        strafeLeft(44, allPower, slowPower);
        runTo(-60 - 12*(6-blockNumber), allPower, slowPower);
    }
}