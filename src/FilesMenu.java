import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.InputEvent;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class FilesMenu extends JMenu {
    private final JMenuItem newFile = new JMenuItem("New");
    private final JMenuItem open = new JMenuItem("Open");
    private final JMenuItem save = new JMenuItem("Save");
    private final JMenuItem rename = new JMenuItem("Rename");
    private final JMenuItem exit = new JMenuItem("Exit");
    private final JFileChooser fileChooser = new JFileChooser();
    JTextArea workingArea;
    private final File workingDir;
    File workingFile;

    FilesMenu(File[] files, JTextArea workingArea) {
        super("Files");
        add(newFile);
        add(open);
        add(save);
        add(rename);
        add(exit);
        this.workingDir = files[0];
        this.workingArea = workingArea;
        this.workingFile = files[1];
        initializeElements();
    }

    public void initializeElements(){
        newFile.addActionListener(actionEvent -> newFile());
        open.addActionListener(actionEvent -> open());
        rename.addActionListener(actionEvent -> rename());
        save.addActionListener(actionEvent -> save());
        exit.addActionListener(actionEvent -> exit());
        newFile.setAccelerator(KeyStroke.getKeyStroke('T', InputEvent.CTRL_DOWN_MASK));
        open.setAccelerator(KeyStroke.getKeyStroke('O', Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        save.setAccelerator(KeyStroke.getKeyStroke('S', Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        rename.setAccelerator(KeyStroke.getKeyStroke('S', Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx() + InputEvent.SHIFT_DOWN_MASK));
        exit.setAccelerator(KeyStroke.getKeyStroke('Q', Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
    }

    public void exit(){
            save();
            JOptionPane.showMessageDialog(null, "Have a great day!");
            System.exit(0);
    }

    public void newFile(){
        if (workingFile.getName().equals("unnamed") && workingArea.getText().length()>0){
            save();
        } else {
            workingArea.setText("");
            workingFile = new File("unnamed");
        }
    }
    
    public void open(){
        fileChooser.setDialogTitle("Select file to open");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setCurrentDirectory(workingDir);
        fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.getName().toLowerCase().endsWith(".txt") || file.isDirectory();
            }

            @Override
            public String getDescription() {
                return "Text Files";
            }
        });
        fileChooser.showOpenDialog(null);
        File file = fileChooser.getSelectedFile();
        workingFile = fileChooser.getSelectedFile();
        if (file != null){
            try {
                workingArea.read(new FileReader(file.getAbsolutePath()), null);
            } catch (IOException ioe){
                ioe.printStackTrace();
            }
        }
        MainWindow.updateTitle(workingFile);
    }
    
    public void save(){
        JFileChooser save = new JFileChooser();
        save.setDialogTitle("Save file");
        save.setFileSelectionMode(JFileChooser.FILES_ONLY);
        save.setCurrentDirectory(workingDir);
        save.setFileFilter( new FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.getName().toLowerCase().endsWith(".txt") || file.isDirectory();
            }

            @Override
            public String getDescription() {
                return "Text Files";
            }
        });
        save.showSaveDialog(null);
        workingFile = save.getSelectedFile();

        if (workingFile != null){
            try {
                String suffix = ".txt";
                if(!(workingFile.getAbsolutePath().endsWith(suffix)))
                    workingFile = new File(fileChooser.getSelectedFile() + suffix);
                FileWriter fw = new FileWriter(workingDir + File.separator + workingFile.getName());
                fw.write(workingArea.getText());
                fw.close();
                MainWindow.updateTitle(workingFile);
            } catch (IOException ioe){
                ioe.printStackTrace();
            }
        }
    }

    public void rename(){
        String newName = JOptionPane.showInputDialog(null, "Enter the new name",JOptionPane.PLAIN_MESSAGE);
        Path source = Paths.get(workingDir+File.separator+ workingFile);
        try {
            Files.move(source, source.resolveSibling(newName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        MainWindow.updateTitle(workingFile);
    }
}