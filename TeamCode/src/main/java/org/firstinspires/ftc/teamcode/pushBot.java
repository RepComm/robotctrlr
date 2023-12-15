package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name="pushBot", group="Linear OpMode")
public class pushBot extends LinearOpMode {

    private DcMotor leftFront;
    private DcMotor leftBack;

    private DcMotor rightFront;

    private DcMotor rightBack;

    @Override
    public void runOpMode() throws InterruptedException {

        leftFront = hardwareMap.get(DcMotor.class, "front left");
        leftBack = hardwareMap.get(DcMotor.class, "back left");
        rightFront = hardwareMap.get(DcMotor.class, "front right");
        rightBack = hardwareMap.get(DcMotor.class, "back right");

        leftBack.setDirection(DcMotor.Direction.REVERSE);
        leftFront.setDirection(DcMotor.Direction.FORWARD);
        rightBack.setDirection(DcMotor.Direction.REVERSE);
        rightFront.setDirection(DcMotor.Direction.FORWARD);


        telemetry.addData("status", "initialized");

        waitForStart();

        while (opModeIsActive()) {
            double max;

            double strafe = -gamepad1.left_stick_x;
            double forwardBack = gamepad1.left_stick_y;
            double turn = gamepad1.right_stick_x;

            double leftFrontPower = strafe + forwardBack - turn;
            double rightFrontPower = strafe - forwardBack - turn;
            double leftBackPower = strafe - forwardBack + turn;
            double rightBackPower = strafe + forwardBack + turn;

            max = Math.max(Math.abs(leftFrontPower), Math.abs(rightFrontPower));
            max = Math.max(max, Math.abs(leftBackPower));
            max = Math.max(max, Math.abs(rightBackPower));

            if (max > 1.0) {
                leftFrontPower /= max;
                rightFrontPower /= max;
                leftBackPower /= max;
                rightBackPower /= max;

            }
        }
    }
}