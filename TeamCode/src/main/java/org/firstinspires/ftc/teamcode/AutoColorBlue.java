package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
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


//Basically Pseudo code at this point. We need to have the robot arm methods and make some other stuff before something like this will work


@Autonomous(name="AutoColorBlue", group="Block side")
public class AutoColorBlue extends GodFatherOfAllAutonomous {
    private String color = null;
    private int blockNumber = 6;
    private boolean wall = false;
    private int side = 2;
    @Override
    public void runOpMode() {

        initialization();

        waitForStart();

        strafeLeft(16, allPower, slowPower);
        strafeLeftUntil(2.2, allPower);
        color = senseColor(side);
        if (color.equals("Yellow")) {
            blockNumber -= 1;
            runTo(8, allPower, slowPower);
            color = senseColor(side);
            if (color.equals("Yellow")) {
                blockNumber -= 1;
                runTo(-8, allPower, slowPower);
            }
        }
        if (blockNumber > 4) {
            runTo(-16, allPower, slowPower);
        }
        strafeLeft(16, allPower, slowPower);
        runTo(2, allPower, slowPower);
        pickUpBlock();
    }
}