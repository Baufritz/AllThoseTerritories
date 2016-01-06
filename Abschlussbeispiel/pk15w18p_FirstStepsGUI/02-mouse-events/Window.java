import javax.swing.*;

public class Window extends JFrame {

    public Window() {
        this.add(new JPanel());
        this.setSize(400, 500);
        this.setTitle("Some Rectangles");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Listener listener = new Listener();
        addMouseListener(listener);

        MotionListener motionlistener = new MotionListener();
        addMouseMotionListener(motionlistener);
    }

}
