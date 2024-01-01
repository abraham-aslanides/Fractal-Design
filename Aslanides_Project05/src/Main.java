import java.awt.*;
import java.util.ArrayList;

/**
 * Main class for the Fractal Generator
 * @author Abraham Aslanides
 * @version CSC 143, June 4, 2023
 */
public class Main {

    /** Unused constructor for the Main class */
    public Main(){}

    /**
     * Main method for the Fractal Generator. Instantiates the GUI to start the program.
     * @param args The arguments
     */
    public static void main(String[] args) {

        // Instantiates default theme from FractalGui class
        ArrayList<Color> defaultTheme = FractalGui.getDefaultColors();

        // Instantiates the FractalGenerator with the default theme
        FractalGenerator generator = new FractalGenerator(defaultTheme);

        // Instantiates the GUI, passing in the FractalGenerator
        FractalGui gui = new FractalGui(generator);

        // Instantiates the FractalDrawing, passing in the FractalGenerator
        new FractalDrawing(generator);
    }
}
