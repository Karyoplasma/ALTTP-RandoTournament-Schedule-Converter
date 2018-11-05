package core;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.JCheckBox;
import javax.swing.JButton;

public class GUIWindow implements ActionListener {
	private final String column_names[]= {"Date","Runners","Modes","Channel", "Commentary", "Tracking"};
	private JFrame frmALinkTo;
	private JComboBox<Mode> comboBox_Mode;
	private JComboBox<String> comboBox_Zone;
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
	private JScrollPane scrollPane;
	private JTable table_1;
	private List<Broadcast> broadcasts;
	private JCheckBox chckbxOnlyShowKnown;
	private JButton btnRefresh;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUIWindow window = new GUIWindow();
					window.frmALinkTo.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUIWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void initialize() {
		frmALinkTo = new JFrame();
		frmALinkTo.setResizable(false);
		frmALinkTo.setTitle("A Link To the Past Randomizer Fall 2018 Tournament Schedule");
		frmALinkTo.setBounds(100, 100, 716, 429);
		frmALinkTo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmALinkTo.getContentPane().setLayout(null);
		
		JLabel lblFilterMode = new JLabel("Filter Mode");
		lblFilterMode.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblFilterMode.setBounds(10, 10, 53, 14);
		frmALinkTo.getContentPane().add(lblFilterMode);
		
		comboBox_Mode = new JComboBox<Mode>();
		comboBox_Mode.setModel(new DefaultComboBoxModel(Mode.values()));
		comboBox_Mode.setSelectedIndex(15);
		comboBox_Mode.setBounds(73, 7, 127, 20);
		comboBox_Mode.addActionListener(this);
		frmALinkTo.getContentPane().add(comboBox_Mode);
		
		JLabel lblOverrideTimezone = new JLabel("Override Timezone");
		lblOverrideTimezone.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblOverrideTimezone.setBounds(432, 10, 90, 14);
		frmALinkTo.getContentPane().add(lblOverrideTimezone);
		
		comboBox_Zone = new JComboBox<String>();
		List<String> ids = new ArrayList<String>();
		ids.addAll(ZoneId.getAvailableZoneIds());
		Collections.sort(ids);
		
		comboBox_Zone.setModel(new DefaultComboBoxModel(ids.toArray()));
		comboBox_Zone.setSelectedItem(ZoneId.systemDefault().toString());
		comboBox_Zone.setBounds(532, 7, 158, 20);
		comboBox_Zone.addActionListener(this);
		frmALinkTo.getContentPane().add(comboBox_Zone);
		
		
		scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(10, 35, 680, 290);
		frmALinkTo.getContentPane().add(scrollPane);
		
		table_1 = new JTable();
		
		
		ScheduleParser p = new ScheduleParser();
		this.broadcasts = p.parseSchedule(false);
		System.out.println(this.broadcasts.size());
		String[][] data = new String[this.broadcasts.size()][6];
		int i = 0;
		for (Broadcast b : this.broadcasts) {
			data[i][0] = b.getDate().format(formatter);
			data[i][1] = b.getPlayers();
			data[i][2] = b.getModesString();
			data[i][3] = b.getChannel();
			data[i][4] = b.getCommentators();
			data[i][5] = b.getTracking();
			i++;
		}
		
		this.initializeTable(data);
		
		
		chckbxOnlyShowKnown = new JCheckBox("only show known restreams");
		chckbxOnlyShowKnown.setFont(new Font("Tahoma", Font.PLAIN, 11));
		chckbxOnlyShowKnown.setBounds(246, 6, 159, 23);
		chckbxOnlyShowKnown.addActionListener(this);
		frmALinkTo.getContentPane().add(chckbxOnlyShowKnown);
		
		btnRefresh = new JButton("Refresh");
		btnRefresh.setBounds(254, 336, 164, 44);
		btnRefresh.addActionListener(this);
		frmALinkTo.getContentPane().add(btnRefresh);
	}

	private void initializeTable(String[][] data) {
		table_1.setModel(new DefaultTableModel(data, column_names));
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		table_1.getColumnModel().getColumn(0).setPreferredWidth(100);
		table_1.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
		table_1.getColumnModel().getColumn(1).setPreferredWidth(200);
		table_1.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
		table_1.getColumnModel().getColumn(2).setPreferredWidth(400);
		table_1.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
		table_1.getColumnModel().getColumn(3).setPreferredWidth(150);
		table_1.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
		table_1.getColumnModel().getColumn(4).setPreferredWidth(200);
		table_1.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
		table_1.getColumnModel().getColumn(5).setPreferredWidth(100);
		table_1.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);
		table_1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		scrollPane.setViewportView(table_1);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.comboBox_Mode) {
			this.filterMode(this.chckbxOnlyShowKnown.isSelected());
		}
		if (e.getSource() == this.chckbxOnlyShowKnown) {
			this.filterMode(this.chckbxOnlyShowKnown.isSelected());
		}
		if (e.getSource() == this.comboBox_Zone) {
			this.updateTime(ZoneId.of(this.comboBox_Zone.getSelectedItem().toString()));
		}
		if (e.getSource() == this.btnRefresh) {
			ScheduleParser p = new ScheduleParser();
			this.broadcasts = p.parseSchedule(false);
			this.updateTime(ZoneId.of(this.comboBox_Zone.getSelectedItem().toString()));
		}
		
	}


	private void updateTime(ZoneId override) {
		for (Broadcast b : this.broadcasts) {
			b.setLocalZone(override);
		}
		this.filterMode(this.chckbxOnlyShowKnown.isSelected());
	}

	private void filterMode(boolean knownOnly) {
		List<Broadcast> filtered = new ArrayList<Broadcast>();
		Mode modeFilter = (Mode) this.comboBox_Mode.getSelectedItem();
		if (knownOnly) {
			for (Broadcast b : this.broadcasts) {
				if (b.knownRestream() && (modeFilter.equals(Mode.AllOverride) || b.getModes().contains(modeFilter))) {
					filtered.add(b);
				}
			}
		} else {
			for (Broadcast b : this.broadcasts) {
				if (b.getModes().contains(modeFilter) || modeFilter.equals(Mode.AllOverride)){
					filtered.add(b);
				}
			}
		}
		int i = 0;
		String[][] data = new String[filtered.size()][6];
		for (Broadcast b : filtered) {
			data[i][0] = b.getDate().format(formatter);
			data[i][1] = b.getPlayers();
			data[i][2] = b.getModesString();
			data[i][3] = b.getChannel();
			data[i][4] = b.getCommentators();
			data[i][5] = b.getTracking();
			i++;
		}
		this.initializeTable(data);
	}
}
