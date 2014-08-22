package com.tsystems.javaschool.vm.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartForm extends JFrame {
    private JTextField loginField;
    private JPasswordField passwordField;
    private JButton withoutLogin;
    private JButton loginButton;
    private JButton getBoardButton;

    public StartForm() {
        initStartFrame();
    }

    public static void main(String[] args) {
        StartForm startForm1 = new StartForm();
    }

    public void initStartFrame() {
        setSize(300, 300);
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5),
                BorderFactory.createLineBorder(Color.black)));
        setContentPane(panel);
        JLabel labelLogin = new JLabel("Enter login");
        JLabel labelPassword  = new JLabel("Enter password");
        panel.add(labelLogin);
        panel.add(labelPassword);
        loginField = new JTextField(30);
        passwordField = new JPasswordField(30);
        panel.add(loginField);
        panel.add(passwordField);
        loginButton  = new JButton("Login");
        withoutLogin = new JButton("Enter without login");
        panel.add(loginButton);
        panel.add(withoutLogin);
        //pack();
        withoutLogin.addActionListener(new WithoutLoginButtonAction(this));
        loginButton.addActionListener(new LoginButtonAction(loginField, passwordField));
        setVisible(true);
    }

    public void initClientFrame() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5),
                BorderFactory.createLineBorder(Color.black)));
        setContentPane(panel);
        getBoardButton = new JButton("Get Board");
        panel.add(getBoardButton);
        //pack();

        setVisible(true);
    }

}
