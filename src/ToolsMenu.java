import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import java.awt.*;

public class ToolsMenu extends JMenu {
    private final JMenuItem find = new JMenuItem("Find");
    private final JMenuItem replace = new JMenuItem("Replace");
    private final JMenuItem selectAll = new JMenuItem("Select All");
    private final JMenuItem cut = new JMenuItem("Cut");
    private final JMenuItem copy = new JMenuItem("Copy");
    private final JMenuItem paste = new JMenuItem("Paste");
    static JTextArea  workingArea;
    Highlighter lighter;

    public ToolsMenu(String name, JTextArea textArea) {
        super(name);
        add(replace);
        add(find);
        add(selectAll);
        add(copy);
        add(paste);
        add(cut);
        try {
            initializeElements();
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
        workingArea = textArea;
        lighter = workingArea.getHighlighter();
        workingArea.setHighlighter(lighter);
    }

    public void initializeElements() throws BadLocationException {
        find.addActionListener(actionEvent -> {
            try {
                findWord(workingArea);
            } catch (BadLocationException e) {
                e.printStackTrace();
            }
        });
        replace.addActionListener(actionEvent -> replace());
        selectAll.addActionListener(actionEvent -> workingArea.selectAll());
        copy.addActionListener(actionEvent -> workingArea.copy());
        paste.addActionListener(actionEvent -> workingArea.paste());
        cut.addActionListener(actionEvent ->workingArea.cut());
        //shortcuts
        find.setAccelerator(KeyStroke.getKeyStroke('F', Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        cut.setAccelerator(KeyStroke.getKeyStroke('X', Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        copy.setAccelerator(KeyStroke.getKeyStroke('C', Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        paste.setAccelerator(KeyStroke.getKeyStroke('P', Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        selectAll.setAccelerator(KeyStroke.getKeyStroke('A', Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
    }



    public void replace(){
        JTextField wordToReplace = new JTextField();
        JTextField replaceWith = new JTextField();
        Object[] message = {
                "Enter the word to replace", wordToReplace,
                "And the new word", replaceWith,
        };
        JOptionPane.showMessageDialog(null, message,"Replace",JOptionPane.PLAIN_MESSAGE);
        String content;
        content = workingArea.getText();
        workingArea.setText(content.replaceAll(wordToReplace.getText(), replaceWith.getText())); //add word boundaries
    }

    public void findWord(JTextArea workingArea) throws BadLocationException {
        String text = workingArea.getText();
        String  word = JOptionPane.showInputDialog(null, "Enter the word you are looking for: ",
                "Find", JOptionPane.PLAIN_MESSAGE);
        int index = text.indexOf(word);
        System.out.println(index);
        if (index >=0 ){ //check if found
            while (index >= 0){
                lighter.addHighlight(index, index + word.length(), new DefaultHighlighter.DefaultHighlightPainter(Color.ORANGE));
                index = text.indexOf(word, index+1);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Word not found!");
        }
    }
}