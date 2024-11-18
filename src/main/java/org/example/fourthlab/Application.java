package org.example.fourthlab;

import org.example.fourthlab.service.PeopleCRUD;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Application {
    public static void main(String[] args) {
        JFrame frame = new JFrame("People CRUD Application");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2));

        JLabel firstNameLabel = new JLabel("First Name");
        JTextField firstNameField = new JTextField();

        JLabel middleNameLabel = new JLabel("Middle Name");
        JTextField middleNameField = new JTextField();

        JLabel lastNameLabel = new JLabel("Last Name");
        JTextField lastNameField = new JTextField();

        JLabel dateOfBirthLabel = new JLabel("Date of Birth");
        JTextField dateOfBirthField = new JTextField();

        JButton addButton = new JButton("Add Person");
        JButton viewButton = new JButton("View All");

        panel.add(firstNameLabel);
        panel.add(firstNameField);
        panel.add(middleNameLabel);
        panel.add(middleNameField);
        panel.add(lastNameLabel);
        panel.add(lastNameField);
        panel.add(dateOfBirthLabel);
        panel.add(dateOfBirthField);
        panel.add(addButton);
        panel.add(viewButton);



        frame.add(panel);
        frame.setVisible(true);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String firstName = firstNameField.getText();
                String middleName = middleNameField.getText();
                String lastName = lastNameField.getText();
                String dateOfBirth = dateOfBirthField.getText();
                PeopleCRUD crud = new PeopleCRUD();
                boolean isAdded = crud.createPerson(firstName, middleName, lastName, dateOfBirth);
                if (isAdded) {
                    JOptionPane.showMessageDialog(frame, "Person added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(frame, "Failed to add person.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ViewAllPeopleWindow();
            }
        });
    }
}