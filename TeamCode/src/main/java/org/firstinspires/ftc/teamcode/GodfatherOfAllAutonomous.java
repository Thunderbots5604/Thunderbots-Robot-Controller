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

    public DcMotor horizontal = null;

    public DcMotor crane1 = null;
    public DcMotor crane2 = null;

    public CRServo wheel = null;
    public CRServo box1 = null;
    public CRServo box2 = null;

    public Servo elevator = null;
    public DistanceSensor distance = null;

    public final float TICKS_PER_INCH = 42.99308433F;
    public final float TICKS_PER_DEGREE = 7.889583333F;

    private final float pi = 3.1415926535897932384626433832F;

    public int location = -1;
    public int objects = 0;
    public static final String TFOD_MODEL_ASSET = "RoverRuckus.tflite";
    public static final String LABEL_GOLD_MINERAL = "Gold Mineral";
    public static final String LABEL_SILVER_MINERAL = "Silver Mineral";

    public static final String VUFORIA_KEY = "AfDTG3b/////AAABmQnAWkqPDkVztdcIurPa8w5lO6BWlTpltO5r1m4s9oA9w3cfFdIBqJ4rjZB0We4YHcRMdc5LkWwOvJk+xcDr2VFpLP8m6zEqLWlrVNQvBnNxmGO8BeZ+xRQeQ34AgPhrqQQqeheWJbfoOn+lekIz2ZMm9f+j+1ng/X0vKDHyFGfxbtXbJuUx4Qh6E3t0esH0b3VQtbuJiOOTpWi9xFAqBsHWp+DQbwub+a6HZV5q42OabnOAyr0GZ7u1vJZs+I/Vlnf7qEMLD4RTIYA5OmMyzOdl5aikZqDSgG223ETSwcbwd3QFKewYE3oXXxkpI0vmsxCiaqBJ1oL9e6n0RXbC8Zdvn2VYwh6oSemcpp+fSjGa";

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
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_GOLD_MINERAL, LABEL_SILVER_MINERAL);
    }


    public void detach() {

        while (distance.getDistance(DistanceUnit.MM) > 100) {
            telemetry.addLine("Phase: Lowering Part 1");
            telemetry.addData("Distance", distance.getDistance(DistanceUnit.MM));
            telemetry.update();
            crane1.setPower(-1);
            crane2.setPower(1);
        }
        runtime.reset();
        while (distance.getDistance(DistanceUnit.MM) > 47) {
            telemetry.addLine("Phase: Lowering Part 2");
            telemetry.addData("Distance", distance.getDistance(DistanceUnit.MM));
            telemetry.update();
            crane1.setPower(-(allPower + .18));
            crane2.setPower(allPower + .18);
        }
        runtime.reset();
        while(runtime.milliseconds() < 200) {
            crane1.setPower(-(allPower + .18));
            crane2.setPower(allPower + .18);
        }
        runtime.reset();
        while(runtime.milliseconds() < 50) {
            crane1.setPower(allPower);
            crane2.setPower(-(allPower));
        }
        crane1.setPower(0);
        crane2.setPower(0);

        sleep(100);
        //Getting out of pesky hook
        //sleep(250);
        runTo(1.5,allPower + .1);
        sleep(500);
        turnRight(45, allPower);
        sleep(100);
        runTo(3,allPower - .2);
        sleep(100);
        turnRight(42,allPower);
    }/*
    public int tfodDetection(double timeOut) {
        int silverPosition = 0;
        int goldPosition = 0;
        location = 0;
        runtime.reset();
        while(runtime.seconds() < timeOut && objects != 2) {
            List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
            if (updatedRecognitions != null) {
                objects = updatedRecognitions.size();
                telemetry.addData("# Object Detected", updatedRecognitions.size());
                if (updatedRecognitions.size() == 2) {
                    for (Recognition r : updatedRecognitions) {
                        if (r.getLabel().equals(LABEL_GOLD_MINERAL)) {
                            goldPosition = (int) r.getTop();
                        } else {
                            silverPosition = (int) r.getTop();
                        }
                    }
                }
            }
        }
        if (goldPosition == 0) {
            telemetry.addLine("Gold Mineral is on Left");
            telemetry.addData("Gold Top", goldPosition);
            telemetry.addData("Silver Top", silverPosition);
            location = 0;
        } else if (goldPosition < silverPosition) {
            telemetry.addLine("Gold Mineral is Center");
            telemetry.addData("Gold Top", goldPosition);
            telemetry.addData("Silver Top", silverPosition);
            location = 1;
        } else if (goldPosition > silverPosition) {
            telemetry.addLine("Gold Mineral is on Right");
            telemetry.addData("Gold Top", goldPosition);
            telemetry.addData("Silver Top", silverPosition);
            location = 2;
        }
        telemetry.update();
        tfod.shutdown();
        return location;
    }*/

    public int objectDetect() {
        initVuforia();

        if (ClassFactory.getInstance().canCreateTFObjectDetector()) {
            initTfod();
        } else {
            telemetry.addData("Sorry!", "This device is not compatible with TFOD");
        }

        if (tfod != null) {
            tfod.activate();
        }

        int silverOnePosition = 0;
        int silverTwoPosition = 0;
        int goldPosition = 0;
        boolean silverDetected = false;
        location = 0;
        while(objects != 3) {
            List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
            if (updatedRecognitions != null) {
                objects = updatedRecognitions.size();
                if (updatedRecognitions.size() == 3) {
                    silverDetected = false;
                    for (Recognition r : updatedRecognitions) {
                        if (r.getLabel().equals(LABEL_GOLD_MINERAL)) {
                            goldPosition = (int) r.getTop();
                        } else if (r.getLabel().equals(LABEL_SILVER_MINERAL) && silverDetected == false){
                            silverOnePosition = (int) r.getTop();
                            silverDetected = true;
                        }
                        else {
                            silverTwoPosition = (int) r.getTop();
                        }
                    }
                }
            }
        }

        if (goldPosition > silverOnePosition && goldPosition > silverTwoPosition) {
            telemetry.addLine("Gold Mineral is on Right");
            telemetry.addData("Gold Top", goldPosition);
            telemetry.addData("Silver One Top", silverOnePosition);
            telemetry.addData("Silver Two Top", silverTwoPosition);
            location = 2;
        } else if ((goldPosition > silverOnePosition && goldPosition < silverTwoPosition) || (goldPosition > silverTwoPosition && goldPosition < silverOnePosition)) {
            telemetry.addLine("Gold Mineral is Center");
            telemetry.addData("Gold Top", goldPosition);
            telemetry.addData("Silver One Top", silverOnePosition);
            telemetry.addData("Silver Two Top", silverTwoPosition);
            location = 1;
        } else if (goldPosition < silverOnePosition && goldPosition < silverTwoPosition) {
            telemetry.addLine("Gold Mineral is on Left");
            telemetry.addData("Gold Top", goldPosition);
            telemetry.addData("Silver One Top", silverOnePosition);
            telemetry.addData("Silver Two Top", silverTwoPosition);
            location = 0;
        }
        telemetry.update();
        tfod.shutdown();
        return location;
    }

    public void runTo(double inches, double power) {
        //.75 is max accurate
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
            while ((leftMotorBack.getCurrentPosition() < targetPosition) && (rightMotorBack.getCurrentPosition() > -targetPosition)) {
                telemetry.addData("Target Position", targetPosition);
                telemetry.addData("Left Motor Front Position", leftMotorFront.getCurrentPosition());
                telemetry.addData("Left Motor Back Position", leftMotorBack.getCurrentPosition());
                telemetry.addData("Right Motor Front Position", rightMotorFront.getCurrentPosition());
                telemetry.addData("Right Motor Back Position", rightMotorBack.getCurrentPosition());
                telemetry.update();
                leftMotorFront.setPower(power);
                leftMotorBack.setPower(-power);
                rightMotorFront.setPower(-power);
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
                leftMotorBack.setPower(power);
                rightMotorFront.setPower(power);
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
        leftMotorFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftMotorBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightMotorFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightMotorBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        int targetTurn = (int)(degrees * TICKS_PER_DEGREE);

        while ((leftMotorFront.getCurrentPosition() < targetTurn) && (rightMotorBack.getCurrentPosition() > -targetTurn)) {
            telemetry.addData("Target Position", targetTurn);
            telemetry.addData("Left Motor Front Position", leftMotorFront.getCurrentPosition());
            telemetry.addData("Left Motor Back Position", leftMotorBack.getCurrentPosition());
            telemetry.addData("Right Motor Front Position", rightMotorFront.getCurrentPosition());
            telemetry.addData("Right Motor Back Position", rightMotorBack.getCurrentPosition());
            telemetry.update();
            leftMotorFront.setPower(-power);
            leftMotorBack.setPower(power);
            rightMotorFront.setPower(-power);
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
    public void turnRight(double degrees, double power) {

        leftMotorFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftMotorBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotorFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotorBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftMotorFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftMotorBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightMotorFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightMotorBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        int targetTurn = (int)(degrees * TICKS_PER_DEGREE);

        while ((leftMotorFront.getCurrentPosition() > -targetTurn) && (rightMotorBack.getCurrentPosition() < targetTurn)) {
            telemetry.addData("Target Position", targetTurn);
            telemetry.addData("Left Motor Front Position", leftMotorFront.getCurrentPosition());
            telemetry.addData("Left Motor Back Position", leftMotorBack.getCurrentPosition());
            telemetry.addData("Right Motor Front Position", rightMotorFront.getCurrentPosition());
            telemetry.addData("Right Motor Back Position", rightMotorBack.getCurrentPosition());
            telemetry.update();
            leftMotorFront.setPower(power);
            leftMotorBack.setPower(-power);
            rightMotorFront.setPower(power);
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

    public void dropMarker(){
        box1.setPower(1);
        box2.setPower(-1);
        sleep(700);
        box1.setPower(0);
        box2.setPower(0);
        wheel.setPower(1);
        sleep(1000);
        box1.setPower(-1);
        box2.setPower(1);
        wheel.setPower(0);
        sleep(1500);
        box1.setPower(0);
        box2.setPower(0);
    }

    public void initialization() {
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

        allPower = getAllPower();
    }
    public double getVoltage() {
        double result = Double.POSITIVE_INFINITY;
        for (VoltageSensor sensor : hardwareMap.voltageSensor) {
            double voltage = sensor.getVoltage();
            if (voltage > 0) {
                result = Math.min(result, voltage);
            }
        }
        return result;
    }
    public double getAllPower(){
        double v = getVoltage();
        double power = .65;
        if (v >= 13) {
            power = (-.027 * (v-13) * (v-13) + .53);
        }
        else if (v > 12) {
            power = (.01 * (v-13) * (v-13) + .53);
        }
        return power;
    }
    public double formatAngle(AngleUnit angleUnit, double angle) {
        return AngleUnit.DEGREES.fromUnit(angleUnit, angle);
    }
}