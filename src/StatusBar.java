import javax.swing.*;
import java.awt.*;

public class StatusBar extends JPanel {
   private final JLabel wordChars = new JLabel();
   private final JLabel position = new JLabel();
   static JTextArea workingArea;


    public StatusBar(JTextArea textArea){
        workingArea = textArea;
        setLayout(new BorderLayout());
        add(position, BorderLayout.LINE_END);
        add(wordChars, BorderLayout.LINE_START);
    }

    public void updateWordChar(){
        Timer timer = new Timer(3000, e -> wordChars.setText("Words: "+ countWords(workingArea) + "/Chars: " + workingArea.getText().length()));
        timer.start();
    }

    public void updatePosition(){

        Timer timer = new Timer(3000, e -> position.setText("Positon "+ workingArea.getLineCount()+","+workingArea.getCaretPosition()+"\t"));
        timer.start();
    }

    public int countWords(JTextArea textArea){
        int counter = 0;
        String[] lines = textArea.getText().split(System.getProperty("line.separator"));
        for (String line:lines){
            if (!line.trim().isEmpty()){
                counter += line.trim().replaceAll("[^a-zA-Z0-9]+", " ").split(" ").length;}
        }
        return counter;
    }

}


