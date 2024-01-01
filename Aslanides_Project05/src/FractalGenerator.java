import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;

/**
 * This class is responsible for generating the fractal elements and instantiating them in an ArrayList
 * It is a subject of the FractalDrawing class.
 * @author Abraham Aslanides
 * @version CSC 143, June 4, 2023
 */

//--------------------------------------------------------------------------------------------------------------------//
// Contents:
// 1. Instance Variables
// 2. Constructor
// 3. Set Methods
// 4. Boolean Flag
// 5. Get Methods
// 6. Fractal Generation
// 7. Observer Methods
//--------------------------------------------------------------------------------------------------------------------//

public class FractalGenerator implements FractalSubject{

    //----------------------------------------------------------------------------------------------------------------//
    // Instance Variables
    //----------------------------------------------------------------------------------------------------------------//

    /** The elements to draw */
    private ArrayList<FractalElement> elements;

    /** The observers */
    private ArrayList<FractalObserver> observers;

    /** The recursion depth */
    private int recursionDepth;

    /** The circle opacity */
    private int circleOpacity;

    /** The color */
    private Color color;

    /** The theme */
    private ArrayList<Color> themeColors;

    /** Whether the elements need to be updated */
    private boolean elementsNeedUpdate = true;

    /** Whether the fractal should be drawn */
    private boolean drawFractal = true;

    //----------------------------------------------------------------------------------------------------------------//
    // Constructor
    //----------------------------------------------------------------------------------------------------------------//

    /**
     * Constructor for the FractalGenerator class
     * @param defaultTheme an ArrayList of the default theme colors
     */
    public FractalGenerator(ArrayList<Color> defaultTheme) {
        observers = new ArrayList<>();
        elements = new ArrayList<>();

        // Default values
        recursionDepth = 5;
        circleOpacity = 255;
        color = null;
        themeColors = defaultTheme;

        notifyObservers();
    }

    //----------------------------------------------------------------------------------------------------------------//
    // Set Methods
    //----------------------------------------------------------------------------------------------------------------//

    /**
     * Sets the recursion depth of the fractal
     * @param recursionDepth The recursion depth
     */
    public void setRecursionDepth(int recursionDepth) {
        this.recursionDepth = recursionDepth;
        elementsNeedUpdate = true;
        notifyObservers();
    }

    /**
     * Sets the circle opacity of the fractal
     * @param circleOpacity The circle opacity
     */
    public void setCircleOpacity(int circleOpacity) {
        this.circleOpacity = circleOpacity;
        elementsNeedUpdate = true;
        notifyObservers();
    }

    /**
     * Sets the color of the fractal
     * @param color The color
     */
    public void setColor(Color color) {
        this.color = color;
        elementsNeedUpdate = true;
        notifyObservers();
    }

    /**
     * Sets the theme colors of the fractal
     * @param themeColors The theme colors
     */
    public void setThemeColors(ArrayList<Color> themeColors) {
        this.themeColors = themeColors;
        elementsNeedUpdate = true;
        notifyObservers();
    }

    //----------------------------------------------------------------------------------------------------------------//
    // Boolean Flag
    //----------------------------------------------------------------------------------------------------------------//

    /**
     * Sets whether the fractal should be drawn
     * @param draw Whether the fractal should be drawn
     */
    public void setDrawFractal(boolean draw) {
        this.drawFractal = draw;
        notifyObservers();
    }

    //----------------------------------------------------------------------------------------------------------------//
    // Get Methods
    //----------------------------------------------------------------------------------------------------------------//

    /**
     * Gets the boolean flag for whether the fractal should be drawn
     * @return Whether the fractal should be drawn
     */
    public boolean isDrawFractal() {
        return drawFractal;
    }

    /**
     * {@inheritDoc}
     * <br><br>
     * <p>In this implementation, elements are generated when they are first requested or if they need to be updated.
     * This generation approach ensures that fractal elements are only created when necessary, optimizing resource
     * usage.</p>
     * @return An ArrayList of {@link FractalElement} representing the fractal elements.
     * The list is freshly generated if the elements need an update, otherwise, it returns the existing list.
     */
    @Override
    public ArrayList<FractalElement> getData() {
        synchronized (this) {
            if (elementsNeedUpdate) {
                generateElements();
                elementsNeedUpdate = false;
            }
            // Return a copy to avoid concurrency issues
            return new ArrayList<>(elements);
        }
    }

