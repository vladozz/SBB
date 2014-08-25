package com.tsystems.javaschool.vm.client.panels.manager;

import com.tsystems.javaschool.vm.client.StartForm;
import com.tsystems.javaschool.vm.client.listeners.AddStationButtonAction;

import javax.swing.*;

public class AddStationPanel extends JPanel {

    public AddStationPanel(StartForm parent) {
        super();


        JLabel label1 = new JLabel("Station name: ");
        JTextField stationField = new JTextField();
        JLabel label2 = new JLabel("Station time zone (digit): ");
        JTextField timeZoneField = new JTextField();
        JButton addButton = new JButton("Add Station");


        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(label1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGap(39, 39, 39))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(label2, GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                                                .addGap(18, 18, 18)))
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addComponent(addButton, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE)
                                        .addComponent(stationField, GroupLayout.Alignment.TRAILING)
                                        .addComponent(timeZoneField, GroupLayout.Alignment.TRAILING)))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(label1)
                                        .addComponent(stationField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(label2)
                                        .addComponent(timeZoneField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(addButton)
                                .addContainerGap(22, Short.MAX_VALUE))
        );

        addButton.addActionListener(new AddStationButtonAction(parent, stationField, timeZoneField));
        setVisible(true);
    }

}
