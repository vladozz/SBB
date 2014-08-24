package com.tsystems.javaschool.vm.client;

import com.tsystems.javaschool.vm.client.listeners.LoginButtonAction;
import com.tsystems.javaschool.vm.client.listeners.WithoutLoginButtonAction;
import com.tsystems.javaschool.vm.client.panels.LoginPanel;
import com.tsystems.javaschool.vm.client.panels.customer.ClientPane;
import com.tsystems.javaschool.vm.client.panels.manager.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartForm extends JFrame {
    private JTextField loginField;
    private JPasswordField passwordField;
    private JButton withoutLoginButton;
    private JButton loginButton;
    private JButton getBoardButton;
    private Long sessionId;
    private JPanel managerMenu;
    private ManagerPanel managerPanel;
    private JPanel managerFrame;
    private final StartForm startForm = this;

    public StartForm() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        initStartFrame();
    }

    public static void main(String[] args) {
        StartForm startForm1 = new StartForm();
    }

    public void initStartFrame() {
        this.setTitle("Login SBB service");
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5),
                BorderFactory.createLineBorder(Color.black)));
        setContentPane(new LoginPanel(this));
        JLabel labelLogin = new JLabel("Enter login");
        JLabel labelPassword  = new JLabel("Enter password");
        panel.add(labelLogin);
        panel.add(labelPassword);
        loginField = new JTextField("admin", 15);
        passwordField = new JPasswordField("admin", 15);
        panel.add(loginField);
        panel.add(passwordField);
        loginButton  = new JButton("Login");
        withoutLoginButton = new JButton("Enter without login");
        panel.add(loginButton);
        panel.add(withoutLoginButton);
        pack();
        //setSize(300, 300);
        withoutLoginButton.addActionListener(new WithoutLoginButtonAction(this));
        loginButton.addActionListener(new LoginButtonAction(loginField, passwordField, this));
        setVisible(true);
    }

    public void initClientFrame() {
        JPanel panel = new JPanel(new CardLayout());
        panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5),
                BorderFactory.createLineBorder(Color.black)));
        setContentPane(panel);
        panel.add(new ClientPane(this));
        this.setTitle("Customer service");
        pack();
        setSize(1024, 680);
        setVisible(true);
    }

    public void initManagerFrame() {
        this.setTitle("Manager service");
        managerFrame  = new JPanel();
        setContentPane(managerFrame);
        managerFrame.add(getManagerMenu());
        pack();
        setVisible(true);
    }



    public void initManagerFrame(JPanel panel) {
        managerFrame  = new JPanel();
        setContentPane(managerFrame);
        if (managerPanel == null) {
            managerPanel = new ManagerPanel();
        }
        managerFrame.add(getManagerMenu());
        managerFrame.add(panel);
        pack();
        setVisible(true);
    }

    public JPanel getManagerMenu() {
        if (managerMenu == null) {
            managerMenu = new JPanel(new GridLayout(7, 1));
            managerMenu.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5),
                    BorderFactory.createLineBorder(Color.black)));
            ButtonGroup buttonGroup = new ButtonGroup();
            JRadioButton addPathButton = new JRadioButton("Add path");
            buttonGroup.add(addPathButton);
            managerMenu.add(addPathButton);
            JRadioButton addStationToPathButton = new JRadioButton("Add station to path");
            buttonGroup.add(addStationToPathButton);
            managerMenu.add(addStationToPathButton);
            JRadioButton addStationButton = new JRadioButton("Add station");
            buttonGroup.add(addStationButton);
            managerMenu.add(addStationButton);
            JRadioButton addTrainButton = new JRadioButton("Add train");
            buttonGroup.add(addTrainButton);
            managerMenu.add(addTrainButton);
            JRadioButton getAllTrainsButton = new JRadioButton("Get all trains");
            buttonGroup.add(getAllTrainsButton);
            managerMenu.add(getAllTrainsButton);
            JRadioButton getAllPassengersOfTripButton = new JRadioButton("Get all passengers if trip");
            buttonGroup.add(getAllPassengersOfTripButton);
            managerMenu.add(getAllPassengersOfTripButton);
            JRadioButton addNewTripButton = new JRadioButton("Add new trip");
            buttonGroup.add(addNewTripButton);
            managerMenu.add(addNewTripButton);

            getAllTrainsButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    initManagerFrame(new GetAllTrainsPanel(startForm));
                }
            });
            getAllPassengersOfTripButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    initManagerFrame(new GetAllPassengersByTripPanel(startForm));
                }
            });
            addStationButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    initManagerFrame(new AddStationPanel(startForm));
                }
            });
            addTrainButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    initManagerFrame(new AddTrainPanel(startForm));
                }
            });

        }
        return managerMenu;
    }

    public void setSessionId(Long sessionId) {
        this.sessionId = sessionId;
    }

    public Long getSessionId() {
        return sessionId;
    }

    public JPanel getManagerFrame() {
        return managerFrame;
    }

}
