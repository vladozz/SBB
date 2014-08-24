package com.tsystems.javaschool.vm.client.panels.customer;

import com.toedter.calendar.JCalendar;
import com.tsystems.javaschool.vm.client.StartForm;
import com.tsystems.javaschool.vm.client.listeners.GetBoardForStationButtonAction;

import javax.swing.table.TableModel;
import java.util.Locale;

public class GetBoardForStationPanel extends javax.swing.JPanel {
    private StartForm parent;

    public GetBoardForStationPanel(StartForm parent) {
        this.parent = parent;
        initComponents();
    }

    public void setModel(TableModel model) {
        boardTable.setModel(model);
    }

    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        stationField = new javax.swing.JTextField();
        get = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        boardTable = new javax.swing.JTable();
        jButton1 = new JCalendar();
        jButton1.setLocale(Locale.ENGLISH);

        jLabel1.setText("Enter station name");

        get.setLabel("Get Station List");
        get.addActionListener(new GetBoardForStationButtonAction(parent, stationField, jButton1, this));

        boardTable.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {
                        /*{null, null, null, null, null, null}*/
                },
                new String [] {
                        "Trip", "Path", "Train", "Arrive", "Stand", "Departure"
                }
        ));
        jScrollPane2.setViewportView(boardTable);



        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(stationField, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(33, 33, 33)
                                                .addComponent(get))
                                        .addGroup(layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 707, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(jLabel1)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(stationField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(38, 38, 38)
                                                .addComponent(get))
                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 520, Short.MAX_VALUE))
                                .addContainerGap())
        );
        parent.pack();
    }



    private javax.swing.JTable boardTable;
    private javax.swing.JButton get;
    private JCalendar jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField stationField;

}

