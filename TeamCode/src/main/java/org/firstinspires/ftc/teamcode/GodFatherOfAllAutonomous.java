package org.firstinspires.ftc.teamcode;


import com.qualcomm.hardware.bosch.BNO055IMU;
import java.lang.annotation.Target;
import com.qualcomm.robotcore.hardware.DcMotor.RunMode;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.SwitchableLight;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
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
import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DistanceSensor;

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

@Disabled
@Autonomous(name="No Use", group="Autonomous Competition")
public class GodFatherOfAllAutonomous extends LinearOpMode {

    //Time
    public ElapsedTime runtime = new ElapsedTime();
    public ElapsedTime adjustTime = new ElapsedTime();
    public ElapsedTime armCooldown = new ElapsedTime();

    //Motors / Servos
    public DcMotor leftMotorFront = null;
    public DcMotor leftMotorBack = null;
    public DcMotor rightMotorFront = null;
    public DcMotor rightMotorBack = null;

    public DcMotor vertical1 = null;
    public DcMotor vertical2 = null;

    public Servo spinnyBoy1 = null;
    public Servo spinnyBoy2 = null;

    public Servo armServo = null;

    public Servo parkServo = null;

    //All Power for autonomous running
    public double allPower = .6;
    public double slowPower = .4;

    //Color Sensor
    public ColorSensor colorSensor;
    public String color = null;
    public int blue = 0;
    public int red = 0;
    public int green = 0;

    //Distance Sensor
    public DistanceSensor distance = null;
    public double mmAway;
    public double mmTraveling;
    public double totalDistance;
    public double blockDistance = 50;
    public boolean blockPickedUp = false;

    //Ticks
    //Ticks for forward and backwards
    public final double TICKS_PER_INCH = 24.5;
    //Ticks for Turning
    public final double TICKS_PER_DEGREE_LLF = -7.44;
    public final double TICKS_PER_DEGREE_LLB = -6.97;
    public final double TICKS_PER_DEGREE_LRF = 7.16;
    public final double TICKS_PER_DEGREE_LRB = 6.89;
    public final double TICKS_PER_DEGREE_RLF = 7.44;
    public final double TICKS_PER_DEGREE_RLB = 7.1;
    public final double TICKS_PER_DEGREE_RRF = -7.13;
    public final double TICKS_PER_DEGREE_RRB = -6.91;
    //Ticks for Strafing
    //First Initial = side of strafing
    //Second Initial = side of Robot the motor is on
    //Third Initial = Front or back of robot
    public final double TICKS_PER_STRAFE_LLF = -39.053;
    public final double TICKS_PER_STRAFE_LLB = 37.623;
    public final double TICKS_PER_STRAFE_LRF = 38.09;
    public final double TICKS_PER_STRAFE_LRB = -35.89;
    public final double TICKS_PER_STRAFE_RLF = 40.678;
    public final double TICKS_PER_STRAFE_RLB = -38.473;
    public final double TICKS_PER_STRAFE_RRF = -48.33;
    public final double TICKS_PER_STRAFE_RRB = 40.291;
    public final float TICKS_MULTIPLIER = 30F / 26F;
    public double strafePower_LLF = Math.abs(50 / TICKS_PER_STRAFE_LLF);
    public double strafePower_LLB = Math.abs(50 / TICKS_PER_STRAFE_LLB);
    public double strafePower_LRF = Math.abs(48 / TICKS_PER_STRAFE_LRF);
    public double strafePower_LRB = Math.abs(50 / TICKS_PER_STRAFE_LRB);
    public double strafePower_RLF = Math.abs(48 / TICKS_PER_STRAFE_RLF);
    public double strafePower_RLB = Math.abs(48 / TICKS_PER_STRAFE_RLB);
    public double strafePower_RRF = Math.abs(48 / TICKS_PER_STRAFE_RRF);
    public double strafePower_RRB = Math.abs(48 / TICKS_PER_STRAFE_RRB);
    public double tickPowers[];
    //Ticks for Vertical
    public double blockTick= 20;

    //Vuforia
    public int location = -1;
    public int objects = 0;
    public static final String TFOD_MODEL_ASSET = "RoverRuckus.tflite";
    public static final String LABEL_SKYSTONE = "Skystone";
    public static final String LABEL_BLOCK = "Stone";
    public static final String VUFORIA_KEY = "Aev2uJj/////AAABmXZLlevRVUibu3ft/8eoZ+p3zmNO/qYTRunRCIvDriYoZlMUcvJWFcEhvD1bCA6j/KWPlsVQyzCyh983kmfZN03G5bBJXhDh4fSgT4yyHL4PScYi5aG1UaxLa38X2vqzrbx9jpUqE3ESk6wYg8enXTPzp8R6+0SnrFoRLa7yobzCbBIfzAIpsGO33F9PVbXV+zsf0jqg0KA9OG24I6WkLZll0YPy1fDkR1okXL4pv2pm7eiKaZa2EXIYE/lGfkOAO42vxFMO8rAqA46/YeX/QPPTrCow0dE81FGSS6Wp9v3z45lqQ/kg+0TnSDkOJFrGKUYD1v6zTkfJLhF6DDAuW1TwPcdof0349IOncpuCpcz9";
    public VuforiaLocalizer vuforia;
    public TFObjectDetector tfod;

    //Angles
    public BNO055IMU imu;
    public Orientation angles;
    public double heading = 0;
    public double angle = 0;
    public double degrees = 0;
    public int side = 0;
    public double targetAngle;
    public double currentAngle;
    public double initialAngle;
    public double angleDifference;
    public String turnTowards;

    //Autonomous
    public int blockNumber = 0;
    public boolean foundation = false;
    public int blockFails = 0;
    public boolean failAttempted = false;
    public boolean firstPick = true;

