
package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;




@TeleOp(name = "Sensor Test", group = "Sensor")

public class GyroTest extends LinearOpMode
{
    BNO055IMU imu;
    Orientation angles;

    @Override public void runOpMode() {
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibration sample opmode
        parameters.loggingEnabled = true;
        parameters.loggingTag = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();

        // Retrieve and initialize the IMU. We expect the IMU to be attached to an I2C port
        // on a Core Device Interface Module, configured to be a sensor of type "AdaFruit IMU",
        // and named "imu".
        imu = hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);

        // Wait until we're told to go
        waitForStart();
    }

    Double formatAngle(AngleUnit angleUnit, double angle) {
        return AngleUnit.DEGREES.fromUnit(angleUnit, angle);
    }

    public void turnRight(double degrees, double power) {
        angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        double heading = formatAngle(angles.angleUnit, angles.firstAngle);

        double finalAngle;
        if(heading - degrees <= -180) {
            degrees += (-180 - heading);
            finalAngle = 180 - degrees;
        }
        else {
            finalAngle = heading - degrees;
        }
        while(heading < finalAngle - 10 || heading > finalAngle + 10) {
            angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
            heading = formatAngle(angles.angleUnit, angles.firstAngle);
            telemetry.addData("Heading: ", heading);
            telemetry.addData("Target: ", finalAngle);
            telemetry.update();
        }
        while(heading < finalAngle - 2 || heading > finalAngle + 2) {
            angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
            heading = formatAngle(angles.angleUnit, angles.firstAngle);
            telemetry.addData("Heading: ", heading);
            telemetry.addData("Target: ", finalAngle);
            telemetry.update();
        }
    }
    public void turnLeft(double degrees, double power) {
        angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        double heading = formatAngle(angles.angleUnit, angles.firstAngle);

        double finalAngle;
        if(heading + degrees >= 180) {
            degrees -= (180 - heading);
            finalAngle = -180 + degrees;
        }
        else {
            finalAngle = heading - degrees;
        }
        while(heading < finalAngle - 10 || heading > finalAngle + 10) {
            angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
            heading = formatAngle(angles.angleUnit, angles.firstAngle);
            telemetry.addData("Heading: ", heading);
            telemetry.addData("Target: ", finalAngle);
            telemetry.update();
        }
        while(heading < finalAngle - 2 || heading > finalAngle + 2) {
            angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
            heading = formatAngle(angles.angleUnit, angles.firstAngle);
            telemetry.addData("Heading: ", heading);
            telemetry.addData("Target: ", finalAngle);
            telemetry.update();
        }
    }
}
