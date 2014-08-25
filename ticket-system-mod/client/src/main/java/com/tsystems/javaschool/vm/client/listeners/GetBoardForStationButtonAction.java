package com.tsystems.javaschool.vm.client.listeners;

import com.toedter.calendar.JCalendar;
import com.tsystems.javaschool.vm.client.Communicator;
import com.tsystems.javaschool.vm.client.StartForm;
import com.tsystems.javaschool.vm.client.panels.customer.GetBoardForStationPanel;
import com.tsystems.javaschool.vm.dto.BoardStationDTO;
import com.tsystems.javaschool.vm.exception.InvalidIdException;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class GetBoardForStationButtonAction implements ActionListener {
    private StartForm parent;
    private JTextField stationField;
    private JCalendar calendar;
    private GetBoardForStationPanel panel;

    public GetBoardForStationButtonAction(StartForm parent, JTextField stationField, JCalendar calendar,
                                          GetBoardForStationPanel panel) {
        this.parent = parent;
        this.stationField = stationField;
        this.calendar = calendar;
        this.panel = panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String station = stationField.getText();
        Calendar calendar1 = calendar.getCalendar();
        calendar1.set(Calendar.HOUR_OF_DAY, 0);
        calendar1.set(Calendar.MINUTE, 0);
        calendar1.set(Calendar.SECOND, 0);
        calendar1.set(Calendar.MILLISECOND, 0);
        if (station != null && !station.equals("")) {
            try {
                final List<BoardStationDTO> list =
                        Communicator.getBoardForStationAction(station, new Timestamp(calendar1.getTimeInMillis()));
                if (list != null) {
                    if (list.size() == 0) {
                        JOptionPane.showMessageDialog(parent, "No rows found.");
                    }
                    TableModel model = new AbstractTableModel() {
                        @Override
                        public String getColumnName(int column) {
                            final String[] titles = {"Trip", "Path", "Train", "Arrive", "Stand", "Departure"};
                            return titles[column];
                        }

                        @Override
                        public int getRowCount() {
                            return list.size();
                        }

                        @Override
                        public int getColumnCount() {
                            return 6;
                        }

                        @Override
                        public Object getValueAt(int rowIndex, int columnIndex) {
                            BoardStationDTO boardStationDTO = list.get(rowIndex);
                            switch (columnIndex) {
                                case 0:
                                    return boardStationDTO.getTripNumber();
                                case 1:
                                    return boardStationDTO.getPathTitle();
                                case 2:
                                    return boardStationDTO.getTrainNumber();
                                case 3:
                                    return new SimpleDateFormat("EEE, d MMM yyyy HH:mm", Locale.ENGLISH).format(boardStationDTO.getArriveTime());
                                case 4:
                                    return boardStationDTO.getStandTime();
                                case 5:
                                    return new SimpleDateFormat("EEE, d MMM yyyy HH:mm", Locale.ENGLISH).format(boardStationDTO.getDepartureTime());
                                default:
                                    break;
                            }
                            return null;
                        }
                    };
                    panel.setModel(model);

                } else {
                    JOptionPane.showMessageDialog(parent, "Operation failed!");
                }
                //todo
            } catch (IOException e1) {

                JOptionPane.showMessageDialog(parent, "Connection failed: " + e1.getClass() + " " + e1.getMessage());
            } catch (InvalidIdException e1) {
                JOptionPane.showMessageDialog(parent, "Trip number " + station + " doesn't exist");
            }

        } else {
            JOptionPane.showMessageDialog(parent, "Trip number field might not be empty");
            return;
        }
    }
}
