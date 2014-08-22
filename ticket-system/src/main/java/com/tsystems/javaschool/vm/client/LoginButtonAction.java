package com.tsystems.javaschool.vm.client;

import com.tsystems.javaschool.vm.dto.LoginDTO;
import com.tsystems.javaschool.vm.protocol.Command;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;

public class LoginButtonAction implements ActionListener {
    private JTextField loginField;
    private JPasswordField passwordField;

    public LoginButtonAction(JTextField loginField, JPasswordField passwordField) {
        this.loginField = loginField;
        this.passwordField = passwordField;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        LoginDTO loginDTO = new LoginDTO(loginField.getText(), passwordField.getText());
        try(Socket socket = new Socket("localhost", 6574)) {
            OutputStream outputStream = socket.getOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(Command.LoginManager);
            objectOutputStream.writeObject(loginDTO);
            socket.shutdownOutput();
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            Long session = (Long) objectInputStream.readObject();

            socket.shutdownInput();
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        }
    }
}
