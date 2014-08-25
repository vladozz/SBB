package com.tsystems.javaschool.vm.client.listeners;

import com.tsystems.javaschool.vm.client.Communicator;
import com.tsystems.javaschool.vm.client.StartForm;
import com.tsystems.javaschool.vm.client.panels.manager.GetStationsOfPathPanel;
import com.tsystems.javaschool.vm.dto.PassengerDTO;
import com.tsystems.javaschool.vm.dto.StationDTO;
import com.tsystems.javaschool.vm.exception.InvalidIdException;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

public class GetStationsOfPathButtonAction implements ActionListener {
    private StartForm parent;
    private JTextField pathField;
    private GetStationsOfPathPanel panel;

    public GetStationsOfPathButtonAction(StartForm parent, JTextField pathField, GetStationsOfPathPanel panel) {
        this.parent = parent;
        this.pathField = pathField;
        this.panel = panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String pathTitle = pathField.getText();
        if (pathTitle != null && !pathTitle.equals("")) {
            try {
                panel.setPathTitle(pathTitle);

                final List<StationDTO> list = Communicator.getStationsOfPathAction(pathTitle, parent.getSessionId());
                if (list != null) {
                    TableModel model = new AbstractTableModel() {

                        @Override
                        public String getColumnName(int column) {
                            final String[] columns = {
                                    "Id", "Title", "TimeZone"
                            };
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
                            final int HOUR = 1000 * 60 * 60;
                            StationDTO stationDTO = list.get(rowIndex);
                            switch (columnIndex) {
                                case 0:
                                    return stationDTO.getId();
                                case 1:
                                    return stationDTO.getTitle();
                                case 2:
                                    int gmt = stationDTO.getTimeZone().getRawOffset() / HOUR;
                                    return "GMT" + (gmt > 0 ? "+" : "") + gmt + ":00";
                                default:
                                    return null;
                            }
                        }
                    };
                    panel.getTable().setModel(model);


                } else {
                    JOptionPane.showMessageDialog(parent, "Operation failed!");
                }
                //todo

            } catch (IOException e1) {
                e1.printStackTrace();
                JOptionPane.showMessageDialog(parent, "Connection failed: " + e1.getClass() + " " + e1.getMessage());
            } catch (InvalidIdException e1) {
                JOptionPane.showMessageDialog(parent, "Station with title \"" + pathTitle + "\" doesn't exist");
            }

        } else {
            JOptionPane.showMessageDialog(parent, "Station title field might not be empty");
            return;
        }
    }
}
