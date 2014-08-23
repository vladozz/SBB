package com.tsystems.javaschool.vm.client.panels.manager;

import com.tsystems.javaschool.vm.client.StartForm;
import com.tsystems.javaschool.vm.client.listeners.AddStationButtonAction;
import com.tsystems.javaschool.vm.client.listeners.AddTrainButtonAction;

import javax.swing.*;
import java.awt.*;

public class AddStationPanel extends JPanel {

    public AddStationPanel(StartForm parent) {
        super();
        setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5),
                BorderFactory.createLineBorder(Color.black)));

        JLabel label = new JLabel("Station name: ");
        JTextField titleField = new JTextField(30);
        JLabel label2 = new JLabel("Station time zone (digit): ");
        JTextField timeZoneField = new JTextField(3);
        JButton addButton = new JButton("New Station");

        add(label);
        add(titleField);
        add(label2);
        add(timeZoneField);
        add(addButton);

        addButton.addActionListener(new AddStationButtonAction(parent, titleField, timeZoneField));
        setVisible(true);
    }

}
