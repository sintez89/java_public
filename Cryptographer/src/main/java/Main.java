import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class Main {

  public static void main(String[] args) {
    JFrame frame = new JFrame("Сryptographer");
    frame.setSize(600, 120);
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    frame.setLocationRelativeTo(null);
    WindowForm form = new WindowForm();
    frame.add(form.getRootPanel());
    frame.setVisible(true);
  }
}
