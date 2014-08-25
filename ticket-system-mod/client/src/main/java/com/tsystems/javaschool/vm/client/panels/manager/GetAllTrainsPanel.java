package com.tsystems.javaschool.vm.client.panels.manager;

import com.tsystems.javaschool.vm.client.Communicator;
import com.tsystems.javaschool.vm.client.StartForm;
import com.tsystems.javaschool.vm.dto.TrainDTO;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

public class GetAllTrainsPanel extends JPanel {

    public GetAllTrainsPanel(final StartForm parent) {

        final JTable table = new JTable();
        JButton button = new JButton("Refresh");
        JScrollPane jScrollPane1 = new JScrollPane();
        jScrollPane1.setViewportView(table);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getData(parent, table);

            }
        });

        table.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {
                },
                new String [] {
                        "Id", "Number", "Places Quantity"
                }
        ));

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(button, GroupLayout.PREFERRED_SIZE, 102, GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 309, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(button, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 0, Short.MAX_VALUE))
                                        .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 420, Short.MAX_VALUE))
                                .addContainerGap())
        );

        getData(parent, table);

        setVisible(true);
    }

    public void getData(StartForm parent, JTable table) {
        try {
            final List<TrainDTO> list = Communicator.getAllTrainsAction(parent.getSessionId());
            if (list == null) {
                JOptionPane.showMessageDialog(parent, "Error! No data received");
                return;
            } else {
                TableModel model = new AbstractTableModel() {

                    @Override
                    public String getColumnName(int column) {
                        final String[] columns = {"Id", "Number", "Places Quantity"};
                        return columns[column];
                    }

                    @Override
                    public int getRowCount() {
                        return list.size();
                    }

                    @Override
                    public int getColumnCount() {
                        return 3;
                    }

                    @Override
                    public Object getValueAt(int rowIndex, int columnIndex) {
                        if (columnIndex == 0) {
                            return list.get(rowIndex).getId();
                        } else if (columnIndex == 1) {
                            return list.get(rowIndex).getNumber();
                        } else if (columnIndex == 2) {
                            return list.get(rowIndex).getPlacesQty();
                        } else {
                            return null;
                        }

                    }
                };
                //TODO: add functions
                table.setModel(model);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(parent, "Connection failed: " + e.getClass() + " " + e.getMessage());
        }
    }

}
