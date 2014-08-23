package com.tsystems.javaschool.vm.client.panels.manager;

import com.tsystems.javaschool.vm.client.Communicator;
import com.tsystems.javaschool.vm.client.StartForm;
import com.tsystems.javaschool.vm.client.listeners.AddTrainButtonAction;
import com.tsystems.javaschool.vm.dto.TrainDTO;

import javax.swing.*;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

public class GetAllTrainsPanel extends JPanel {

    public GetAllTrainsPanel(final StartForm parent) {
        super();
        setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5),
                BorderFactory.createLineBorder(Color.black)));

        try {
            final List<TrainDTO> list = Communicator.getAllTrainsAction(parent.getSessionId());
            if (list == null) {
                JOptionPane.showMessageDialog(parent, "Error! No data received");
                return;
            } else {
                TableModel model = new AbstractTableModel() {

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
                            return list.get(rowIndex).getNumber();
                        } else if (columnIndex == 1) {
                            return list.get(rowIndex).getPlacesQty();
                        } else {
                            return new JButton(list.get(rowIndex).getNumber());
                        }

                    }
                };

                final JTable table = new JTable(model);
                JButton button = new JButton("Kick me");
                JButton button2 = new JButton("Kick me");
                add(table);
                add(button);
                add(button2);
                //TODO: add functions
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int i =  table.getSelectedRow();
                        if (i >= 0)
                            JOptionPane.showMessageDialog(parent, "" + list.get(i));

                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(parent, "Connection failed: " + e.getClass() + " " + e.getMessage());
        }

        setVisible(true);
    }

}
