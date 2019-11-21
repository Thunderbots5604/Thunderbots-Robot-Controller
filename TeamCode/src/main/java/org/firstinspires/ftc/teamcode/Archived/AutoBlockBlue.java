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



@Autonomous(name="Autonomous Blue", group="Autonomous Competition")
public class AutoBlockBlue extends GodFatherOfAllAutonomous {

    private String color = "Blue";

    @Override
    public void runOpMode() {
        initialization();
        int[] skystoneLocation = objectDetect(color);
        int skystone1 = skystoneLocation[0];
        int skystone2 = skystoneLocation[1];

        telemetry.addData("Skystone1 ", skystone1);
        telemetry.addData("Skystone2 ", skystone2);
        telemetry.update();

        //Rig the system for testing
        /*int skystone1 = 3;
        int skystone2 = skystone1 + 3;*/

        waitForStart();

        if (skystone1 == 1){
            turnLeft (15, allPower);
            runTo (20, allPower);
            sleep(1000);
            runTo(3, .25);
            sleep(500);
            runTo(3, .2);
            armDown();
            sleep(500);
            runTo (-12, allPower);
            sleep(1000);
            turnLeft(75, .4);
            runTo (35, allPower);
            runTo (15, allPower / 2);
            turnRight(60, allPower);
            runTo(30, allPower);
            armUp();
            runTo(-30, allPower);
            turnLeft(60, allPower);
            sleep(500);
            turnRight(10, allPower);
            runTo (-40, allPower);
            runTo (-27, allPower / 2);
            turnRight(105, .4);
            runTo(7, allPower / 2);
            sleep(500);
            runTo(5, .2);
            armDown();
            sleep(500);
            runTo(-12, allPower);
            turnLeft(85, allPower);
            runTo(35, allPower);
            runTo(15, allPower / 2);
            turnRight(60, allPower);
            runTo(30, allPower);
            armUp();
            runTo(-30, allPower);
            turnLeft(60, allPower);
        }
        else if (skystone1 == 2){
            turnRight (5, allPower);
            runTo (20, allPower);
            sleep(1000);
            runTo(3, .25);
            sleep(500);
            runTo(3, .2);
            armDown();
            sleep(500);
            runTo (-12, allPower);
            sleep(1000);
            turnLeft (75, .4);
            runTo (35, allPower);
            runTo (15, allPower / 2);
            turnRight(60, allPower);
            runTo(30, allPower);
            armUp();
            runTo(-30, allPower);
            turnLeft(60, allPower);
            sleep(500);
            turnRight(10, allPower);
            runTo (-40, allPower);
            runTo (-27, allPower / 2);
            turnRight(90, .4);
            runTo(7, allPower / 2);
            sleep(500);
            runTo(5, .2);
            armDown();
            sleep(500);
            runTo(-12, allPower);
            turnLeft(85, allPower);
            runTo(35, allPower);
            runTo(15, allPower / 2);
            turnRight(60, allPower);
            runTo(30, allPower);
            armUp();
            runTo(-30, allPower);
            turnLeft(60, allPower);
        }
        else {
            turnRight (20, allPower);
            runTo (20, allPower);
            sleep(1000);
            runTo(3, .25);
            sleep(500);
            runTo(3, .2);
            armDown();
            sleep(500);
            runTo (-12, allPower);
            sleep(1000);
            turnLeft (95, .4);
            runTo (35, allPower);
            runTo (15, allPower / 2);
            turnRight(60, allPower);
            runTo(30, allPower);
            armUp();
            runTo(-30, allPower);
            turnLeft(60, allPower);
            sleep(500);
            turnRight(10, allPower);
            runTo (-40, allPower);
            runTo (-27, allPower / 2);
            turnRight(105, .4);
            runTo(7, allPower / 2);
            sleep(500);
            runTo(5, .2);
            armDown();
            sleep(500);
            runTo(-12, allPower);
            turnLeft(95, allPower);
            runTo(35, allPower);
            runTo(15, allPower / 2);
            turnRight(60, allPower);
            runTo(30, allPower);
            armUp();
            runTo(-30, allPower);
            turnLeft(60, allPower);
        }
    }
}