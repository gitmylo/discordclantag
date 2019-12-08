package com.mylo.betterrichpresense;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.mylo.betterrichpresense.animatedtext.animatedtext;
import com.mylo.betterrichpresense.animatedtext.animtype;

import net.arikia.dev.drpc.DiscordEventHandlers;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class gui extends JFrame {

	private JPanel contentPane;
	
	static String statustext = "custom status";
	static int scrollspeed = 3;
	static animtype type = animtype.SCROLL;
	private JTextField txtCustomStatus;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					richpresense();
					gui frame = new gui();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public gui() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 180);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		txtCustomStatus = new JTextField();
		txtCustomStatus.setText("custom status");
		txtCustomStatus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				statustext = txtCustomStatus.getText();
			}
		});
		txtCustomStatus.setBounds(10, 30, 424, 20);
		contentPane.add(txtCustomStatus);
		txtCustomStatus.setColumns(10);
		
		JLabel lblEnterToSet = new JLabel("enter to set status");
		lblEnterToSet.setBounds(10, 11, 424, 14);
		contentPane.add(lblEnterToSet);
		
		JSpinner spinner = new JSpinner();
		spinner.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				scrollspeed = (int)spinner.getValue();
			}
		});
		spinner.setModel(new SpinnerNumberModel(3, 1, 20, 1));
		spinner.setBounds(10, 86, 424, 20);
		contentPane.add(spinner);
		
		JLabel lblSpeed = new JLabel("speed");
		lblSpeed.setBounds(10, 61, 424, 14);
		contentPane.add(lblSpeed);
		
		JComboBox comboBox = new JComboBox();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				type = animtype.valueOf(comboBox.getSelectedItem().toString());
			}
		});
		comboBox.setModel(new DefaultComboBoxModel(animtype.values()));
		comboBox.setBounds(10, 117, 424, 20);
		contentPane.add(comboBox);
	}
	
	public static void richpresense() {
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			DiscordRPC.discordShutdown();
		} ));
		DiscordEventHandlers handlers = new DiscordEventHandlers.Builder().setReadyEventHandler((user) -> {
            DiscordRichPresence.Builder presence = new DiscordRichPresence.Builder("");
            presence.setDetails("waiting");
            DiscordRPC.discordUpdatePresence(presence.build());
        }).build();
		  DiscordRPC.discordInitialize("653231096816730141", handlers, false);
		  DiscordRPC.discordRegister("653231096816730141", "");
		new Thread(() ->{
			while (true) {
				DiscordRPC.discordRunCallbacks();
                DiscordRichPresence.Builder presence = new DiscordRichPresence.Builder("");
                String presensetext = animatedtext.animatetext(statustext, type, scrollspeed);
                presence.setDetails(presensetext);
                //System.out.println(presensetext);
                DiscordRPC.discordUpdatePresence(presence.build());
                try {
                    Thread.sleep(200);
                } catch (InterruptedException ignored) {}
            }
		}).start();
	}
}
