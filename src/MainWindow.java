import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class MainWindow {
    private static final JTextArea textArea = new JTextArea();
    private static final JFrame frame = new JFrame("unnamed");
    protected static Font font = textArea.getFont();

    public static void main(String[] args) {
        //menu bar
        JMenuBar menuBar = new JMenuBar();
        FilesMenu filesMenu = new FilesMenu(createDirectory(),textArea);
        JMenu tools = new ToolsMenu("Tools", textArea);
        JMenu view = new ViewMenu("View", textArea);
        JMenu help = new HelpMenu("Help");

        //create frame
        frame.setSize(1000, 700);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        WindowListener exitListener = new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                filesMenu.exit();
            }
        };
        frame.addWindowListener(exitListener);
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));

        //scrolling text area wrapper
        textArea.setLineWrap(true);
        // textArea.setWrapStyleWord(true);
       // textArea.setFont(new Font("Bodoni", 0, 18));
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        frame.add(scrollPane);

        //status board
        StatusBar stBar = new StatusBar(textArea);
        stBar.setLayout(new BoxLayout(stBar, BoxLayout.X_AXIS));
        stBar.setBorder(BorderFactory.createEtchedBorder());

        //menu bars and its elements to the frame
        menuBar.add(filesMenu);
        menuBar.add(tools);
        menuBar.add(view);
        menuBar.add(help);
        frame.add(stBar);
        frame.setJMenuBar(menuBar);
        frame.setVisible(true);
        stBar.updatePosition();
        stBar.updateWordChar();
    }

    public static File[] createDirectory(){
        String user_home = System.getProperty("user.home").replace("\\", "/");
        File workingDir = new File(user_home + File.separator + "JEditor");
        File file = new File(workingDir+File.separator+"unnamed");
        return new File[]{workingDir, file};
    }

    public static void updateTitle(File file){
        frame.setTitle(file.getName());
    }

    public static void updateFontSize(float num){
        float initSize = (float) (font.getSize() + 1.0);
        font = font.deriveFont(initSize + num );
        textArea.setFont(font);
    }
}