    //Hopefully magnificent raiseAndRun
    public int driveTargetPosition = 0;
    public int verticalTargetPosition = 0;
    public int remainingTicks = 0;
    public double remainingInches = 0;
    int runMultiplier;
    double verticalMultiplier;
    int absTarget;
    int absLeftPosition;
    int absRightPosition;
    private double verticalPower = .9;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();
    }
    //Does all the stuff when init is pressed on phone
    public void initialization() {
        leftMotorFront = hardwareMap.get(DcMotor.class, "left_motor_front");
        leftMotorBack = hardwareMap.get(DcMotor.class, "left_motor_back");
        rightMotorFront = hardwareMap.get(DcMotor.class, "right_motor_front");
        rightMotorBack = hardwareMap.get(DcMotor.class, "right_motor_back");

        vertical1 = hardwareMap.get(DcMotor.class, "vertical1");
        vertical2 = hardwareMap.get(DcMotor.class, "vertical2");

        spinnyBoy1 = hardwareMap.get(Servo.class, "spin1");
        spinnyBoy2 = hardwareMap.get(Servo.class, "spin2");

        armServo = hardwareMap.get(Servo.class, "armServo");

        parkServo = hardwareMap.get(Servo.class, "parkServo");

        colorSensor = hardwareMap.colorSensor.get("color_sensor");
        distance = hardwareMap.get(DistanceSensor.class, "distance");

        leftMotorFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftMotorBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightMotorFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightMotorBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        vertical1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        vertical2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibration sample opmode
        parameters.loggingEnabled = true;
        parameters.loggingTag = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();

        imu = hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);

        rightMotorFront.setDirection(DcMotorSimple.Direction.REVERSE);
        rightMotorBack.setDirection(DcMotorSimple.Direction.REVERSE);
        spinnyBoy1.setDirection(Servo.Direction.REVERSE);
        vertical2.setDirection(DcMotorSimple.Direction.REVERSE);

        colorSensor.enableLed(false);

        spinnyBoyUp();

        vertical1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        vertical2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        vertical1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        vertical2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        //there's nothing that talks about foundation anywhere else. fix later
        /*if (foundation == false) {
            dropBlock();
        }*/

        double maxLeft = 0;
        double maxRight = 0;
        double[] tickPowers = {TICKS_PER_STRAFE_LLF, TICKS_PER_STRAFE_LLB, TICKS_PER_STRAFE_LRF, TICKS_PER_STRAFE_LRB, TICKS_PER_STRAFE_RLF, TICKS_PER_STRAFE_RLB, TICKS_PER_STRAFE_RRF, TICKS_PER_STRAFE_LRB};
        for (double i : tickPowers) {
            if (i < 4) {
                if (i > maxLeft) {
                    maxLeft = i;
                }
            }
            else {
                if (i > maxRight) {
                    maxRight = i;
                }
            }
        }
        strafePower_LLF = Math.abs(TICKS_PER_STRAFE_LLF / maxLeft);
        strafePower_LLB = Math.abs(TICKS_PER_STRAFE_LLB / maxLeft);
        strafePower_LRF = Math.abs(TICKS_PER_STRAFE_LRF / maxLeft);
        strafePower_LRB = Math.abs(TICKS_PER_STRAFE_LRB / maxLeft);
        strafePower_RLF = Math.abs(TICKS_PER_STRAFE_RLF / maxRight);
        strafePower_RLB = Math.abs(TICKS_PER_STRAFE_RLB / maxRight);
        strafePower_RRF = Math.abs(TICKS_PER_STRAFE_RRF / maxRight);
        strafePower_RRB = Math.abs(TICKS_PER_STRAFE_RRB / maxRight);
    }
    //Use encoders to move robot a certain number of inches. For power, use allPower
    public void runTo(double inches, double power, double slowerPower) {
        while (opModeIsActive()) {
            initialAngle = getAngle();
            runtime.reset();
            leftMotorFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            leftMotorBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            rightMotorFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            rightMotorBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            leftMotorFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            leftMotorBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            rightMotorFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            rightMotorBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            int targetPosition = (int)(inches * TICKS_PER_INCH * TICKS_MULTIPLIER);

            if (inches > 0) {
                while ((leftMotorBack.getCurrentPosition() < targetPosition * .6) && (rightMotorBack.getCurrentPosition() > -targetPosition * .6) && opModeIsActive() && runtime.milliseconds() < 4000) {
                    telemetry.addData("Target Position", targetPosition);
                    telemetry.addData("Left Motor Front Position", leftMotorFront.getCurrentPosition());
                    telemetry.addData("Left Motor Back Position", leftMotorBack.getCurrentPosition());
                    telemetry.addData("Right Motor Front Position", rightMotorFront.getCurrentPosition());
                    telemetry.addData("Right Motor Back Position", rightMotorBack.getCurrentPosition());
                    telemetry.addData("Left Motor Front Power", leftMotorFront.getPower());
                    telemetry.addData("Left Motor Back Power", leftMotorBack.getPower());
                    telemetry.addData("Right Motor Front Power", rightMotorFront.getPower());
                    telemetry.addData("Right Motor Back Power", rightMotorBack.getPower());
                    telemetry.update();
                    leftMotorFront.setPower(power);
                    leftMotorBack.setPower(power);
                    rightMotorFront.setPower(power);
                    rightMotorBack.setPower(power);

                    //New code to adjust in running methods if angle curves off too much. Like strafing. Gonna use for strafing and runTo
                    //getAngleDifference gives a positive result of difference between 2 angles
                    currentAngle = getAngle();
                    angleDifference = getAngleDifference(currentAngle, initialAngle);
                    turnTowards = closerSide(currentAngle, initialAngle);
                    if (angleDifference > 5 && adjustTime.milliseconds() > 500) {
                        targetPosition = targetPosition - (leftMotorBack.getCurrentPosition() / 2);
                        targetPosition = targetPosition - (rightMotorBack.getCurrentPosition() / 2);

                        leftMotorFront.setPower(0);
                        leftMotorBack.setPower(0);
                        rightMotorFront.setPower(0);
                        rightMotorBack.setPower(0);
                        turnTo(initialAngle, power, slowerPower);

                        adjustTime.reset();
                        leftMotorFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                        leftMotorBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                        rightMotorFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                        rightMotorBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                        leftMotorFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                        leftMotorBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                        rightMotorFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                        rightMotorBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                    }
                    //End of the new code
                }
                leftMotorFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                leftMotorBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                rightMotorFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                rightMotorBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                while ((leftMotorBack.getCurrentPosition() < targetPosition) && (rightMotorBack.getCurrentPosition() > -targetPosition) && opModeIsActive() && runtime.milliseconds() < 4000) {
                    telemetry.addData("Target Position", targetPosition);
                    telemetry.addData("Left Motor Front Position", leftMotorFront.getCurrentPosition());
                    telemetry.addData("Left Motor Back Position", leftMotorBack.getCurrentPosition());
                    telemetry.addData("Right Motor Front Position", rightMotorFront.getCurrentPosition());
                    telemetry.addData("Right Motor Back Position", rightMotorBack.getCurrentPosition());
                    telemetry.addData("Left Motor Front Power", leftMotorFront.getPower());
                    telemetry.addData("Left Motor Back Power", leftMotorBack.getPower());
                    telemetry.addData("Right Motor Front Power", rightMotorFront.getPower());
                    telemetry.addData("Right Motor Back Power", rightMotorBack.getPower());
                    telemetry.update();
                    leftMotorFront.setPower(slowerPower);
                    leftMotorBack.setPower(slowerPower);
                    rightMotorFront.setPower(slowerPower);
                    rightMotorBack.setPower(slowerPower);

                    //New code to adjust in running methods if angle curves off too much. Like strafing. Gonna use for strafing and runTo
                    //getAngleDifference gives a positive result of difference between 2 angles
                    currentAngle = getAngle();
                    angleDifference = getAngleDifference(currentAngle, initialAngle);
                    turnTowards = closerSide(currentAngle, initialAngle);
                    if (angleDifference > 5 && adjustTime.milliseconds() > 500) {
                        targetPosition = targetPosition - (leftMotorBack.getCurrentPosition() / 2);
                        targetPosition = targetPosition - (rightMotorBack.getCurrentPosition() / 2);

                        leftMotorFront.setPower(0);
                        leftMotorBack.setPower(0);
                        rightMotorFront.setPower(0);
                        rightMotorBack.setPower(0);
                        turnTo(initialAngle, power, slowerPower);

                        adjustTime.reset();
                        leftMotorFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                        leftMotorBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                        rightMotorFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                        rightMotorBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                        leftMotorFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                        leftMotorBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                        rightMotorFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                        rightMotorBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                    }
                    //End of the new code
                }
            }
            else {
                while ((leftMotorBack.getCurrentPosition() > targetPosition * .6) && (rightMotorBack.getCurrentPosition() < -targetPosition * .6) && opModeIsActive() && runtime.milliseconds() < 4000) {
                    telemetry.addData("Target Position", targetPosition);
                    telemetry.addData("Left Motor Front Position", leftMotorFront.getCurrentPosition());
                    telemetry.addData("Left Motor Back Position", leftMotorBack.getCurrentPosition());
                    telemetry.addData("Right Motor Front Position", rightMotorFront.getCurrentPosition());
                    telemetry.addData("Right Motor Back Position", rightMotorBack.getCurrentPosition());
                    telemetry.addData("Left Motor Front Power", leftMotorFront.getPower());
                    telemetry.addData("Left Motor Back Power", leftMotorBack.getPower());
                    telemetry.addData("Right Motor Front Power", rightMotorFront.getPower());
                    telemetry.addData("Right Motor Back Power", rightMotorBack.getPower());
                    telemetry.update();
                    leftMotorFront.setPower(-power);
                    leftMotorBack.setPower(-power);
                    rightMotorFront.setPower(-power);
                    rightMotorBack.setPower(-power);

                    //New code to adjust in running methods if angle curves off too much. Like strafing. Gonna use for strafing and runTo
                    //getAngleDifference gives a positive result of difference between 2 angles
                    currentAngle = getAngle();
                    angleDifference = getAngleDifference(currentAngle, initialAngle);
                    turnTowards = closerSide(currentAngle, initialAngle);
                    if (angleDifference > 5 && adjustTime.milliseconds() > 500) {
                        targetPosition = targetPosition - (leftMotorBack.getCurrentPosition() / 2);
                        targetPosition = targetPosition - (rightMotorBack.getCurrentPosition() / 2);

                        leftMotorFront.setPower(0);
                        leftMotorBack.setPower(0);
                        rightMotorFront.setPower(0);
                        rightMotorBack.setPower(0);
                        turnTo(initialAngle, power, slowerPower);

                        adjustTime.reset();
                        leftMotorFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                        leftMotorBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                        rightMotorFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                        rightMotorBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                        leftMotorFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                        leftMotorBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                        rightMotorFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                        rightMotorBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                    }
                }
                leftMotorFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                leftMotorBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                rightMotorFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                rightMotorBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                while ((leftMotorBack.getCurrentPosition() > targetPosition) && (rightMotorBack.getCurrentPosition() < -targetPosition) && opModeIsActive() && runtime.milliseconds() < 4000) {
                    telemetry.addData("Target Position", targetPosition);
                    telemetry.addData("Left Motor Front Position", leftMotorFront.getCurrentPosition());
                    telemetry.addData("Left Motor Back Position", leftMotorBack.getCurrentPosition());
                    telemetry.addData("Right Motor Front Position", rightMotorFront.getCurrentPosition());
                    telemetry.addData("Right Motor Back Position", rightMotorBack.getCurrentPosition());
                    telemetry.addData("Left Motor Front Power", leftMotorFront.getPower());
                    telemetry.addData("Left Motor Back Power", leftMotorBack.getPower());
                    telemetry.addData("Right Motor Front Power", rightMotorFront.getPower());
                    telemetry.addData("Right Motor Back Power", rightMotorBack.getPower());
                    telemetry.update();
                    leftMotorFront.setPower(-slowerPower);
                    leftMotorBack.setPower(-slowerPower);
                    rightMotorFront.setPower(-slowerPower);
                    rightMotorBack.setPower(-slowerPower);

                    //New code to adjust in running methods if angle curves off too much. Like strafing. Gonna use for strafing and runTo
                    //getAngleDifference gives a positive result of difference between 2 angles
                    currentAngle = getAngle();
                    angleDifference = getAngleDifference(currentAngle, initialAngle);
                    turnTowards = closerSide(currentAngle, initialAngle);
                    if (angleDifference > 5 && adjustTime.milliseconds() > 500) {
                        targetPosition = targetPosition - (leftMotorBack.getCurrentPosition() / 2);
                        targetPosition = targetPosition - (rightMotorBack.getCurrentPosition() / 2);

                        leftMotorFront.setPower(0);
                        leftMotorBack.setPower(0);
                        rightMotorFront.setPower(0);
                        rightMotorBack.setPower(0);
                        turnTo(initialAngle, power, slowerPower);

                        adjustTime.reset();
                        leftMotorFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                        leftMotorBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                        rightMotorFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                        rightMotorBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                        leftMotorFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                        leftMotorBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                        rightMotorFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                        rightMotorBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                    }
                }
            }
            leftMotorFront.setPower(0);
            leftMotorBack.setPower(0);
            rightMotorFront.setPower(0);
            rightMotorBack.setPower(0);
            leftMotorFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            leftMotorBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            rightMotorFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            rightMotorBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            leftMotorFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            leftMotorBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            rightMotorFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            rightMotorBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            return;
        }
        leftMotorFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftMotorBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotorFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotorBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftMotorFront.setPower(0);
        leftMotorBack.setPower(0);
        rightMotorFront.setPower(0);
        rightMotorBack.setPower(0);
    }
    //Turns left a certain number of degrees.
    public void turnLeft(double degrees, double power, double slowerPower) {
        while (opModeIsActive()) {
            runtime.reset();
            leftMotorFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            leftMotorBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            rightMotorFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            rightMotorBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            leftMotorFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            leftMotorBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            rightMotorFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            rightMotorBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

            int targetTurn_LLF = (int)(degrees * TICKS_PER_DEGREE_LLF * TICKS_MULTIPLIER);
            int targetTurn_LLB = (int)(degrees * TICKS_PER_DEGREE_LLB * TICKS_MULTIPLIER);
            int targetTurn_LRF = (int)(degrees * TICKS_PER_DEGREE_LRF * TICKS_MULTIPLIER);
            int targetTurn_LRB = (int)(degrees * TICKS_PER_DEGREE_LRB * TICKS_MULTIPLIER);

            while ((leftMotorFront.getCurrentPosition() > targetTurn_LLF * .6) && (rightMotorBack.getCurrentPosition() < targetTurn_LRB * .6) && (leftMotorBack.getCurrentPosition() > targetTurn_LLB * .6) && (rightMotorFront.getCurrentPosition() < targetTurn_LRF * .6) && opModeIsActive() && runtime.milliseconds() < 4000) {
                telemetry.addData("Target Position", targetTurn_LLF);
                telemetry.addData("Left Motor Front Position", leftMotorFront.getCurrentPosition());
                telemetry.addData("Left Motor Back Position", leftMotorBack.getCurrentPosition());
                telemetry.addData("Right Motor Front Position", rightMotorFront.getCurrentPosition());
                telemetry.addData("Right Motor Back Position", rightMotorBack.getCurrentPosition());
                telemetry.addData("Turning", "Left");
                telemetry.update();
                leftMotorFront.setPower(-power);
                leftMotorBack.setPower(-power);
                rightMotorFront.setPower(power);
                rightMotorBack.setPower(power);
            }
            while ((leftMotorFront.getCurrentPosition() > targetTurn_LLF) && (rightMotorBack.getCurrentPosition() < targetTurn_LRB) && (leftMotorBack.getCurrentPosition() > targetTurn_LLB) && (rightMotorFront.getCurrentPosition() < targetTurn_LRF) && opModeIsActive() && runtime.milliseconds() < 4000) {
                telemetry.addData("Target Position", targetTurn_LLF);
                telemetry.addData("Left Motor Front Position", leftMotorFront.getCurrentPosition());
                telemetry.addData("Left Motor Back Position", leftMotorBack.getCurrentPosition());
                telemetry.addData("Right Motor Front Position", rightMotorFront.getCurrentPosition());
                telemetry.addData("Right Motor Back Position", rightMotorBack.getCurrentPosition());
                telemetry.addData("Turning", "Left");
                telemetry.update();
                leftMotorFront.setPower(-slowerPower);
                leftMotorBack.setPower(-slowerPower);
                rightMotorFront.setPower(slowerPower);
                rightMotorBack.setPower(slowerPower);
            }
            leftMotorFront.setPower(0);
            leftMotorBack.setPower(0);
            rightMotorFront.setPower(0);
            rightMotorBack.setPower(0);
            return;
        }
        leftMotorFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftMotorBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotorFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotorBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftMotorFront.setPower(0);
        leftMotorBack.setPower(0);
        rightMotorFront.setPower(0);
        rightMotorBack.setPower(0);
    }
    //Turns right a certain number of degrees.
    public void turnRight(double degrees, double power, double slowerPower) {
        while (opModeIsActive()) {
            runtime.reset();
            leftMotorFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            leftMotorBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            rightMotorFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            rightMotorBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            leftMotorFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            leftMotorBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            rightMotorFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            rightMotorBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

            int targetTurn_RLF = (int)(degrees * TICKS_PER_DEGREE_RLF * TICKS_MULTIPLIER);
            int targetTurn_RLB = (int)(degrees * TICKS_PER_DEGREE_RLB * TICKS_MULTIPLIER);
            int targetTurn_RRF = (int)(degrees * TICKS_PER_DEGREE_RRF * TICKS_MULTIPLIER);
            int targetTurn_RRB = (int)(degrees * TICKS_PER_DEGREE_RRB * TICKS_MULTIPLIER);

            while ((leftMotorFront.getCurrentPosition() < targetTurn_RLF * .6) && (rightMotorBack.getCurrentPosition() > targetTurn_RRB * .6) && (leftMotorBack.getCurrentPosition() < targetTurn_RLB * .6) && (rightMotorFront.getCurrentPosition() > targetTurn_RRF * .6) && opModeIsActive() && runtime.milliseconds() < 4000) {
                telemetry.addData("Target Position", targetTurn_RLF);
                telemetry.addData("Left Motor Front Position", leftMotorFront.getCurrentPosition());
                telemetry.addData("Left Motor Back Position", leftMotorBack.getCurrentPosition());
                telemetry.addData("Right Motor Front Position", rightMotorFront.getCurrentPosition());
                telemetry.addData("Right Motor Back Position", rightMotorBack.getCurrentPosition());
                telemetry.addData("Turning", "Right");
                telemetry.update();
                leftMotorFront.setPower(power);
                leftMotorBack.setPower(power);
                rightMotorFront.setPower(-power);
                rightMotorBack.setPower(-power);
            }
            while ((leftMotorFront.getCurrentPosition() > targetTurn_RLF) && (rightMotorBack.getCurrentPosition() < targetTurn_RRB) && (leftMotorBack.getCurrentPosition() > targetTurn_RLB) && (rightMotorFront.getCurrentPosition() < targetTurn_RRF) && opModeIsActive() && runtime.milliseconds() < 4000) {
                telemetry.addData("Target Position", targetTurn_RLF);
                telemetry.addData("Left Motor Front Position", leftMotorFront.getCurrentPosition());
                telemetry.addData("Left Motor Back Position", leftMotorBack.getCurrentPosition());
                telemetry.addData("Right Motor Front Position", rightMotorFront.getCurrentPosition());
                telemetry.addData("Right Motor Back Position", rightMotorBack.getCurrentPosition());
                telemetry.addData("Turning", "Right");
                telemetry.update();
                leftMotorFront.setPower(slowerPower);
                leftMotorBack.setPower(slowerPower);
                rightMotorFront.setPower(-slowerPower);
                rightMotorBack.setPower(-slowerPower);
            }
            leftMotorFront.setPower(0);
            leftMotorBack.setPower(0);
            rightMotorFront.setPower(0);
            rightMotorBack.setPower(0);
            return;
        }
        leftMotorFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftMotorBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotorFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotorBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftMotorFront.setPower(0);
        leftMotorBack.setPower(0);
        rightMotorFront.setPower(0);
        rightMotorBack.setPower(0);
    }
    public void strafeLeft(double inches, double power, double slowerPower) {
        while (opModeIsActive()) {
            initialAngle = getAngle();
            runtime.reset();
            leftMotorFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            leftMotorBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            rightMotorFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            rightMotorBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            leftMotorFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            leftMotorBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            rightMotorFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            rightMotorBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

            int targetStrafe_LLF = (int)(inches * TICKS_PER_STRAFE_LLF * TICKS_MULTIPLIER);
            int targetStrafe_LLB = (int)(inches * TICKS_PER_STRAFE_LLB * TICKS_MULTIPLIER);
            int targetStrafe_LRF = (int)(inches * TICKS_PER_STRAFE_LRF * TICKS_MULTIPLIER);
            int targetStrafe_LRB = (int)(inches * TICKS_PER_STRAFE_LRB * TICKS_MULTIPLIER);

            while ((leftMotorFront.getCurrentPosition() > targetStrafe_LLF * .6) && (rightMotorBack.getCurrentPosition() > targetStrafe_LRB * .6) && (leftMotorBack.getCurrentPosition() < targetStrafe_LLB * .6) && (rightMotorFront.getCurrentPosition() < targetStrafe_LRF * .6) && opModeIsActive() && runtime.milliseconds() < 4000) {
                telemetry.addData("Target Position", targetStrafe_LLF);
                telemetry.addData("Left Motor Front Position", leftMotorFront.getCurrentPosition());
                telemetry.addData("Left Motor Back Position", leftMotorBack.getCurrentPosition());
                telemetry.addData("Right Motor Front Position", rightMotorFront.getCurrentPosition());
                telemetry.addData("Right Motor Back Position", rightMotorBack.getCurrentPosition());
                telemetry.update();
                leftMotorFront.setPower(-power * strafePower_LLF);
                leftMotorBack.setPower(power * strafePower_LLB);
                rightMotorFront.setPower(power * strafePower_LRF);
                rightMotorBack.setPower(-power * strafePower_LRB);

                //New code to adjust in running methods if angle curves off too much. Like strafing. Gonna use for strafing and runTo
                //getAngleDifference gives a positive result of difference between 2 angles
                currentAngle = getAngle();
                angleDifference = getAngleDifference(currentAngle, initialAngle);
                turnTowards = closerSide(currentAngle, initialAngle);
                if (angleDifference > 5 && adjustTime.milliseconds() > 500) {
                    targetStrafe_LLF = targetStrafe_LLF - leftMotorFront.getCurrentPosition();
                    targetStrafe_LLB = targetStrafe_LLB - leftMotorBack.getCurrentPosition();
                    targetStrafe_LRF = targetStrafe_LRF - rightMotorFront.getCurrentPosition();
                    targetStrafe_LRB = targetStrafe_LRB - rightMotorBack.getCurrentPosition();

                    leftMotorFront.setPower(0);
                    leftMotorBack.setPower(0);
                    rightMotorFront.setPower(0);
                    rightMotorBack.setPower(0);
                    turnTo(initialAngle, power, slowerPower);

                    adjustTime.reset();
                    leftMotorFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                    leftMotorBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                    rightMotorFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                    rightMotorBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                    leftMotorFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                    leftMotorBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                    rightMotorFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                    rightMotorBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                }
                //End of the new code
            }
            while ((leftMotorFront.getCurrentPosition() > targetStrafe_LLF) && (rightMotorBack.getCurrentPosition() > targetStrafe_LRB) && (leftMotorBack.getCurrentPosition() < targetStrafe_LLB) && (rightMotorFront.getCurrentPosition() < targetStrafe_LRF) && opModeIsActive() && runtime.milliseconds() < 4000) {
                telemetry.addData("Target Position", targetStrafe_LLF);
                telemetry.addData("Left Motor Front Position", leftMotorFront.getCurrentPosition());
                telemetry.addData("Left Motor Back Position", leftMotorBack.getCurrentPosition());
                telemetry.addData("Right Motor Front Position", rightMotorFront.getCurrentPosition());
                telemetry.addData("Right Motor Back Position", rightMotorBack.getCurrentPosition());
                telemetry.update();
                leftMotorFront.setPower(-slowerPower * strafePower_LLF);
                leftMotorBack.setPower(slowerPower * strafePower_LLB);
                rightMotorFront.setPower(slowerPower * strafePower_LRF);
                rightMotorBack.setPower(-slowerPower * strafePower_LRB);

                //New code to adjust in running methods if angle curves off too much. Like strafing. Gonna use for strafing and runTo
                //getAngleDifference gives a positive result of difference between 2 angles
                currentAngle = getAngle();
                angleDifference = getAngleDifference(currentAngle, initialAngle);
                turnTowards = closerSide(currentAngle, initialAngle);
                if (angleDifference > 5 && adjustTime.milliseconds() > 500) {
                    targetStrafe_LLF = targetStrafe_LLF - leftMotorFront.getCurrentPosition();
                    targetStrafe_LLB = targetStrafe_LLB - leftMotorBack.getCurrentPosition();
                    targetStrafe_LRF = targetStrafe_LRF - rightMotorFront.getCurrentPosition();
                    targetStrafe_LRB = targetStrafe_LRB - rightMotorBack.getCurrentPosition();

                    leftMotorFront.setPower(0);
                    leftMotorBack.setPower(0);
                    rightMotorFront.setPower(0);
                    rightMotorBack.setPower(0);
                    turnTo(initialAngle, power, slowerPower);

                    adjustTime.reset();
                    leftMotorFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                    leftMotorBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                    rightMotorFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                    rightMotorBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                    leftMotorFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                    leftMotorBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                    rightMotorFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                    rightMotorBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                }
                //End of the new code
            }
            leftMotorFront.setPower(0);
            leftMotorBack.setPower(0);
            rightMotorFront.setPower(0);
            rightMotorBack.setPower(0);
            return;
        }
        leftMotorFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftMotorBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotorFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotorBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftMotorFront.setPower(0);
        leftMotorBack.setPower(0);
        rightMotorFront.setPower(0);
        rightMotorBack.setPower(0);
    }
    public void strafeRight(double inches, double power, double slowerPower) {
        while (opModeIsActive()) {
            initialAngle = getAngle();
            runtime.reset();
            leftMotorFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            leftMotorBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            rightMotorFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            rightMotorBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            leftMotorFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            leftMotorBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            rightMotorFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            rightMotorBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

            int targetStrafe_RLF = (int)(inches * TICKS_PER_STRAFE_RLF * TICKS_MULTIPLIER);
            int targetStrafe_RLB = (int)(inches * TICKS_PER_STRAFE_RLB * TICKS_MULTIPLIER);
            int targetStrafe_RRF = (int)(inches * TICKS_PER_STRAFE_RRF * TICKS_MULTIPLIER);
            int targetStrafe_RRB = (int)(inches * TICKS_PER_STRAFE_RRB * TICKS_MULTIPLIER);

            while ((leftMotorFront.getCurrentPosition() < targetStrafe_RLF * .6) && (rightMotorBack.getCurrentPosition() < targetStrafe_RRB * .6) && (leftMotorBack.getCurrentPosition() > targetStrafe_RLB * .6) && (rightMotorFront.getCurrentPosition() > targetStrafe_RRF * .6) && opModeIsActive() && runtime.milliseconds() < 4000) {
                telemetry.addData("Target Position", targetStrafe_RLF);
                telemetry.addData("Left Motor Front Position", leftMotorFront.getCurrentPosition());
                telemetry.addData("Left Motor Back Position", leftMotorBack.getCurrentPosition());
                telemetry.addData("Right Motor Front Position", rightMotorFront.getCurrentPosition());
                telemetry.addData("Right Motor Back Position", rightMotorBack.getCurrentPosition());
                telemetry.update();
                leftMotorFront.setPower(power * strafePower_RLF);
                leftMotorBack.setPower(-power * strafePower_RLB);
                rightMotorFront.setPower(-power * strafePower_RRF);
                rightMotorBack.setPower(power * strafePower_RRB);

                //New code to adjust in running methods if angle curves off too much. Like strafing. Gonna use for strafing and runTo
                //getAngleDifference gives a positive result of difference between 2 angles
                currentAngle = getAngle();
                angleDifference = getAngleDifference(currentAngle, initialAngle);
                turnTowards = closerSide(currentAngle, initialAngle);
                if (angleDifference > 5 && adjustTime.milliseconds() > 500) {
                    targetStrafe_RLF = targetStrafe_RLF - leftMotorFront.getCurrentPosition();
                    targetStrafe_RLB = targetStrafe_RLB - leftMotorBack.getCurrentPosition();
                    targetStrafe_RRF = targetStrafe_RRF - rightMotorFront.getCurrentPosition();
                    targetStrafe_RRB = targetStrafe_RRB - rightMotorBack.getCurrentPosition();

                    leftMotorFront.setPower(0);
                    leftMotorBack.setPower(0);
                    rightMotorFront.setPower(0);
                    rightMotorBack.setPower(0);
                    turnTo(initialAngle, power, slowerPower);

                    adjustTime.reset();
                    leftMotorFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                    leftMotorBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                    rightMotorFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                    rightMotorBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                    leftMotorFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                    leftMotorBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                    rightMotorFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                    rightMotorBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                }
                //End of the new code
            }
            while ((leftMotorFront.getCurrentPosition() < targetStrafe_RLF) && (rightMotorBack.getCurrentPosition() < targetStrafe_RRB) && (leftMotorBack.getCurrentPosition() > targetStrafe_RLB) && (rightMotorFront.getCurrentPosition() > targetStrafe_RRF) && opModeIsActive() && runtime.milliseconds() < 4000) {
                telemetry.addData("Target Position", targetStrafe_RLF);
                telemetry.addData("Left Motor Front Position", leftMotorFront.getCurrentPosition());
                telemetry.addData("Left Motor Back Position", leftMotorBack.getCurrentPosition());
                telemetry.addData("Right Motor Front Position", rightMotorFront.getCurrentPosition());
                telemetry.addData("Right Motor Back Position", rightMotorBack.getCurrentPosition());
                telemetry.update();
                leftMotorFront.setPower(slowerPower * strafePower_RLF);
                leftMotorBack.setPower(-slowerPower * strafePower_RLB);
                rightMotorFront.setPower(-slowerPower * strafePower_RRF);
                rightMotorBack.setPower(slowerPower * strafePower_LLF);

                //New code to adjust in running methods if angle curves off too much. Like strafing. Gonna use for strafing and runTo
                //getAngleDifference gives a positive result of difference between 2 angles
                currentAngle = getAngle();
                angleDifference = getAngleDifference(currentAngle, initialAngle);
                turnTowards = closerSide(currentAngle, initialAngle);
                if (angleDifference > 5 && adjustTime.milliseconds() > 500) {
                    targetStrafe_RLF = targetStrafe_RLF - leftMotorFront.getCurrentPosition();
                    targetStrafe_RLB = targetStrafe_RLB - leftMotorBack.getCurrentPosition();
                    targetStrafe_RRF = targetStrafe_RRF - rightMotorFront.getCurrentPosition();
                    targetStrafe_RRB = targetStrafe_RRB - rightMotorBack.getCurrentPosition();

                    leftMotorFront.setPower(0);
                    leftMotorBack.setPower(0);
                    rightMotorFront.setPower(0);
                    rightMotorBack.setPower(0);
                    turnTo(initialAngle, power, slowerPower);

                    adjustTime.reset();
                    leftMotorFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                    leftMotorBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                    rightMotorFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                    rightMotorBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                    leftMotorFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                    leftMotorBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                    rightMotorFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                    rightMotorBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                }
                //End of the new code
            }
            leftMotorFront.setPower(0);
            leftMotorBack.setPower(0);
            rightMotorFront.setPower(0);
            rightMotorBack.setPower(0);
            return;
        }
        leftMotorFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftMotorBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotorFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotorBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftMotorFront.setPower(0);
        leftMotorBack.setPower(0);
        rightMotorFront.setPower(0);
        rightMotorBack.setPower(0);
    }

    //Method to find angle, don't use. Just use getAngle
    public double formatAngle(AngleUnit angleUnit, double angle) {
        return AngleUnit.DEGREES.fromUnit(angleUnit, angle);
    }
    //Returns if it's yellow or not. if it is, it returns "Yellow"
    public String senseColor() {
        color = "Yellow";
        green = 0;
        while (green == 0 && runtime.milliseconds() < 1000) {
            green = colorSensor.green();
        }
        //Wait for a better Read
        sleep(500);
        red = colorSensor.red();
        blue = colorSensor.blue();
        green = colorSensor.green();
        if (green < 25) {
            if (Math.abs(red - green) > 2) {
                color = "Black";
            }
        }
        else {
            if ((green >= red) && (green > blue)) {
                color = "Black";
            }
        }
        //Go Back to original position
        return color;
    }
    //Uses distance sensor, but we don't have one yet, on the robot
    public double getDistance() {
        mmAway = 0;
        runtime.reset();
        while (mmAway == 0 && runtime.milliseconds() < 1500) {
            mmAway = distance.getDistance(DistanceUnit.MM);
        }
        return mmAway;
    }
    //Method that returns current angle relative to start
    //(left goes 0-180, right goes -180 to 0)
    public double getAngle() {
        heading = 0;
        runtime.reset();
        while (runtime.milliseconds() < 1500 && heading == 0) {
            angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
            heading = formatAngle(angles.angleUnit, angles.firstAngle);
        }
        return heading;
    }
    public void runUntil(double mm, double power) {
        leftMotorFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftMotorBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightMotorFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightMotorBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        runtime.reset();
        mmAway = getDistance();
        power = Math.abs(power);
        if (mmAway < mm || mmAway > 800) {
            return;
        }
        mmTraveling = mmAway - mm;
        totalDistance = mmTraveling;
        while (mmTraveling > 300 && runtime.milliseconds() < 3000 && totalDistance * 1.4 >= mmTraveling) {
            mmAway = getDistance();
            mmTraveling = mmAway - mm;
            leftMotorFront.setPower(power);
            leftMotorBack.setPower(power);
            rightMotorFront.setPower(power);
            rightMotorBack.setPower(power);
        }
        leftMotorFront.setPower(0);
        leftMotorBack.setPower(0);
        rightMotorFront.setPower(0);
        rightMotorBack.setPower(0);
        mmAway = getDistance();
        if (mmAway < mm || totalDistance < mmAway) {
            return;
        }
        mmTraveling = mmAway - mm;
        runTo(((mmTraveling * .9) / 25.4), power * .8, power * .5);
    }
    public void resetArm() {
        armServo.setPosition(1);
    }
    public void pickUpBlock() {
        armServo.setPosition(.2);
        if (firstPick) {
            sleep(500);
            armServo.setPosition(.2);
            firstPick = false;
        }
        sleep(600);
        blockPickedUp = blockIsGrabbed();
        if (!blockPickedUp && blockFails < 3 && !failAttempted) {
            armServo.setPosition(1);
            while (armServo.getPosition() < .9){}
            runUntil(80, slowPower);
            runTo(3, slowPower * .7, slowPower * .5);
            blockFails += 1;
            failAttempted = true;
            pickUpBlock();
        }
        if (failAttempted) {
            runTo(-4.5, allPower, slowPower);
        }
        failAttempted = false;
    }
    public void dropBlock() {
        armServo.setPosition(.5);
    }
    public void spinnyBoyDown() {
        spinnyBoy1.setPosition(.29);
        spinnyBoy2.setPosition(.29);
        sleep(500);
    }
    public void spinnyBoyUp() {
        spinnyBoy1.setPosition(.7);
        spinnyBoy2.setPosition(.7);
        sleep(500);
    }
    //Returns minimum difference between 2 angles. Mainly current and target Angle
    public double getAngleDifference(double angle1, double angle2) {
        double tempAngle = angle1;
        double angleCheck;
        //Set angle1 as smaller
        if (angle1 > angle2) {
            angle1 = angle2;
            angle2 = tempAngle;
        }
        angleCheck = angle2 - angle1;
        if (angleCheck > 180) {
            angleCheck = 360 - angleCheck;
        }
        angleCheck = Math.abs(angleCheck);
        return angleCheck;
    }
    //Returns side that would be closer to turn towards
    public String closerSide (double currentAngle, double targetAngle) {
        side = 1;
        if (currentAngle > targetAngle) {
            side = 2;
        }
        if (side == 1) {
            if (targetAngle - currentAngle < 180) {
                return "Left";
            }
            else {
                return "Right";
            }
        }
        else {
            if (currentAngle - targetAngle < 180) {
                return "Right";
            }
            else {
                return "Left";
            }
        }
    }

    public void startBlock(boolean red) {
        runTo(20, allPower, slowPower);
        runUntil(200, slowPower * .9);
        runTo(5.9, allPower * .7, slowPower);
        turnTo(0, allPower, slowPower);
        if (red) {
            while (blockNumber < 2){
                color = senseColor();
                if (color == "Yellow") {
                    blockNumber += 1;
                    strafeLeft(8.5, allPower, slowPower);
                    turnTo(0, allPower, slowPower);
                }
                else {
                    break;
                }
            }
            runTo(3, slowPower * .7, slowPower * .7);
            pickUpBlock();
            runTo(-5, allPower, slowPower);
            turnRight(80, allPower, slowPower);
            turnTo(-90, allPower * .8, slowPower);
        }
        else {
            while (blockNumber < 2){
                color = senseColor();
                if (color == "Yellow") {
                    blockNumber += 1;
                    strafeRight(8.5, allPower, slowPower);
                }
                else {
                    break;
                }
            }
            runTo(3, slowPower * .7, slowPower * .7);
            pickUpBlock();
            runTo(-5, allPower, slowPower);
            turnLeft(80, allPower, slowPower);
            turnTo(90, allPower * .8, slowPower);
            turnLeft(5, allPower, slowPower);
        }
    }
    public void moveFoundation(boolean red) {
        spinnyBoyDown();
        //Bring block to corner
        runTo(-45, allPower * 1.1, slowPower * 1.4);
        if (red) {
            turnRight(80, 1, .9);
            turnTo(-100, .9, .8);
        }
        else {
            turnLeft(80, 1, .9);
            turnTo(100, .9, .8);
        }
        spinnyBoyUp();
    }
    //Superior accurate turn. Faster but haven't been tested
    public void turnTo(double targetAngle, double allPower, double slowerPower) {
        runtime.reset();
        sleep(200);
        angle = getAngle();
        angleDifference = getAngleDifference(targetAngle, angle);
        turnTowards = closerSide(angle, targetAngle);
        double angleDifferenceInitial = angleDifference;
        double angleInitial = getAngle();
        double changeInTurn = getAngleDifference(angle, angleInitial);
        if (angleDifference < 5) {
            return;
        }
        if (turnTowards == "Left") {
            while (angleDifference > 15 && angleDifference < 130 && runtime.milliseconds() < 3000) {
                turnLeft(20, allPower, slowerPower);
                sleep(200);
                angle = getAngle();
                angleDifference = getAngleDifference(targetAngle, angle);
                turnTowards = closerSide(angle, targetAngle);
                changeInTurn = getAngleDifference(angle, angleInitial);
                if ((changeInTurn > angleDifferenceInitial && angleDifferenceInitial > 10) || turnTowards == "Right") {
                    break;
                }
            }
        }
        else {
            while (angleDifference > 15 && angleDifference < 130 && runtime.milliseconds() < 3000) {
                turnRight(20, allPower, slowerPower);
                angle = getAngle();
                angleDifference = getAngleDifference(targetAngle, angle);
                turnTowards = closerSide(angle, targetAngle);
                changeInTurn = getAngleDifference(angle, angleInitial);
                if ((changeInTurn > angleDifferenceInitial && angleDifferenceInitial > 10) || turnTowards == "Left") {
                    break;
                }
            }
        }
        angle = getAngle();
        angleDifference = getAngleDifference(angle, targetAngle);
        turnTowards = closerSide(angle, targetAngle);
        if (angleDifference > 130) {
            return;
        }
        if (turnTowards == "Left") {
            turnLeft(angleDifference, allPower, slowerPower);
        }
        else {
            turnRight(angleDifference, allPower, slowerPower);
        }
        return;
    }
    //parkServo methods. Adjust the values for the positions later
    public void parkArmOut(){
        parkServo.setPosition(.5);
    }
    public void parkArmIn(){
        parkServo.setPosition(.4);
    }
    public boolean blockIsGrabbed() {
        mmAway = getDistance();
        if (mmAway < blockDistance) {
            return true;
        }
        else {
            return false;
        }
    }
    public void raiseAndRun(int height, double inches, double power, double slowerPower) {
        while (opModeIsActive()) {
            runtime.reset();
            leftMotorFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            leftMotorBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            rightMotorFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            rightMotorBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            leftMotorFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            leftMotorBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            rightMotorFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            rightMotorBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

            driveTargetPosition = (int)(inches * TICKS_PER_INCH * TICKS_MULTIPLIER);
            absTarget = Math.abs(driveTargetPosition);
            absLeftPosition = Math.abs(leftMotorBack.getCurrentPosition());
            absRightPosition = Math.abs(rightMotorBack.getCurrentPosition());
            verticalMultiplier = 1;
            runMultiplier = 1;

            if (height == 0) {
                verticalTargetPosition = -10;
            }
            else if (height == 1) {
                verticalTargetPosition = 100;
            }
            else if (height == 2) {
                verticalTargetPosition = 180;
            }
            else {
                verticalTargetPosition = 210;
            }
            if (inches < 0) {
                runMultiplier *= -1;
            }
            if (verticalTargetPosition < vertical1.getCurrentPosition()) {
                verticalMultiplier = -.8;
                telemetry.addData("Vertical Down: ", "Yes");
                telemetry.update();
            }
            if (verticalMultiplier > 0) {
                while (runtime.milliseconds() < 4000 && absLeftPosition < absTarget * .6 && absRightPosition < absTarget * .6 && verticalTargetPosition > vertical1.getCurrentPosition()) {
                    telemetry.addData("Left Back: ", absLeftPosition);
                    telemetry.addData("Right Back: ", absRightPosition);
                    telemetry.addData("Vertical1 ", vertical1.getCurrentPosition());
                    telemetry.addData("Vertical2 ", vertical2.getCurrentPosition());
                    telemetry.addData("Target Position", absTarget);
                    telemetry.update();

                    absTarget =  (Math.abs(driveTargetPosition) * 5) / 2;
                    absLeftPosition = Math.abs(leftMotorBack.getCurrentPosition());
                    absRightPosition = Math.abs(rightMotorBack.getCurrentPosition());

                    vertical1.setPower(verticalPower * verticalMultiplier);
                    vertical2.setPower(verticalPower * verticalMultiplier);
                    leftMotorBack.setPower(power * runMultiplier);
                    leftMotorFront.setPower(power * runMultiplier);
                    rightMotorBack.setPower(power * runMultiplier);
                    rightMotorFront.setPower(power * runMultiplier);
                }
                if (verticalTargetPosition <= vertical1.getCurrentPosition()) {
                    vertical1.setPower(0);
                    vertical2.setPower(0);
                    remainingTicks = Math.abs(Math.abs(driveTargetPosition) - Math.abs(((leftMotorBack.getCurrentPosition() + leftMotorFront.getCurrentPosition() + rightMotorBack.getCurrentPosition() + rightMotorFront.getCurrentPosition()) / 4)));
                    remainingInches = (remainingTicks)/(TICKS_PER_INCH * TICKS_MULTIPLIER);
                    runTo(remainingInches * runMultiplier, power, slowerPower);
                    return;
                }
                if (runtime.milliseconds() > 4000) {
                    return;
                }
                else if (absLeftPosition < absTarget * .6 && absRightPosition < absTarget * .6){
                    runtime.reset();
                    while (runtime.milliseconds() < 4000 && absLeftPosition < absTarget && absRightPosition < absTarget && verticalTargetPosition > vertical1.getCurrentPosition()) {
                        telemetry.addData("Left Back: ", absLeftPosition);
                        telemetry.addData("Right Back: ", absRightPosition);
                        telemetry.addData("Vertical1 ", vertical1.getCurrentPosition());
                        telemetry.addData("Vertical2 ", vertical2.getCurrentPosition());
                        telemetry.addData("Target Position", absTarget);
                        telemetry.update();

                        absTarget = Math.abs(driveTargetPosition);
                        absLeftPosition = Math.abs(leftMotorBack.getCurrentPosition());
                        absRightPosition = Math.abs(rightMotorBack.getCurrentPosition());

                        leftMotorFront.setPower(slowerPower * runMultiplier);
                        leftMotorBack.setPower(slowerPower * runMultiplier);
                        rightMotorBack.setPower(slowerPower * runMultiplier);
                        rightMotorFront.setPower(slowerPower * runMultiplier);
                    }
                    if (verticalTargetPosition <= vertical1.getCurrentPosition()) {
                        vertical1.setPower(0);
                        vertical2.setPower(0);
                        remainingTicks = Math.abs(Math.abs(driveTargetPosition) - Math.abs(((leftMotorBack.getCurrentPosition() + leftMotorFront.getCurrentPosition() + rightMotorBack.getCurrentPosition() + rightMotorFront.getCurrentPosition()) / 4)));
                        runTo(remainingInches * runMultiplier, slowerPower, slowerPower * .9);
                        return;
                    }
                    else {
                        leftMotorBack.setPower(0);
                        leftMotorFront.setPower(0);
                        rightMotorBack.setPower(0);
                        rightMotorFront.setPower(0);
                        while (verticalTargetPosition > vertical1.getCurrentPosition()) {
                            telemetry.addData("Vertical1 ", vertical1.getCurrentPosition());
                            telemetry.addData("Vertical2 ", vertical2.getCurrentPosition());
                            telemetry.update();
                            vertical1.setPower(verticalPower * verticalMultiplier * .8);
                            vertical2.setPower(verticalPower * verticalMultiplier * .8);
                        }
                        vertical1.setPower(0);
                        vertical2.setPower(0);
                    }
                }
            }
            else {
                while (runtime.milliseconds() < 4000 && absLeftPosition < absTarget * .6 && absRightPosition < absTarget * .6 && verticalTargetPosition < vertical1.getCurrentPosition()) {
                    telemetry.addData("Left Back: ", absLeftPosition);
                    telemetry.addData("Right Back: ", absRightPosition);
                    telemetry.addData("Vertical1 ", vertical1.getCurrentPosition());
                    telemetry.addData("Vertical2 ", vertical2.getCurrentPosition());
                    telemetry.addData("Target Position", absTarget);
                    telemetry.update();

                    absTarget =  (Math.abs(driveTargetPosition) * 5) / 2;
                    absLeftPosition = Math.abs(leftMotorBack.getCurrentPosition());
                    absRightPosition = Math.abs(rightMotorBack.getCurrentPosition());

                    vertical1.setPower(verticalPower * verticalMultiplier);
                    vertical2.setPower(verticalPower * verticalMultiplier);
                    leftMotorBack.setPower(power * runMultiplier);
                    leftMotorFront.setPower(power * runMultiplier);
                    rightMotorBack.setPower(power * runMultiplier);
                    rightMotorFront.setPower(power * runMultiplier);
                }
                if (verticalTargetPosition <= vertical1.getCurrentPosition()) {
                    vertical1.setPower(0);
                    vertical2.setPower(0);
                    remainingTicks = Math.abs(Math.abs(driveTargetPosition) - Math.abs(((leftMotorBack.getCurrentPosition() + leftMotorFront.getCurrentPosition() + rightMotorBack.getCurrentPosition() + rightMotorFront.getCurrentPosition()) / 4)));
                    remainingInches = (remainingTicks)/(TICKS_PER_INCH * TICKS_MULTIPLIER);
                    runTo(remainingInches * runMultiplier, power, slowerPower);
                    return;
                }
                if (runtime.milliseconds() > 4000) {
                    return;
                }
                else if (absLeftPosition < absTarget * .6 && absRightPosition < absTarget * .6){
                    runtime.reset();
                    while (runtime.milliseconds() < 4000 && absLeftPosition < absTarget && absRightPosition < absTarget && verticalTargetPosition < vertical1.getCurrentPosition()) {
                        telemetry.addData("Left Back: ", absLeftPosition);
                        telemetry.addData("Right Back: ", absRightPosition);
                        telemetry.addData("Vertical1 ", vertical1.getCurrentPosition());
                        telemetry.addData("Vertical2 ", vertical2.getCurrentPosition());
                        telemetry.addData("Target Position", absTarget);
                        telemetry.update();

                        absTarget = Math.abs(driveTargetPosition);
                        absLeftPosition = Math.abs(leftMotorBack.getCurrentPosition());
                        absRightPosition = Math.abs(rightMotorBack.getCurrentPosition());

                        leftMotorFront.setPower(slowerPower * runMultiplier);
                        leftMotorBack.setPower(slowerPower * runMultiplier);
                        rightMotorBack.setPower(slowerPower * runMultiplier);
                        rightMotorFront.setPower(slowerPower * runMultiplier);
                    }
                    if (verticalTargetPosition >= vertical1.getCurrentPosition()) {
                        vertical1.setPower(0);
                        vertical2.setPower(0);
                        remainingTicks = Math.abs(Math.abs(driveTargetPosition) - Math.abs(((leftMotorBack.getCurrentPosition() + leftMotorFront.getCurrentPosition() + rightMotorBack.getCurrentPosition() + rightMotorFront.getCurrentPosition()) / 4)));
                        runTo(remainingInches * runMultiplier, slowerPower, slowerPower * .9);
                        return;
                    }
                    else {
                        leftMotorBack.setPower(0);
                        leftMotorFront.setPower(0);
                        rightMotorBack.setPower(0);
                        rightMotorFront.setPower(0);
                        while (verticalTargetPosition < vertical1.getCurrentPosition()) {
                            telemetry.addData("Vertical1 ", vertical1.getCurrentPosition());
                            telemetry.addData("Vertical2 ", vertical2.getCurrentPosition());
                            telemetry.update();
                            vertical1.setPower(verticalPower * verticalMultiplier * .8);
                            vertical2.setPower(verticalPower * verticalMultiplier * .8);
                        }
                        vertical1.setPower(0);
                        vertical2.setPower(0);
                    }
                }
            }
            leftMotorBack.setPower(0);
            leftMotorFront.setPower(0);
            rightMotorBack.setPower(0);
            rightMotorFront.setPower(0);
            vertical1.setPower(0);
            vertical2.setPower(0);
        }
        leftMotorFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftMotorBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotorFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotorBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        vertical1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        vertical2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftMotorBack.setPower(0);
        leftMotorFront.setPower(0);
        rightMotorBack.setPower(0);
        rightMotorFront.setPower(0);
        vertical1.setPower(0);
        vertical2.setPower(0);
    }
}
