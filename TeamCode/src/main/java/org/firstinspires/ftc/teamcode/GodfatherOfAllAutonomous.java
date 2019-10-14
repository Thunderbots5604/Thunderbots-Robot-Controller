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
public class GodfatherOfAllAutonomous extends LinearOpMode {

    // Declare OpMode members.
    public ElapsedTime runtime = new ElapsedTime();
    public DcMotor leftMotorFront = null;
    public DcMotor leftMotorBack = null;
    public DcMotor rightMotorFront = null;
    public DcMotor rightMotorBack = null;

    public final float TICKS_PER_INCH = 42.99308433F;
    public final float TICKS_PER_DEGREE = 7.889583333F;

    private final float pi = 3.1415926535897932384626433832F;

    public int location = -1;
    public int objects = 0;
    public static final String TFOD_MODEL_ASSET = "SkyStone.tflite";
    public static final String LABEL_SKYSTONE= "SkyStone";
    public static final String LABEL_BLOCK = "Block";

    // Bruh we still need one
    //public static final String VUFORIA_KEY = "AfDTG3b/////AAABmQnAWkqPDkVztdcIurPa8w5lO6BWlTpltO5r1m4s9oA9w3cfFdIBqJ4rjZB0We4YHcRMdc5LkWwOvJk+xcDr2VFpLP8m6zEqLWlrVNQvBnNxmGO8BeZ+xRQeQ34AgPhrqQQqeheWJbfoOn+lekIz2ZMm9f+j+1ng/X0vKDHyFGfxbtXbJuUx4Qh6E3t0esH0b3VQtbuJiOOTpWi9xFAqBsHWp+DQbwub+a6HZV5q42OabnOAyr0GZ7u1vJZs+I/Vlnf7qEMLD4RTIYA5OmMyzOdl5aikZqDSgG223ETSwcbwd3QFKewYE3oXXxkpI0vmsxCiaqBJ1oL9e6n0RXbC8Zdvn2VYwh6oSemcpp+fSjGa";

    public VuforiaLocalizer vuforia;

    public TFObjectDetector tfod;

    public double allPower = .8;

    public BNO055IMU imu;
    public Orientation angles;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();
    }
    /*public void initVuforia() {

         //* Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.

        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection = CameraDirection.BACK;

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);
    }*/
    public void initTfod() {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_SKYSTONE, LABEL_BLOCK);
    }

    public int objectDetect() {
        //initVuforia();

        if (ClassFactory.getInstance().canCreateTFObjectDetector()) {
            initTfod();
        } else {
            telemetry.addData("Sorry!", "This device is not compatible with TFOD");
        }

        if (tfod != null) {
            tfod.activate();
        }

        List<Recognition> updatedRecognitions = null;
        location = 0;
        runtime.reset();
        while(objects < 6 && runtime.seconds() < 6) {
            updatedRecognitions = tfod.getUpdatedRecognitions();
            if(updatedRecognitions != null) {
                objects = updatedRecognitions.size();
            }
        }
        updatedRecognitions = tfod.getUpdatedRecognitions();
        boolean skyblock1 = false;
        boolean skyblock2 = false;

        if (updatedRecognitions != null) {
            if (updatedRecognitions.size() == 6) {
                for (Recognition r : updatedRecognitions) {
                    //Write Code For assigning variables
                }
            }
        }
        //Write code for location identification
        telemetry.addData("Array: ", updatedRecognitions);


        telemetry.update();
        tfod.shutdown();
        return location;
    }

    public double formatAngle(AngleUnit angleUnit, double angle) {
        return AngleUnit.DEGREES.fromUnit(angleUnit, angle);
    }
    public void initialization() {
        leftMotorFront = hardwareMap.get(DcMotor.class, "left_motor_front");
        leftMotorBack = hardwareMap.get(DcMotor.class, "left_motor_back");
        rightMotorFront = hardwareMap.get(DcMotor.class, "right_motor_front");
        rightMotorBack = hardwareMap.get(DcMotor.class, "right_motor_back");

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
    }
}