package com.tsystems.javaschool.vm.client.panels.manager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ManagerPanel extends JPanel {
    private JLabel label;
    private JTextField textField;
    private JButton runButton;

    public ManagerPanel() {
        super();
        setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5),
                BorderFactory.createLineBorder(Color.black)));

        label = new JLabel("Enter field: ");
        textField = new JTextField(15);
        runButton = new JButton("Run");

        add(label);
        add(textField);
        add(runButton);
        setVisible(true);
    }

    public void setLabel(String text) {
        label.setText(text);
        setVisible(true);
    }

    public int setActionListener(ActionListener listener) {
        ActionListener[] actionListeners = runButton.getActionListeners();
        if (actionListeners.length == 0) {
            runButton.addActionListener(listener);
        } else {
            actionListeners[0] = listener;
        }
        return actionListeners.length;
    }
}
