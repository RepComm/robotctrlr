package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Autonomous
public class autoBot extends LinearOpMode {

    protected DcMotor frontLeft;
    protected DcMotor backLeft;
    protected DcMotor frontRight;
    protected DcMotor backRight;

    @Override
    public void runOpMode() throws InterruptedException {

       frontLeft = hardwareMap.get(DcMotor.class,"front left");
        frontRight = hardwareMap.get(DcMotor.class,"front right");
        backLeft = hardwareMap.get(DcMotor.class,"back left");
        backRight = hardwareMap.get(DcMotor.class,"back right");

        frontLeft.setDirection(DcMotor.Direction.FORWARD);
        frontRight.setDirection(DcMotor.Direction.REVERSE);
        backLeft.setDirection(DcMotor.Direction.FORWARD);
        backRight.setDirection(DcMotor.Direction.REVERSE);

        waitForStart();

        frontLeft.setPower(1);
    }


}
