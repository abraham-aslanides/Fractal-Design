import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JComboBox;
import javax.swing.JColorChooser;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * GUI for the fractal generator. This class handles user interactions for setting fractal parameters such as
 * recursive depth, opacity, color, and themes. It also provides buttons to draw and clear the fractal,
 * along with a display showing the selected color or theme.
 * <p>
 * This class interacts with {@link FractalGenerator} to reflect the user's choices in fractal generation.
 * </p>
 *
 * @author Abraham Aslanides
 * @version CSC 143, June 4, 2023
 */

//--------------------------------------------------------------------------------------------------------------------//
// Contents:
// 1. Static Variables
// 2. Static Block
// 3. Constructor
// 3.1 Draw Button
// 3.2 Clear Button
// 3.3 Recursion Depth Slider
// 3.4 Circle Opacity Slider
// 3.5 Color Selection Display
// 3.6 Color Selection
// 3.7 Theme Selection
// 4. Default Theme
//--------------------------------------------------------------------------------------------------------------------//
public class FractalGui {

    //----------------------------------------------------------------------------------------------------------------//
    // Static Variables
    //----------------------------------------------------------------------------------------------------------------//

    /** ArrayList of the names of the themes */
    private static final ArrayList<String> themeNames;

    /** ArrayList of the colors of the themes */
    private static final ArrayList<Color> themeColors;

    /** ArrayList of the default theme colors */
    private static final ArrayList<Color> defaultColors;

    /** Array of JPanels to display the selected theme or color */
    private static JPanel[] themeDisplay;

    //----------------------------------------------------------------------------------------------------------------//
    // Static block to initialize the themeNames and themeColors ArrayLists from Fractal_Themes.csv file
    //----------------------------------------------------------------------------------------------------------------//

