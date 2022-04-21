import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;
import javax.swing.text.Utilities;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class MainWindow {
    private static final JTextPane textArea = new JTextPane();
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

        //status board
        StatusBar stBar = new StatusBar(textArea);
        stBar.setLayout(new BoxLayout(stBar, BoxLayout.X_AXIS));
        stBar.setBorder(BorderFactory.createEtchedBorder());

        textArea.setFont(new Font("Bodoni", Font.PLAIN, 18));
        //add change listeners for calling status methods
        textArea.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                stBar.updatePosition();
            }
        });
        //mouse listener used for the spell checker
        textArea.addMouseListener(new MouseAdapter() {

            // get the text wich is misspelled
            public void highlightClick(MouseEvent e){
                if (SwingUtilities.isRightMouseButton(e)){
                    try {
                        StyledDocument doc = textArea.getStyledDocument();
                        int offset = textArea.viewToModel2D(e.getPoint()); //location of click
                        //set selection to that specific word
                        int start = Utilities.getWordStart(textArea, offset);
                        int end = Utilities.getWordEnd(textArea, offset);
                        textArea.setSelectionStart(start);
                        textArea.setSelectionStart(end);
                        //obtain the value of the selected word
                        String misspelled = doc.getText(start, end-start);
                        //create a JComboBox
                        Object[] suggestions = SpellCheck.suggestions(misspelled).toArray();
                        String correction = String.valueOf(JOptionPane.showInputDialog(
                                null, "Choose one word", "Correct the word",
                                JOptionPane.QUESTION_MESSAGE, null, suggestions, JOptionPane.NO_OPTION));
                        // replace the misspelled/selected word with the correction selected by user
                        doc.remove(start, end - start);
                        doc.insertString(start, correction, null);
                        // reset caret so the user can continue writing
                        textArea.setCaretPosition(start+correction.length());
                    } catch (BadLocationException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                highlightClick(e);
            }

            @Override
            public void mousePressed(MouseEvent e){
                super.mousePressed(e);
                highlightClick(e);
            }

            @Override
            public void mouseReleased(MouseEvent e){
                super.mouseReleased(e);
                highlightClick(e);
            }
        });
        textArea.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent documentEvent) {
                stBar.updateWordChar();
                stBar.updatePosition();
            }

            @Override
            public void removeUpdate(DocumentEvent documentEvent) {
                stBar.updatePosition();
                stBar.updateWordChar();
            }

            @Override
            public void changedUpdate(DocumentEvent documentEvent) {}
        });
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        frame.add(scrollPane);

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
