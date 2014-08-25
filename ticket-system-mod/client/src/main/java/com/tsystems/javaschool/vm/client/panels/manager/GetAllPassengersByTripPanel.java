package com.tsystems.javaschool.vm.client.panels.manager;

import com.tsystems.javaschool.vm.client.StartForm;
import com.tsystems.javaschool.vm.client.listeners.GetAllPassengersByTripButtonAction;

import javax.swing.*;

public class GetAllPassengersByTripPanel extends JPanel {


    public GetAllPassengersByTripPanel(StartForm parent) {
        JLabel label = new JLabel("Enter trip number: ");
        JTextField textField = new JTextField("40", 10);
        JButton button = new JButton("Get passengers list");
        JScrollPane jScrollPane1 = new JScrollPane();
        JTable table = new JTable();

        table.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {},
                new String [] {
                        "Id", "First name", "Last name", "Date of birth"
                }
        ));
        jScrollPane1.setViewportView(table);

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addComponent(button, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(label, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(textField))
                                .addGap(28, 28, 28)
                                .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 303, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 393, Short.MAX_VALUE)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(label)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(button, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
                                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );




        button.addActionListener(new GetAllPassengersByTripButtonAction(parent, textField, table));

        setVisible(true);
    }
}
