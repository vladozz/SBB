package com.tsystems.javaschool.vm.client.panels.manager;

import com.tsystems.javaschool.vm.client.StartForm;
import com.tsystems.javaschool.vm.client.listeners.AddStationButtonAction;
import com.tsystems.javaschool.vm.client.listeners.AddTrainButtonAction;

import javax.swing.*;
import java.awt.*;

public class AddStationPanel extends JPanel {

    public AddStationPanel(StartForm parent) {
        super();


        JLabel label1 = new JLabel("Station name: ");
        JTextField stationField = new JTextField();
        JLabel label2 = new JLabel("Station time zone (digit): ");
        JTextField timeZoneField = new JTextField();
        JButton addButton = new JButton("Add Station");


        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(label1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGap(39, 39, 39))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(label2, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                                                .addGap(18, 18, 18)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(addButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE)
                                        .addComponent(stationField, javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(timeZoneField, javax.swing.GroupLayout.Alignment.TRAILING)))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(label1)
                                        .addComponent(stationField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(label2)
                                        .addComponent(timeZoneField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(addButton)
                                .addContainerGap(22, Short.MAX_VALUE))
        );

        addButton.addActionListener(new AddStationButtonAction(parent, stationField, timeZoneField));
        setVisible(true);
    }

}
