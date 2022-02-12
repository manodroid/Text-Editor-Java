import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class ViewMenu extends JMenu{
    private final JMenuItem font = new JMenuItem("Font");
    private final JCheckBoxMenuItem theme = new JCheckBoxMenuItem("Dark Mode");
    private final JMenuItem increaseFontSize = new JMenuItem("Increase Font Size");
    private final JMenuItem decreaseFontSize = new JMenuItem("Decrease Font Size");
    static JTextArea workingArea;

    public ViewMenu(String name, JTextArea workingArea){
        super(name);
        add(increaseFontSize);
        add(decreaseFontSize);
        add(theme);
        add(font);
        ViewMenu.workingArea = workingArea;
        initializeElements();
    }

    public void initializeElements(){
        theme.addActionListener(actionEvent -> {
            Color bgColor = workingArea.getBackground();
            if (Objects.equals(bgColor, new Color(255, 255, 255))){
                workingArea.setBackground(Color.DARK_GRAY);
                workingArea.setForeground(Color.WHITE);
            } else {
                workingArea.setBackground(Color.WHITE);
                workingArea.setForeground(Color.BLACK);
            }
        });

        font.addActionListener(actionEvent -> {
            Object[] family = {"Frutiger", "Times", "Baskerville", "Helvetica" ,"Gotham"};
            String newFont = String.valueOf(JOptionPane.showInputDialog(null, "Choose font family","Fonts",
                    JOptionPane.QUESTION_MESSAGE, null, family, JOptionPane.NO_OPTION));
            workingArea.setFont(new Font(newFont, Font.PLAIN, workingArea.getFont().getSize()));
        });

        increaseFontSize.addActionListener(actionEvent -> MainWindow.updateFontSize(+2.0f));
        increaseFontSize.setAccelerator(KeyStroke.getKeyStroke('=', Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        decreaseFontSize.addActionListener(actionEvent -> MainWindow.updateFontSize(-2.0f));
        decreaseFontSize.setAccelerator(KeyStroke.getKeyStroke('-', Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
    }

}