    //----------------------------------------------------------------------------------------------------------------//
    // Fractal Generation
    //----------------------------------------------------------------------------------------------------------------//

    /**
     * Generates the fractal elements using recursive helper method
     */
    public void generateElements() {
        System.out.println("generateElements called."); // Debugging
        ArrayList<FractalElement> newElements = new ArrayList<>();

        Point[] triangle = new Point[3];
        triangle[0] = new Point(300, 0);
        triangle[1] = new Point(0, 520);
        triangle[2] = new Point(600, 520);
        generateElements(newElements,triangle, recursionDepth);

        synchronized (this) {
            this.elements = newElements;
        }
    }

    /**
     * Recursive helper method. Generates the fractal elements using recursive calls.
     * @param newElements The ArrayList of FractalElements to add to
     * @param triangle The triangle to generate elements from
     * @param recursionIndex The recursion index
     */
    private void generateElements(ArrayList<FractalElement> newElements, Point[] triangle, int recursionIndex) {
        if (recursionIndex > 0) {
            // Calculate the center of the triangle
            int centerX = (triangle[0].x + triangle[1].x + triangle[2].x) / 3;
            int centerY = (triangle[0].y + triangle[1].y + triangle[2].y) / 3;
            Point center = new Point(centerX, centerY);

            // Calculate the radius of the circle as one sixth the height of the triangle
            int radius = 0;
            if (triangle[0].y <= triangle[1].y) {
                radius = Math.abs((triangle[2].y - triangle[0].y)) / 6;
            } else if (triangle[0].y <= triangle[2].y) {
                radius = Math.abs((triangle[1].y - triangle[0].y)) / 6;
            }

            // Random color generation for themes
            Color realCircleColor;
            Color realTriangleColor;
            if (color == null) {
                realCircleColor = themeColors.get((int)(Math.random() * themeColors.size()));
                realTriangleColor = themeColors.get((int)(Math.random() * themeColors.size()));
            } else {
                realCircleColor = color;
                realTriangleColor = color;
            }

            // Create the circle and triangle elements
            Circle circleElement = new Circle(center, radius, realCircleColor, circleOpacity);
            Triangle triangleElement = new Triangle(triangle[0], triangle[1], triangle[2], realTriangleColor);
            System.out.println("Recursive generateElements called. Recursion index: " + recursionIndex); // Debugging
            newElements.add(circleElement);
            newElements.add(triangleElement);

            // Generate new triangles at the corners of the current triangle
            Point[] newTriangle1 = new Point[]{triangle[0], midpoint(triangle[0], triangle[1]), midpoint(triangle[0], triangle[2])};
            Point[] newTriangle2 = new Point[]{triangle[1], midpoint(triangle[1], triangle[0]), midpoint(triangle[1], triangle[2])};
            Point[] newTriangle3 = new Point[]{triangle[2], midpoint(triangle[2], triangle[0]), midpoint(triangle[2], triangle[1])};

            generateElements(newElements, newTriangle1, recursionIndex - 1);
            generateElements(newElements, newTriangle2, recursionIndex - 1);
            generateElements(newElements, newTriangle3, recursionIndex - 1);
        }
    }

    /**
     * Calculates the midpoint of two points
     * @param p1 The first point
     * @param p2 The second point
     * @return The midpoint
     */
    private Point midpoint(Point p1, Point p2) {
        return new Point((p1.x + p2.x) / 2, (p1.y + p2.y) / 2);
    }

    //----------------------------------------------------------------------------------------------------------------//
    // Observer Methods
    //----------------------------------------------------------------------------------------------------------------//

    /**
     * {@inheritDoc}
     * @param observer The observer
     */
    @Override
    public void registerObserver(FractalObserver observer) {
        observers.add(observer);
    }

    /**
     * {@inheritDoc}
     * @param observer The observer
     */
    @Override
    public void removeObserver(FractalObserver observer) {
        observers.remove(observer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void notifyObservers() {
        for (FractalObserver observer : observers) {
            observer.update();
        }
    }
}
