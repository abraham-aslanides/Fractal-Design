import java.awt.Graphics;

/**
 * This interface is used to draw the fractal elements
 * @author Abraham Aslanides
 * @version CSC 143, June 4, 2023
 */
public interface FractalElement {

    /**
     * This method is used to draw the fractal element
     * @param g The graphics object
     */
    void draw(Graphics g);
}
