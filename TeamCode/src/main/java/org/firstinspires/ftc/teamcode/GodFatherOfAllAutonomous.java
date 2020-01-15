package org.firstinspires.ftc.teamcode;


import com.qualcomm.hardware.bosch.BNO055IMU;
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

    //All Power for autonomous running
    public double allPower = .65;
    public double slowPower = .35;

    //Color Sensor
    public ColorSensor colorSensor;
    public String color = null;
    public int blue = 0;
    public int red = 0;
    public int green = 0;

    //Distance Sensor
    public DistanceSensor distance = null;
    public double mmAway;
    public double inchesAway;
    public double inchesTraveling;
    public double inches;
    public double totalDistance;

    //Ticks
    //Ticks for forward and backwards
    public final double TICKS_PER_INCH = 55;
    //Ticks for Turning
    public final double TICKS_PER_DEGREE_LLF = -11.13;
    public final double TICKS_PER_DEGREE_LLB = -9.84;
    public final double TICKS_PER_DEGREE_LRF = 11.64;
    public final double TICKS_PER_DEGREE_LRB = 9.59;
    public final double TICKS_PER_DEGREE_RLF = 11.63;
    public final double TICKS_PER_DEGREE_RLB = 10.74;
    public final double TICKS_PER_DEGREE_RRF = -11.7;
    public final double TICKS_PER_DEGREE_RRB = -10.81;
    //Ticks for Strafing
    //First Initial = side of strafing
    //Second Initial = side of Robot the motor is on
    //Third Initial = Front or back of robot
    public final double TICKS_PER_STRAFE_LLF = -45.9;
    public final double TICKS_PER_STRAFE_LLB = 47.9;
    public final double TICKS_PER_STRAFE_LRF = 49.7;
    public final double TICKS_PER_STRAFE_LRB = -41.1;
    public final double TICKS_PER_STRAFE_RLF = 44.2;
    public final double TICKS_PER_STRAFE_RLB = -47.2;
    public final double TICKS_PER_STRAFE_RRF = -44.2;
    public final double TICKS_PER_STRAFE_RRB = 45.6;
    public final double TICKS_MULTIPLIER = 20 / 20;
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

        colorSensor = hardwareMap.colorSensor.get("color_sensor");
        distance = hardwareMap.get(DistanceSensor.class, "distance");

        leftMotorFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftMotorBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightMotorFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightMotorBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibration sample opmode
        parameters.loggingEnabled = true;
        parameters.loggingTag = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();

        imu = hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);

        leftMotorFront.setDirection(DcMotorSimple.Direction.REVERSE);
        leftMotorBack.setDirection(DcMotorSimple.Direction.REVERSE);
        spinnyBoy1.setDirection(Servo.Direction.REVERSE);

        colorSensor.enableLed(false);

        spinnyBoyUp();

        vertical1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        vertical2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        vertical1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        vertical2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        //there's nothing that talks about foundation anywhere else. fix later
        /*if (foundation == false) {
            resetArm();
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
                if (angleDifference > 6) {
                    if (turnTowards == "Left") {
                        turnLeft(angleDifference, power, slowerPower);
                    }
                    else {
                        turnRight(angleDifference, power, slowerPower);
                    }
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
                if (angleDifference > 6) {
                    if (turnTowards == "Left") {
                        turnLeft(angleDifference, power, slowerPower);
                    }
                    else {
                        turnRight(angleDifference, power, slowerPower);
                    }
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
    }
    //Turns left a certain number of degrees.
    public void turnLeft(double degrees, double power, double slowerPower) {
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
    }
    //Turns right a certain number of degrees.
    public void turnRight(double degrees, double power, double slowerPower) {
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
    }
    public void strafeLeft(double inches, double power, double slowerPower) {
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
            if (angleDifference > 6) {
                if (turnTowards == "Left") {
                    turnLeft(angleDifference, power, slowerPower);
                }
                else {
                    turnRight(angleDifference, power, slowerPower);
                }
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
            if (angleDifference > 6) {
                if (turnTowards == "Left") {
                    turnLeft(angleDifference, power, slowerPower);
                }
                else {
                    turnRight(angleDifference, power, slowerPower);
                }
            }
            //End of the new code
        }
        leftMotorFront.setPower(0);
        leftMotorBack.setPower(0);
        rightMotorFront.setPower(0);
        rightMotorBack.setPower(0);
    }
    public void strafeRight(double inches, double power, double slowerPower) {
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
            if (angleDifference > 6) {
                if (turnTowards == "Left") {
                    turnLeft(angleDifference, power, slowerPower);
                }
                else {
                    turnRight(angleDifference, power, slowerPower);
                }
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
            if (angleDifference > 6) {
                if (turnTowards == "Left") {
                    turnLeft(angleDifference, power, slowerPower);
                }
                else {
                    turnRight(angleDifference, power, slowerPower);
                }
            }
            //End of the new code
        }
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
        mmAway = distance.getDistance(DistanceUnit.MM);
        return mmAway;
    }
    //Method that returns current angle relative to start
    //(left goes 0-180, right goes -180 to 0)
    public double getAngle() {
        heading = 0;
        runtime.reset();
        while (runtime.milliseconds() < 1000 && heading == 0) {
            angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
            heading = formatAngle(angles.angleUnit, angles.firstAngle);
        }
        return heading;
    }
    //Turns left using gyroscope and angle. Turns left towards angle based on initial position
    public void accurateTurnLeft(double targetAngle, double power) {
        side = 0;
        degrees = 0;
        angle = getAngle();
        runtime.reset();
        if (targetAngle > angle) {
            degrees = targetAngle - angle;
            side = 1;
        }
        else if (targetAngle < angle) {
            degrees = 360 - (angle - targetAngle);
            side = 2;
        }
        while (degrees > 20 && runtime.milliseconds() < 5000) {
            turnLeft(10, power, slowPower);
            sleep(500);
            angle = getAngle();
            if (side == 1) {
                if (targetAngle > angle) {
                    degrees = targetAngle - angle;
                }
                else {
                    return;
                }
            }
            else if (side == 2) {
                if (targetAngle < angle) {
                    degrees = 360 - (angle - targetAngle);
                }
                else {
                    return;
                }
            }
        }
        if (Math.abs(degrees) < 3) {
            return;
        }
        turnLeft(degrees, power, slowPower);
        return;
    }
    //Turns right using gyroscope and angle. Turns right towards angle based on initial position
    public void accurateTurnRight(double targetAngle, double power) {
        sleep(500);
        degrees = 0;
        side = 0;
        angle = getAngle();
        runtime.reset();
        if (targetAngle < angle) {
            degrees = angle - targetAngle;
            side = 1;
        }
        else if (targetAngle > angle) {
            degrees = 360 - (targetAngle - angle);
            side = 2;
        }
        while (degrees > 20 && runtime.milliseconds() < 5000) {
            turnRight(10, power, slowPower);
            sleep(500);
            angle = getAngle();
            if (side == 1) {
                degrees = angle - targetAngle;
            }
            else if (side == 2) {
                degrees = 360 - (targetAngle - angle);
            }
        }
        angle = getAngle();
        if (targetAngle < angle) {
            degrees = angle - targetAngle;
            side = 1;
        }
        else if (targetAngle > angle) {
            degrees = 360 - (targetAngle - angle);
            side = 2;
        }
        if (Math.abs(degrees) < 3) {
            return;
        }
        turnRight(degrees, power, slowPower);
        return;
    }
    public void runUntil(double inches, double power) {

        leftMotorFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftMotorBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightMotorFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightMotorBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        sleep(600);
        runtime.reset();
        mmAway= getDistance();
        inchesAway = mmAway / 25.4;
        //inches = mm / 25.4;
        power = Math.abs(power);
        if (inchesAway < inches || mmAway > 500) {
            return;
        }
        inchesTraveling = inchesAway - inches;
        totalDistance = inchesTraveling;
        while (inchesTraveling > 2 && runtime.milliseconds() < 3000 && totalDistance * 1.4 >= inchesTraveling) {
            mmAway = getDistance();
            inchesAway = mmAway / 25.4;
            inchesTraveling = inchesAway - inches;
            leftMotorFront.setPower(power);
            leftMotorBack.setPower(power);
            rightMotorFront.setPower(power);
            rightMotorBack.setPower(power);
        }
        leftMotorFront.setPower(0);
        leftMotorBack.setPower(0);
        rightMotorFront.setPower(0);
        rightMotorBack.setPower(0);
        mmAway= getDistance();
        inchesAway = mmAway / 25.4;
        if (inchesAway < inches || totalDistance < inchesAway) {
            return;
        }
        sleep(300);
        inchesTraveling = inchesAway - inches;
        runTo(inchesTraveling * .8, power * .8, power * .6);
    }
    public void resetArm() {
        armServo.setPosition(1);
    }
    public void pickUpBlock() {
        armServo.setPosition(.2);
        sleep(900);
    }
    public void dropBlock() {
        armServo.setPosition(.35);
    }
    public void spinnyBoyDown() {
        spinnyBoy1.setPosition(.29);
        spinnyBoy2.setPosition(.29);
    }
    public void spinnyBoyUp() {
        spinnyBoy1.setPosition(.7);
        spinnyBoy2.setPosition(.7);
    }
    //Method to set robot to angle it was initially at
    public void adjustToInitialAngle() {
        runtime.reset();
        sleep(1000);
        angle = getAngle();
        if (Math.abs(angle) < 5) {
            return;
        }
        else if (angle < 0) {
            side = 1;
        }
        else {
            side = 2;
        }

        if (side == 1) {
            while (angle < -30 && runtime.milliseconds() < 3000) {
                turnLeft(13, allPower, slowPower);
                sleep(600);
                angle = getAngle();
                if (angle > 0) {
                    break;
                }
            }
            sleep(100);
            angle = Math.abs(getAngle());
            if (angle < 5 || angle > 30) {
                return;
            }
            turnLeft(angle, allPower, slowPower);
            return;
        }
        else if (side == 2) {
            while (angle > 30 && runtime.milliseconds() < 3000) {
                turnRight(13, allPower, slowPower);
                sleep(600);
                angle = getAngle();
                if (angle < 0) {
                    break;
                }
            }
            sleep(100);
            angle = Math.abs(getAngle());
            if (angle < 5 || angle > 30) {
                return;
            }
            turnRight(angle, allPower, slowPower);
            return;
        }
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
        return angleCheck;
    }
    //Returns side that would be closer to turn towards
    public String closerSide (double currentAngle, double targetAngle) {
        if (currentAngle > targetAngle) {
            currentAngle *= -1;
            targetAngle *= -1;
        }
        if (targetAngle - currentAngle < 180) {
            return "Right";
        }
        else {
            return "Left";
        }
    }
    //Superior accurate turn. Faster but haven't been tested
    public void turnTo(double targetAngle, double allPower, double slowerPower) {
        sleep(500);
        runtime.reset();
        angle = getAngle();
        angleDifference = getAngleDifference(targetAngle, angle);
        turnTowards = closerSide(angle, targetAngle);
        double angleDifferenceInitial = angleDifference;
        if (turnTowards == "Left") {
            while (angleDifference > 15 && runtime.milliseconds() < 3000) {
                turnLeft(20, allPower, slowerPower);
                angle = getAngle();
                angleDifference = getAngleDifference(targetAngle, angle);
                turnTowards = closerSide(angle, targetAngle);
                if ((angleDifference > angleDifferenceInitial && angleDifferenceInitial > 30) || turnTowards == "Right") {
                    turnRight(angleDifference, allPower, slowerPower);
                    return;
                }
            }
            sleep(500);
            angle = getAngle();
            angleDifference = getAngleDifference(angle, targetAngle);
            turnTowards = closerSide(angle, targetAngle);
            if (turnTowards == "Left") {
                turnLeft(angleDifference, allPower, slowerPower);
            }
            else {
                turnRight(angleDifference, allPower, slowerPower);
            }
            return;
        }
        else {
            while (angleDifference > 15 && runtime.milliseconds() < 3000) {
                turnRight(20, allPower, slowerPower);
                angle = getAngle();
                angleDifference = getAngleDifference(targetAngle, angle);
                turnTowards = closerSide(angle, targetAngle);
                if (angleDifference > angleDifferenceInitial || turnTowards == "Left") {
                    turnLeft(angleDifference, allPower, slowerPower);
                    return;
                }
            }
            sleep(500);
            angle = getAngle();
            angleDifference = getAngleDifference(angle, targetAngle);
            turnTowards = closerSide(angle, targetAngle);
            if (turnTowards == "Left") {
                turnLeft(angleDifference, allPower, slowerPower);
            }
            else {
                turnRight(angleDifference, allPower, slowerPower);
            }
            return;
        }
    }
}