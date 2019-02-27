package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;

@Autonomous(name="Gyro Crater-Depot", group="Gyro")
public class GyroCraterToDepot extends GodfatherOfAllAutonomous {

    @Override
    public void runOpMode() {

        int cooldown = 1000;
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        initialization();

        location = objectDetect();

        waitForStart();

        detach();
        double heading = 0;
        double turn;
        while (runtime.milliseconds() < 5000 && heading == 0) {
            angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
            heading = formatAngle(angles.angleUnit, angles.firstAngle);
        }
        turn = ((Math.abs(33 + heading))*1.1);
        telemetry.addData("Runtime", runtime.milliseconds());
        telemetry.addData("Heading: ", heading);
        telemetry.addData("Turning: ", turn);
        telemetry.update();
        if (heading > -33.0) {
            turnRight(turn, allPower);
        }
        else if (heading < -33.0) {
            turnLeft(turn, allPower);
        }
        //detach finished

        //Auto Crater only
        sleep(cooldown);
        if (location == 2) {
            //Hit Marker
            runTo(22, allPower);
            sleep(100);
            //Go Back
            runTo(-14,allPower);
            //Adjust
            turnLeft(46,allPower);
            runTo(2.5,allPower);
            turnLeft(69,allPower);
            runTo(45,allPower);
            // Go to Depot
            turnLeft(35,allPower);
            runTo(30,allPower);
            //Drop marker
            sleep(100);
            dropMarker();
            //Run back to crater to park
            runTo(-55,1);
            runTo(-10,allPower * .8);
        }
        else if (location == 1) {
            runTo(5, allPower);
            sleep(cooldown);
            turnLeft(32, allPower);
            sleep(cooldown);
            runTo(16.6, allPower);
            runTo(-13,allPower);
            turnLeft(100,allPower);
            runTo(39,allPower);
            turnLeft(22,allPower);
            runTo(11,allPower);
            turnLeft(20,allPower);
            runTo(26,allPower);
            //Drop marker
            sleep(100);
            dropMarker();
            //Run back to crater to park
            runTo(-55,1);
            runTo(-10,allPower * .8);
        }
        else if (location == 0){
            runTo(5, allPower);
            sleep(cooldown);
            turnLeft(120, allPower);
            runTo(3,allPower);
            sleep(cooldown);
            turnRight(86,allPower);
            runTo(20, allPower * .85);
            runTo(-6,allPower);
            turnLeft(30,allPower);
            runTo(16.6,allPower);
            turnLeft(60,allPower);
            runTo(10,allPower);
            turnLeft(30,allPower);
            runTo(33,allPower);
            //Drop marker
            sleep(100);
            dropMarker();
            //Run back to crater to park
            runTo(-55,1);
            runTo(-10,allPower * .8);
        }
    }
}