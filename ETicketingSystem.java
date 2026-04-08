
// MongoDB Imports
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

// PDF Imports
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ETicketingSystem extends JFrame implements ActionListener {

    private JLabel nameLabel, ageLabel, destinationLabel, ticketLabel;
    private JTextField nameField, ageField;
    private JComboBox<String> destinationBox;
    private JButton bookButton, clearButton, pdfButton;
    private JTextArea ticketArea;

    public ETicketingSystem() {
        // Setting up the frame
        setTitle("E-Ticketing System");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        // Initializing components
        nameLabel = new JLabel("Name:");
        ageLabel = new JLabel("Age:");
        destinationLabel = new JLabel("Destination:");
        ticketLabel = new JLabel("Your Ticket:");
        pdfButton = new JButton("Save as PDF");
        pdfButton.addActionListener(this);

        nameField = new JTextField(20);
        ageField = new JTextField(3);

        String[] destinations = { "Chennai", "Mumbai", "Kolkata", "Kashmir", "Delhi" };
        destinationBox = new JComboBox<>(destinations);

        bookButton = new JButton("Book Ticket");
        clearButton = new JButton("Clear");

        ticketArea = new JTextArea(5, 30);
        ticketArea.setEditable(false);

        // Adding action listeners to buttons
        bookButton.addActionListener(this);
        clearButton.addActionListener(this);

        // Adding components to the frame
        add(nameLabel);
        add(nameField);
        add(ageLabel);
        add(ageField);
        add(destinationLabel);
        add(destinationBox);
        add(bookButton);
        add(clearButton);
        add(ticketLabel);
        add(ticketArea);
        add(pdfButton);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == bookButton) {
            String name = nameField.getText();
            String age = ageField.getText();
            String destination = (String) destinationBox.getSelectedItem();

            if (name.isEmpty() || age.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill out all fields", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                ticketArea.setText("Ticket Booked!\nName: " + name + "\nAge: " + age + "\nDestination: " + destination);

                // CONNECT TO CUSTOM PORT 27020
                try {
                    MongoClient mongoClient = MongoClients.create("mongodb://localhost:27020");
                    MongoDatabase database = mongoClient.getDatabase("TicketDB");
                    MongoCollection<Document> collection = database.getCollection("tickets");

                    Document doc = new Document("name", name)
                            .append("age", age)
                            .append("destination", destination)
                            .append("status", "Confirmed");
                    collection.insertOne(doc);

                    System.out.println("Saved successfully to MongoDB on port 27020!");
                    mongoClient.close();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "MongoDB Error: " + ex.getMessage());
                }
            }
        } else if (e.getSource() == clearButton) {
            nameField.setText("");
            ageField.setText("");
            destinationBox.setSelectedIndex(0);
            ticketArea.setText("");
        } else if (e.getSource() == pdfButton) {
            if (ticketArea.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "No ticket to save! Book first.");
                return;
            }

            try {
                String fileName = "Ticket_" + nameField.getText() + ".pdf";
                com.itextpdf.text.Document document = new com.itextpdf.text.Document();
                PdfWriter.getInstance(document, new FileOutputStream(fileName));

                document.open();
                document.add(new Paragraph("--- E-TICKET CONFIRMATION ---\n\n"));
                document.add(new Paragraph(ticketArea.getText()));
                document.close();

                JOptionPane.showMessageDialog(this, "PDF Saved! Check your project folder.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "PDF Error: " + ex.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        // new ETicketingSystem();
        new LoginScreen();
    }
}

class LoginScreen extends JFrame implements ActionListener {
    JTextField userText;
    JPasswordField passText;
    JButton loginButton;

    public LoginScreen() {
        setTitle("Login");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        add(new JLabel("Username:"));
        userText = new JTextField(15);
        add(userText);

        add(new JLabel("Password:"));
        passText = new JPasswordField(15);
        add(passText);

        loginButton = new JButton("Login");
        loginButton.addActionListener(this);
        add(loginButton);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (userText.getText().equals("admin") && new String(passText.getPassword()).equals("password")) {
            JOptionPane.showMessageDialog(this, "Login Successful");
            this.dispose();
            new ETicketingSystem();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid credentials", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
