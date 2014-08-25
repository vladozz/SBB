package com.tsystems.javaschool.vm.client.panels;

import com.tsystems.javaschool.vm.client.StartForm;
import com.tsystems.javaschool.vm.client.listeners.LoginButtonAction;
import com.tsystems.javaschool.vm.client.listeners.WithoutLoginButtonAction;

import javax.swing.*;

public class LoginPanel extends JPanel {
    private StartForm parent;

    public LoginPanel(StartForm parent) {
        this.parent = parent;
        initComponents();
    }

    private void initComponents() {

        labelLogin = new JLabel("Login");
        loginField = new JTextField("admin");
        labelPassword = new JLabel("Password");
        passwordField = new JPasswordField("admin");
        loginButton = new JButton("Sign in");
        withoutLoginButton = new JButton("Customer enter");

        labelLogin.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N


        loginField.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N


        labelPassword.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N


        passwordField.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        loginButton.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N


        withoutLoginButton.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        withoutLoginButton.addActionListener(new WithoutLoginButtonAction(parent));
        loginButton.addActionListener(new LoginButtonAction(loginField, passwordField, parent));

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                        .addComponent(labelPassword, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(labelLogin, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(loginField)
                                                        .addComponent(passwordField, GroupLayout.DEFAULT_SIZE, 221, Short.MAX_VALUE)))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(loginButton, GroupLayout.PREFERRED_SIZE, 142, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 71, Short.MAX_VALUE)
                                                .addComponent(withoutLoginButton, GroupLayout.PREFERRED_SIZE, 142, GroupLayout.PREFERRED_SIZE)
                                                .addGap(27, 27, 27)))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addComponent(loginField, GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                                        .addComponent(labelLogin, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addComponent(passwordField, GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                                        .addComponent(labelPassword, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(loginButton, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(withoutLoginButton, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE))
                                .addGap(45, 45, 45))
        );
    }// </editor-fold>



    // Variables declaration - do not modify
    private JLabel labelLogin;
    private JLabel labelPassword;
    private JButton loginButton;
    private JTextField loginField;
    private JPasswordField passwordField;
    private JButton withoutLoginButton;
}
