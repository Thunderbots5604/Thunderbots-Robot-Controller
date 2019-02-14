package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="Autonomous Crater Only", group="Autonomous Competition")
public class AutonomousCraterOnly extends GodfatherOfAllAutonomous {

    private int location = -1;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();
//backwards goes more than forward
        initialization();

        waitForStart();

        detach();

        location = tfodDetection(4);
        if (location == 2) {
            turnRight(25,allPower);
            runTo(21, allPower);
            turnLeft(30,allPower);
            runTo(7,allPower);
        }
        if (location == 1) {
            turnLeft(18, allPower);
            runTo(24,allPower);
        }
        if (location == 0){
            turnLeft(56, allPower);
            runTo(33,allPower);
            sleep(500);
            runTo(-22,allPower);
            turnRight(150,allPower);
            sleep(250);
            turnRight(150,allPower);
            runTo(36,allPower);
            runTo(-3,allPower);
            turnLeft(85,allPower);
            runTo(20,allPower);
        }
        box1.setPower(.8);
        box2.setPower(-.8);
        sleep(1000);
        box1.setPower(0);
        box2.setPower(0);
    }
}