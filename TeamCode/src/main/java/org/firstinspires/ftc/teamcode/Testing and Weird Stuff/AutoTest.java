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
    public Servo clawServo = null;
    public Servo armServo = null;

    //All Power for autonomous running
    public double allPower = .75;

    //Color Sensor
    public ColorSensor colorSensor;
    public String color = null;
    public int blue = 0;
    public int red = 0;
    public int green = 0;

    //Distance Sensor
    public DistanceSensor distance = null;
    public double millimetersAway;

    //Ticks
    public final float TICKS_PER_INCH = 45.501275F;
    public final float TICKS_PER_DEGREE_LLF = 6.761111111F;
    public final float TICKS_PER_DEGREE_LLB = 16.54305556F;
    public final float TICKS_PER_DEGREE_LRF = -11.22222222F;
    public final float TICKS_PER_DEGREE_LRB = -10.96111111F;
    public final float TICKS_PER_DEGREE_RLF= -8.968888889F;
    public final float TICKS_PER_DEGREE_RLB = -10.34888889F;
    public final float TICKS_PER_DEGREE_RRF = 8.142222222F;
    public final float TICKS_PER_DEGREE_RRB = 12.42888889F;
    public final float TICKS_MULTIPLIER = /*(Ticks in competition for wall to foundation) / (Ticks in testing for wall to foundation) */1F;

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

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();
    }

    public void runTo(double inches, double power) {
        leftMotorFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftMotorBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotorFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotorBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftMotorFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftMotorBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightMotorFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightMotorBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        int targetPosition = (int)(inches * TICKS_PER_INCH);
        if (inches > 0) {
            while ((leftMotorBack.getCurrentPosition() < targetPosition) && (rightMotorBack.getCurrentPosition() > -targetPosition) && opModeIsActive()) {
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
        }
        else {
            while ((leftMotorBack.getCurrentPosition() < -targetPosition) && (rightMotorBack.getCurrentPosition() < -targetPosition) && opModeIsActive()) {
                telemetry.addData("Target Position", -targetPosition);
                telemetry.addData("Left Motor Front Position", leftMotorFront.getCurrentPosition());
                telemetry.addData("Left Motor Back Position", leftMotorBack.getCurrentPosition());
                telemetry.addData("Right Motor Front Position", rightMotorFront.getCurrentPosition());
                telemetry.addData("Right Motor Back Position", rightMotorBack.getCurrentPosition());
                telemetry.update();
                leftMotorFront.setPower(-power);
                leftMotorBack.setPower(-power);
                rightMotorFront.setPower(-power);
                rightMotorBack.setPower(-power);
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
    public void turnLeft(double degrees, double power) {

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

        while ((leftMotorFront.getCurrentPosition() < targetTurn_LLF) && (rightMotorBack.getCurrentPosition() > targetTurn_LRB) && (leftMotorBack.getCurrentPosition() < targetTurn_LLB) && (rightMotorFront.getCurrentPosition() > targetTurn_LRF) && opModeIsActive()) {
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
        leftMotorFront.setPower(0);
        leftMotorBack.setPower(0);
        rightMotorFront.setPower(0);
        rightMotorBack.setPower(0);
    }
    public void turnRight(double degrees, double power) {

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

        while ((leftMotorFront.getCurrentPosition() > targetTurn_RLF) && (rightMotorBack.getCurrentPosition() < targetTurn_RRB) && (leftMotorBack.getCurrentPosition() > targetTurn_RLB) && (rightMotorFront.getCurrentPosition() < targetTurn_RRF) && opModeIsActive()) {
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
        leftMotorFront.setPower(0);
        leftMotorBack.setPower(0);
        rightMotorFront.setPower(0);
        rightMotorBack.setPower(0);
    }

    public void initialization() {
        leftMotorFront = hardwareMap.get(DcMotor.class, "left_motor_front");
        leftMotorBack = hardwareMap.get(DcMotor.class, "left_motor_back");
        rightMotorFront = hardwareMap.get(DcMotor.class, "right_motor_front");
        rightMotorBack = hardwareMap.get(DcMotor.class, "right_motor_back");
        clawServo = hardwareMap.get(Servo.class, "claw_servo");
        armServo = hardwareMap.get(Servo.class, "arm_servo");
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

        colorSensor.enableLed(false);

        armUp();
    }
    public double formatAngle(AngleUnit angleUnit, double angle) {
        return AngleUnit.DEGREES.fromUnit(angleUnit, angle);
    }
    public String senseColor() {
        color = "Yellow";
        red = colorSensor.red();
        blue = colorSensor.blue();
        green = colorSensor.green();
        telemetry
                .addData("red: ", red)
                .addData("Green: ", green)
                .addData("Blue: ", blue);
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
        return color;
    }
    public void armUp() {
        armCooldown.reset();
        clawServo.setPosition(0.1);
        sleep(1000);
        armServo.setPosition(.1);
    }
    public void armDown() {
        armCooldown.reset();
        clawServo.setPosition(1);
        sleep(1000);
        armServo.setPosition(1);
    }
    public double getDistance() {
        millimetersAway = distance.getDistance(DistanceUnit.MM);
        return millimetersAway;
    }
    public double getAngle() {
        heading = 0;
        runtime.reset();
        while (runtime.milliseconds() < 3000 && heading == 0) {
            angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
            heading = formatAngle(angles.angleUnit, angles.firstAngle);
        }
        return heading;
    }
    public void accurateTurnLeft(double targetAngle, double power) {
        side = 0;
        degrees = 0;
        angle = getAngle();
        if (targetAngle > angle) {
            degrees = targetAngle - angle;
            side = 1;
        }
        else if (targetAngle < angle) {
            degrees = 360 - (angle - targetAngle);
            side = 2;
        }
        while (degrees > 30) {
            turnLeft(30, power);
            if (targetAngle > angle && side == 1) {
                degrees = targetAngle - angle;
            }
            else if (targetAngle < angle && side == 2) {
                degrees = 360 - (angle - targetAngle);
            }
        }
        turnLeft(degrees, power);
    }
    public void accurateTurnRight(double targetAngle, double power) {
        degrees = 0;
        side = 0;
        angle = getAngle();
        if (targetAngle < angle) {
            degrees = angle - targetAngle;
            side = 1;
        }
        else if (targetAngle > angle) {
            degrees = 360 - (targetAngle - angle);
            side = 2;
        }
        while (degrees > 30) {
            turnRight(30, power);
            if (targetAngle < angle && side == 1) {
                degrees = angle - targetAngle;
            }
            else if (targetAngle > angle && side == 2) {
                degrees = 360 - (targetAngle - angle);
            }
        }
        turnRight(degrees, power);
    }
}
