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

@Disabled
@Autonomous(name="No Use", group="Autonomous Competition")
public class GodFatherOfAllAutonomous extends LinearOpMode {

    // Declare OpMode members.
    public ElapsedTime runtime = new ElapsedTime();
    public DcMotor leftMotorFront = null;
    public DcMotor leftMotorBack = null;
    public DcMotor rightMotorFront = null;
    public DcMotor rightMotorBack = null;
    private Servo clawServo = null;
    private Servo armServo = null;
    private ElapsedTime armCooldown = new ElapsedTime();

    public final float TICKS_PER_INCH = 42.99308433F;
    public final float TICKS_PER_DEGREE = 7.889583333F;

    private final float pi = 3.1415926535897932384626433832F;
    public int location = -1;
    public int objects = 0;
    public static final String TFOD_MODEL_ASSET = "RoverRuckus.tflite";
    public static final String LABEL_SKYSTONE = "Skystone";
    public static final String LABEL_BLOCK = "Stone";

    public static final String VUFORIA_KEY = "Aev2uJj/////AAABmXZLlevRVUibu3ft/8eoZ+p3zmNO/qYTRunRCIvDriYoZlMUcvJWFcEhvD1bCA6j/KWPlsVQyzCyh983kmfZN03G5bBJXhDh4fSgT4yyHL4PScYi5aG1UaxLa38X2vqzrbx9jpUqE3ESk6wYg8enXTPzp8R6+0SnrFoRLa7yobzCbBIfzAIpsGO33F9PVbXV+zsf0jqg0KA9OG24I6WkLZll0YPy1fDkR1okXL4pv2pm7eiKaZa2EXIYE/lGfkOAO42vxFMO8rAqA46/YeX/QPPTrCow0dE81FGSS6Wp9v3z45lqQ/kg+0TnSDkOJFrGKUYD1v6zTkfJLhF6DDAuW1TwPcdof0349IOncpuCpcz9";

    public VuforiaLocalizer vuforia;

    public TFObjectDetector tfod;

    public double allPower = .65;

    public BNO055IMU imu;

    public Orientation angles;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();
    }
    public void initVuforia() {
        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         */
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection = CameraDirection.BACK;

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);
    }
    public void initTfod() {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_SKYSTONE, LABEL_BLOCK);
    }

    public int[] objectDetect() {
        initVuforia();

        if (ClassFactory.getInstance().canCreateTFObjectDetector()) {
            initTfod();
        } else {
            telemetry.addData("Sorry!", "This device is not compatible with TFOD");
        }

        if (tfod != null) {
            tfod.activate();
        }

        int skystone1Position = 0;
        int skystone2Position = 0;
        int[] blockPositions = {0, 0, 0, 0};
        int blocksDetected = 0;
        int objects = 0;
        int[] skystoneLocation = {1, 2};
        int blocksTested = 0;

        List<Recognition> updatedRecognitions = null;
        runtime.reset();
        while(objects != 6 && runtime.seconds() < 6) {
            updatedRecognitions = tfod.getUpdatedRecognitions();
            if(updatedRecognitions != null) {
                objects = updatedRecognitions.size();
            }
        }
        if (objects != 6) {
            updatedRecognitions = tfod.getUpdatedRecognitions();
        }
        boolean skystone1Detected = false;
        if (updatedRecognitions != null) {
                for (Recognition r : updatedRecognitions) {
                    if (r.getLabel().equals(LABEL_SKYSTONE) && !skystone1Detected) {
                        skystone1Position = (int) r.getTop();
                        skystone1Detected = true;

                    } else if (r.getLabel().equals(LABEL_SKYSTONE) && skystone1Detected){
                        skystone2Position = (int) r.getTop();
                    }
                    else {
                        blockPositions[blocksDetected] = (int) r.getTop();
                        blocksDetected += 1;
                    }
                }
                //Make Skystone 1 on top
                if (skystone1Position < skystone2Position) {
                    int skystone1PositionSub = skystone1Position;
                    skystone1Position = skystone2Position;
                    skystone2Position = skystone1PositionSub;
                }
                // Locate blocks
                for (blocksTested = 0; blocksDetected > blocksTested; blocksTested++) {
                    if (blockPositions[blocksTested] > skystone1Position) {
                        skystoneLocation[0] += 1;
                    }
                    if (blockPositions[blocksTested] > skystone2Position) {
                        skystoneLocation[1] += 1;
                    }
                }
        }

        telemetry.addData("Array: ", updatedRecognitions);
        telemetry.update();
        tfod.shutdown();
        return skystoneLocation;
    }

    public void runTo(double inches, double power) {
        leftMotorFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftMotorBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotorFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotorBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        int targetPosition = (int)(inches * TICKS_PER_INCH);
        if (inches > 0) {
            while ((leftMotorBack.getCurrentPosition() < targetPosition) && (rightMotorBack.getCurrentPosition() > -targetPosition)) {
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
            leftMotorFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            leftMotorBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            rightMotorFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            rightMotorBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        }
        else {
            while ((leftMotorBack.getCurrentPosition() > targetPosition) && (rightMotorBack.getCurrentPosition() > targetPosition)) {
                telemetry.addData("Target Position", targetPosition);
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
            leftMotorFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            leftMotorBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            rightMotorFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            rightMotorBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        }
        leftMotorFront.setPower(0);
        leftMotorBack.setPower(0);
        rightMotorFront.setPower(0);
        rightMotorBack.setPower(0);
    }
    public void turnLeft(double degrees, double power) {

        leftMotorFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftMotorBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotorFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotorBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        int targetTurn = (int)(degrees * TICKS_PER_DEGREE);

        while ((leftMotorFront.getCurrentPosition() < targetTurn) && (rightMotorBack.getCurrentPosition() > -targetTurn)) {
            telemetry.addData("Target Position", targetTurn);
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
        leftMotorFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftMotorBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotorFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotorBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }
    public void turnRight(double degrees, double power) {

        leftMotorFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftMotorBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotorFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotorBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        int targetTurn = (int)(degrees * TICKS_PER_DEGREE);

        while ((leftMotorFront.getCurrentPosition() > -targetTurn) && (rightMotorBack.getCurrentPosition() < targetTurn)) {
            telemetry.addData("Target Position", targetTurn);
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
        leftMotorFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftMotorBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotorFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotorBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public void initialization() {
        leftMotorFront = hardwareMap.get(DcMotor.class, "left_motor_front");
        leftMotorBack = hardwareMap.get(DcMotor.class, "left_motor_back");
        rightMotorFront = hardwareMap.get(DcMotor.class, "right_motor_front");
        rightMotorBack = hardwareMap.get(DcMotor.class, "right_motor_back");
        clawServo = hardwareMap.get(Servo.class, "claw_servo");
        armServo = hardwareMap.get(Servo.class, "arm_servo");

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

        allPower = allPower;
    }
    public double formatAngle(AngleUnit angleUnit, double angle) {
        return AngleUnit.DEGREES.fromUnit(angleUnit, angle);
    }
    public void armDown () {
        armCooldown.reset();
        armServo.setPosition(90);
        sleep(500);
        clawServo.setPosition(0);
        sleep(500);
        armServo.setPosition(0);
    }
    public void armUp () {
        armCooldown.reset();
        armServo.setPosition(90);
        sleep(500);
        clawServo.setPosition(90);
        sleep(500);
        armServo.setPosition(0);
    }
}
