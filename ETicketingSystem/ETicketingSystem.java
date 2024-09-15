import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ETicketingSystem extends JFrame implements ActionListener {

    private JLabel nameLabel, ageLabel, destinationLabel, ticketLabel;
    private JTextField nameField, ageField;
    private JComboBox<String> destinationBox;
    private JButton bookButton, clearButton;
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

        nameField = new JTextField(20);
        ageField = new JTextField(3);

        String[] destinations = {"Chennai", "Mumbai", "Kolkata", "Kashmir", "Delhi"};
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
            }
        } else if (e.getSource() == clearButton) {
            nameField.setText("");
            ageField.setText("");
            destinationBox.setSelectedIndex(0);
            ticketArea.setText("");
        }
    }

    public static void main(String[] args) {
        new ETicketingSystem();
    }
}