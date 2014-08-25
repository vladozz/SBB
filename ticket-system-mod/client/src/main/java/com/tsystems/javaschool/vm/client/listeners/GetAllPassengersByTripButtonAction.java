package com.tsystems.javaschool.vm.client.listeners;

import com.tsystems.javaschool.vm.client.Communicator;
import com.tsystems.javaschool.vm.client.StartForm;
import com.tsystems.javaschool.vm.dto.PassengerDTO;
import com.tsystems.javaschool.vm.exception.InvalidIdException;
import com.tsystems.javaschool.vm.exception.InvalidSessionException;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

public class GetAllPassengersByTripButtonAction implements ActionListener {
    private StartForm parent;
    private JTextField tripIdField;
    private JTable table;

    public GetAllPassengersByTripButtonAction(StartForm parent, JTextField tripIdField, JTable table) {
        this.parent = parent;
        this.tripIdField = tripIdField;
        this.table = table;
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
                        public String getColumnName(int column) {
                            final String[] columns = {
                                    "Id", "First name", "Last name", "Date of birth"
                            };
                            return columns[column];
                        }

                        @Override
                        public int getRowCount() {
                            return list.size();
                        }

                        @Override
                        public int getColumnCount() {
                            return 4;
                        }

                        @Override
                        public Object getValueAt(int rowIndex, int columnIndex) {
                            PassengerDTO passengerDTO = list.get(rowIndex);
                            switch (columnIndex) {
                                case 0:
                                    return passengerDTO.getId();
                                case 1:
                                    return passengerDTO.getFirstName();
                                case 2:
                                    return passengerDTO.getLastName();
                                case 3:
                                    return new SimpleDateFormat("y / M / d").format(passengerDTO.getBirthDate().getTime());
                                default:
                                    return null;
                            }
                        }
                    };
                    table.setModel(model);


                } else {
                    JOptionPane.showMessageDialog(parent, "Operation failed!");
                }
                //todo
            } catch (NumberFormatException e1) {
                JOptionPane.showMessageDialog(parent, "Trip number field has illegal format");
            } catch (IOException e1) {
                JOptionPane.showMessageDialog(parent, "Connection failed: " + e1.getClass() + " " + e1.getMessage());
            } catch (InvalidIdException e1) {
                JOptionPane.showMessageDialog(parent, "Trip number " + idString + " doesn't exist");
            } catch (InvalidSessionException e1) {
                JOptionPane.showMessageDialog(parent, "Invalid session! Log in again "+ "\n\n" + e1);
            }

        } else {
            JOptionPane.showMessageDialog(parent, "Trip number field might not be empty");
            return;
        }
    }
}
