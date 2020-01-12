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

@Autonomous(name="Autonomous_Foundation Blue & Block", group="Foundation")
public class AutoFoundationBlueAndBlock extends GodFatherOfAllAutonomous {

    @Override
    public void runOpMode() {
        initialization();

        waitForStart();

        runtime.reset();
        strafeLeft(foundationStrafeInitial, allPower, slowPower);
        runTo(foundationRunInitial, allPower, slowPower);
        turnLeft(40, allPower, slowPower);
        sleep(1000);
        accurateTurnRight(0, allPower);
        runTo(foundationRun1, allPower, slowPower);
        runTo(foundationRun2, allPower, slowPower * .8);
        spinnyBoyDown();
        sleep(1000);
        runTo(-foundationRun3, foundationPower1, foundationPower2);
        spinnyBoyUp();
        sleep(500);
        adjustToInitialAngle();
        strafeRight(foundationStrafe1, allPower, slowPower);
        runTo(foundationRun4, allPower, slowPower);
        //Wait until 20 seconds left so teamate can get  skystones
        while (runtime.milliseconds() < 20000) {}
        strafeRight(foundationStrafe2, allPower, slowPower);
        runTo(colorRun1, allPower, slowPower);
        mmAway = getDistance();
        if (mmAway > 300) {
            strafeRight(strafeToNextBlock, allPower, slowPower);
        }
        runUntil(colorRunUntil1, slowPower);
        runTo(colorRun2, allPower * .8, slowPower * .7);
        sleep(1000);
        //pickUpBlock();
        runTo(-colorRunToWall, allPower, slowPower);
        turnLeft(70, allPower, slowPower);
        accurateTurnLeft(90, allPower);
        runTo(foundationStrafe2, allPower, slowPower);
    }
}