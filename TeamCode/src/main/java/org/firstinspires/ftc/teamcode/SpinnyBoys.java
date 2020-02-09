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
public class SpinnyBoys extends GodFatherOfAllTeleOp {

    // servos
    private Servo spinnyBoy1 = null;
    private Servo spinnyBoy2 = null;

    // toggle
    private boolean spinnyBoysDown = null;

    //positions
    private double upSpinny1 = null;
    private double upSpinny2 = null;
    private double downSpinny1 = null;
    private double downSpinny2 = null;

    // constructor sets everything to what it should be by default and takes servo names
    public SpinnyBoys(String spinnyBoy1, String spinnyBoy2, double up1, double up2, double down1, double down2){
        this.spinnyBoy1 = hardwareMap.get(Servo.class, spinnyBoy1);
        this.spinnyBoy2 = hardwareMap.get(Servo.class, spinnyBoy2);
        this.upSpinny1 = up1;
        this.upSpinny2 = up2;
        this.downSpinny1 = down1;
        this.downSpinny2 = down2;
    }
    public SpinnyBoys(String spinnyBoy1, String spinnyBoy2, double up, double down){
        this.SpinnyBoys(spinnyBoy1, spinnyBoy2, up, up, down, down);
    }
    public SpinnyBoys(String spinnyBoy1, String spinnyBoy2) {
        this.SpinnyBoys(spinnyBoy1, spinnyBoy2, .7, 1, .2, .2);
    }
    public void spinnyBoysUp() {
        this.spinnyBoy1.setPosition(this.upSpinny1);
        this.spinnyBoy2.setPosition(this.upSpinny2);
        this.spinnyBoysDown = false;
    }
    public void spinnyBoysDown() {
        this.spinnyBoy1.setPosition(this.downSpinny1);
        this.spinnyBoy2.setPosition(this.downSpinny2);
        this.spinnyBoysDown = true;
    }
    public void spinnyBoysToggle() {
        if (spinnyBoysDown == true) {
            this.spinnyBoysUp();
        }
        else{
            this.spinnyBoysDown();
        }
    }
    public boolean isDown() {
        return this.spinnyBoysDown;
    }
}