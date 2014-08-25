package com.tsystems.javaschool.vm.client.panels.manager;


import com.tsystems.javaschool.vm.client.StartForm;

import javax.swing.*;
import java.awt.*;

public class ManagerPane extends JPanel {
    StartForm parent;

    public ManagerPane(StartForm parent) {
        super(new GridLayout(1, 1));
        this.parent = parent;

        JTabbedPane tabbedPane = new JTabbedPane();


        JPanel addStationPanel = new AddStationPanel(parent);
        tabbedPane.addTab("Add Station", addStationPanel);

        JPanel addTrainPanel = new AddTrainPanel(parent);
        tabbedPane.addTab("Add Train", addTrainPanel);

        JPanel getAllTrainsPanel = new GetAllTrainsPanel(parent);
        tabbedPane.addTab("Get All Trains", getAllTrainsPanel);

        GetStationsOfPathPanel getStationsOfPath = new GetStationsOfPathPanel(parent, tabbedPane);
        JPanel getAllPathsPanel = new GetAllPathsPanel(parent, getStationsOfPath);
        tabbedPane.addTab("Get All Paths", getAllPathsPanel);
        tabbedPane.addTab("Get stations of path", getStationsOfPath);

        JPanel getAllPassengersByTripPanel = new GetAllPassengersByTripPanel(parent);
        tabbedPane.addTab("Get All Passengers Of Trip", getAllPassengersByTripPanel);




        //Add the tabbed pane to this panel.
        add(tabbedPane);

        //The following line enables to use scrolling tabs.
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
    }

}