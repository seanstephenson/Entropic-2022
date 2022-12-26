package org.firstinspires.ftc.teamcode.geometry;

import org.firstinspires.ftc.teamcode.util.ScalingUtil;

/**
 * A viewport with its own coordinate system, relative to an external coordinate system.
 */
public class Viewport {

    private double viewWidth, viewHeight;

    private Position externalTopLeft;
    private Position externalTopRight;
    private Position externalBottomLeft;
    private Position externalBottomRight;

    public Viewport(
            double viewWidth, double viewHeight,
            Position externalTopLeft, Position externalTopRight,
            Position externalBottomLeft, Position externalBottomRight
    ) {
        this.viewWidth = viewWidth;
        this.viewHeight = viewHeight;
        this.externalTopLeft = externalTopLeft;
        this.externalTopRight = externalTopRight;
        this.externalBottomLeft = externalBottomLeft;
        this.externalBottomRight = externalBottomRight;
    }

    public Position convertViewToExternal(Position viewPosition) {
        // Scale the pixel coordinates to be in (0-1).
        double x = ScalingUtil.scaleLinear(viewPosition.getX(), 0, viewWidth, 0, 1);
        double y = ScalingUtil.scaleLinear(viewPosition.getY(), 0, viewHeight, 0, 1);

        // Bilinear interpolation, first find the position on the left and right sides in the y direction.
        Position left = between(externalBottomLeft, externalTopLeft, y);
        Position right = between(externalBottomRight, externalTopRight, y);

        // Now find the point in the middle in the x direction.
        Position middle = between(left, right, x);
        return middle;
    }

    private Position between(Position first, Position second, double fraction) {
        // Calculate the position between two points
        Vector2 difference = second.minus(first).multiply(fraction);
        return first.add(difference);
    }

}