    static{
        themeNames = new ArrayList<>();
        themeColors = new ArrayList<>();

        try {
            File themeFile = new File("Fractal_Themes.csv");
            Scanner input = new Scanner(themeFile);

            input.useDelimiter(",");
            input.nextLine();

            while (input.hasNextLine()) {
                String line = input.nextLine();
                if (!line.isEmpty()) {
                    String[] parts = line.split(",");
                    // If the line has only one part, it is a theme name
                    if (parts.length == 1) {
                        themeNames.add(parts[0]);
                        // If the line has three parts, it is a color
                    } else if (parts.length == 3) {
                        int r = Integer.parseInt(parts[0]);
                        int g = Integer.parseInt(parts[1]);
                        int b = Integer.parseInt(parts[2]);
                        Color color = new Color(r, g, b);
                        themeColors.add(color);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error: Theme file not found. Defaulting to basic themes.");
            // Add some basic themes/colors as fallback
            themeNames.add("Basic");
            themeColors.add(Color.RED); // Example
            // ... more basic themes/colors ...
        }

        defaultColors = new ArrayList<>();
        for (int index = 0; index < 5; index++) {
            defaultColors.add(themeColors.get(index));
        }
    }

    //----------------------------------------------------------------------------------------------------------------//
    // Constructor
    //----------------------------------------------------------------------------------------------------------------//


    /**
     * {@inheritDoc}
     * <p>
     * This method initializes the GUI components and sets up the event listeners for user interactions.
     * It relies on the {@link FractalGenerator} for generating the fractal based on user inputs.
     * </p>
     *
     * @param generator The {@link FractalGenerator} instance to be used for fractal generation.
     */
    public FractalGui(FractalGenerator generator) {

        JFrame frame = new JFrame();

        frame.setSize(400, 600);
        frame.setTitle("Fractal GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        Toolkit tk = Toolkit.getDefaultToolkit();
        frame.setLocation((tk.getScreenSize().width - frame.getWidth()) /8,
                (tk.getScreenSize().height - frame.getHeight()) /4);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        frame.getContentPane().add(mainPanel);

        //-----------------------------------------------------------------------------------------------------------//
        // Draw Button
        //-----------------------------------------------------------------------------------------------------------//

        JButton drawButton = new JButton("DRAW!");
        drawButton.setBounds(150, 500, 100, 30);
        mainPanel.add(drawButton);

        drawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generator.setDrawFractal(true);
            }
        });

        //-----------------------------------------------------------------------------------------------------------//
        // Clear Button
        //-----------------------------------------------------------------------------------------------------------//

        JButton clearButton = new JButton("CLEAR!");
        clearButton.setBounds(50, 500, 100, 30);
        mainPanel.add(clearButton);

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generator.setDrawFractal(false);
            }
        });

        //-----------------------------------------------------------------------------------------------------------//
        // Recursion Depth Slider
        //-----------------------------------------------------------------------------------------------------------//

        JLabel recursionDepthLabel = new JLabel("Recursion Depth");
        recursionDepthLabel.setBounds(50, 50, 200, 30);
        mainPanel.add(recursionDepthLabel);

        JSlider recursionDepthSlider = new JSlider(1, 8, 5);
        recursionDepthSlider.setBounds(50, 80, 200, 50);
        recursionDepthSlider.setMajorTickSpacing(1);
        recursionDepthSlider.setPaintTicks(true);
        recursionDepthSlider.setPaintLabels(true);
        mainPanel.add(recursionDepthSlider);

        recursionDepthSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                generator.setRecursionDepth(recursionDepthSlider.getValue());
                generator.setDrawFractal(true);
            }
        });

        //-----------------------------------------------------------------------------------------------------------//
        // Circle Opacity Slider
        //-----------------------------------------------------------------------------------------------------------//

        JLabel circleOpacityLabel = new JLabel("Circle Opacity");
        circleOpacityLabel.setBounds(50, 150, 200, 30);
        mainPanel.add(circleOpacityLabel);

        JSlider circleOpacitySlider = new JSlider(0, 255, 255);
        circleOpacitySlider.setBounds(50, 180, 200, 50);
        circleOpacitySlider.setMajorTickSpacing(50);
        circleOpacitySlider.setPaintTicks(true);
        circleOpacitySlider.setPaintLabels(true);
        mainPanel.add(circleOpacitySlider);

        circleOpacitySlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                generator.setCircleOpacity(circleOpacitySlider.getValue());
                generator.setDrawFractal(true);
            }
        });

        //-----------------------------------------------------------------------------------------------------------//
        // Color Selection Display
        //-----------------------------------------------------------------------------------------------------------//

        JLabel colorSelectionLabel = new JLabel("Color Selection");
        colorSelectionLabel.setBounds(50, 380, 200, 30);
        mainPanel.add(colorSelectionLabel);

        themeDisplay = new JPanel[5]; // Initialize the array size
        for (int index = 0; index < themeDisplay.length; index++) {
            themeDisplay[index] = new JPanel(); // Instantiate each JPanel
            themeDisplay[index].setBounds(50 + 30 * index, 420, 30, 30);
            themeDisplay[index].setBackground(defaultColors.get(index));
            mainPanel.add(themeDisplay[index]);
        }


        //-----------------------------------------------------------------------------------------------------------//
        // Color Selection
        //-----------------------------------------------------------------------------------------------------------//

        JButton colorChooser = new JButton("Choose Color");
        colorChooser.setBounds(50, 250, 200, 30);
        mainPanel.add(colorChooser);


        colorChooser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color color = JColorChooser.showDialog(null, "Select a color", null);
                if (color != null) {
                    generator.setColor(color);
                    for (int index = 0; index < 5; index++) {
                        themeDisplay[index].setBackground(color);
                    }
                    generator.setDrawFractal(true);
                }
            }
        });


        //-----------------------------------------------------------------------------------------------------------//
        // Theme Selection
        //-----------------------------------------------------------------------------------------------------------//

        JLabel themeLabel = new JLabel("Choose Theme");
        themeLabel.setBounds(50, 300, 200, 30);
        mainPanel.add(themeLabel);

        JComboBox<String> themeSelection = new JComboBox<>(themeNames.toArray(new String[themeNames.size()]));
        themeSelection.setBounds(50, 330, 200, 30);
        mainPanel.add(themeSelection);

        themeSelection.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selection = themeSelection.getSelectedIndex();
                ArrayList<Color> colors = new ArrayList<>();
                for (int index = 0; index < 5; index++) {
                    colors.add(themeColors.get(selection * 5 + index));
                }
                generator.setThemeColors(colors);
                for (int index = 0; index < 5; index++) {
                    themeDisplay[index].setBackground(colors.get(index));
                }
                generator.setColor(null);
                generator.setDrawFractal(true);
            }
        });
        generator.setDrawFractal(true);
        frame.setVisible(true);
    }

    //---------------------------------------------------------------------------------------------------------------//
    // Default Theme
    //---------------------------------------------------------------------------------------------------------------//

    /**
     * Retrieves the default color themes used in the fractal generator.
     *
     * @return An ArrayList of default {@link Color} objects.
     */
    public static ArrayList<Color> getDefaultColors() {
        return defaultColors;
    }
}
