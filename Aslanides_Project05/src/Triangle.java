import java.awt.Color;
import java.awt.Point;
import java.awt.Graphics;

/**
 * The triangle class is used to draw a triangle fractal element on the screen.
 * @author Abraham Aslanides
 * @version CSC 143, June 4, 2023
 */

//--------------------------------------------------------------------------------------------------------------------//
// Contents
// 1. Instance Variables
// 2. Constructor
// 3. Draw Method
//--------------------------------------------------------------------------------------------------------------------//

public class Triangle implements FractalElement{

    //----------------------------------------------------------------------------------------------------------------//
    // Instance Variables
    //----------------------------------------------------------------------------------------------------------------//

    /** The first point of the triangle */
    private final Point ptA;

    /** The second point of the triangle */
    private final Point ptB;

    /** The third point of the triangle */
    private final Point ptC;

    /** The color of the triangle */
    private final Color color;

    //----------------------------------------------------------------------------------------------------------------//
    // Constructor
    //----------------------------------------------------------------------------------------------------------------//

    /**
     * Constructor for the Triangle class
     * @param ptA The first point of the triangle
     * @param ptB The second point of the triangle
     * @param ptC The third point of the triangle
     * @param color The color of the triangle
     */
    public Triangle(Point ptA, Point ptB, Point ptC, Color color) {
        this.ptA = ptA;
        this.ptB = ptB;
        this.ptC = ptC;
        this.color = color;
    }

    //----------------------------------------------------------------------------------------------------------------//
    // Draw Method
    //----------------------------------------------------------------------------------------------------------------//
    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        g.drawLine(ptA.x, ptA.y, ptB.x, ptB.y);
        g.drawLine(ptB.x, ptB.y, ptC.x, ptC.y);
        g.drawLine(ptC.x, ptC.y, ptA.x, ptA.y);
    }
}
