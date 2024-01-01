import java.util.ArrayList;

/**
 * Interface for the FractalGenerator class
 * This interface is used to generate the fractal elements
 * @author Abraham Aslanides
 * @version CSC 143, June 4, 2023
 */
public interface FractalSubject {

    /**
     * This method is used to register an observer.
     * @param observer The observer
     */
    void registerObserver(FractalObserver observer);

    /**
     * This method is used to remove an observer.
     * @param observer The observer
     */
    void removeObserver(FractalObserver observer);

    /**
     * This method is used to notify the observers.
     */
    void notifyObservers();

    /**
     * This method is used to retrieve the data on the fractal elements.
     * @return an ArrayList of FractalElements
     */
    ArrayList<FractalElement> getData();
}
