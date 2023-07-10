package org.example;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.List;

public class StartWindow extends JFrame {
    private int windowWidth = 1000;
    private int windowHeight = 800;
    private InfoBot infoBot;
    private JPanel jPanel;
    private JLabel activeUser = new JLabel();
    private JLabel popularApi = new JLabel();
    private JLabel users = new JLabel();
    private JLabel requests = new JLabel();
    private String activeName;
    private int xGroup = 700;
    private int yGroup = 80;
    private int widthGroup = 110;
    private int heightGroup = 50;
    private static List<JCheckBox> selectedCheckBoxes;
    private final int maxOptions = 3;




    public StartWindow(InfoBot infoBot) {
        this.infoBot = infoBot;
        this.activeName = infoBot.getActiveUser();

        try {
            this.setSize(windowWidth, windowHeight);
            this.setLayout(null);
            this.setResizable(false);
            this.setLocationRelativeTo(null);
            this.setTitle("InfoBot");
            this.setDefaultCloseOperation(EXIT_ON_CLOSE);

//            infoBot = new InfoBot();
            TelegramBotsApi api = new TelegramBotsApi(DefaultBotSession.class);
            api.registerBot(infoBot);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }

        jPanel = new JPanel(null);
        jPanel.setBackground(new Color(0x00FFB8));
        jPanel.setSize(windowWidth, windowHeight);

        JLabel title = new JLabel("My Bot");
        title.setBounds((int) (windowWidth/2 - windowWidth * (0.16/2)), 20, (int) (windowWidth * 0.16), 50);
        title.setFont(new Font("Arial", Font.ITALIC, 50));
        jPanel.add(title);

        JLabel callsCounter = new JLabel("Amount of calls: ");
        callsCounter.setBounds(10, yGroup, 160, 50);
        callsCounter.setFont(new Font("Arial", Font.ITALIC, 20));
        jPanel.add(callsCounter);

        JLabel usersCounter = new JLabel("Amount of unique users: ");
        usersCounter.setBounds(10, yGroup+60, 230, 50);
        usersCounter.setFont(new Font("Arial", Font.ITALIC, 20));
        jPanel.add(usersCounter);

        JLabel activeCounter = new JLabel("The most active user: ");
        activeCounter.setBounds(10, yGroup+60*2, 230, 50);
        activeCounter.setFont(new Font("Arial", Font.ITALIC, 20));
        jPanel.add(activeCounter);

        JLabel popularActivity = new JLabel("The most popular activity: ");
        popularActivity.setBounds(10, yGroup+60*3, 250, 50);
        popularActivity.setFont(new Font("Arial", Font.ITALIC, 20));
        jPanel.add(popularActivity);

            this.loop();

    }
    public void loop() {
        JCheckBox[] jCheckBoxes = new JCheckBox[5];
        jCheckBoxes[0] = new JCheckBox("Country");
        jCheckBoxes[1] = new JCheckBox("Joke");
        jCheckBoxes[2] = new JCheckBox("Quote");
        jCheckBoxes[3] = new JCheckBox("Number fact");
        jCheckBoxes[4] = new JCheckBox("Cat fact");

        jCheckBoxes[0].setBounds(xGroup,yGroup,widthGroup,heightGroup);
        jCheckBoxes[0].setBackground(new Color(0xE01AE214, true));

        jCheckBoxes[1].setBounds(xGroup,yGroup+heightGroup,widthGroup,heightGroup);
        jCheckBoxes[1].setBackground(new Color(0xE01AE214, true));

        jCheckBoxes[2].setBounds(xGroup,yGroup+2*heightGroup,widthGroup,heightGroup);
        jCheckBoxes[2].setBackground(new Color(0xE01AE214, true));

        jCheckBoxes[3].setBounds(xGroup,yGroup+3*heightGroup,widthGroup,heightGroup);
        jCheckBoxes[3].setBackground(new Color(0xE01AE214, true));

        jCheckBoxes[4].setBounds(xGroup,yGroup+4*heightGroup,widthGroup,heightGroup);
        jCheckBoxes[4].setBackground(new Color(0xE01AE214, true));


        jPanel.add(jCheckBoxes[0]);
        jPanel.add(jCheckBoxes[1]);
        jPanel.add(jCheckBoxes[2]);
        jPanel.add(jCheckBoxes[3]);
        jPanel.add(jCheckBoxes[4]);

        JButton refreshButton = new JButton("Refresh");
        refreshButton.setBounds(xGroup,yGroup+5*heightGroup,widthGroup,heightGroup);;
        refreshButton.addActionListener(e->{
            System.out.println(infoBot.getSelectedCheckBoxesToString());
            jPanel.revalidate();
            jPanel.repaint();
        });

        jPanel.add(refreshButton);
        this.add(jPanel);

        new Thread(() -> {
            while (true) {
                SwingUtilities.invokeLater(() -> {
                    if (infoBot != null && infoBot.getFullName() != null) {
                        // ...
//                        String firstName = infoBot.getFullName().getFirstName();
//                        String lastName = infoBot.getFullName().getLastName();
//                        String message = infoBot.getMessage();

                        requests.setText(String.valueOf(this.infoBot.getCallsCounter()));
                        requests.setBounds(160, 80, 50, 50);
                        requests.setFont(new Font("Arial", Font.BOLD, 20));

                        users.setText(String.valueOf(this.infoBot.getUniqueUserCounter()));
                        users.setBounds(230, 140, 50, 50);
                        users.setFont(new Font("Arial", Font.BOLD, 20));

                        activeUser.setText(infoBot.getActiveUserName());
                        activeUser.setBounds(230, 200, 150, 50);
                        activeUser.setFont(new Font("Arial", Font.BOLD, 20));

                        popularApi.setText(infoBot.getPopularActivity());
                        popularApi.setBounds(260, yGroup+60*3, 250, 50);
                        popularApi.setFont(new Font("Arial", Font.BOLD, 20));

                        jPanel.add(popularApi);
                        jPanel.add(activeUser);
                        jPanel.add(users);
                        jPanel.add(requests);

                    }
                });

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
        }).start();

         new Thread(() -> {
             selectedCheckBoxes = new ArrayList<>();

             for (int i = 0; i < jCheckBoxes.length; i++) {
                 JCheckBox checkBox = jCheckBoxes[i];
                 checkBox.addItemListener(e -> {
                     if (e.getStateChange() == ItemEvent.SELECTED){
                         if (selectedCheckBoxes.size() >= this.maxOptions) {
                             checkBox.setSelected(false);
                         }else {
                             selectedCheckBoxes.add(checkBox);
                             infoBot.getSelectedCheckBoxesToString().add(checkBox.getText());
                         }
                     }else if (e.getStateChange() == ItemEvent.DESELECTED){
                         selectedCheckBoxes.remove(checkBox);
                         infoBot.getSelectedCheckBoxesToString().remove(checkBox.getText());
                     }
                 });
             }
         }).start();
    }


    public void showWindow() {
        this.setVisible(true);
    }
}
