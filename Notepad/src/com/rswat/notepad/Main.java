package com.rswat.notepad;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class Main {

    static JTextArea editArea;

    public static void main(String[] args) throws IOException {
        JPanel gui = new JPanel();
        gui.setBorder(new EmptyBorder(2,3,2,3));
        editArea = new JTextArea(45,100);
        JButton save = new JButton("Save As");
        Font font = new Font(Font.MONOSPACED, Font.PLAIN, editArea.getFont().getSize());
        editArea.setFont(font);
        editArea.setTabSize(4);
        editArea.setSelectionColor(Color.GRAY);
        gui.add(new JScrollPane(editArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS));
        JFrame f = new JFrame("Notepad");
        f.add(gui);
        JButton open = new JButton("Open");
        gui.add(open);
        open.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                readFile();
            }
        });
        gui.add(save);
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.setLocationByPlatform(true);
        f.setVisible(true);
        f.setSize(1000,850);
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser(new File("Documents"));
                chooser.setDialogTitle("Save As");
                int result = chooser.showSaveDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    String text = editArea.getText();
                    File f = chooser.getSelectedFile();
                    try {
                        FileWriter writer = new FileWriter(f.getPath());
                        writer.write(text);
                        writer.flush();
                        writer.close();
                    } catch (IOException e1) {
                        try {
                            f.createNewFile();
                            FileWriter writer = new FileWriter(f.getPath());
                            writer.write(text);
                            writer.flush();
                            writer.close();
                        } catch (IOException e2) {
                            e2.printStackTrace();
                        }
                    }
                }
            }
        });
    }

    public static String readFile() {
        JFileChooser chooser = new JFileChooser("Documents");
        chooser.setDialogTitle("Open");
        int opt = chooser.showOpenDialog(null);
        if (opt == 0) {
            File f = chooser.getSelectedFile();
            try {
                FileReader reader = new FileReader(f.getPath());
                BufferedReader bufferedReader = new BufferedReader(reader);
                String line, content = "";
                while ((line = bufferedReader.readLine()) != null) {
                    content += line + "\n";
                }
                editArea.setText(content);
                reader.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
