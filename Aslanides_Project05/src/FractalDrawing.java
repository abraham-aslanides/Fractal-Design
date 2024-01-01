import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 *
 * This class is responsible for drawing the fractal on the screen.
 * It is an observer of the FractalGenerator class.
 * @author Abraham Aslanides
 * @version CSC 143, June 4, 2023
 */
public class FractalDrawing implements FractalObserver{

    /** The width of the window */
    public static final int WIDTH = 600;

    /** The height of the window */
    public static final int HEIGHT = 600;

    /** The draw area */
    private final DrawArea drawArea;

    /** The subject */
    private final FractalSubject subject;

    /** The elements to draw */
    private ArrayList<FractalElement> elements;

    /** Whether the fractal should be drawn */
    private boolean drawFractal = true;


    /**
     * Constructor for the FractalDrawing class
     * @param subject The subject which this class is observing (FractalGenerator)
     */
    public FractalDrawing(FractalSubject subject){

        this.subject = subject;
        this.subject.registerObserver(this);
        this.elements = new ArrayList<>();

        JFrame frame = new JFrame();

        frame.setSize(WIDTH, HEIGHT);
        frame.setTitle("Fractal GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        Toolkit tk = Toolkit.getDefaultToolkit();
        frame.setLocation((tk.getScreenSize().width - frame.getWidth()) * 7/8,
                (tk.getScreenSize().height - frame.getHeight()) /4);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        frame.getContentPane().add(mainPanel);

        drawArea = new DrawArea();
        drawArea.setBounds(0, 0, WIDTH, HEIGHT);
        //drawArea.setBackground(Color.WHITE);
        mainPanel.add(drawArea);

        frame.setVisible(true);
    }

    /**
     * {@inheritDoc}
     * <br><br>
     * <p>This specific implementation also ensures that the drawing area is repainted
     * to reflect the updated fractal elements.</p>     */
    @Override
    public void update() {

        // Debugging
        System.out.println("Updated elements in FractalDrawing: " + elements.size());

        elements = subject.getData();
        // Type casting needed to access isDrawFractal()
        drawFractal = ((FractalGenerator) subject).isDrawFractal();
        drawArea.repaint();
    }

    /**
     * Private inner class for the draw area
     */
    private class DrawArea extends JPanel {

        /** Unused constructor for the DrawArea class */
        public DrawArea() {}

        /**
         * {@inheritDoc}
         * <br><br>
         * <p>Draws the elements on the screen.
         * Syncs on the subject to avoid concurrent modification.</p>
         */
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            ArrayList<FractalElement> localElements;
            // Synchronize on the subject to avoid concurrent modification
            synchronized (subject) {
                localElements = new ArrayList<>(subject.getData());
            }
            if (drawFractal) {
                for (FractalElement element : localElements) {
                    element.draw(g);
                }
            }
        }
    }
}
