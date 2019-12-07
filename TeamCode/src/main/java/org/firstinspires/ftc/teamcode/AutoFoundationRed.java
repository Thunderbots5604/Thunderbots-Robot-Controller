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

@Autonomous(name="Autonomous_Foundation Red", group="Autonomous Competition")
public class AutoFoundationRed extends GodFatherOfAllAutonomous {

    @Override
    public void runOpMode() {
        initialization();

        waitForStart();

        //move to foundation


        turnLeft(20, allPower);
        runTo(-42, allPower);
        turnLeft(60, allPower);
        accurateTurnLeft(90, allPower);
        turnRight(10, allPower);
        runTo(-32.5, allPower * .7);
        runTo(-3, allPower * .6);
        sleep(1000);
        //attach to foundation
        spinnyBoyDown();
        //back up
        sleep(1000);
        mmAway = getDistance();
        leftMotorFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftMotorBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightMotorFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightMotorBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftMotorFront.setPower(.9);
        leftMotorBack.setPower(.9);
        rightMotorFront.setPower(.5);
        rightMotorBack.setPower(.4);
        while(mmAway > 250) {
            mmAway = getDistance();
        }
        leftMotorFront.setPower(0);
        leftMotorBack.setPower(0);
        rightMotorFront.setPower(0);
        rightMotorBack.setPower(0);
        //Unattach from foundation
        spinnyBoyUp();
        sleep(1000);
        runTo(1, allPower);
        //turn and move to line
        turnRight(70, allPower);
        accurateTurnRight(0, allPower);
        runTo(37, .9);

    }
}