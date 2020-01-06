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

    //All Power for autonomous running
    //increasing all power b/c slower motors/drive system. originally .65
    public double allPower = .8;
    public double slowPower = .5;

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
    public final float TICKS_PER_INCH = 44.5092593F;
    //Ticks for Turning
    public final float TICKS_PER_DEGREE_LLF = 11.5F;
    public final float TICKS_PER_DEGREE_LLB = 11.466666667F;
    public final float TICKS_PER_DEGREE_LRF = -12.12222222F;
    public final float TICKS_PER_DEGREE_LRB = -12.1F;
    public final float TICKS_PER_DEGREE_RLF= -11.66666667F;
    public final float TICKS_PER_DEGREE_RLB = -12.7444444F;
    public final float TICKS_PER_DEGREE_RRF = 11.63333333F;
    public final float TICKS_PER_DEGREE_RRB = 11.12222222F;
    //Ticks for Strafing
    public final float TICKS_PER_STRAFE_LLF = 48.2978723F;
    public final float TICKS_PER_STRAFE_LLB = -56.8510638F;
    public final float TICKS_PER_STRAFE_LRF = -56.1702128F;
    public final float TICKS_PER_STRAFE_LRB = 50.5106383F;
    public final float TICKS_PER_STRAFE_RLF = -45.9583333F;
    public final float TICKS_PER_STRAFE_RLB = 51.875F;
    public final float TICKS_PER_STRAFE_RRF = 56.5F;
    public final float TICKS_PER_STRAFE_RRB = -59.3333333F;
    public final float TICKS_MULTIPLIER = 20F / 22F;
    public float strafePower_LLF = Math.abs(48 / TICKS_PER_STRAFE_LLF);
    public float strafePower_LLB = Math.abs(48 / TICKS_PER_STRAFE_LLB);
    public float strafePower_LRF = Math.abs(48 / TICKS_PER_STRAFE_LRF);
    public float strafePower_LRB = Math.abs(48 / TICKS_PER_STRAFE_LRB);
    public float strafePower_RLF = Math.abs(45 / TICKS_PER_STRAFE_RLF);
    public float strafePower_RLB = Math.abs(45 / TICKS_PER_STRAFE_RLB);
    public float strafePower_RRF = Math.abs(45 / TICKS_PER_STRAFE_RRF);
    public float strafePower_RRB = Math.abs(45 / TICKS_PER_STRAFE_RRB);

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
    boolean angleNeg;
    boolean targetNeg;

    //Constant Values used in all or most codes, order = when it's used
    //Block Side
    public double colorRun1 = 20;
    public double colorRunUntil1 = 4;
    public double strafeToNextBlock = 8;
    public double colorRun2 = 2;
    public double colorRun3 = 3;
    public double colorRun4 = 36;
    public double colorRunMultiplier1 = 12;
    public double colorRun5 = colorRun4 + 26;
    public double colorRun6 = colorRun5 - 4;
    public double colorRunToWall = 23;

    //Shift sideways slightly since color sensor isn't centered
    public double colorSense = 2;

    //Foundation Side
    public double foundationRun1 = 50;
    public double foundationRun2 = 10;
    public double foundationRun3 = 55;
    public double foundationRun4 = 4;
    public double foundationPower1 = .9;
    public double foundationPower2 = .8;
    public double foundationStrafe1 = 35;
    public double foundationRun5 = 25;
    public double foundationStrafe2 = 30;

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
        vertical2.setDirection(DcMotorSimple.Direction.REVERSE);

        colorSensor.enableLed(false);

        //spinnyBoyUp();
    }
    //Use encoders to move robot a certain number of inches. For power, use allPower
    public void runTo(double inches, double power, double slowerPower) {
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
            while ((leftMotorBack.getCurrentPosition() < targetPosition * .7) && (rightMotorBack.getCurrentPosition() > -targetPosition * .7) && opModeIsActive() && runtime.milliseconds() < 4000) {
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
            }
        }
        else {
            while ((leftMotorBack.getCurrentPosition() > targetPosition * .7) && (rightMotorBack.getCurrentPosition() < -targetPosition * .7) && opModeIsActive() && runtime.milliseconds() < 4000) {
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
                leftMotorBack.setPower(-+power);
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

        while ((leftMotorFront.getCurrentPosition() < targetTurn_LLF * .7) && (rightMotorBack.getCurrentPosition() > targetTurn_LRB * .7) && (leftMotorBack.getCurrentPosition() < targetTurn_LLB * .7) && (rightMotorFront.getCurrentPosition() > targetTurn_LRF * .7) && opModeIsActive() && runtime.milliseconds() < 4000) {
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
        while ((leftMotorFront.getCurrentPosition() < targetTurn_LLF) && (rightMotorBack.getCurrentPosition() > targetTurn_LRB) && (leftMotorBack.getCurrentPosition() < targetTurn_LLB) && (rightMotorFront.getCurrentPosition() > targetTurn_LRF) && opModeIsActive() && runtime.milliseconds() < 4000) {
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

        while ((leftMotorFront.getCurrentPosition() > targetTurn_RLF * .7) && (rightMotorBack.getCurrentPosition() < targetTurn_RRB * .7) && (leftMotorBack.getCurrentPosition() > targetTurn_RLB * .8) && (rightMotorFront.getCurrentPosition() < targetTurn_RRF * .8) && opModeIsActive() && runtime.milliseconds() < 4000) {
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

        while ((leftMotorFront.getCurrentPosition() < targetStrafe_LLF * .7) && (rightMotorBack.getCurrentPosition() < targetStrafe_LRB * .7) && (leftMotorBack.getCurrentPosition() > targetStrafe_LLB * .7) && (rightMotorFront.getCurrentPosition() > targetStrafe_LRF * .7) && opModeIsActive() && runtime.milliseconds() < 4000) {
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
        }
        while ((leftMotorFront.getCurrentPosition() < targetStrafe_LLF) && (rightMotorBack.getCurrentPosition() < targetStrafe_LRB) && (leftMotorBack.getCurrentPosition() > targetStrafe_LLB) && (rightMotorFront.getCurrentPosition() > targetStrafe_LRF) && opModeIsActive() && runtime.milliseconds() < 4000) {
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
        }
        leftMotorFront.setPower(0);
        leftMotorBack.setPower(0);
        rightMotorFront.setPower(0);
        rightMotorBack.setPower(0);
    }
    public void strafeRight(double inches, double power, double slowerPower) {
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

        while ((leftMotorFront.getCurrentPosition() > targetStrafe_RLF * .7) && (rightMotorBack.getCurrentPosition() > targetStrafe_RRB * .7) && (leftMotorBack.getCurrentPosition() < targetStrafe_RLB * .7) && (rightMotorFront.getCurrentPosition() < targetStrafe_RRF * .7) && opModeIsActive() && runtime.milliseconds() < 4000) {
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
        }
        while ((leftMotorFront.getCurrentPosition() > targetStrafe_RLF) && (rightMotorBack.getCurrentPosition() > targetStrafe_RRB) && (leftMotorBack.getCurrentPosition() < targetStrafe_RLB) && (rightMotorFront.getCurrentPosition() < targetStrafe_RRF) && opModeIsActive() && runtime.milliseconds() < 4000) {
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
        //Move forward a little for better read
        runTo(2, allPower, slowPower);
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
        runTo(-2, allPower, slowPower);
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
        while (runtime.milliseconds() < 3000 && heading == 0) {
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
            sleep(250);
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
        turnLeft(degrees, power, slowPower);
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
        turnRight(degrees, power, slowPower);
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
        inchesTraveling = inchesAway - inches;
        runTo(inchesTraveling * .8, power * .8, power * .6);
    }
    public void pickUpBlock () {

    }
    public void dropBlock () {

    }
    public void spinnyBoyDown() {
        //Move them
    }
    public void spinnyBoyUp() {
        //Move them
    }
    //Method to set robot to angle it was initially at
    public void adjustToInitialAngle() {
        degrees = 0;
        sleep(100);
        angle = getAngle();
        degrees = angle;
        if (angle == 0) {
            return;
        }
        else if (angle < 0) {
            side = 1;
        }
        else {
            side = 2;
        }

        if (side == 1) {
            while (angle < -30) {
                turnLeft(15, allPower, slowPower);
                sleep(300);
                angle = getAngle();
            }
            turnLeft(-angle, allPower, slowPower);
        }
        else if (side == 2) {
            while (angle > 30) {
                turnRight(15, allPower, slowPower);
                sleep(300);
                angle = getAngle();
            }
            turnRight(angle, allPower, slowPower);
        }
    }
}