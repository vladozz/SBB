package com.tsystems.javaschool.vm.client;

import javax.swing.*;

public class StartLoginForm implements Runnable {
    @Override
    public void run() {
        JFrame startForm = new StartForm();
        startForm.setTitle("Login Frame");
        startForm.setVisible(true);
    }
}
