package com.tsystems.javaschool.vm.client.listeners;

import com.tsystems.javaschool.vm.client.Communicator;
import com.tsystems.javaschool.vm.client.StartForm;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.TimeZone;

public class AddStationButtonAction implements ActionListener {
    private StartForm parent;
    private JTextField titleField;
    private JTextField timeZoneField;

    public AddStationButtonAction(StartForm parent, JTextField titleField, JTextField timeZoneField) {
        this.parent = parent;
        this.titleField = titleField;
        this.timeZoneField = timeZoneField;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String number = titleField.getText();
        if (number != null && !number.equals("")) {
            String timeZoneString = timeZoneField.getText();
            if (timeZoneString != null && !timeZoneString.equals("")) {
                try {
                    Short timeZone = Short.parseShort(timeZoneString);
                    timeZoneString = "GMT" + (timeZone > 0 ? "+" : "") + timeZone + ":00";
                    Long stationId = Communicator.addStationAction(number, TimeZone.getTimeZone(timeZoneString), parent.getSessionId());
                    if (stationId != null) {
                        JOptionPane.showMessageDialog(parent, "Creation success! Station ID is " + stationId);
                    } else {
                        JOptionPane.showMessageDialog(parent, "Creation failed!");
                    }
                    //todo
                } catch (NumberFormatException e1) {
                    JOptionPane.showMessageDialog(parent, "Time Zone field has illegal format");
                } catch (IOException e1) {
                    JOptionPane.showMessageDialog(parent, "Connection failed: " + e1.getClass() + " " + e1.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(parent, "Time Zone field might not be empty");
                return;
            }
        } else {
            JOptionPane.showMessageDialog(parent, "Title field might not be empty");
            return;
        }
    }
}
