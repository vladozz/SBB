package com.tsystems.javaschool.vm.client.listeners;

import com.tsystems.javaschool.vm.client.Communicator;
import com.tsystems.javaschool.vm.client.StartForm;
import com.tsystems.javaschool.vm.dto.PassengerDTO;
import com.tsystems.javaschool.vm.exception.InvalidIdException;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;
import java.util.TimeZone;

public class GetAllPassengersByTripButtonAction implements ActionListener {
    private StartForm parent;
    private JTextField tripIdField;
    private JPanel panel;

    public GetAllPassengersByTripButtonAction(StartForm parent, JTextField tripIdField, JPanel panel) {
        this.parent = parent;
        this.tripIdField = tripIdField;
        this.panel = panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String idString = tripIdField.getText();
        if (idString != null && !idString.equals("")) {
            try {
                Long tripId = Long.parseLong(idString);

                final List<PassengerDTO> list = Communicator.getAllPassengersByTripAction(tripId, parent.getSessionId());
                if (list != null) {
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
                                return list.get(rowIndex).getFirstName();
                            } else if (columnIndex == 1) {
                                return list.get(rowIndex).getLastName();
                            } else {
                                return list.get(rowIndex).getBirthDate();
                            }
                        }
                    };
                    JTable table = new JTable(model);
                    panel.add(table);
                    parent.pack();

                } else {
                    JOptionPane.showMessageDialog(parent, "Operation failed!");
                }
                //todo
            } catch (NumberFormatException e1) {
                JOptionPane.showMessageDialog(parent, "Trip number field has illegal format");
            } catch (IOException e1) {
                e1.printStackTrace();
                JOptionPane.showMessageDialog(parent, "Connection failed: " + e1.getClass() + " " + e1.getMessage());
            } catch (InvalidIdException e1) {
                JOptionPane.showMessageDialog(parent, "Trip number " + idString + " doesn't exist");
            }

        } else {
            JOptionPane.showMessageDialog(parent, "Trip number field might not be empty");
            return;
        }
    }
}
