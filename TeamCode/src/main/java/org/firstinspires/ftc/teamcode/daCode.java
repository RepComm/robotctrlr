package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

//import com.qualcomm.robotcore.util.ElapsedTime;
@TeleOp(name="daCode", group="Linear OpMode")
public class daCode extends LinearOpMode {
//    private ElapsedTime runtime = new ElapsedTime();

    private DcMotor leftFront;

    private DcMotor leftBack;

    private DcMotor rightFront;

    private DcMotor rightBack;

    private Servo launcher;

    private DcMotor slide;

    private Servo rightWrist;

    private Servo leftWrist;

    private Servo rightClaw;

    private Servo leftClaw;

    private DcMotor liftMotor;
    private int liftMotorMin = 0;
    private int liftMotorMax = 3500;
    private int liftMotorTarget = 0;
    private int liftMotorAdjust = 3;

//    private DcMotor linear;

    int slideLowest = 0;
    int slideHighest = 3000;

    int slideTarget = slideLowest;

    int slideAdjust = 5;

    @Override
    public void runOpMode() {

        leftFront = hardwareMap.get(DcMotor.class, "front left");
        leftBack = hardwareMap.get(DcMotor.class, "back left");
        rightFront = hardwareMap.get(DcMotor.class, "front right");
        rightBack = hardwareMap.get(DcMotor.class, "back right");

        launcher = hardwareMap.get(Servo.class, "launcher");
        slide = hardwareMap.get(DcMotor.class, "Slide");
        rightWrist = hardwareMap.get(Servo.class, "right wrist");
        leftWrist = hardwareMap.get(Servo.class, "Left Wrist");
        rightClaw = hardwareMap.get(Servo.class, "right claw");
        leftClaw = hardwareMap.get(Servo.class, "left claw");

        leftBack.setDirection(DcMotor.Direction.REVERSE);
        leftFront.setDirection(DcMotor.Direction.FORWARD);
        rightBack.setDirection(DcMotor.Direction.REVERSE);
        rightFront.setDirection(DcMotor.Direction.FORWARD);

        rightWrist.setDirection(Servo.Direction.REVERSE);
        leftWrist.setDirection(Servo.Direction.FORWARD);

        rightClaw.setDirection(Servo.Direction.REVERSE);
        leftClaw.setDirection(Servo.Direction.FORWARD);

        liftMotor = hardwareMap.get(DcMotor.class, "lift");

        telemetry.addData("status", "initialized");

        waitForStart();
        //    runtime.reset();

        slide.setTargetPosition(slide.getCurrentPosition());
        slide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slide.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        liftMotor.setTargetPosition(liftMotor.getCurrentPosition());
        liftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        liftMotor.setPower(0.4);

        while (opModeIsActive()) {
            double max;

            double strafe = -gamepad1.left_stick_x;
            double forwardBack = gamepad1.left_stick_y;
            double turn = gamepad1.right_stick_x;

            double lateral = 1d;

            if (gamepad1.dpad_down) {
                this.slideTarget -= this.slideAdjust/2;
            } else if (gamepad1.dpad_up) {
                this.slideTarget += this.slideAdjust;
            }

            if (this.slideTarget > this.slideHighest) {
                this.slideTarget = this.slideHighest;
            } else if (this.slideTarget < this.slideLowest) {
                this.slideTarget = this.slideLowest;
            }

            double leftFrontPower = -(strafe + forwardBack - turn); //negative because of new sprokets added in front wheels
            double rightFrontPower = -(strafe - forwardBack - turn); //negative because of new sprokets added in front wheels
            double leftBackPower = strafe - forwardBack + turn;
            double rightBackPower = strafe + forwardBack + turn;

            double slidePower = lateral;
            slide.setPower(slidePower);

            max = Math.max(Math.abs(leftFrontPower), Math.abs(rightFrontPower));
            max = Math.max(max, Math.abs(leftBackPower));
            max = Math.max(max, Math.abs(rightBackPower));

            if (max > 1.0) {
                leftFrontPower /= max;
                rightFrontPower /= max;
                leftBackPower /= max;
                rightBackPower /= max;
            }


            leftFront.setPower(-leftFrontPower);
            rightFront.setPower(-rightFrontPower);
            leftBack.setPower(leftBackPower);
            rightBack.setPower(rightBackPower);

            slide.setTargetPosition(slideTarget);
            int pos = slide.getCurrentPosition();
            this.telemetry.addData("Slide", "Encoder reads: %d , target is: %d", pos, this.slideTarget);

            if (gamepad1.y) {
                launcher.setPosition(0.7 );
            } else {
                launcher.setPosition(0.4);
            }

            if (gamepad1.right_bumper) {
                rightClaw.setPosition(.96);
                leftClaw.setPosition(.96);
            }

            if (gamepad1.left_bumper) {
                rightClaw.setPosition(.7);
                leftClaw.setPosition(.7);
            }

            if (gamepad1.right_trigger == 1) {
                rightWrist.setPosition(.7);
//                leftWrist.setPosition(1);
            }
            if (gamepad1.left_trigger == 1) {
                rightWrist.setPosition(-.1);
//                leftWrist.setPosition(-1);
            }

            if (gamepad1.a) {
                liftMotorTarget += liftMotorAdjust;
            } else if (gamepad1.b) {
                liftMotorTarget -= liftMotorAdjust;
            }
            if (liftMotorTarget > liftMotorMax) liftMotorTarget = liftMotorMax;
            if (liftMotorTarget < liftMotorMin) liftMotorTarget = liftMotorMin;
            liftMotor.setTargetPosition(liftMotorTarget);
            telemetry.addData("lift target", liftMotorTarget);

            telemetry.update();
      }
    }
}










