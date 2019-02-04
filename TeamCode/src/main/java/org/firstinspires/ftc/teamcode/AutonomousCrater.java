package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

@Autonomous(name="Autonomous Crater", group="Autonomous Competition")
public class AutonomousCrater extends GodfatherOfAllAutonomous {

    private int location = -1;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        leftMotorFront = hardwareMap.get(DcMotor.class, "left_motor_front");
        leftMotorBack = hardwareMap.get(DcMotor.class, "left_motor_back");
        rightMotorFront = hardwareMap.get(DcMotor.class, "right_motor_front");
        rightMotorBack = hardwareMap.get(DcMotor.class, "right_motor_back");
        horizontal = hardwareMap.get(DcMotor.class, "horizontal");
        wheel = hardwareMap.get(CRServo.class, "wheel");
        crane1 = hardwareMap.get(DcMotor.class, "crane_a");
        crane2 = hardwareMap.get(DcMotor.class, "crane_b");
        box1 = hardwareMap.get(CRServo.class, "right_crater");
        box2 = hardwareMap.get(CRServo.class, "left_crater");
        elevator = hardwareMap.get(Servo.class, "elevator");
        distance = hardwareMap.get(DistanceSensor.class, "distance");

        rightMotorFront.setDirection(DcMotor.Direction.REVERSE);
        rightMotorBack.setDirection(DcMotor.Direction.REVERSE);

        leftMotorFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftMotorBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightMotorFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightMotorBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        distance = hardwareMap.get(DistanceSensor.class, "distance");
        waitForStart();

        detach();
        location = tfodDetection(5);
        if (location == 1){
            turnRight(8,45);
        }
        telemetry.addData("Location: ", location);
        telemetry.update();
    }
}