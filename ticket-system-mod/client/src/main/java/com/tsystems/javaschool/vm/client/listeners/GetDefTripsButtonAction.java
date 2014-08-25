package com.tsystems.javaschool.vm.client.listeners;

import com.toedter.calendar.JCalendar;
import com.tsystems.javaschool.vm.client.Communicator;
import com.tsystems.javaschool.vm.client.StartForm;
import com.tsystems.javaschool.vm.client.panels.customer.GetDefTripsPanel;
import com.tsystems.javaschool.vm.dto.ReqDefTripDTO;
import com.tsystems.javaschool.vm.exception.InvalidIdException;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

public class GetDefTripsButtonAction implements ActionListener {
    private StartForm parent;
    private JTextField arriveStationField;
    private JTextField departureStationField;
    private JCalendar calendarAfter;
    private JCalendar calendarBefore;
    private GetDefTripsPanel panel;

    public GetDefTripsButtonAction(StartForm parent, JTextField arriveStationField, JTextField departureStationField,
                                   JCalendar calendarAfter, JCalendar calendarBefore, GetDefTripsPanel panel) {
        this.parent = parent;
        this.arriveStationField = arriveStationField;
        this.departureStationField = departureStationField;
        this.calendarAfter = calendarAfter;
        this.calendarBefore = calendarBefore;
        this.panel = panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String arriveStation = arriveStationField.getText();
        String departureStation = departureStationField.getText();
        Calendar calendarAfter = this.calendarAfter.getCalendar();
        calendarAfter.set(Calendar.HOUR_OF_DAY, 0);
        calendarAfter.set(Calendar.MINUTE, 0);
        calendarAfter.set(Calendar.SECOND, 0);
        calendarAfter.set(Calendar.MILLISECOND, 0);
        Calendar calendarBefore = this.calendarBefore.getCalendar();
        calendarBefore.set(Calendar.HOUR_OF_DAY, 0);
        calendarBefore.set(Calendar.MINUTE, 0);
        calendarBefore.set(Calendar.SECOND, 0);
        calendarBefore.set(Calendar.MILLISECOND, 0);
        if (arriveStation != null && !arriveStation.equals("")
                && departureStation != null && !departureStation.equals("")) {
            try {

                Timestamp departureTime = new Timestamp(calendarAfter.getTimeInMillis());
                Timestamp arriveTime = new Timestamp(calendarBefore.getTimeInMillis() + 86400000);
                final List<ReqDefTripDTO> list =
                        Communicator.getDefTripsAction(departureStation, arriveStation, departureTime, arriveTime);
                if (list != null) {
                    if (list.size() == 0) {
                        JOptionPane.showMessageDialog(parent, "No trips found.");
                    } else {
                        panel.setTripsList(list);
                        TableModel model = new AbstractTableModel() {
                            @Override
                            public String getColumnName(int column) {
                                final String[] titles = {"Trip", "Path", "Train", "Departure time", "Arrive Time"};
                                return titles[column];
                            }

                            @Override
                            public int getRowCount() {
                                return list.size();
                            }

                            @Override
                            public int getColumnCount() {
                                return 5;
                            }

                            @Override
                            public Object getValueAt(int rowIndex, int columnIndex) {
                                ReqDefTripDTO reqDefTripDTO = list.get(rowIndex);
                                switch (columnIndex) {
                                    case 0:
                                        return reqDefTripDTO.getTripId();
                                    case 1:
                                        return reqDefTripDTO.getPathTitle();
                                    case 2:
                                        return reqDefTripDTO.getTrainNumber();
                                    case 3:
                                        return reqDefTripDTO.getDepartureTime();
                                    case 4:
                                        return reqDefTripDTO.getArriveTime();
                                    default:
                                        break;
                                }
                                return null;
                            }

                        };
                        panel.setModel(model);
                    }
                } else {
                    JOptionPane.showMessageDialog(parent, "Operation failed!");
                }
                //todo
            } catch (IOException e1) {
                JOptionPane.showMessageDialog(parent, "Connection failed: " + e1.getClass() + " " + e1.getMessage());
            } catch (InvalidIdException e1) {
                JOptionPane.showMessageDialog(parent, "Trip number " + arriveStation + " doesn't exist");
            }

        } else {
            JOptionPane.showMessageDialog(parent, "Trip number field might not be empty");
            return;
        }
    }
}
