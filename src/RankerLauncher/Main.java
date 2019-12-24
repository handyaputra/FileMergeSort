package RankerLauncher;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.util.Scanner;
import java.awt.event.ActionEvent;

import Ranker.Main.*;

public class Main extends JFrame {

	private JPanel contentPane;
	private JTextField textFile;
	
	JFileChooser fc = new JFileChooser();
	String file = "";
	Integer status = 0;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
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
	public Main() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 244);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblProgramRanking = new JLabel("Program Ranking");
		lblProgramRanking.setFont(new Font("Corbel", Font.BOLD | Font.ITALIC, 18));
		lblProgramRanking.setBounds(145, 11, 163, 23);
		contentPane.add(lblProgramRanking);
		
		JLabel lblDenganMergeSort = new JLabel("dengan Merge Sort");
		lblDenganMergeSort.setBounds(155, 35, 123, 14);
		contentPane.add(lblDenganMergeSort);
		
		textFile = new JTextField();
		textFile.setBounds(67, 72, 302, 20);
		contentPane.add(textFile);
		textFile.setColumns(10);
		textFile.setText("Pilih lokasi file..");
		
		JButton btnBrowse = new JButton("Browse..");
		btnBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Main main = new Main();
				try {
					FileNameExtensionFilter filter = new FileNameExtensionFilter("TXT file", "txt");
					fc.setFileFilter(filter);
					int response = fc.showOpenDialog(Main.this);
					if (response == JFileChooser.APPROVE_OPTION) {
						textFile.setText(fc.getSelectedFile().toString());
						file = fc.getSelectedFile().toString();
					} else {
						textFile.setText("Pilih lokasi file..");
						file = "null";
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		btnBrowse.setBounds(280, 103, 89, 23);
		contentPane.add(btnBrowse);
		
		JLabel lblUrutkanBerdasarkan = new JLabel("Urutkan berdasarkan :");
		lblUrutkanBerdasarkan.setBounds(67, 112, 130, 14);
		contentPane.add(lblUrutkanBerdasarkan);
		
		JComboBox cmbSort = new JComboBox();
		cmbSort.setModel(new DefaultComboBoxModel(new String[] {"Nilai Terkecil", "Nilai Terbesar"}));
		cmbSort.setBounds(67, 131, 172, 20);
		contentPane.add(cmbSort);
		
		JButton btnRank = new JButton("RANK!");
		btnRank.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (cmbSort.getSelectedItem() == "Nilai Terkecil") status = 0;
				else if (cmbSort.getSelectedItem() == "Nilai Terbesar") status = 1;
				Ranker.Main launch = new Ranker.Main();
				launch.create(file,status);
				JOptionPane.showMessageDialog(null, "Data sedang diolah :))");
			}
		});
		btnRank.setBounds(280, 156, 89, 23);
		contentPane.add(btnRank);
	}
}
