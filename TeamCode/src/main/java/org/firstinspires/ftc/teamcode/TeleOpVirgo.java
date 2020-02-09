package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="TeleOpVirgo", group="Linear Opmode")
public class TeleOpVirgo extends GodFatherOfAllTeleOp {

    @Override
    public void runOpMode() {
        telinitialization();

        public MecanumDrive mecanumDrive = new MecanumDrive("left_motor_front", "left_motor_back", "right_motor_front", "right_motor_back");
        public SpinnyBoys spinnyBoys = new SpinnyBoys("spin1", "spin2");
        public CapstoneArm capstoneArm = new CapstoneArm("markServo", .9, .1);

        while(OpModeIsActive()){
            mecanumDrive.checkRunMecanumSystem();
            spinnyBoys.checkRun();
            capstoneArm.checkRun();
        }
        mecanumDrive.stop();
    }
}
