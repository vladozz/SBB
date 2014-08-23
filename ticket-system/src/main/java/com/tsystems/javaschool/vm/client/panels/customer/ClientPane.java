package com.tsystems.javaschool.vm.client.panels.customer;


import com.tsystems.javaschool.vm.client.StartForm;

import javax.swing.*;
import java.awt.*;

public class ClientPane extends JPanel {
    StartForm parent;

    public ClientPane(StartForm parent) {
        super(new GridLayout(1, 1));
        this.parent = parent;

        JTabbedPane tabbedPane = new JTabbedPane();


        JPanel getBoardForStationTab = new GetBoardForStationPanel(parent);
        tabbedPane.addTab("Get Board For Station", getBoardForStationTab);


        JPanel buyTicketTab = new GetDefTripsPanel(parent);
        tabbedPane.addTab("Get Required Trips", buyTicketTab);


        //Add the tabbed pane to this panel.
        add(tabbedPane);

        //The following line enables to use scrolling tabs.
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
    }

}