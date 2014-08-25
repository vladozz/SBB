package com.tsystems.javaschool.vm.client.listeners;

import com.tsystems.javaschool.vm.client.Communicator;
import com.tsystems.javaschool.vm.client.StartForm;
import com.tsystems.javaschool.vm.client.panels.manager.GetStationsOfPathPanel;
import com.tsystems.javaschool.vm.exception.InvalidIndexException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class RemoveStationFromPathButtonAction implements ActionListener {
    private StartForm parent;
    private GetStationsOfPathPanel panel;


    public RemoveStationFromPathButtonAction(StartForm parent, GetStationsOfPathPanel panel) {
        this.parent = parent;
        this.panel = panel;
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        int index = panel.getTable().getSelectedRow();

        if (index > -1) {
            String pathTitle = panel.getPathTitle();
            if (pathTitle != null && !pathTitle.equals("")) {
                    try {
                        boolean answer = Communicator.removeStationFromPath(pathTitle,  index + 1, parent.getSessionId());

                        if (answer) {
                            panel.click(pathTitle);
                            JOptionPane.showMessageDialog(parent, "Operation success! Station is added");
                        } else {
                            JOptionPane.showMessageDialog(parent, "Operation failed! Station or path don't exist");
                        }

                    } catch (IOException e1) {

                        JOptionPane.showMessageDialog(parent, "Connection failed: " + e1.getClass() + " " + e1.getMessage());
                    } catch (InvalidIndexException e1) {
                        JOptionPane.showMessageDialog(parent, "Invalid input data");
                    }
            } else {
                JOptionPane.showMessageDialog(parent, "Path might not be empty. Enter path title and press button \"Get stations\" before this operation!");
                return;
            }
        } else {
            JOptionPane.showMessageDialog(parent, "No station selected. Please select a station");
            return;
        }
    }
}
