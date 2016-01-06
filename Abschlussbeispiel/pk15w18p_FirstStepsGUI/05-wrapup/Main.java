import java.awt.EventQueue;
import javax.swing.JFrame;

public class Main extends JFrame {

    public Main() {
        this.add(new Panel());
        this.setSize(400, 500);
        this.setTitle("Some Rectangles");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        // nicht den erweiterten JFrame in Draw selbst
        // erzeugen sondern der EventQueue übergeben,
        // dass sie selbst den Initialisierungscode
        // ausführen kann (verpacken in Runnable):
        // vgl. https://docs.oracle.com/javase/tutorial/uiswing/concurrency/initial.html
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                // selbe zwei Zeilen Initialisierungscode wie zuvor:
                // erweiterten JFrame erzeugen
                Main window = new Main();
                // und sichtbar machen
                window.setVisible(true);
            }
        });
    }
}
