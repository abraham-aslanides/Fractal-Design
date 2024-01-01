import java.awt.Color;
import java.awt.Point;
import java.awt.Graphics;

/**
 * The circle class is used to draw a circle fractal element on the screen.
 * @author Abraham Aslanides
 * @version CSC 143, June 4, 2023
 */

//--------------------------------------------------------------------------------------------------------------------//
// Contents
// 1. Instance Variables
// 2. Constructor
// 3. Draw Method
//--------------------------------------------------------------------------------------------------------------------//

public class Circle implements FractalElement{

    //----------------------------------------------------------------------------------------------------------------//
    // Instance Variables
    //----------------------------------------------------------------------------------------------------------------//

    /** The center point of the circle */
    private final Point center;

    /** The radius of the circle */
    private final double radius;

    /** The color of the circle */
    private final Color color;

    /** The opacity of the circle */
    private final int opacity;

    //----------------------------------------------------------------------------------------------------------------//
    // Constructor
    //----------------------------------------------------------------------------------------------------------------//

    /**
     * Constructor for the Circle class
     * @param center The center point of the circle
     * @param radius The radius of the circle
     * @param color The color of the circle
     * @param opacity The opacity of the circle
     */
    public Circle(Point center, double radius, Color color, int opacity) {
        this.center = center;
        this.radius = radius;
        this.color = color;
        this.opacity = opacity;
    }

    //----------------------------------------------------------------------------------------------------------------//
    // Draw Method
    //----------------------------------------------------------------------------------------------------------------//

    @Override
    public void draw(Graphics g) {
        g.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), opacity));
        g.fillOval(center.x - (int)radius, center.y - (int)radius, 2*(int)radius, 2*(int)radius);
    }
}
