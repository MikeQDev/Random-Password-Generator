import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

public class MainGUI extends JFrame {
	private JTextField textfieldLength;
	private JTextArea textAreaPassword;
	private JButton buttonCopyPassword;
	private JButton buttonGeneratePassword;
	private JCheckBox checkBoxUppercase;
	private JCheckBox checkBoxLowercase;
	private JCheckBox checkBoxNumeric;
	private JCheckBox checkBoxSpecial;
	private PWGen passGenerator;

	/**
	 * Builds the GUI on creation
	 */

	public MainGUI() {
		this.setTitle("PassGen");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);

		passGenerator = new PWGen();

		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		getContentPane().setLayout(null);

		checkBoxUppercase = new JCheckBox("Uppercase");
		checkBoxUppercase.setBounds(6, 23, 99, 20);
		checkBoxLowercase = new JCheckBox("Lowercase");
		checkBoxLowercase.setBounds(6, 43, 98, 20);
		checkBoxNumeric = new JCheckBox("Numeric");
		checkBoxNumeric.setBounds(6, 63, 87, 20);
		checkBoxSpecial = new JCheckBox("Special chars");
		checkBoxSpecial.setBounds(6, 83, 105, 20);

		JPanel optionsPanel = new JPanel();
		optionsPanel.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), "Settings",
				TitledBorder.LEADING, TitledBorder.TOP, null,
				new Color(0, 0, 0)));
		optionsPanel.setBounds(36, 5, 116, 126);
		optionsPanel.setLayout(null);
		optionsPanel.add(checkBoxUppercase);
		optionsPanel.add(checkBoxLowercase);
		optionsPanel.add(checkBoxNumeric);
		optionsPanel.add(checkBoxSpecial);

		textfieldLength = new JTextField();
		textfieldLength.setBounds(55, 103, 50, 20);
		optionsPanel.add(textfieldLength);
		textfieldLength.setColumns(10);

		JLabel lblLength = new JLabel("Length:");
		lblLength.setBounds(10, 103, 50, 20);
		optionsPanel.add(lblLength);

		getContentPane().add(optionsPanel);

		buttonGeneratePassword = new JButton("Generate Password");
		buttonGeneratePassword.setBounds(10, 137, 160, 23);
		buttonGeneratePassword.addActionListener(new GenerateListener());
		getContentPane().add(buttonGeneratePassword);

		buttonCopyPassword = new JButton("Copy to clipboard");
		buttonCopyPassword.setBounds(10, 292, 160, 23);
		buttonCopyPassword.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				StringSelection password = new StringSelection(textAreaPassword
						.getText());
				Clipboard clipboard = Toolkit.getDefaultToolkit()
						.getSystemClipboard();
				clipboard.setContents(password, null);
			}
		});
		getContentPane().add(buttonCopyPassword);

		textAreaPassword = new JTextArea();
		textAreaPassword.setLineWrap(true);

		JScrollPane scrollPanePassword = new JScrollPane(textAreaPassword);

		scrollPanePassword.setBounds(10, 171, 160, 110);
		scrollPanePassword
				.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPanePassword
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

		getContentPane().add(scrollPanePassword);

		this.setSize(187, 354);
		this.setVisible(true);
	}

	/**
	 * Listens for actions to be performed and then generates a new password
	 * with the given criteria and outputs the password in the text area
	 * 
	 * @author Mike
	 *
	 */
	class GenerateListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			boolean uppercase = checkBoxUppercase.isSelected();
			boolean lowercase = checkBoxLowercase.isSelected();
			boolean numeric = checkBoxNumeric.isSelected();
			boolean special = checkBoxSpecial.isSelected();
			int length;

			try {
				length = Integer.parseInt(textfieldLength.getText().toString());
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(null,
						"Error: please provide a valid password length.");
				return;
			}

			try {
				passGenerator.setFields(length, uppercase, lowercase, numeric,
						special);
			} catch (NotEnoughInfoProvided e1) {
				JOptionPane.showMessageDialog(null,
						"Error: please check at least one password criteria.");
				return;
			}

			passGenerator.generatePassword();
			textAreaPassword.setText(passGenerator.getPassword());
		}
	}

	/**
	 * Main method
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		new MainGUI();
	}
}
