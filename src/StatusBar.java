import javax.swing.*;
import java.awt.*;
import java.util.regex.*;

public class StatusBar extends JPanel {
   private final JLabel wordChars = new JLabel();
   private final JLabel position = new JLabel();
   static JTextPane workingArea;


   public StatusBar(JTextPane textPane){
        workingArea = textPane;
        setLayout(new BorderLayout());
        add(position, BorderLayout.LINE_END);
        add(wordChars, BorderLayout.LINE_START);
    }


    public void updateWordChar(){
         wordChars.setText("Words: " + countWords() + "/Chars: " + workingArea.getText().length());
    }

    public void updatePosition(){
       Point point = new Point(workingArea.getX(), workingArea.getY());
       //int position = workingArea.viewToModel2D(point);
       position.setText("Positon "+workingArea.getCaretPosition()+"\t");
    }

    public int countWords(){
        Pattern p = Pattern.compile("\\w+");
        Matcher m = p.matcher(workingArea.getText());
        return (int) m.results().count();
    }

}


