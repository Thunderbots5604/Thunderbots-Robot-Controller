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

@Autonomous(name="AutonomousDetachBall", group="Autonomous Competition")
public class AutoTest extends LinearOpMode {

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
    private final double INCHES_PER_TICK = .0215524172;
    private final double DEGREES_PER_TICK = .1525087903;

    private int location = -1;
    private int objects = 0;
    private static final String TFOD_MODEL_ASSET = "RoverRuckus.tflite";
    private static final String LABEL_GOLD_MINERAL = "Gold Mineral";
    private static final String LABEL_SILVER_MINERAL = "Silver Mineral";

    /*
     * IMPORTANT: You need to obtain your own license key to use Vuforia. The string below with which
     * 'parameters.vuforiaLicenseKey' is initialized is for illustration only, and will not function.
     * A Vuforia 'Development' license key, can be obtained free of charge from the Vuforia developer
     * web site at https://developer.vuforia.com/license-manager.
     *
     * Vuforia license keys are always 380 characters long, and look as if they contain mostly
     * random data. As an example, here is a example of a fragment of a valid key:
     *      ... yIgIzTqZ4mWjk9wd3cZO9T1axEqzuhxoGlfOOI2dRzKS4T0hQ8kT ...
     * Once you've obtained a license key, copy the string from the Vuforia web site
     * and paste it in to your code on the next line, between the double quotes.
     */
    private static final String VUFORIA_KEY = "AfDTG3b/////AAABmQnAWkqPDkVztdcIurPa8w5lO6BWlTpltO5r1m4s9oA9w3cfFdIBqJ4rjZB0We4YHcRMdc5LkWwOvJk+xcDr2VFpLP8m6zEqLWlrVNQvBnNxmGO8BeZ+xRQeQ34AgPhrqQQqeheWJbfoOn+lekIz2ZMm9f+j+1ng/X0vKDHyFGfxbtXbJuUx4Qh6E3t0esH0b3VQtbuJiOOTpWi9xFAqBsHWp+DQbwub+a6HZV5q42OabnOAyr0GZ7u1vJZs+I/Vlnf7qEMLD4RTIYA5OmMyzOdl5aikZqDSgG223ETSwcbwd3QFKewYE3oXXxkpI0vmsxCiaqBJ1oL9e6n0RXbC8Zdvn2VYwh6oSemcpp+fSjGa";

    /**
     * {@link #vuforia} is the variable we will use to store our instance of the Vuforia
     * localization engine.
     */
    private VuforiaLocalizer vuforia;

    /**
     * {@link #tfod} is the variable we will use to store our instance of the Tensor Flow Object
     * Detection engine.
     */
    private TFObjectDetector tfod;

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
        //Stuff to display for Telemetry

        //crane.setPower(1);
        runtime.reset();
        while (Double.isNaN(distance.getDistance(DistanceUnit.MM))) {
            telemetry.addLine("Phase: Lowering Part 1");
            telemetry.addData("Distance", distance.getDistance(DistanceUnit.MM));
            telemetry.update();
            crane.setPower(1);
        }
        sleep(900);
        crane.setPower(0);
        runtime.reset();
        while (distance.getDistance(DistanceUnit.MM) > 70) {
            telemetry.addLine("Phase: Lowering Part 2");
            telemetry.addData("Distance", distance.getDistance(DistanceUnit.MM));
            telemetry.update();
            crane.setPower(.75);
        }
        crane.setPower(0);
        sleep(500);
        runtime.reset();
        //changed from negetive to positive power due to going down too much and getting stuck that way
        while(runtime.milliseconds() < 50) {
            crane.setPower(0.75);
        }
        crane.setPower(0);

        sleep(500);

