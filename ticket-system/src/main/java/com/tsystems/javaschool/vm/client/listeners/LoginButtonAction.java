package com.tsystems.javaschool.vm.client.listeners;

import com.tsystems.javaschool.vm.client.Communicator;
import com.tsystems.javaschool.vm.client.StartForm;
import com.tsystems.javaschool.vm.dto.LoginDTO;
import com.tsystems.javaschool.vm.exception.InvalidLoginOrPasswordException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class LoginButtonAction implements ActionListener {
    private JTextField loginField;
    private JPasswordField passwordField;
    private StartForm parent;

    public LoginButtonAction(JTextField loginField, JPasswordField passwordField, StartForm parent) {
        this.loginField = loginField;
        this.passwordField = passwordField;
        this.parent = parent;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        LoginDTO loginDTO = new LoginDTO(loginField.getText(), passwordField.getText());
        try {
            Long session = Communicator.loginAction(loginDTO);
            parent.setSessionId(session);
            //JOptionPane.showMessageDialog(parent, "Login success!");
            parent.initManagerFrame();
        } catch (InvalidLoginOrPasswordException e1) {
            JOptionPane.showMessageDialog(parent, "Invalid login or password! Try again");
        } catch (IOException e1) {
            e1.printStackTrace();
            JOptionPane.showMessageDialog(parent, "Connection failed: " + e1.getClass() + " " + e1.getMessage());
        }

    }
}
