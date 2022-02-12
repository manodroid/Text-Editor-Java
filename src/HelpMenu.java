import javax.swing.*;

public class HelpMenu extends JMenu {
    private final JMenuItem shortCuts = new JMenuItem("Shortcuts");
    private final JMenuItem about = new JMenuItem("About");

    public HelpMenu(String name){
        super(name);
        add(shortCuts);
        add(about);
        initializeElements();
    }

    protected void initializeElements(){
        about.addActionListener(actionEvent -> {
            Object[] about = {
                    "This was our approach at creating a simple text editor since it a tool"+"\n"+
                    "we use daily we tried to implement some necessary tools for every developer","\n",
                    "Source code -> www.github/manodroid","\n",
                            "   Credits:"+"\n"+
                            "   Emanuel Golaj"+"\n"+
                            "   Klohair Pashaj"+"\n"+
                            "   Denis Taska"+"\n",
            };
            JOptionPane.showMessageDialog(null, about,"About JEditor", JOptionPane.PLAIN_MESSAGE);

        });

        shortCuts.addActionListener(actionEvent -> {
            Object[] keybindings = {
                    "CTRL + T", "New File",
                    "CTRL + O", "Open File",
                    "CTRL + N", "New File",
                    "Alt + S", "Save File",
                    "CTRL + R", "Save/Rename File",
                    "CTRL + -/=", "Decrease/Increase font size",
                    "CTRL + Q", "Quit Editor",
                    "CTRL + F", "Find occurrences of word",
                    "CTRL + R", "Change occurrences of word",
                    "CTRl + P/X/C" + "Paste, Cut & Copy",
                    "CTRL + A", "Select all text",
            };
            JOptionPane.showInternalMessageDialog(null,keybindings, "Keybindings", JOptionPane.PLAIN_MESSAGE);
        });
    }
}
