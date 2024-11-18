package org.example.fourthlab;

import org.example.fourthlab.model.Person;
import org.example.fourthlab.service.LettersCRUD;
import org.example.fourthlab.service.PeopleCRUD;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;

import java.util.List;

public class ViewAllPeopleWindow extends JFrame {

    public ViewAllPeopleWindow() {
        setTitle("All People");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("First Name");
        model.addColumn("Middle Name");
        model.addColumn("Last Name");
        model.addColumn("Date of Birth");

        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        PeopleCRUD crud = new PeopleCRUD();
        List<Person> people = crud.retrieveAllPeople();
        for (Person person : people) {
            model.addRow(new Object[]{
                    person.getId(),
                    person.getFirstName(),
                    person.getMiddleName(),
                    person.getLastName(),
                    person.getDateOfBirth()
            });
        }

        JPanel buttonPanel = new JPanel();
        JButton deleteButton = getjButton(crud, model);

        JButton editButton = new JButton("Edit Selected Person");
        editButton.addActionListener(e -> {
            editButtonPressAction(crud, model);
        });

        JButton writeLetterButton = new JButton("Write Letter");
        writeLetterButton.addActionListener(e -> openWriteLetterDialog());
        buttonPanel.add(writeLetterButton);

        JButton viewLettersButton = new JButton("View All Letters");
        viewLettersButton.addActionListener(e -> new ViewAllLettersWindow());
        buttonPanel.add(viewLettersButton);

        buttonPanel.add(deleteButton);
        buttonPanel.add(editButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        add(panel);
        setVisible(true);
    }

    private void editButtonPressAction(PeopleCRUD crud, DefaultTableModel model) {
        String input = JOptionPane.showInputDialog(ViewAllPeopleWindow.this,
                "Enter ID of person to edit:", "Edit Person", JOptionPane.QUESTION_MESSAGE);

        if (input != null && !input.trim().isEmpty()) {
            try {
                int idToEdit = Integer.parseInt(input.trim());

                Person personToEdit = crud.retrievePersonById(idToEdit);

                if (personToEdit != null) {
                    String newFirstName = JOptionPane.showInputDialog(ViewAllPeopleWindow.this,
                            "Edit First Name:", personToEdit.getFirstName());
                    String newMiddleName = JOptionPane.showInputDialog(ViewAllPeopleWindow.this,
                            "Edit Middle Name:", personToEdit.getMiddleName());
                    String newLastName = JOptionPane.showInputDialog(ViewAllPeopleWindow.this,
                            "Edit Last Name:", personToEdit.getLastName());
                    String newDateOfBirth = JOptionPane.showInputDialog(ViewAllPeopleWindow.this,
                            "Edit Date of Birth:", personToEdit.getDateOfBirth());

                    personToEdit.setFirstName(newFirstName);
                    personToEdit.setMiddleName(newMiddleName);
                    personToEdit.setLastName(newLastName);
                    personToEdit.setDateOfBirth(newDateOfBirth);

                    boolean isUpdated = crud.updatePerson(personToEdit);

                    if (isUpdated) {
                        JOptionPane.showMessageDialog(ViewAllPeopleWindow.this, "Person updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        refreshTable(model, crud);
                    } else {
                        JOptionPane.showMessageDialog(ViewAllPeopleWindow.this, "Failed to update person.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(ViewAllPeopleWindow.this, "Person not found for the given ID.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(ViewAllPeopleWindow.this, "Invalid ID entered.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    private void openWriteLetterDialog() {
        JDialog dialog = new JDialog(this, "Write Letter", true);
        dialog.setSize(400, 300);
        dialog.setLayout(new GridLayout(5, 2));

        JTextField senderIdField = new JTextField();
        JTextField receiverIdField = new JTextField();
        JTextField subjectField = new JTextField();
        JTextArea bodyField = new JTextArea();

        dialog.add(new JLabel("Sender ID:"));
        dialog.add(senderIdField);
        dialog.add(new JLabel("Receiver ID:"));
        dialog.add(receiverIdField);
        dialog.add(new JLabel("Subject:"));
        dialog.add(subjectField);
        dialog.add(new JLabel("Body:"));
        dialog.add(new JScrollPane(bodyField));

        JButton submitButton = new JButton("Send Letter");
        submitButton.addActionListener(e -> {
            try {
                int senderId = Integer.parseInt(senderIdField.getText().trim());
                int receiverId = Integer.parseInt(receiverIdField.getText().trim());
                String subject = subjectField.getText().trim();
                String body = bodyField.getText().trim();

                if (subject.isEmpty() || body.isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "Subject and Body cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                LettersCRUD lettersCRUD = new LettersCRUD();
                if (lettersCRUD.createLetter(senderId, receiverId, subject, body)) {
                    JOptionPane.showMessageDialog(dialog, "Letter sent successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    dialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(dialog, "Failed to send the letter.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Invalid ID format.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        dialog.add(new JLabel());
        dialog.add(submitButton);
        dialog.setVisible(true);
    }

    private JButton getjButton(PeopleCRUD crud, DefaultTableModel model) {
        JButton deleteButton = new JButton("Delete Selected Person");
        deleteButton.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(ViewAllPeopleWindow.this,
                    "Enter ID of person to delete:", "Delete Person", JOptionPane.QUESTION_MESSAGE);

            if (input != null && !input.trim().isEmpty()) {
                try {
                    int idToDelete = Integer.parseInt(input.trim());

                    boolean isDeleted = crud.deletePerson(idToDelete);

                    if (isDeleted) {
                        JOptionPane.showMessageDialog(ViewAllPeopleWindow.this, "Person deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        refreshTable(model, crud);
                    } else {
                        JOptionPane.showMessageDialog(ViewAllPeopleWindow.this, "Failed to delete person. ID not found.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(ViewAllPeopleWindow.this, "Invalid ID entered.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        return deleteButton;
    }

    // Method to refresh the table after deletion or update
    private void refreshTable(DefaultTableModel model, PeopleCRUD crud) {
        // Clear the table
        model.setRowCount(0);

        // Re-fetch the updated list of people and populate the table
        List<Person> people = crud.retrieveAllPeople();
        for (Person person : people) {
            model.addRow(new Object[]{
                    person.getId(),
                    person.getFirstName(),
                    person.getMiddleName(),
                    person.getLastName(),
                    person.getDateOfBirth()
            });
        }
    }
}
