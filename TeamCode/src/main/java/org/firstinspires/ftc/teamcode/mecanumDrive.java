package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@Disabled
@TeleOp(name "No Use", group="TeleOp Competition")
public class mecanumDrive extends GodFatherOfAllTeleOp {
    //motors
    private DcMotor leftMotorFront;
    private DcMotor leftMotorBack;
    private DcMotor rightMotorFront;
    private DcMotor rightMotorBack;

    //maneuverability stuff
    private boolean halfSpeed;
    private boolean reverse;
    private double maneuverMultiplier;

    //powers and multipliers
    private double[] mecanumPowers;
    private double[] turnDrivePowers;
    private double[] driveMultipliers;
    private double[] motorPowers;

    //constructor sets everything to what it should be by default and takes motor names
    public mecanumDrive(Srting leftMotorFront, String leftMotorBack, String rightMotorFront, String rightMotorBack){
        this.leftMotorFront = hardwareMap.get(DcMotor.class, leftMotorFront);
        this.leftMotorBack = hardwareMap.get(DcMotor.class, leftMotorBack);
        this.rightMotorFront = hardwareMap.get(DcMotor.class, rightMotorFront);
        this.rightMotorBack = hardwareMap.get(DcMotor.class, rightMotorBack);
        this.halfSpeed = false;
        this.reverse = false;
        this.mecanumPowers = new double[2];
        this.turnDrivePowers = new double[2];
        this.driveMultipliers = new double[2];
        this.motorPowers = new double[4];
        this.multiplier = 1;
    }
    //halfSpeed
    public void toggleHalfSpeed(){
        if (this.halfSpeed == false){
            this.halfSpeed = true;
        }
        else {
            this.halfSpeed = false;
        }
    }
    //reverse
    public void toggleReverse{
        if (this.reverse == false){
            this.reverse = true;
        }
        else {
            this.reverse = false;
        }
    }
    // REWRITE THIS
    // given polar coordinates, calculate mecanum drive power
    public void calculateMecanumDrivePower(double radius, double theta){
        if (theta >=0 && theta < Math.PI / 2) {
            if (theta >= 0){
                mecanumPowers[0] = radius;
            }
        }
        /*// convert theta to make it easier
        // move 0 to forward on the joystick
        double convertedTheta = theta - (Math.PI / 2);
        // if we moved too far, rotate to the right 360 degrees
        if (closeEnough((convertedTheta, (-Math.PI))) || convertedTheta < (-Math.PI)) {
            convertedTheta += (2 * Math.PI);
        }
        // translate by pi / 4 to make the math work out
        convertedTheta -= Math.PI / 4;
        // actually calculate the values based on that
        // forward and left
        if (convertedTheta >= 0 && convertedTheta < Math.PI /2){
            this.mecanumPowers[0] = radius;
            this.mecanumPowers[1] = radius * Math.tan(convertedTheta);
        }
        // backwards and left
        else if (convertedTheta >= Math.PI / 2) {
            this.mecanumPowers[1] = -radius;
            this.mecanumPowers[0] = radius * Math.cot(convertedTheta);
        }
        // forwards and right
        else if (convertedTheta < 0 && convertedTheta >= (-Math.PI / 2)) {
            this.mecanumPowers[1] = radius;
            this.mecanumPowers[0] = radius * Math.cot(convertedTheta);
        }
        // backwards and right
        else if (convertedTheta < (-Math.PI / 2)) {
            this.mecanumPowers[0] = -radius;
            this.mecanumPowers[1] = radius * Math.tan(convertedTheta);*/
    }
    // power for turns given x-coordinate of right stick
    public void calculateTurnDrivePower (double xTurn){
        this.turnDrivePowers[0] = xTurn;
        this.turnDrivePowers[1] = -xTurn;
    }
    // multipliers for turnDrive and mecanumDrive powers given joystick positions
    public void calculateDriveMultiplier (double leftX, double leftY, double rightX) {
        // set up an array for output like [mecanum, rotate]
        if ((leftX != 0 || leftY != 0) && x2 != 0){
            this.driveMultipliers[0] = .5;
            this.driveMultipliers[1] = .5;
        }
        else if (leftX != 0 || leftY != 0){
            this.driveMultipliers[0] = 1;
            this.driveMultipliers[1] = 0;
        }
        else if (rightX != 0){
            this.driveMultipliers[0] = 0;
            this.driveMultipliers[1] = 1;
        }
        else{
            this.driveMultipliers[0] = 0;
            this.driveMultipliers[1] = 0;
        }
    }
    public void calculateManeuverMultiplier(){
        this.maneuverMultiplier = 1;
        if (this.halfSpeed == true){
            this.maneuverMultiplier *= .5;
        }
        if (this.reverse == true){
            this.maneuverMultiplier *= -1;
        }
    }
    public void calculatePowers(){
        this.motorPowers[0]  =
    }
    public void updatePowers (double x1, double y, double x2) {
        double[] polarCoords = toPolar(x1, y);
        this.calculateMecanumDrivePower(polarCoords[0], polarCoords[1]);
        this.calculateTurnDrivePower(x2);
        this.calculateDriveMultiplier(x1, y, x2);

    }
}