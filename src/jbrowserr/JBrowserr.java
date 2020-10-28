package jbrowserr;
import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

public class JBrowserr extends JFrame {
    private final JTabbedPane tabbedPane = new JTabbedPane();
    String copy;
    JTextField TextField = new JTextField(40);
    

    public JBrowserr() {
        super("My Web Browser");
        createNewTab();
        getContentPane().add(tabbedPane);
    }
 
    private void createNewTab() {
        JPanel panel = new JPanel(new BorderLayout());
        tab browserTab = new tab();
        TextField = browserTab.locationTextField;
        panel.add(browserTab, BorderLayout.CENTER);
        tabbedPane.addTab("Browser " + tabbedPane.getTabCount(), panel);
    }


    public static void main(String[] args) {
        JBrowserr browser1 = new JBrowserr();
        browser1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        browser1.setSize(1100, 700);
        browser1.setVisible(true);
    }
}
