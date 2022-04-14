import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.util.*;
import java.util.stream.Collectors;

public class ToolsMenu extends JMenu {
    private final JMenuItem find = new JMenuItem("Find");
    private final JMenuItem replace = new JMenuItem("Replace");
    private final JMenuItem selectAll = new JMenuItem("Select All");
    private final JMenuItem cut = new JMenuItem("Cut");
    private final JMenuItem copy = new JMenuItem("Copy");
    private final JMenuItem paste = new JMenuItem("Paste");
    private final JMenuItem checkSpelling = new JMenuItem("Check Spelling");
    static JTextPane workingArea;
    Highlighter lighter;
    
    public ToolsMenu(String name, JTextPane textArea) {
        super(name);
        add(replace);
        add(find);
        add(selectAll);
        add(copy);
        add(paste);
        add(cut);
        add(checkSpelling);
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
        checkSpelling.addActionListener(actionEvent -> {
            try {
                checkSpelling(workingArea);
            } catch (BadLocationException e) {
                e.printStackTrace();
            }
        });
        //shortcuts
        find.setAccelerator(KeyStroke.getKeyStroke('F', Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        cut.setAccelerator(KeyStroke.getKeyStroke('X', Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        copy.setAccelerator(KeyStroke.getKeyStroke('C', Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        paste.setAccelerator(KeyStroke.getKeyStroke('P', Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        selectAll.setAccelerator(KeyStroke.getKeyStroke('A', Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        checkSpelling.setAccelerator(KeyStroke.getKeyStroke('D', Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
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

    public void findWord(JTextPane workingArea) throws BadLocationException {
        String text = workingArea.getText();
        String  word = JOptionPane.showInputDialog(null, "Enter the word you are looking for: ",
                "Find", JOptionPane.PLAIN_MESSAGE);
        int index = text.indexOf(word);
        System.out.println(index);
        if (index >=0 ){ //check if found
            while (index >= 0){
                lighter.addHighlight(index, index + word.length(), new DefaultHighlighter.DefaultHighlightPainter(Color.ORANGE));
                index = text.indexOf(word, index+1);
                System.out.println(index+" "+word);
            }
            System.out.println("broke");
        } else {
            JOptionPane.showMessageDialog(null, "Word not found!");
        }
    }

    public void checkSpelling(JTextPane workingArea) throws BadLocationException{
        String text = workingArea.getText();
        //generate dicitonary by reading file
        Set<String> dictionary = SpellCheck.generateWordList("/home/guts/Text-Editor-Java/src/wordlist.txt");
        Set<String> misspelled = Arrays.stream(text.split("\\s+")) //filter misspelled words
                                .filter(word -> !dictionary.contains(word))
                                .collect(Collectors.toSet());

        for (String word:misspelled){//find all occurrences/indexes for each word and underline them
            int index = text.indexOf(word);
            while (index >= 0){
                lighter.addHighlight(index,index + word.length(), new DefaultHighlighter.DefaultHighlightPainter(Color.RED));
                index = text.indexOf(word, index+1);
            }
        }
    }
}