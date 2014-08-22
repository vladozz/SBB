package com.tsystems.javaschool.vm.client;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WithoutLoginButtonAction implements ActionListener {
    private StartForm parentForm;

    public WithoutLoginButtonAction(StartForm parentForm) {
        this.parentForm = parentForm;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        parentForm.initClientFrame();

    }
}
