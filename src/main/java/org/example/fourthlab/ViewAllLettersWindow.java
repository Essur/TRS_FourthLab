package org.example.fourthlab;

import org.example.fourthlab.model.Letter;
import org.example.fourthlab.service.LettersCRUD;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ViewAllLettersWindow extends JFrame {

    public ViewAllLettersWindow() {
        setTitle("All Letters");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Sender ID");
        model.addColumn("Receiver ID");
        model.addColumn("Subject");
        model.addColumn("Body");
        model.addColumn("Date Sent");

        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton deleteButton = new JButton("Delete Letter");
        JButton editButton = new JButton("Edit Letter");
        buttonPanel.add(deleteButton);
        buttonPanel.add(editButton);
        add(buttonPanel, BorderLayout.SOUTH);

        LettersCRUD crud = new LettersCRUD();
        crud.retrieveAllLetters().forEach(letter -> model.addRow(new Object[]{
                letter.getId(),
                letter.getSenderId(),
                letter.getReceiverId(),
                letter.getSubject(),
                letter.getBody(),
                letter.getCreatedAt()
        }));

        deleteButton.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(this, "Enter ID of letter to delete:");
            if (input != null && !input.trim().isEmpty()) {
                try {
                    int idToDelete = Integer.parseInt(input.trim());
                    if (crud.deleteLetter(idToDelete)) {
                        JOptionPane.showMessageDialog(this, "Letter deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        refreshTable(model, crud);
                    } else {
                        JOptionPane.showMessageDialog(this, "Letter not found.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Invalid ID.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        editButton.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(this, "Enter ID of letter to edit:");
            if (input != null && !input.trim().isEmpty()) {
                try {
                    int idToEdit = Integer.parseInt(input.trim());
                    Letter letter = crud.retrieveAllLetters().stream()
                            .filter(l -> l.getId() == idToEdit)
                            .findFirst().orElse(null);
                    if (letter != null) {
                        String newSubject = JOptionPane.showInputDialog(this, "Edit Subject:", letter.getSubject());
                        String newBody = JOptionPane.showInputDialog(this, "Edit Body:", letter.getBody());
                        if (newSubject != null && newBody != null) {
                            letter.setSubject(newSubject);
                            letter.setBody(newBody);
                            if (crud.updateLetter(letter)) {
                                JOptionPane.showMessageDialog(this, "Letter updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                                refreshTable(model, crud);
                            } else {
                                JOptionPane.showMessageDialog(this, "Failed to update letter.", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Invalid ID.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        setVisible(true);
    }

    private void refreshTable(DefaultTableModel model, LettersCRUD crud) {
        model.setRowCount(0);
        crud.retrieveAllLetters().forEach(letter -> model.addRow(new Object[]{
                letter.getId(),
                letter.getSenderId(),
                letter.getReceiverId(),
                letter.getSubject(),
                letter.getBody(),
                letter.getCreatedAt()
        }));
    }
}
