package org.firstinspires.ftc.teamcode.calibration;

import static org.firstinspires.ftc.teamcode.Controller.Button.*;
import static org.firstinspires.ftc.teamcode.Controller.Button.A;
import static org.firstinspires.ftc.teamcode.Controller.Button.B;
import static org.firstinspires.ftc.teamcode.Controller.Button.DPAD_DOWN;
import static org.firstinspires.ftc.teamcode.Controller.Button.DPAD_LEFT;
import static org.firstinspires.ftc.teamcode.Controller.Button.DPAD_RIGHT;
import static org.firstinspires.ftc.teamcode.Controller.Button.DPAD_UP;
import static org.firstinspires.ftc.teamcode.Controller.Button.X;
import static org.firstinspires.ftc.teamcode.Controller.nonZero;
import static org.firstinspires.ftc.teamcode.components.Turret.Orientation.*;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Controller;
import org.firstinspires.ftc.teamcode.components.Robot;
import org.firstinspires.ftc.teamcode.components.TileEdgeDetector;
import org.firstinspires.ftc.teamcode.components.Turret;
import org.firstinspires.ftc.teamcode.util.HoughLineDetector;

@TeleOp
public class IntakeTurretSlideCalibration extends OpMode {

    private Robot robot;
    private Controller controller;

    private int slideTicks;
    private double turretPosition;

    private double intakeTime;
    private double intakePower;

    @Override
    public void init() {
        robot = new Robot(this);
        robot.init();

        controller = new Controller(gamepad1);

        slideTicks = 0;
        turretPosition = LEFT_SIDE.getServoPosition();

        intakeTime = .5;
        intakePower = .5;
    }

    @Override
    public void loop() {

        if (nonZero(controller.leftStickY())) {
            slideTicks += controller.leftStickY() * 5;
        }

        if (nonZero(controller.rightStickY())) {
            turretPosition += controller.rightStickY() * .01;
        }

        if (controller.isPressed(DPAD_UP)) {
            intakeTime += .05;
        } else if (controller.isPressed(DPAD_DOWN)) {
            intakeTime -= .05;
        } else if (controller.isPressed(DPAD_RIGHT)) {
            intakePower += .05;
        } else if (controller.isPressed(DPAD_LEFT)) {
            intakePower -= .05;
        }

        if (controller.isPressed(A)) {
            robot.getIntake().intake(intakePower, intakeTime);
        } else if (controller.isPressed(B)) {
            robot.getIntake().outake(intakePower, intakeTime);
        }

        if (controller.isPressed(X)) {
            robot.getTurret().moveToPosition(turretPosition);
        }
        if (controller.isPressed(Y)) {
            robot.getSlide().moveToTicks(slideTicks);
        }

        if(controller.isPressed(B)) {
            robot.getSlide().stopAllCommands();
            robot.getTurret().stopTurret();
            robot.getIntake().stopIntake();
        }

        telemetry.addData("Slide Ticks", slideTicks);
        telemetry.addData("Target Turret Position", turretPosition);
        telemetry.addData("Turret Position", robot.getTurret().getTurretPosition());

        telemetry.addData("Intake Power", intakePower);
        telemetry.addData("Intake Time", intakeTime);

        robot.updateStatus();
    }
}
