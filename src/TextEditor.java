import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.file.*;

public class TextEditor extends JFrame {

    JTextArea textArea;
    JPanel panelUp;
    JPanel panelDown;
    JTextField filePathTextArea;
    JButton buttonSave;
    JButton buttonLoad;
    JScrollPane pane;

    public TextEditor() {
        super("The first stage");

        /*__________________________ JButtons __________________________ */
        this.buttonSave = new JButton("Save");
        this.buttonLoad = new JButton("Load");
        this.buttonSave.setName("SaveButton");
        this.buttonLoad.setName("LoadButton");
        this.buttonSave.setFocusable(false);
        this.buttonLoad.setFocusable(false);


        /*__________________________ Action listener load button __________________________ */
        this.buttonLoad.addActionListener(e -> {
            String filePath = filePathTextArea.getText();
            Path path = Paths.get(filePath);
            try {
                byte[] bytes = Files.readAllBytes(path);
                textArea.setText(new String(bytes));
            } catch (IOException ioException) {
                textArea.setText("");
                System.out.println("File doesn't exist");
            }
        });
        /*__________________________ Action listener save button __________________________ */
        this.buttonSave.addActionListener(e -> {
            String filePath = filePathTextArea.getText();
            try (
                    BufferedWriter br = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath)))) {
                String text = textArea.getText();
                br.write(text);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        /*__________________________ JTextArea __________________________ */
        this.textArea = new JTextArea();
        this.textArea.setBounds(10, 10, 600, 500);
        this.textArea.setName("TextArea");
        this.textArea.setLineWrap(true);
        this.textArea.setMargin(new Insets(10, 10, 10, 10));
        /*__________________________ JTextAreaFilePath __________________________ */
        this.filePathTextArea = new JTextField();
        this.filePathTextArea.setPreferredSize(new Dimension(400, 30));
        this.filePathTextArea.setMargin(new Insets(0, 5, 0, 0));
        this.filePathTextArea.setName("FilenameField");
        /*__________________________ JPanel Up __________________________ */
        this.panelUp = new JPanel();
        this.panelUp = new JPanel();
        this.panelUp.setBackground(new Color(210, 203, 207, 30));
        this.panelUp.setPreferredSize(new Dimension(100, 50));
        this.panelUp.setLayout(new FlowLayout());
        this.panelUp.add(filePathTextArea);
        this.panelUp.add(buttonSave);
        this.panelUp.add(buttonLoad);
        /*__________________________ JScrollPane __________________________ */
        this.pane = new JScrollPane(textArea);
        this.pane.setPreferredSize(new Dimension(100, 110));
        this.pane.setName("ScrollPane");
        this.pane.setBounds(100, 100, 100, 100);
        /*__________________________ JPanel Down __________________________ */
        this.panelDown = new JPanel();
        this.panelDown.setBackground(Color.red);
        this.panelDown.setLayout(new BorderLayout());
        this.panelDown.setPreferredSize(new Dimension(100, 510));
        /*__________________________ JFrame __________________________ */
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 600);
        setLayout(new BorderLayout());
        add(panelUp, BorderLayout.NORTH);
        getContentPane().add(pane, BorderLayout.CENTER);
        setVisible(true);
        setResizable(false);
    }
}