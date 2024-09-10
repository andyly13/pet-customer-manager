package labs.lab9;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class CustomerManager extends JFrame{
	
	private static Vector<Customer> customers;
	
	private static final int FRAME_WIDTH = 1400;
	private static final int FRAME_HEIGHT = 600;
	private static final int AREA_ROWS = 5;
	private static final int AREA_COLUMNS = 20;
	final int FIELD_WIDTH = 15;
	private String[] CITIES = {"Irvine", "Los Angeles", "Paris", "Shanghai", "New York", "London"};
	
	private JList<Customer> jList;
	private JScrollPane scrollPane;
	private JButton deleteButton;
	private JPanel leftPanel;
	
	private JLabel nameLabel;
	private JTextField nameField; 
	private JPanel namePanel;
	
	private JPanel rightPanel;
	
	private JTextField emailField;
	private JLabel emailLabel;
	private JPanel emailPanel;
	
	private JButton saveCustomerButton;
	private JButton newCustomerButton;
	private JPanel newAndSave;
	
	private JCheckBox dogs;
	private JCheckBox cats;
	private JCheckBox birds;
	private JCheckBox fish;
	private JCheckBox other;
	private JPanel petPanel;
	
	private JLabel amountSpentLabel;
	private JTextField totalAmountSpent;
	private JPanel amountSpentPanel;
	
	private JComboBox<String> dropdown;
	private JPanel citiesPanel;
	
	
	private JTextArea notes;
	private JPanel notesPanel;
	
	
	private JSplitPane splitPane;
	
	
	
	public CustomerManager() {
		customers = new Vector<>();
		
		createCustomerListAndScrollPane();
		createDeleteButton();
		createLeftPanel();
		
		createNameField();
		createEmailField();
		createPetCheckBoxes();
		createSaveAndNewCustomerButton();
		createHomeStoreLocation();
		createTotalAmountSpent();
		createNotes();
		createRightPanel();
		
		createSplitPane();
		
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
		
	}
	
	// left pane
	
	public int findCityIndex(String city) {
		for (int i = 0; i < CITIES.length; i++) {
			if (city.equals(CITIES[i])) {return i;}
		}
		return -1;
	}
	
	private void createCustomerListAndScrollPane() {
		jList = new JList<>(customers);
		scrollPane = new JScrollPane(jList);
		scrollPane.setPreferredSize(scrollPane.getPreferredSize());
		
		jList.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) { 
                    if (jList.getSelectedIndex() != -1) {
                        Customer c = jList.getSelectedValue();
                        // Perform action based on the selected item
                        nameField.setText(c.getName());
            			emailField.setText(c.getEmail());
            			
            			setPets(c, c.getPets());
            			dogs.setSelected(c.getPets()[0]);
            			cats.setSelected(c.getPets()[1]);
            			birds.setSelected(c.getPets()[2]);
            			fish.setSelected(c.getPets()[3]);
            			other.setSelected(c.getPets()[4]);
            			
            			totalAmountSpent.setText(c.getTotalAmountSpent());
            			
            			dropdown.setSelectedIndex(findCityIndex(c.getHomeStoreLocation()));
            			
            			notes.setText(c.getNotes());
                    }
                }
            }
        });
	}
	
	class DeleteListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			Customer selectedCustomer = jList.getSelectedValue();
			
			if (selectedCustomer != null) {
				customers.remove(selectedCustomer);
				jList.setListData(customers);
			}
		}
	}
	
	private void createDeleteButton() {
		deleteButton =  new JButton("Delete");
		deleteButton.setPreferredSize(deleteButton.getPreferredSize());
		ActionListener listener = new DeleteListener();
		deleteButton.addActionListener(listener);
		
	}
	
	private void createLeftPanel() {
		leftPanel = new JPanel();
		leftPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 0));
