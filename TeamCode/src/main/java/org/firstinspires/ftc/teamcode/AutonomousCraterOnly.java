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
            turnRight(10,allPower);
            runTo(20, allPower);
            turnLeft(30,allPower);
            runTo(5,allPower);
        }
        if (location == 1) {
            turnLeft(10, allPower);
            runTo(22,allPower);
        }
        if (location == 0){
            turnLeft(56, allPower);
            runTo(30,allPower);
            turnRight(30,allPower);
            runTo(5,allPower);
        }

        telemetry.addData("Location: ", location);
        telemetry.update();
    }
}