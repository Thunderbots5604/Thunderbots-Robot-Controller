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
public class MecanumDrive extends GodFatherOfAllTeleOp {
    // motors
    private DcMotor leftMotorFront;
    private DcMotor leftMotorBack;
    private DcMotor rightMotorFront;
    private DcMotor rightMotorBack;

    // maneuverability stuff
    private int direction;
    private boolean halfSpeed;
    private boolean reverse;
    private double maneuverMultiplier;

    // powers and multipliers
    // mecanumPowers is [FRBL, FLBR]
    private double[] mecanumPowers;
    // turnDrivePowers is [FLBL, FRBR]
    private double[] turnDrivePowers;
    // driveMultipliers is [mecanum, turn]
    private double[] driveMultipliers;
    // motorPowers is [FL, FR, BL, BR]
    private double[] motorPowers;

    // previous and current values
    private boolean xButtonPrevious;
    private boolean yButtonPrevious;
    private double p1LeftStickXPrevious;
    private double p1LeftStickYPrevious;
    private double p1RightStickXPrevious;
    private boolean xButtonCurrent;
    private boolean yButtonCurrent;
    private double p1LeftStickXCurrent;
    private double p1LeftStickYCurrent;
    private double p1RightStickXCurrent;

    // constructor sets everything to what it should be by default and takes motor names
    public MecanumDrive(String leftMotorFront, String leftMotorBack, String rightMotorFront, String rightMotorBack){
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
        this.maneuverMultiplier = 1;
        //putting this in for now because I think it's necessary
        this.rightMotorFront.setDirection(DcMotorSimple.Direction.REVERSE);
        this.rightMotorBack.setDirection(DcMotorSimple.Direction.REVERSE);
        //set up current and previous values
        //make previous values be false and 0 because weird stuff will happen if you hold them on start otherwise
        xButtonPrevious = false;
        yButtonPrevious = false;
        p1LeftStickXPrevious = 0;
        p1LeftStickYPrevious = 0;
        p1RightStickXPrevious = 0;
        //set current values
        xButtonCurrent = gamepad1.x || gamepad2.x;
        yButtonCurrent = gamepad1.y || gamepad2.y;
        p1LeftStickXCurrent = gamepad1.left_stick_x;
        p1LeftStickYCurrent = gamepad1.left_stick_y;
        p1RightStickXCurrent = gamepad1.right_stick_x;
    }
    // halfSpeed (calling this will be handled by the big teleop, so we don't need to deal with checking if we should switch here)
    public void toggleHalfSpeed(){
        if (this.halfSpeed == false){
            this.halfSpeed = true;
        }
        else {
            this.halfSpeed = false;
        }
    }
    // reverse (calling this will be handled by the big teleop, so we don't need to deal with checking if we should switch here)
    public void toggleReverse() {
        if (this.reverse == false){
            this.reverse = true;
        }
        else {
            this.reverse = false;
        }
    }
    //changes direction for big method later
    public void changeDirection() {

    }
    // given polar coordinates, calculate mecanum drive power
    public void calculateMecanumDrivePower(double radius, double theta){
        // make everything in the top half and then set the radius to negative so we can cut down on conditionals
        if (theta < 0){
            theta += Math.PI;
            radius *= -1;
        }
        // calculate powers
        // quadrant 1 (or 3)
        if (theta >= 0 && theta <= Math.PI / 2) {
            this.mecanumPowers[0] = radius;
            this.mecanumPowers[1] = (Math.sin(theta) - Math.cos(theta)) * radius;
        }
        // quadrant 2 (or 4)
        else if (theta > Math.PI / 2) {
            this.mecanumPowers[0] = (Math.sin(theta) + Math.cos(theta)) * radius;
            this.mecanumPowers[1] = radius;
        }
    }
    // power for turns given x-coordinate of right stick
    public void calculateTurnDrivePower (double xTurn){
        this.turnDrivePowers[0] = xTurn;
        this.turnDrivePowers[1] = -xTurn;
    }
    // multipliers for turnDrive and mecanumDrive powers given joystick positions
    public void calculateDriveMultiplier (double leftX, double leftY, double rightX) {
        // if both are being used
        if ((leftX != 0 || leftY != 0) && x2 != 0){
            this.driveMultipliers[0] = .5;
            this.driveMultipliers[1] = .5;
        }
        // if left stick is being used
        else if (leftX != 0 || leftY != 0){
            this.driveMultipliers[0] = 1;
            this.driveMultipliers[1] = 0;
        }
        // if right stick is being used
        else if (rightX != 0){
            this.driveMultipliers[0] = 0;
            this.driveMultipliers[1] = 1;
        }
        // if neither is being used
        else{
            this.driveMultipliers[0] = 0;
            this.driveMultipliers[1] = 0;
        }
    }
    // calculates movement multiplier for halfSpeed and reverse
    public void calculateManeuverMultiplier(){
        // start by resetting to 1
        this.maneuverMultiplier = 1;
        // half it if it's halfspeed
        if (this.halfSpeed == true){
            this.maneuverMultiplier *= .5;
        }
        // negative if it's reverse
        if (this.reverse == true){
            this.maneuverMultiplier *= -1;
        }
    }
    // calculates the powers if everything else is set up
    public void calculatePowers(){
        // use mecanumPowers, turnDrivePowers, driveMultipliers, and maneuverMultiplier to generate motor-specific powers
        this.motorPowers[0] = ((this.mecanumPowers[0] * this.driveMultipliers[0]) + (this.turnDrivePowers[0] * this.driveMultipliers[1])) * this.maneuverMultiplier;
        this.motorPowers[1] = ((this.mecanumPowers[1] * this.driveMultipliers[0]) + (this.turnDrivePowers[1] * this.driveMultipliers[1])) * this.maneuverMultiplier;
        this.motorPowers[2] = ((this.mecanumPowers[1] * this.driveMultipliers[0]) + (this.turnDrivePowers[0] * this.driveMultipliers[1])) * this.maneuverMultiplier;
        this.motorPowers[3] = ((this.mecanumPowers[0] * this.driveMultipliers[0]) + (this.turnDrivePowers[1] * this.driveMultipliers[1])) * this.maneuverMultiplier;
        // in case we messed up and the maximum motor power is above 1
        double maxPower = maxValue(motorPowers);
        if (maxPower > 1){
            this.motorPowers[0] /= maxPower;
            this.motorPowers[1] /= maxPower;
            this.motorPowers[2] /= maxPower;
            this.motorPowers[3] /= maxPower;
        }
    }
    // runs everything to get all the variables set up
    public void updatePowers (double x1, double y, double x2) {
        double[] polarCoords = toPolar(x1, y);
        this.calculateMecanumDrivePower(polarCoords[0], polarCoords[1]);
        this.calculateTurnDrivePower(x2);
        this.calculateDriveMultiplier(x1, y, x2);
        this.calculatePowers();
    }
    // takes already set up powers and puts them onto the motors
    public void pushPowers () {
        this.leftMotorFront.setPower(motorPowers[0]);
        this.rightMotorFront.setPower(motorPowers[1]);
        this.leftMotorBack.setPower(motorPowers[2]);
        this.rightMotorBack.setPower(motorPowers[3]);
    }
    // does everything given joystick coordinates
    public void updateAndPushPowers(double x1, double y, double x2) {
        this.updatePowers(x1, y, x2);
        this.pushPowers();
    }
    public void updateCurrentValues() {
        this.xButtonCurrent = gamepad1.x || gamepad2.x;
        this.yButtonCurrent = gamepad1.y || gamepad2.y;
        this.p1LeftStickXCurrent = gamepad1.left_stick_x;
        this.p1LeftStickYCurrent = gamepad1.left_stick_y;
        this.p1RightStickXCurrent = gamepad1.right_stick_x;
    }
    public void updatePreviousValues() {
        this.xButtonPrevious = this.xButtonCurrent;
        this.yButtonPrevious = this.yButtonCurrent;
        this.p1LeftStickXPrevious = this.p1LeftStickXCurrent;
        this.p1LeftStickYPrevious = this.p1LeftStickYCurrent;
        this.p1RightStickXCurrent = this.p1RightStickXCurrent;
    }
    public void checkRunHalfSpeed(){
        if (this.xButtonCurrent != this.xButtonPrevious){
            this.toggleHalfSpeed();
        }
    }
    public void checkRunReverse() {
        if (this.yButtonCurrent != this.yButtonPrevious) {
            this.toggleReverse();
        }
    }
    public void checkRunDrive() {
        if ((this.p1LeftStickYCurrent != this.p1LeftStickYPrevious) || (this.p1LeftStickXCurrent != this.p1LeftStickXPrevious) || (this.p1RightStickXCurrent != this.p1RightStickXPrevious)){
            this.updateAndPushPowers(p1LeftStickXCurrent, p1LeftStickYCurrent, p1RightStickXCurrent);
        }
    }
    public void checkRunMecanumSystem() {
        this.updateCurrentValues();
        this.checkRunHalfSpeed();
        this.checkRunReverse();
        this.checkRunDrive();
        this.updatePreviousValues();
    }
    public boolean getHalfSpeed() {
        return this.halfSpeed;
    }
    public boolean getReverse() {
        return this.reverse;
    }
    public double getManeuverMultiplier() {
        this.calculateManeuverMultiplier();
        return this.maneuverMultiplier;
    }
    public double[] getMecanumPowers() {
        return this.mecanumPowers;
    }
    public double[] getTurnDrivePowers() {
        return this.turnDrivePowers;
    }
    public double[] getDriveMultipliers() {
        return this.driveMultipliers;
    }
    public double[] getMotorPowers() {
        return this.motorPowers;
    }
    public double getLeftFrontPower() {
        return this.motorPowers[0];
    }
    public double getRightFrontPower() {
        return this.motorPowers[1];
    }
    public double getLeftBackPower() {
        return this.motorPowers[2];
    }
    public double getRightBackPower() {
        return this.motorPowers[3];
    }
}