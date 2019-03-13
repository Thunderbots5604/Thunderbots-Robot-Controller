package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;


@TeleOp(name="TeleOp", group="Linear Opmode")
public class TeleOpTest extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime reversedCooldown = new ElapsedTime();
    private ElapsedTime halfSpeedCooldown = new ElapsedTime();

    private DcMotor leftMotorFront = null;
    private DcMotor leftMotorBack = null;
    private DcMotor rightMotorFront = null;
    private DcMotor rightMotorBack = null;

    private DcMotor horizontal = null;

    private DcMotor crane1 = null;
    private DcMotor crane2 = null;

    private CRServo wheel = null;
    private CRServo box1 = null;
    private CRServo box2 = null;

    private Servo elevator = null;

    private boolean reversed = false;
    private boolean halfSpeed = false;
    private double multiplier = 1;
    private boolean moved = false;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();


        leftMotorFront = hardwareMap.get(DcMotor.class, "left_motor_front");
        leftMotorBack = hardwareMap.get(DcMotor.class, "left_motor_back");
        rightMotorFront = hardwareMap.get(DcMotor.class, "right_motor_front");
        rightMotorBack = hardwareMap.get(DcMotor.class, "right_motor_back");

        horizontal = hardwareMap.get(DcMotor.class, "horizontal");

        wheel = hardwareMap.get(CRServo.class, "wheel");
        box1 = hardwareMap.get(CRServo.class, "right_crater");
        box2 = hardwareMap.get(CRServo.class, "left_crater");
        crane1 = hardwareMap.get(DcMotor.class, "crane_a");
        crane2 = hardwareMap.get(DcMotor.class, "crane_b");

        elevator = hardwareMap.get(Servo.class, "elevator");

        leftMotorFront.setDirection(DcMotorSimple.Direction.REVERSE);
        rightMotorBack.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();
        halfSpeedCooldown.reset();
        reversedCooldown.reset();
        while (opModeIsActive()) {

            telemetry.addData("Left Front Power: ", leftMotorFront.getPower());
            telemetry.addData("Left Back Power: ", leftMotorBack.getPower());
            telemetry.addData("Right Front Power: ", rightMotorFront.getPower());
            telemetry.addData("Right Back Power: ", rightMotorBack.getPower());
            telemetry.addData("Reversed: ", reversed);
            telemetry.addData("Half Speed: ", halfSpeed);
            telemetry.update();

            if(gamepad1.b && reversedCooldown.seconds() > .5) {
                reversed = !reversed;
                multiplier *= -1;
                reversedCooldown.reset();
            }
            if(gamepad1.x && halfSpeedCooldown.seconds() > .5) {
                halfSpeed = !halfSpeed;
                if(halfSpeed) {
                    multiplier *= .5;
                }
                else {
                    multiplier *= 2;
                }
                halfSpeedCooldown.reset();
            }

            //Moving Forward
            if (gamepad1.left_stick_y != 0 && gamepad1.right_stick_x == 0) {
                leftMotorFront.setPower(multiplier * gamepad1.left_stick_y);
                leftMotorBack.setPower(multiplier * gamepad1.left_stick_y);
                rightMotorFront.setPower(multiplier * gamepad1.left_stick_y);
                rightMotorBack.setPower(multiplier * gamepad1.left_stick_y);
            }

            //Point Turn
            else if (gamepad1.right_stick_x != 0 && gamepad1.left_stick_y == 0 & reversed == false) {
                leftMotorFront.setPower(-multiplier * gamepad1.right_stick_x);
                leftMotorBack.setPower(-multiplier * gamepad1.right_stick_x);
                rightMotorFront.setPower(multiplier * gamepad1.right_stick_x);
                rightMotorBack.setPower(multiplier * gamepad1.right_stick_x);
            }
            else if (gamepad1.right_stick_x != 0 && gamepad1.left_stick_y == 0 & reversed) {
                leftMotorFront.setPower(multiplier * gamepad1.right_stick_x);
                leftMotorBack.setPower(multiplier * gamepad1.right_stick_x);
                rightMotorFront.setPower(-multiplier * gamepad1.right_stick_x);
                rightMotorBack.setPower(-multiplier * gamepad1.right_stick_x);
            }

            //Swing Turn without Reverse
            else if (gamepad1.left_stick_y < 0 && gamepad1.right_stick_x > 0 && reversed == false) {
                leftMotorFront.setPower(-(Math.abs(gamepad1.right_stick_x) + Math.abs(gamepad1.left_stick_y) / 2));
                leftMotorBack.setPower(-(Math.abs(gamepad1.right_stick_x) + Math.abs(gamepad1.left_stick_y) / 2));
                rightMotorFront.setPower(0);
                rightMotorBack.setPower(0);
            }
            else if (gamepad1.left_stick_y > 0 && gamepad1.right_stick_x > 0 && reversed == false) {
                leftMotorFront.setPower((Math.abs(gamepad1.right_stick_x) + Math.abs(gamepad1.left_stick_y) / 2));
                leftMotorBack.setPower((Math.abs(gamepad1.right_stick_x) + Math.abs(gamepad1.left_stick_y) / 2));
                rightMotorFront.setPower(0);
                rightMotorBack.setPower(0);
            }
            else if (gamepad1.left_stick_y > 0 && gamepad1.right_stick_x < 0 && reversed == false) {
                leftMotorFront.setPower(0);
                leftMotorBack.setPower(0);
                rightMotorFront.setPower((Math.abs(gamepad1.right_stick_x) + Math.abs(gamepad1.left_stick_y) / 2));
                rightMotorBack.setPower((Math.abs(gamepad1.right_stick_x) + Math.abs(gamepad1.left_stick_y) / 2));
            }
            else if (gamepad1.left_stick_y < 0 && gamepad1.right_stick_x < 0 && reversed == false) {
                leftMotorFront.setPower(0);
                leftMotorBack.setPower(0);
                rightMotorFront.setPower(-(Math.abs(gamepad1.right_stick_x) + Math.abs(gamepad1.left_stick_y) / 2));
                rightMotorBack.setPower(-(Math.abs(gamepad1.right_stick_x) + Math.abs(gamepad1.left_stick_y) / 2));
            }

            //Swing Turn with Reverse
            else if (gamepad1.left_stick_y < 0 && gamepad1.right_stick_x > 0 && reversed) {
                leftMotorFront.setPower(0);
                leftMotorBack.setPower(0);
                rightMotorFront.setPower((Math.abs(gamepad1.right_stick_x) + Math.abs(gamepad1.left_stick_y) / 2));
                rightMotorBack.setPower((Math.abs(gamepad1.right_stick_x) + Math.abs(gamepad1.left_stick_y) / 2));
            }
            else if (gamepad1.left_stick_y > 0 && gamepad1.right_stick_x > 0 && reversed) {
                leftMotorFront.setPower(0);
                leftMotorBack.setPower(0);
                rightMotorFront.setPower(-(Math.abs(gamepad1.right_stick_x) + Math.abs(gamepad1.left_stick_y) / 2));
                rightMotorBack.setPower(-(Math.abs(gamepad1.right_stick_x) + Math.abs(gamepad1.left_stick_y) / 2));
            }
            else if (gamepad1.left_stick_y > 0 && gamepad1.right_stick_x < 0 && reversed) {
                leftMotorFront.setPower(-(Math.abs(gamepad1.right_stick_x) + Math.abs(gamepad1.left_stick_y) / 2));
                leftMotorBack.setPower(-(Math.abs(gamepad1.right_stick_x) + Math.abs(gamepad1.left_stick_y) / 2));
                rightMotorFront.setPower(0);
                rightMotorBack.setPower(0);
            }
            else if (gamepad1.left_stick_y < 0 && gamepad1.right_stick_x < 0 && reversed) {
                leftMotorFront.setPower((Math.abs(gamepad1.right_stick_x) + Math.abs(gamepad1.left_stick_y) / 2));
                leftMotorBack.setPower((Math.abs(gamepad1.right_stick_x) + Math.abs(gamepad1.left_stick_y) / 2));
                rightMotorFront.setPower(0);
                rightMotorBack.setPower(0);
            }
            else {
                leftMotorFront.setPower(0);
                leftMotorBack.setPower(0);
                rightMotorFront.setPower(0);
                rightMotorBack.setPower(0);
            }

            if (gamepad1.dpad_down || gamepad2.dpad_down) {
                crane1.setPower(-1);
                crane2.setPower(1);
            }
            else if (gamepad1.dpad_up || gamepad2.dpad_up) {
                crane1.setPower(1);
                crane2.setPower(-1);
            }
            else {
                crane1.setPower(0);
                crane2.setPower(0);
            }



            if (gamepad1.left_bumper || gamepad2.left_bumper) {
                horizontal.setPower(-.5);
            }
            else if (gamepad1.right_bumper || gamepad2.right_bumper) {
                horizontal.setPower(.5);
            }
            else {
                horizontal.setPower(0);
            }



            if (gamepad1.left_trigger > 0 || gamepad2.left_trigger > 0) {
                wheel.setPower(1);
            }
            else if (gamepad1.right_trigger > 0 || gamepad2.right_trigger > 0) {
                wheel.setPower(-1);
            }
            else {
                wheel.setPower(0);
            }



            if ((gamepad1.y || gamepad2.y) && elevator.getPosition() == .15) {
                elevator.setPosition(.8);
            }
            else if ((gamepad1.y || gamepad2.y) && elevator.getPosition() == .8) {
                elevator.setPosition(.15);
            }
            else if(gamepad1.y || gamepad2.y) {
                elevator.setPosition(.15);
            }


            if (gamepad1.dpad_left || gamepad2.dpad_left) {
                box1.setPower(-1);
                box2.setPower(1);
                //moved = true;
            }
            else if (gamepad1.dpad_right || gamepad2.dpad_right) {
                box1.setPower(1);
                box2.setPower(-1);
                //moved = false;
            }
            /*else if (moved == true) {
                box1.setPower(.05);
                box2.setPower(-.05);
            }*/
            else {
                box1.setPower(0);
                box2.setPower(0);
            }
        }
    }
}
