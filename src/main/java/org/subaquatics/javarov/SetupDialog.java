package org.subaquatics.javarov;

import java.awt.EventQueue;
import javax.swing.*;
import java.awt.BorderLayout;
import java.util.ArrayList;
import net.miginfocom.swing.MigLayout;
import org.subaquatics.javarov.actions.RobotActions;
import org.subaquatics.javarov.actions.ControllerActions;

public class SetupDialog extends JFrame implements Runnable {

	// Graphical stuff
	private JLabel robotLabel;
	private JComboBox robotComboBox;
	private JButton robotRefreshButton;

	private JLabel controllerLabel;
	private JComboBox controllerComboBox;
	private JButton controllerRefreshButton;

	private JButton startButton;
	
	public SetupDialog() {
		JPanel panel = new JPanel(new MigLayout("wrap 3, fill"));

		// COM ports
		robotLabel = new JLabel("Robot: ");
		robotComboBox = new JComboBox(new String[]{});
		robotRefreshButton = new JButton("Refresh");
		robotRefreshButton.addActionListener((e) -> {
			RobotActions.getSerialPorts((ports) -> {
				robotComboBox.setModel(new DefaultComboBoxModel(ports));
			});
		});

		// Controller ports
		controllerLabel = new JLabel("Controller: ");
		controllerComboBox = new JComboBox(new String[]{});
		controllerRefreshButton = new JButton("Refresh");
		controllerRefreshButton.addActionListener((e) -> {
			ControllerActions.getControllers((controllers) -> {
				controllerComboBox.setModel(new DefaultComboBoxModel(controllers));
			});
		});

		startButton = new JButton("Start");
		startButton.addActionListener((e) -> {
			this.setVisible(false);
			(new Thread(new RunningGUI())).start();
		});

		// Add stuff to the layout
		panel.add(robotLabel);
		panel.add(robotComboBox, "growx");
		panel.add(robotRefreshButton);

		panel.add(controllerLabel);
		panel.add(controllerComboBox, "growx");
		panel.add(controllerRefreshButton);

		panel.add(startButton, "span 3");
		
		add(panel, BorderLayout.CENTER);
	}

	private void initUI() {
		setTitle("GUI");
		setSize(500, 400);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public void run() {
		initUI();

		this.setVisible(true);
	}
}
