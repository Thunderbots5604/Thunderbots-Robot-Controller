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
public class Controller extends GodFatherOfAllTeleOp {
    //the controller that we're running off of
    private Object controllerName;
    //answer keys
    private String[] commandsAnswerKey;
    //previous values
    private boolean[] booleanPrevious;
    private double[] doublePrevious;
    //current values
    private boolean[] booleanCurrent;
    private double[] doubleCurrent;
    //commands to run
    private boolean[] commands;
    //commands that are going to be run
    private String[] commandsToRun;

    public Controller(Object controller){
        this.controllerName = controller;
        //If you want to map controls, do it here. For now, I just set up the drive system because I'm lazy and I don't know what I want yet
        //I have two toggles so far, so I'm putting two spots in this. If we get more, add more
        this.booleanCurrent = new boolean[2];
        //I'm setting these two to false to prevent wonky-ness that would result from pressing them when they were initialized
        //boolean[0] is x-button we'll say
        this.booleanCurrent[0] = false;
        //and boolean[1] is y-button
        this.booleanCurrent[1] = false;
        //clone the current to the previous to start with
        this.booleanPrevious = this.booleanCurrent.clone();
        //now do it for the double arrays
        //just using left stick x and y and right stick x so far so only need three values for now
        this.doubleCurrent = new double[3];
        //double[0] is left stick x
        this.doubleCurrent[0] = this.controllerName.left_stick_x;
        //double[1] is left stick y
        this.doubleCurrent[1] = this.controllerName.left_stick_y;
        //double[2] is right stick x
        this.doubleCurrent[2] = this.controllerName.right_stick_x;
        //clone current to previous to start with
        this.doublePrevious = this.doubleCurrent.clone();
        //set up commandsAnswerKey. We're going to make this a lot bigger in the future, but we just have three commands right now
        this.commandsAnswerKey = new String[3];
        //all this stuff is just what it says. [0] is halfspeed
        this.commandsAnswerKey[0] = "HALFSPEED";
        //[1] is direction
        this.commandsAnswerKey[1] = "CHANGEDIRECTION";
        //[2] is drive
        this.commandsAnswerKey[2] = "UPDATEDRIVE";
        //now we set up something to say which commands should be run
        this.commands = new boolean[3];
        this.commands[0] = false;
        this.commands[1] = false;
        this.commands[2] = false;
    }
    //updates current values to what they currently are
    public void updateCurrentValues() {
        this.booleanCurrent[0] = this.controllerName.x;
        this.booleanCurrent[1] = this.controllerName.y;
        this.doubleCurrent[0] = this.controllerName.left_stick_x;
        this.doubleCurrent[1] = this.controllerName.left_stick_y;
        this.doubleCurrent[2] = this.controllerName.right_stick_x;
    }
    //sets previous values to current values
    public void updatePreviousValues() {
        this.booleanPrevious = this.booleanCurrent.clone();
        this.doublePrevious = this.doubleCurrent.clone();
    }
    //updates commands based on what's different between current and previous
    public void updateCommands() {
        if (this.booleanCurrent[0] != this.booleanPrevious[0]) {
            this.commands[0] = true;
        }
        if (this.booleanCurrent[1] != this.booleanPrevious[1]) {
            this.commands[1] = true;
        }
        if ((this.doubleCurrent[0] != this.doublePrevious[0]) || (this.doubleCurrent[1] != this.doublePrevious[1]) || (this.doubleCurrent[2] != this.duoblePrevious[2])) {
            this.commands[2] = true;
        }
    }
    //makes an array of commands to run using commandsAnswerKey and commands
    //the array should be of a length equal to the number of trues in the commands list
    public void setUpCommandsToRun() {
        int numberOfCommands = 0;
        for (int commandsLocation = 0; commandsLocation < this.commands.length; commandsLocation++) {
            if (this.commands[commandsLocation] == true) {
                numberOfCommands += 1;
            }
        }
        this.commandsToRun = new String[numberOfCommands];
    }
    public void valuesForCommandsToRun() {
        int commandsToRunLocation = 0;
        for (int commandsLocation = 0; commandsLocation < this.commands.length; commandsLocation++) {
            if (this.commands[commandsLocation] == true) {
                this.commandsToRun[commandsToRunLocation] = this.commandsAnswerKey[commandsLocation];
                commandsToRunLocation += 1;
            }
        }
    }
    //brings it all together
    public String[] whatToRun() {
        updateCurrentValues();
        updateCommands();
        setUpCommandsToRun();
        valuesForCommandsToRun();
        updatePreviousValues();
        return this.commandsToRun;
    }
}