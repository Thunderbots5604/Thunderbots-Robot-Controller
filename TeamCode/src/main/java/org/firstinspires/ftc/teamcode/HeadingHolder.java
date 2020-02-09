//literally just stores a single variable, what the last heading was during teleop
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Disabled
public abstract class HeadingHolder extends LinearOpMode {
    //set a static heading variable
    private static double lastHeading = 0;
    //set method
    public void setLastHeading(int heading) {
        lastHeading = heading;
    }
    public double getLastHeading() {
        return lastHeading;
    }
}