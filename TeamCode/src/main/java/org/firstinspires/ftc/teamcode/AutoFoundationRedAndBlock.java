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

@Autonomous(name="Autonomous_Foundation Red & Block", group="Foundation")
public class AutoFoundationRedAndBlock extends GodFatherOfAllAutonomous {

    private boolean red = true;


    @Override
    public void runOpMode() {
        initialization();
        
        waitForStart();
        
        verticalUp();
        resetArm();
        spinnyBoyUp();

        
        strafeRight(8, allPower, slowPower);
        runTo(16, allPower, slowPower);
        runUntil(70, allPower * .7);
        runTo(3, allPower * .6, slowPower * .9);
        moveFoundation(red);
        runTo(10, allPower, slowPower);
        runTo(-10, allPower, slowPower);
        strafeRight(7, allPower, slowPower);
        verticalDown();
        turnTo(-90, allPower, slowPower);
        runTo(-110, allPower, slowPower * .8);
        turnLeft(90, allPower, slowPower);
        turnTo(20, allPower, slowPower);
        mmAway = getDistance();
        if (mmAway > 400) {
            turnTo(0, allPower, slowPower);
        }
        runTo(8, allPower, slowPower);
        runUntil(100, slowPower * .9);
        runTo(3, allPower * .6, slowPower * .8);
        pickUpBlock();
        runTo(-3, allPower, slowPower);
        if (mmAway > 400) {
            strafeLeft(9, allPower, slowPower);
        }
        runTo(-26, allPower, slowPower);
        turnTo(-90, allPower, slowPower);
        runTo(90, allPower, slowPower);
        dropBlock();
        runTo(-30, allPower, slowPower);
    }
}