package com.tsystems.javaschool.vm.client.panels.manager;

import com.tsystems.javaschool.vm.client.StartForm;
import com.tsystems.javaschool.vm.client.listeners.AddTrainButtonAction;

import javax.swing.*;
import java.awt.*;

public class AddTrainPanel extends JPanel {

    public AddTrainPanel(StartForm parent) {
        super();
        setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5),
                BorderFactory.createLineBorder(Color.black)));

        JLabel label = new JLabel("Train name: ");
        JTextField titleField = new JTextField(15);
        JTextField placesQtyField = new JTextField(5);
        JButton addButton = new JButton("New Train");

        add(label);
        add(titleField);
        add(placesQtyField);
        add(addButton);

        addButton.addActionListener(new AddTrainButtonAction(parent, titleField, placesQtyField));
        setVisible(true);
    }

}
