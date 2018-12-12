package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;



//Left motors are now right physically
//Right motors are now left physically


@TeleOp(name="TeleOp", group="Linear Opmode")
public class TeleOpTest extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor leftMotorFront = null;
    private DcMotor leftMotorBack = null;
    private DcMotor rightMotorFront = null;
    private DcMotor rightMotorBack = null;
    private DcMotor crane = null;
    private DcMotor crater = null;
    private CRServo wheel = null;/*
    private Servo elevator = null;*/
    private boolean reversed = false;
    private int multiplier = 1;
    private final double INCHES_PER_TICK = .0223147377;
    private final double DEGREES_PER_TICK = .1525087902;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();


        leftMotorFront = hardwareMap.get(DcMotor.class, "left_motor_front");
        leftMotorBack = hardwareMap.get(DcMotor.class, "left_motor_back");
        rightMotorFront = hardwareMap.get(DcMotor.class, "right_motor_front");
        rightMotorBack = hardwareMap.get(DcMotor.class, "right_motor_back");
        crane = hardwareMap.get(DcMotor.class, "crane");
        crater = hardwareMap.get(DcMotor.class, "crater");
        wheel = hardwareMap.get(CRServo.class, "wheel");/*
        elevator = hardwareMap.get(Servo.class, "elevator");*/

        rightMotorFront.setDirection(DcMotor.Direction.REVERSE);
        rightMotorBack.setDirection(DcMotor.Direction.REVERSE);

        waitForStart();
        runtime.reset();/*
        elevator.setPosition(.15);*/

        while (opModeIsActive()) {

            telemetry.addData("Left Power: ", leftMotorFront.getPower());
            telemetry.addData("Right Power: ", rightMotorFront.getPower());
            telemetry.addData("Reversed: ", reversed);
            telemetry.update();

            if(gamepad1.b && runtime.seconds() > 1) {
                if(reversed == true) {
                    multiplier = 1;
                    reversed = false;
                    runtime.reset();
                }
                else if(reversed == false) {
                    reversed = true;
                    multiplier = -1;
                    runtime.reset();
                }
            }

            if (gamepad1.left_stick_y != 0 && gamepad1.right_stick_x == 0) {
                leftMotorFront.setPower(multiplier * gamepad1.left_stick_y);
                leftMotorBack.setPower(multiplier * gamepad1.left_stick_y);
                rightMotorFront.setPower(multiplier * gamepad1.left_stick_y);
                rightMotorBack.setPower(multiplier * gamepad1.left_stick_y);
            }
            if (gamepad1.right_stick_x != 0 && gamepad1.left_stick_y == 0) {
                leftMotorFront.setPower(gamepad1.right_stick_x);
                leftMotorBack.setPower(gamepad1.right_stick_x);
                rightMotorFront.setPower(-gamepad1.right_stick_x);
                rightMotorBack.setPower(-gamepad1.right_stick_x);
            }
            if (reversed == false && gamepad1.left_stick_y < 0 && gamepad1.right_stick_x < 0) {
                leftMotorFront.setPower(-(Math.abs(gamepad1.right_stick_x) + Math.abs(gamepad1.left_stick_y) / 2));
                leftMotorBack.setPower(-(Math.abs(gamepad1.right_stick_x) + Math.abs(gamepad1.left_stick_y) / 2));
                rightMotorFront.setPower(0);
                rightMotorBack.setPower(0);
            }
            if (reversed == true && gamepad1.left_stick_y < 0 && gamepad1.right_stick_x < 0) {
                leftMotorFront.setPower(0);
                leftMotorBack.setPower(0);
                rightMotorFront.setPower(Math.abs(gamepad1.right_stick_x) + Math.abs(gamepad1.left_stick_y)/2);
                rightMotorBack.setPower(Math.abs(gamepad1.right_stick_x) + Math.abs(gamepad1.left_stick_y)/2);
            }
            if (gamepad1.left_stick_y > 0 && gamepad1.right_stick_x < 0) {
                leftMotorFront.setPower(0);
                leftMotorBack.setPower(0);
                rightMotorFront.setPower(Math.abs(gamepad1.right_stick_x) + Math.abs(gamepad1.left_stick_y)/2);
                rightMotorBack.setPower(Math.abs(gamepad1.right_stick_x) + Math.abs(gamepad1.left_stick_y)/2);
            }
            if (reversed == false && gamepad1.left_stick_y < 0 && gamepad1.right_stick_x > 0) {
                leftMotorFront.setPower(0);
                leftMotorBack.setPower(0);
                rightMotorFront.setPower(-(Math.abs(gamepad1.right_stick_x) + Math.abs(gamepad1.left_stick_y)/2));
                rightMotorBack.setPower(-(Math.abs(gamepad1.right_stick_x) + Math.abs(gamepad1.left_stick_y)/2));
            }
            if (reversed == true && gamepad1.left_stick_y < 0 && gamepad1.right_stick_x > 0) {
                leftMotorFront.setPower((Math.abs(gamepad1.right_stick_x) + Math.abs(gamepad1.left_stick_y)/2));
                leftMotorBack.setPower((Math.abs(gamepad1.right_stick_x) + Math.abs(gamepad1.left_stick_y)/2));
                rightMotorFront.setPower(0);
                rightMotorBack.setPower(0);
            }
            if (gamepad1.left_stick_y > 0 && gamepad1.right_stick_x > 0) {
                leftMotorFront.setPower(Math.abs(gamepad1.right_stick_x) + Math.abs(gamepad1.left_stick_y)/2);
                leftMotorBack.setPower(Math.abs(gamepad1.right_stick_x) + Math.abs(gamepad1.left_stick_y)/2);
                rightMotorFront.setPower(0);
                rightMotorBack.setPower(0);
            }
            if(gamepad1.dpad_down || gamepad2.dpad_down){
                crane.setPower(-1);
            }
            if(gamepad1.dpad_up || gamepad2.dpad_up) {
                crane.setPower(1);
            }
            if(gamepad1.left_bumper || gamepad2.left_bumper) {
                crater.setPower(0.5);
            }
            if(gamepad1.right_bumper || gamepad2.right_bumper) {
                crater.setPower(-0.5);
            }
            if(gamepad1.left_trigger > 0){
                wheel.setPower(1);
            }
            if(gamepad1.right_trigger > 0){
                wheel.setPower(-1);
            }
            /*
            if(gamepad1.x){
                elevator.setPosition(.15);
            }
            if(gamepad1.y){
                elevator.setPosition(0.7);
            }*/
            else{
                leftMotorFront.setPower(0);
                leftMotorBack.setPower(0);
                rightMotorFront.setPower(0);
                rightMotorBack.setPower(0);
                crane.setPower(0);
                crater.setPower(0);
                wheel.setPower(0);
            }
        }
    }
}
