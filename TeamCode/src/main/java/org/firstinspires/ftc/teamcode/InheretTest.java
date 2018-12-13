package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import java.util.List;

@Autonomous(name="Test Inheret", group="Test")
public abstract class InheretTest extends LinearOpMode {
    private class Testing extends AutoDetach {
        // Declare OpMode members.
        private ElapsedTime runtime = new ElapsedTime();
        private DcMotor leftMotorFront = null;
        private DcMotor leftMotorBack = null;
        private DcMotor rightMotorFront = null;
        private DcMotor rightMotorBack = null;
        private DcMotor crane = null;
        //positive brings crater up
        private DcMotor crater = null;
        private DistanceSensor distance = null;
        private final double INCHES_PER_TICK = .0223147377;
        private final double DEGREES_PER_TICK = .1525087903;

        @Override
        public void runOpMode() {
            telemetry.addData("Status", "Initialized");
            telemetry.update();

            leftMotorFront = hardwareMap.get(DcMotor.class, "left_motor_front");
            leftMotorBack = hardwareMap.get(DcMotor.class, "left_motor_back");
            rightMotorFront = hardwareMap.get(DcMotor.class, "right_motor_front");
            rightMotorBack = hardwareMap.get(DcMotor.class, "right_motor_back");
            crane = hardwareMap.get(DcMotor.class, "crane");
            crater = hardwareMap.get(DcMotor.class, "crater");
            distance = hardwareMap.get(DistanceSensor.class, "distance");
            rightMotorFront.setDirection(DcMotor.Direction.REVERSE);
            rightMotorBack.setDirection(DcMotor.Direction.REVERSE);

            waitForStart();

            super.runTo(50, .5);
            super.detach();
        }
    }
}

