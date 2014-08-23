package com.tsystems.javaschool.vm.client.panels.manager;

import com.tsystems.javaschool.vm.client.Communicator;
import com.tsystems.javaschool.vm.client.StartForm;
import com.tsystems.javaschool.vm.client.listeners.GetAllPassengersByTripButtonAction;
import com.tsystems.javaschool.vm.dto.TrainDTO;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

public class GetAllPassengersByTripPanel extends JPanel {

    public GetAllPassengersByTripPanel(final StartForm parent) {
        super();
        setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5),
                BorderFactory.createLineBorder(Color.black)));
        JLabel label = new JLabel("Enter trip number: ");
        JTextField textField = new JTextField("40", 10);
        JButton button = new JButton("Get passengers list");
        add(label);
        add(textField);
        add(button);
        button.addActionListener(new GetAllPassengersByTripButtonAction(parent, textField, this));

        setVisible(true);
    }

}
