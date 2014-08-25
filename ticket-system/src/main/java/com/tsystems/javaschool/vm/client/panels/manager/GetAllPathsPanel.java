package com.tsystems.javaschool.vm.client.panels.manager;

import com.tsystems.javaschool.vm.client.Communicator;
import com.tsystems.javaschool.vm.client.StartForm;
import com.tsystems.javaschool.vm.dto.PathDTO;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

public class GetAllPathsPanel extends JPanel {


    public GetAllPathsPanel(final StartForm parent, final GetStationsOfPathPanel getStationsOfPathPanel) {

        final JTable table = new JTable();
        JButton button = new JButton("Refresh");
        final JButton getStationsButton = new JButton("Get stations");
        JScrollPane jScrollPane1 = new JScrollPane();
        jScrollPane1.setViewportView(table);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getData(parent, table);

            }
        });
        getStationsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table.getSelectedRow();
                if (row > -1) {
                    getStationsOfPathPanel.click((String) table.getValueAt(row, 1));
                }

            }
        });

        table.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {
                },
                new String [] {
                        "Id", "Title"
                }
        ));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(button, javax.swing.GroupLayout.DEFAULT_SIZE, 102, Short.MAX_VALUE)
                                        .addComponent(getStationsButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(button, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(43, 43, 43)
                                                .addComponent(getStationsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 0, Short.MAX_VALUE))
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 420, Short.MAX_VALUE))
                                .addContainerGap())
        );

        getData(parent, table);

        setVisible(true);
    }

    public void getData(StartForm parent, JTable table) {
        try {
            final List<PathDTO> list = Communicator.getAllPathsAction(parent.getSessionId());
            if (list == null) {
                JOptionPane.showMessageDialog(parent, "Error! No data received");
                return;
            } else {
                TableModel model = new AbstractTableModel() {

                    @Override
                    public String getColumnName(int column) {
                        final String[] columns = {"Id", "Title"};
                        return columns[column];
                    }

                    @Override
                    public int getRowCount() {
                        return list.size();
                    }

                    @Override
                    public int getColumnCount() {
                        return 2;
                    }

                    @Override
                    public Object getValueAt(int rowIndex, int columnIndex) {
                        if (columnIndex == 0) {
                            return list.get(rowIndex).getId();
                        } else if (columnIndex == 1) {
                            return list.get(rowIndex).getTitle();
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
