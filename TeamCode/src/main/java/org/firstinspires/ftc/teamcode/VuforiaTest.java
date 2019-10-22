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

@Autonomous(name="Vuforia Test", group="Autonomous Competition")
public class VuforiaTest extends GodFatherOfAllAutonomous {

    @Override
    public void runOpMode() {
        initVuforia();
        initTfod();

        if (tfod != null) {
            tfod.activate();
        }

        waitForStart();

        int skystone1Position = 0;
        int blockPosition = 0;

        List<Recognition> updatedRecognitions = null;
        runtime.reset();
        while(objects == 0 && runtime.seconds() < 6) {
            updatedRecognitions = tfod.getUpdatedRecognitions();
            if(updatedRecognitions != null) {
                objects = updatedRecognitions.size();
            }
        }
        if (objects != 0) {
            updatedRecognitions = tfod.getUpdatedRecognitions();
        }
        if (updatedRecognitions != null) {
            for (Recognition r : updatedRecognitions) {
                if (r.getLabel().equals(LABEL_SKYSTONE)) {
                    skystone1Position = (int) r.getTop();
                } else {
                    blockPosition = (int) r.getTop();
                }
            }
            telemetry.addData("Skystone: ", skystone1Position);
            telemetry.addData("Block: ", blockPosition);
            telemetry.update();
            tfod.shutdown();
            sleep()
        }
    }
}