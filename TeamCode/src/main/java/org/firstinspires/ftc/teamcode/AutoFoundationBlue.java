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

@Autonomous(name="Autonomous_Foundation Blue", group="Foundation")
public class AutoFoundationBlue extends GodFatherOfAllAutonomous {

    @Override
    public void runOpMode() {
        initialization();

        waitForStart();

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
    }
}