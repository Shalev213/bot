package org.example;

import javax.swing.*;
import java.awt.*;

public class StartWindow extends JFrame {
    private int width=1000;
    private int height=800;

    public StartWindow(){

        this.setSize(width,height);
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.setTitle("InfoBot");

        JPanel jPanel = new JPanel();
        jPanel.setVisible(true);
        jPanel.setBackground(new Color(0x00FFB8));

        this.add(jPanel);
    }
    public void showWindow(){
        this.setVisible(true);
    }
}