//		leftPanel.setLayout(new BorderLayout());
//		leftPanel.add(scrollPane, BorderLayout.CENTER);
//		leftPanel.add(deleteButton, BorderLayout.SOUTH);
		
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
		leftPanel.add(scrollPane);
		deleteButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		leftPanel.add(deleteButton);
		
	}
	
	// right pane
	
	private void createNameField() {
		nameLabel = new JLabel("Name: ");
		nameField = new JTextField(FIELD_WIDTH);	
		nameField.setText("");
		
		namePanel = new JPanel();
		namePanel.add(nameLabel);
		namePanel.add(nameField);
	}
	
	private void createEmailField() {
		emailLabel = new JLabel("Email: ");
		emailField = new JTextField(FIELD_WIDTH);	
		emailField.setText("");
		
		emailPanel = new JPanel();
		emailPanel.add(emailLabel);
		emailPanel.add(emailField);
	}
	
	class SaveCustomerListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			String name = nameField.getText();
			String email = emailField.getText();
			
			
			if (name.isEmpty() || email.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Invalid Input!", "Error", JOptionPane.INFORMATION_MESSAGE);
			}
			else if (notRepeat(new Customer(name, email)) && jList.getSelectedIndex() == -1) {
				Customer c = new Customer(name, email);
				customers.add(c);
				
				Boolean[] petsClicked = {dogs.isSelected(), cats.isSelected(), birds.isSelected(), fish.isSelected(), other.isSelected()};
				setPets(c, petsClicked);
				dogs.setSelected(c.getPets()[0]);
    			cats.setSelected(c.getPets()[1]);
    			birds.setSelected(c.getPets()[2]);
    			fish.setSelected(c.getPets()[3]);
    			other.setSelected(c.getPets()[4]);
				try {
					c.setTotalAmountSpent(Double.parseDouble(totalAmountSpent.getText()));
				}
				catch(NumberFormatException exception ){
					JOptionPane.showMessageDialog(null, "Invalid Input!", "Error", JOptionPane.INFORMATION_MESSAGE);

				}
				
				c.setHomeStoreLocation((String)dropdown.getSelectedItem());
				
				c.setNotes(notes.getText());
				
				Collections.sort(customers); // make alphabetical 
				jList.setListData(customers);
				JOptionPane.showMessageDialog(null, "Customer Saved!", "Success", JOptionPane.INFORMATION_MESSAGE);
			}
			else if (jList.getSelectedIndex() != -1) {
				Customer c = jList.getSelectedValue();
				
				if (!nameField.getText().equals(c.getName()) && nameInList(nameField.getText())) {
					JOptionPane.showMessageDialog(null, "Invalid Input!", "Error", JOptionPane.INFORMATION_MESSAGE);

				}
				else {
					c.setName(nameField.getText());
					
					c.setEmail(emailField.getText());
					
					Boolean[] petsClicked = {dogs.isSelected(), cats.isSelected(), birds.isSelected(), fish.isSelected(), other.isSelected()};
					setPets(c, petsClicked);
					dogs.setSelected(c.getPets()[0]);
        			cats.setSelected(c.getPets()[1]);
        			birds.setSelected(c.getPets()[2]);
        			fish.setSelected(c.getPets()[3]);
        			other.setSelected(c.getPets()[4]);
					
        			try {
    					c.setTotalAmountSpent(Double.parseDouble(totalAmountSpent.getText()));
    				}
    				catch(NumberFormatException exception ){
    					JOptionPane.showMessageDialog(null, "Invalid Input!", "Error", JOptionPane.INFORMATION_MESSAGE);
    				}					
					c.setHomeStoreLocation((String)dropdown.getSelectedItem());
					
					c.setNotes(notes.getText());
					
					Collections.sort(customers); // make alphabetical 
					jList.setListData(customers);
					JOptionPane.showMessageDialog(null, "Customer Saved!", "Success", JOptionPane.INFORMATION_MESSAGE);
				}
			}
			
		}
			
			
	}
	
	private Boolean nameInList(String name) {
		for (Customer c: customers) {
			if (c.getName().equals(name)) {return true;}
		}
		return false;
	}
		
	
	
	private void setPets(Customer c, Boolean[] petsClicked) {
		for (int i = 0; i < 5; i++) {
			c.getPets()[i] = petsClicked[i];
		}
	}
	
	class NewCustomerListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			nameField.setText("");
			emailField.setText("");
			
			dogs.setSelected(false);
			cats.setSelected(false);
			birds.setSelected(false);
			fish.setSelected(false);
			other.setSelected(false);
			
			totalAmountSpent.setText("0.0");
			
			dropdown.setSelectedIndex(0);
			
			notes.setText("");
			
		}
		
	}
	
	private void createSaveAndNewCustomerButton() {
		saveCustomerButton = new JButton("Save Customer");
		
		ActionListener listener = new SaveCustomerListener();
		saveCustomerButton.addActionListener(listener);
		
		newCustomerButton = new JButton("New Customer");
		ActionListener listener2 = new NewCustomerListener();
		newCustomerButton.addActionListener(listener2);
		
		newAndSave = new JPanel();
		newAndSave.add(saveCustomerButton);
		newAndSave.add(newCustomerButton);
	}
	
	
	private void createPetCheckBoxes() {
		dogs = new JCheckBox("Dog(s)");
		cats = new JCheckBox("Cat(s)");
		birds = new JCheckBox("Bird(s)");
		fish = new JCheckBox("Fish");
		other = new JCheckBox("Other");
		
		petPanel = new JPanel();
		petPanel.add(new JLabel("Pets: "));
		petPanel.add(dogs);
		petPanel.add(cats);
		petPanel.add(birds);
		petPanel.add(fish);
		petPanel.add(other);
		
		
	}
	
	private void createTotalAmountSpent() {
		amountSpentLabel = new JLabel("Total Amount Spent: ");
		totalAmountSpent = new JTextField(FIELD_WIDTH);
		totalAmountSpent.setText("0.0");
		
		amountSpentPanel = new JPanel();
		amountSpentPanel.add(amountSpentLabel);
		amountSpentPanel.add(totalAmountSpent);
	
	}
	
	private void createHomeStoreLocation() {
		dropdown = new JComboBox<>(CITIES);
		JLabel citiesLabel = new JLabel("Home Store Location: ");
		dropdown.setSelectedIndex(0);
		citiesPanel = new JPanel();
		citiesPanel.add(citiesLabel);
		citiesPanel.add(dropdown);
		

	}
	
	private void createNotes() {
		notesPanel = new JPanel();
		JLabel notesLabel = new JLabel("Notes: ");
		notes = new JTextArea(AREA_ROWS, AREA_COLUMNS);
		
		notesPanel.add(notesLabel);
		notesPanel.add(notes);
	}
	
	private void createRightPanel() {
		rightPanel = new JPanel();
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
		
		rightPanel.add(namePanel);
		rightPanel.add(emailPanel);
		rightPanel.add(petPanel);
		rightPanel.add(amountSpentPanel);
		rightPanel.add(citiesPanel);
		rightPanel.add(notesPanel);
		rightPanel.add(newAndSave);
		
		
	}
	
	private void createSplitPane() {
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
		splitPane.setResizeWeight(0.5);
		splitPane.setDividerSize(1);
		add(splitPane);
	}
	
	private boolean notRepeat(Customer c) {
		for (Customer cus: customers) {
			if (c.compareTo(cus) == 0) {return false;}
		}
		return true;
	}

	
	public static void main (String[] args) {
		JFrame frame = new CustomerManager();
		
		frame.setTitle("Andrew Ly - 52121050");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
	}
	
}