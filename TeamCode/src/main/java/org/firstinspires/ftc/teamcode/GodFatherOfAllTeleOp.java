package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;

import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.Arrays;


public abstract class GodFatherOfAllTeleOp extends LinearOpMode {
    //some angle measuring stuff
    public BNO055IMU imu;
    public ElapsedTime angleRuntime = new ElapsedTime();
    public double heading = 0;
    public Orientation angles = null;
    public double angleAdjust = 0;

    public void telinitialization() {
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibration sample opmode
        parameters.loggingEnabled = true;
        parameters.loggingTag = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();

        imu = hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);

        angleAdjust = HeadingHolder.getLastHeading();

    }
    //getAngle
    public double formatAngle(AngleUnit angleUnit, double angle) {
        return AngleUnit.DEGREES.fromUnit(angleUnit, angle);
    }
    public double getAngle() {
        heading = 0;
        angleRuntime.reset();
        while (angleRuntime.milliseconds() < 1500 && heading == 0) {
            angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
            heading = this.formatAngle(angles.angleUnit, angles.firstAngle);
        }
        return heading - angleAdjust;
    }
    // converts cartesian coordinates to polar coordinates
    public double[] toPolar(double x, double y){
        double[] polarCoordinates = new double[2];
        // radius
        polarCoordinates[0] = Math.sqrt((x * x) + (y * y));
        // angle
        polarCoordinates[1] = Math.atan2(y, x);
        return polarCoordinates;
    }
    // check if two values are close enough to be considered equal (using radians isn't perfectly accurate in java)
    public boolean closeEnough(double a, double b){
        if ((a <= b - 0.000000000009) && (a >= b + 0.000000000009)) {
            return true;
        }
        else {
            return false;
        }
    }
    public double maxValue(double[] array){
        double max = array[0];
        for(int i = 1; i < array.length; i++){
            if (array[i] > max) {
                max = array[i];
            }
        }
        return max;
    }
}