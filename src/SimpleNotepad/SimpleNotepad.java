package SimpleNotepad;

import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.UIManager;

/**
 *
 * @author Chase
 */
public class SimpleNotepad {
    
    /**
     * The main method that initializes and runs the Notepad GUI application.
     * Sets the FlatLightLaf look and feel for the application's user interface.
     * 
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception ex) {
            System.err.println("Failed to initialize LaF");
        }
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SimpleNotepadGui().setVisible(true);
            }
        });
    }
}
