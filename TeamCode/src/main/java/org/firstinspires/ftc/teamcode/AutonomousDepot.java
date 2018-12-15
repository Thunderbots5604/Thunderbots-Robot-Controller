package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous(name="Autonomous Depot", group="Autonomous Competition")
public class AutonomousDepot extends GodfatherOfAllAutonomous {

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
        crater = hardwareMap.get(DcMotor.class, "crater");
        wheel = hardwareMap.get(CRServo.class, "wheel");
        crane1 = hardwareMap.get(DcMotor.class, "crane_a");
        crane2 = hardwareMap.get(DcMotor.class, "crane_b");
        elevator = hardwareMap.get(Servo.class, "elevator");

        rightMotorFront.setDirection(DcMotor.Direction.REVERSE);
        rightMotorBack.setDirection(DcMotor.Direction.REVERSE);

        distance = hardwareMap.get(DistanceSensor.class, "distance");

        initVuforia();

        waitForStart();

        detach();
        location = tfodDetection(3);

        if(location == 2) {
            turnRight(55, .45);
            runTo(-15, .5);
        }
        else if (location == 1) {
            turnRight(10,.5);
            runTo(-20,.5);
        }
        else {
            turnLeft(50, .5);
            runTo(-5,.5);
        }

        crater.setPower(.5);
        elevator.setPosition(0.6);
        sleep(500);
        crater.setPower(0);
        sleep(1500);
        elevator.setPosition(.15);
        sleep(750);
    }
}