//literally just stores a single variable, what the last heading was during teleop
@Disabled
public class HeadingHolder extends LinearOpMode {
    //set a static heading variable
    private static int lastHeading = 0;
    //set method
    public void setLastHeading(int heading) {
        this.lastHeading = heading;
    }
    public int getLastHeading() {
        return this.lastHeading;
    }
}