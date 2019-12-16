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

@Autonomous(name="Autonomous_Foundation Blue", group="Autonomous Competition")
public class AutoFoundationBlue extends GodFatherOfAllAutonomous {

    @Override
    public void runOpMode() {
        initialization();

        waitForStart();

        armUp(); spinnyBoyUp();

        //move to foundation


        turnRight(20, allPower);
        runTo(-42, allPower);
        turnRight(60, allPower);
        accurateTurnRight(-85, allPower);
        runTo(-32.5, allPower * .7);
        runTo(-5, allPower * .5);
        sleep(1000);
        //attach to foundation
        spinnyBoyDown();
        //back up
        sleep(1000);
        runtime.reset();
        mmAway = getDistance();
        leftMotorFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftMotorBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightMotorFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightMotorBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftMotorFront.setPower(.5);
        leftMotorBack.setPower(.4);
        rightMotorFront.setPower(.9);
        rightMotorBack.setPower(.9);
        while(runtime.milliseconds() < 2000 && mmAway > 150) {
            mmAway = getDistance();
        }
        leftMotorFront.setPower(0);
        leftMotorBack.setPower(0);
        rightMotorFront.setPower(0);
        rightMotorBack.setPower(0);
        //Unattach from foundation
        spinnyBoyUp();
        sleep(1000);
        runTo(2, allPower);
        //turn and move to line
        accurateTurnLeft(0, allPower * 1.2);
        turnLeft(15, allPower);
        angle = getAngle();
        if (angle < 10) {
            spinnyBoyDown();
            turnRight(30, allPower);
            runTo(-5, .9);
            runTo(3, allPower);
        }
        runTo(35, allPower);
    }
}