        if(Double.isNaN(distance.getDistance(DistanceUnit.MM)) != true) {
            runTo(.5, .25);
            turnRight(35, .35);
            runTo(10, .25);
            turnLeft(40, .35);
            runTo(-7, .25);
            sleep(500);
            //increased from 15 due to not turning enough all of a sudden
            turnRight(20,.35);
            // The TFObjectDetector uses the camera frames from the VuforiaLocalizer, so we create that
            // first.
            initVuforia();

            if (ClassFactory.getInstance().canCreateTFObjectDetector()) {
                initTfod();
            } else {
                telemetry.addData("Sorry!", "This device is not compatible with TFOD");
            }

            if (tfod != null) {
                tfod.activate();
            }

            if (opModeIsActive()) {
                /** Activate Tensor Flow Object Detection. */
                if (tfod != null) {
                    tfod.activate();
                }
                runtime.reset();
                while (runtime.seconds() < 3) {
                    if (tfod != null) {
                        // getUpdatedRecognitions() will return null if no new information is available since
                        // the last time that call was made.

                        List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
                        if (updatedRecognitions != null) {
                            telemetry.addData("# Object Detected", updatedRecognitions.size());
                            objects = updatedRecognitions.size();
                            if (updatedRecognitions.size() == 2) {
                                int goldMineralX = -1;
                                int silverMineral1X = -1;
                                int silverMineral2X = -1;

                                for (Recognition recognition : updatedRecognitions) {
                                    if (recognition.getLabel().equals(LABEL_GOLD_MINERAL)) {
                                        goldMineralX = (int) recognition.getTop();
                                        telemetry.addLine("Right");
                                        telemetry.addData("getTop?", recognition.getTop());
                                        telemetry.update();
                                    } else if (silverMineral1X == -1) {
                                        silverMineral1X = (int) recognition.getTop();
                                        telemetry.addLine("Center");
                                        telemetry.addData("getTop?", recognition.getTop());
                                        telemetry.update();
                                    } else {
                                        silverMineral2X = (int) recognition.getTop();
                                        telemetry.addLine("Left");
                                        telemetry.addData("getTop?", recognition.getTop());
                                        telemetry.update();
                                        location = 2;
                                    }
                                }
                                if (goldMineralX != -1 && silverMineral1X != -1) {
                                    if (goldMineralX < silverMineral1X) {
                                        telemetry.addData("Gold Mineral Position", "Center");
                                        location = 1;
                                    } else if (goldMineralX > silverMineral1X) {
                                        telemetry.addData("Gold Mineral Position", "Right");
                                        location = 0;
                                    } else {
                                        telemetry.addData("Gold Mineral Position", "Left");
                                        location = 2;
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if (tfod != null) {
                tfod.shutdown();
            }

            if (location == 0 ) {
                turnRight(10, .35);
                runTo(25, .25);
                turnLeft(30, .35);
                runTo(15, .25);
            }
            else if (location == 1) {
                turnLeft(20, .35);
                runTo(25, .25);
                turnRight(15, .35);
                runTo(15, .25);
            }
            //still needs work
            else if (location == 2) {
                turnLeft(50, .4);
                //May need to be increased
                runTo(33, .25);
                turnRight(40, .35);
                runTo(3, .2);
                turnRight(25,.25);
                runTo(14,.25);
                //final turning to get ready for going to crater may need slight readjustment
                turnRight(25,.35);
                //this one is set up for backing up straight into crater
            }

            sleep(500);
            crater.setPower(-.15);
            sleep(2000);
            crater.setPower(0);
            sleep(500);
            crater.setPower(.75);
            sleep(900);
            crater.setPower(0);
        }
    }
    private void runTo(double inches, double power) {
        //Just do 40 inches

        leftMotorFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftMotorBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotorFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotorBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        int negative = -1;
        if (inches < 0) {
            negative = 1;
        }
        inches *= -1;
        leftMotorFront.setTargetPosition((int)(inches / INCHES_PER_TICK));
        leftMotorBack.setTargetPosition((int)(inches / INCHES_PER_TICK));
        rightMotorFront.setTargetPosition((int)(inches / INCHES_PER_TICK));
        rightMotorBack.setTargetPosition((int)(inches / INCHES_PER_TICK));
        leftMotorFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftMotorBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightMotorFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightMotorBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        while (leftMotorFront.isBusy() && rightMotorFront.isBusy()) {
            telemetry.addData("Target Position", leftMotorFront.getTargetPosition());
            telemetry.addData("Left Motor Position", leftMotorFront.getCurrentPosition());
            telemetry.addData("Right Motor Position", rightMotorFront.getCurrentPosition());
            telemetry.update();
            leftMotorFront.setPower(negative * power);
            leftMotorBack.setPower(negative * power);
            rightMotorFront.setPower(negative * power);
            rightMotorBack.setPower(negative * power);
        }
        runtime.reset();
        while(runtime.milliseconds() < 100) {
            leftMotorFront.setPower(-1 * negative * .1);
            leftMotorBack.setPower(-1 * negative * .1);
            rightMotorFront.setPower(-1 * negative * .1);
            rightMotorBack.setPower(-1 * negative * .1);
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
    private void turnRight(double degrees, double power) {
        degrees *= .95;

        leftMotorFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftMotorBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotorFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotorBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftMotorFront.setTargetPosition((int)((degrees) / DEGREES_PER_TICK));
        leftMotorBack.setTargetPosition((int)((degrees) / DEGREES_PER_TICK));
        rightMotorFront.setTargetPosition((int)((-degrees) / DEGREES_PER_TICK));
        rightMotorBack.setTargetPosition((int)((-degrees) / DEGREES_PER_TICK));
        leftMotorFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftMotorBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightMotorFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightMotorBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        while (leftMotorFront.isBusy() && rightMotorFront.isBusy()) {
            telemetry.addData("Target Turn", degrees);
            telemetry.addData("Left Motor Position", leftMotorFront.getCurrentPosition());
            telemetry.addData("Right Motor Position", rightMotorFront.getCurrentPosition());
            telemetry.update();
            leftMotorFront.setPower(power);
            leftMotorBack.setPower(power);
            rightMotorFront.setPower(-power);
            rightMotorBack.setPower(-power);
        }
        runtime.reset();
        while(runtime.milliseconds() < 100) {
            leftMotorFront.setPower(-.1);
            leftMotorBack.setPower(-.1);
            rightMotorFront.setPower(.1);
            rightMotorBack.setPower(.1);
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
    private void turnLeft(double degrees, double power) {
        degrees *= .95;

        leftMotorFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftMotorBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotorFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotorBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftMotorFront.setTargetPosition((int)((-degrees) / DEGREES_PER_TICK));
        leftMotorBack.setTargetPosition((int)((-degrees) / DEGREES_PER_TICK));
        rightMotorFront.setTargetPosition((int)((degrees) / DEGREES_PER_TICK));
        rightMotorBack.setTargetPosition((int)((degrees) / DEGREES_PER_TICK));
        leftMotorFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftMotorBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightMotorFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightMotorBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        while (leftMotorFront.isBusy() && rightMotorFront.isBusy()) {
            telemetry.addData("Target Turn", degrees);
            telemetry.addData("Left Motor Position", leftMotorFront.getCurrentPosition());
            telemetry.addData("Right Motor Position", rightMotorFront.getCurrentPosition());
            telemetry.update();
            leftMotorFront.setPower(-power);
            leftMotorBack.setPower(-power);
            rightMotorFront.setPower(power);
            rightMotorBack.setPower(power);
        }
        runtime.reset();
        while(runtime.milliseconds() < 100) {
            leftMotorFront.setPower(.1);
            leftMotorBack.setPower(.1);
            rightMotorFront.setPower(-.1);
            rightMotorBack.setPower(-.1);
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
    /**
     * Initialize the Vuforia localization engine.
     */
    private void initVuforia() {
        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         */
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection = CameraDirection.BACK;

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        // Loading trackables is not necessary for the Tensor Flow Object Detection engine.
    }

    /**
     * Initialize the Tensor Flow Object Detection engine.
     */
    private void initTfod() {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_GOLD_MINERAL, LABEL_SILVER_MINERAL);
    }
}

