package com.tsystems.javaschool.vm.client.listeners;

import com.tsystems.javaschool.vm.client.Communicator;
import com.tsystems.javaschool.vm.client.StartForm;
import com.tsystems.javaschool.vm.client.panels.customer.GetDefTripsPanel;
import com.tsystems.javaschool.vm.client.panels.customer.PassengerPanel;
import com.tsystems.javaschool.vm.domain.Passenger;
import com.tsystems.javaschool.vm.domain.Ticket;
import com.tsystems.javaschool.vm.dto.PassengerDTO;
import com.tsystems.javaschool.vm.dto.ReqDefTripDTO;
import com.tsystems.javaschool.vm.dto.TicketDTO;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class BuyTicketButtonAction implements ActionListener {
    private StartForm parent;
    private List<ReqDefTripDTO> list;
    private GetDefTripsPanel panel;


    public BuyTicketButtonAction(StartForm parent, GetDefTripsPanel panel) {
        this.parent = parent;
        this.list = panel.getTripsList();
        this.panel = panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        list = panel.getTripsList();
        if (list != null) {
            int i = panel.getTripsTable().getSelectedRow();
            if (i == -1 && panel.getTripsTable().getRowCount() == 1) {
                i = 0;
            }
            if (i > -1) {
                JFrame frame = new JFrame();
                frame.setTitle("Buying ticket");
                frame.add(new PassengerPanel(frame, list.get(i).getDepartureBoardId(), list.get(i).getArriveBoardId()));
                frame.pack();
                frame.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(parent, "No row selected.");
            }
        } else {
            JOptionPane.showMessageDialog(parent, "Select a trip.");
        }
    }
}
