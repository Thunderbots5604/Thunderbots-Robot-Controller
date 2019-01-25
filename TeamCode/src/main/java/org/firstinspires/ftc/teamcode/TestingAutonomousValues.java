package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import java.util.List;

@Autonomous(name="Testing Autonomous Values", group="Autonomous Competition")
public class TestingAutonomousValues extends LinearOpMode {

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

        waitForStart();

        initVuforia();

        if (ClassFactory.getInstance().canCreateTFObjectDetector()) {
            initTfod();
        } else {
            telemetry.addData("Sorry!", "This device is not compatible with TFOD");
        }

        if (tfod != null) {
            tfod.activate();
        }

        int silverPosition = 0;
        int goldPosition = 0;

        while (opModeIsActive()) {
            if (tfod != null) {
                tfod.activate();
            }
            List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
            if (updatedRecognitions != null) {
                telemetry.addData("# Object Detected", updatedRecognitions.size());

                for(Recognition r : updatedRecognitions) {
                    if(r.getLabel().equals(LABEL_GOLD_MINERAL)) {
                        goldPosition = (int) r.getTop();
                    }
                    else {
                        silverPosition = (int) r.getTop();
                    }
                }
            }

            if(goldPosition > silverPosition) {
                telemetry.addLine("Gold Mineral is on left");
                telemetry.addData("Gold Top", goldPosition);
                telemetry.addData("Silver Top", silverPosition);
            }
            else {
                telemetry.addLine("Gold Mineral is on right");
                telemetry.addData("Gold Top", goldPosition);
                telemetry.addData("Silver Top", silverPosition);
            }

            telemetry.update();
        }
    }
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

