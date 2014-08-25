package com.tsystems.javaschool.vm.client.listeners;

import com.tsystems.javaschool.vm.client.Communicator;
import com.tsystems.javaschool.vm.client.StartForm;
import com.tsystems.javaschool.vm.exception.InvalidSessionException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class AddTrainButtonAction implements ActionListener {
    private StartForm parent;
    private JTextField numberField;
    private JTextField placesQtyField;

    public AddTrainButtonAction(StartForm parent, JTextField numberField, JTextField placesQtyField) {
        this.parent = parent;
        this.numberField = numberField;
        this.placesQtyField = placesQtyField;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String number = numberField.getText();
        if (number != null && !number.equals("")) {
            String placesQtyString = placesQtyField.getText();
            if (placesQtyString != null && !placesQtyString.equals("")) {
                try {
                    Short placesQty = Short.parseShort(placesQtyString);
                    Long trainId = Communicator.addTrainAction(number, placesQty, parent.getSessionId());
                    if (trainId != null) {
                        JOptionPane.showMessageDialog(parent, "Creation success! Train ID is " + trainId);
                    } else {
                        JOptionPane.showMessageDialog(parent, "Creation failed!");
                    }
                    //todo
                } catch (NumberFormatException e1) {
                    JOptionPane.showMessageDialog(parent, "Places Quantity field has illegal format");
                } catch (IOException e1) {
                    JOptionPane.showMessageDialog(parent, "Connection failed: " + e1.getClass() + " " + e1.getMessage());
                } catch (InvalidSessionException e1) {
                    JOptionPane.showMessageDialog(parent, "Invalid session! Log in again "+ "\n\n" + e1);
                }
            } else {
                JOptionPane.showMessageDialog(parent, "Places Quantity field might not be empty");
                return;
            }
        } else {
            JOptionPane.showMessageDialog(parent, "Number field might not be empty");
            return;
        }
    }
}
