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
    
    
    //Still Needs Testing.
    
    
    @Autonomous(name="AutoEverythingRed", group="All")
    public class AutoEverythingRed extends GodFatherOfAllAutonomous {
        private String color = null;
        private boolean red = true;
        @Override
        public void runOpMode() {
    
            initialization();
            
            waitForStart();
            
            autoTime.reset();
            
            spinnyBoyUp();
            resetArm();
            
            startBlock(red);
            turnRight(10, allPower, slowPower);
            runTo(30 + 8 * (blockNumber), 1, allPower);
            strafeRight(5, allPower, slowPower);
            //if (blockNumber != 2) {
                //runTo(10, allPower, slowPower);
            //}
            raiseAndRun(1, 50, allPower * 1.1, slowPower);
            turnLeft(80, allPower * 1.1, slowPower);
            turnTo(0, allPower, slowPower);
            turnRight(8, allPower, slowPower);
            runTo(12, allPower * .7, slowPower * .9);
            dropBlock();
            moveFoundation(red);
            
            strafeLeft(11, allPower, slowPower);
            raiseAndRun(0, -40, allPower * 1.2, slowPower);
            turnTo(-90, allPower, slowPower);
            runTo(-48 - 8 * (blockNumber), .9, allPower * .8);
            resetArm();
            turnLeft(80, allPower * 1.1, slowPower);
            turnTo(0, allPower, slowPower);
            //Branch off based on blockNumber
            if (blockNumber == 0) {
                strafeLeft(25, allPower, slowPower);
                runUntil(80, slowPower);
                runTo(2, allPower * .8, slowPower);
                turnTo(0, allPower, slowPower);
                pickUpBlock();
                runTo(-3, allPower, slowPower);
                turnRight(80, allPower * 1.1, slowPower);
                turnTo(-90, allPower, slowPower);
                runTo(66 + 8 * (blockNumber), .9, allPower * .8);
                raiseAndRun(1, 30, allPower, slowPower);
                dropBlock();
                raiseAndRun(0, -30, allPower, slowPower);
                runTo(-10, allPower, slowPower);
            }
            else if (blockNumber == 1) {
                strafeLeft(24, allPower, slowPower);
                runUntil(80, slowPower);
                runTo(2, allPower * .8, slowPower);
                turnTo(0, allPower, slowPower);
                pickUpBlock();
                runTo(-3, allPower, slowPower);
                turnRight(80, allPower * 1.1, slowPower);
                turnTo(-90, allPower, slowPower);
                runTo(74 + 8 * (blockNumber), .9, allPower * .8);
                dropBlock();
                runTo(-15, allPower, slowPower);
            }
            else {
                strafeLeft(16, allPower, slowPower);
                turnTo(20, allPower, slowPower);
                runTo(5, allPower, slowPower);
                runTo(5, allPower * .9, slowPower);
                pickUpBlock();
                runTo(-3, allPower, slowPower);
                turnRight(80, allPower * 1.1, slowPower);
                turnTo(-90, allPower, slowPower);
                runTo(74 + 8 * (blockNumber), .9, allPower * .8);
                dropBlock();
                runTo(-15, allPower, slowPower);
            }
        }
    }
    
    
    
