/*
 * Course Generator
 * Copyright (C) 2016 Pierre Delore
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package course_generator.dialogs;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.util.ResourceBundle;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.KeyStroke;

import course_generator.TrackData;
import course_generator.settings.CgSettings;
import course_generator.utils.CgConst;
import course_generator.utils.Utils;

public class FrmExportWaypoints extends javax.swing.JDialog {

	private ResourceBundle bundle;
	private boolean ok;
	private int tag;

	private JPanel jPanelButtons;
	private JButton btCancel;
	private JButton btOk;
	private JPanel panelTags;
	private JCheckBox chkHighPt;
	private JCheckBox chkLowPt;
	private JLabel lbHighPt;
	private JLabel lbLowPt;
	private JCheckBox chkEat;
	private JLabel lbEat;
	private JCheckBox chkDrink;
	private JLabel lbDrink;
	private JCheckBox chkPhoto;
	private JLabel lbPhoto;
	private JCheckBox chkNote;
	private JLabel lbNote;
	private JCheckBox chkInfo;
	private JLabel lbInfo;
	private JCheckBox chkRoadbook;
	private JLabel lbRoadbook;
	private JCheckBox chkMark;
	private JLabel lbMark;

	/**
	 * Creates new form frmSettings
	 */
	public FrmExportWaypoints() {
		bundle = java.util.ResourceBundle.getBundle("course_generator/Bundle");
		initComponents();
		setModal(true);
	}

	/**
	 * Show the dialog
	 * @return 
	 * 	true if ok was pressed
	 */
	public boolean showDialog() {
		ok = false;
		tag=0;

		// -- Show the dialog
		setVisible(true);

		if (ok) {
			tag=0;
            //Higher point
			if (chkHighPt.isSelected())
				tag=tag | CgConst.TAG_HIGH_PT;

			//Lower point
			if (chkLowPt.isSelected())
				tag=tag | CgConst.TAG_LOW_PT;

            //Station
			if (chkEat.isSelected())
            	tag=tag | CgConst.TAG_EAT_PT;

            //Drink
			if (chkDrink.isSelected())
				tag=tag | CgConst.TAG_WATER_PT;
            
            //Mark
			if (chkMark.isSelected())
				tag=tag | CgConst.TAG_MARK;
            
            //Roadbook
			if (chkRoadbook.isSelected())
				tag=tag | CgConst.TAG_ROADBOOK;
            
            //Photo
			if (chkPhoto.isSelected())
				tag=tag | CgConst.TAG_COOL_PT;
            
            //Note
			if (chkNote.isSelected())
				tag=tag | CgConst.TAG_NOTE;

            //Info
			if (chkInfo.isSelected())
				tag=tag | CgConst.TAG_INFO;

		}
		return ok;
	}

	/**
	 * Manage low level key strokes ESCAPE : Close the window
	 *
	 * @return
	 */
	protected JRootPane createRootPane() {
		JRootPane rootPane = new JRootPane();
		KeyStroke strokeEscape = KeyStroke.getKeyStroke("ESCAPE");
		KeyStroke strokeEnter = KeyStroke.getKeyStroke("ENTER");

		Action actionListener = new AbstractAction() {
			public void actionPerformed(ActionEvent actionEvent) {
				setVisible(false);
			}
		};

		Action actionListenerEnter = new AbstractAction() {
			public void actionPerformed(ActionEvent actionEvent) {
				RequestToClose();
			}
		};

		InputMap inputMap = rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		inputMap.put(strokeEscape, "ESCAPE");
		rootPane.getActionMap().put("ESCAPE", actionListener);

		inputMap.put(strokeEnter, "ENTER");
		rootPane.getActionMap().put("ENTER", actionListenerEnter);

		return rootPane;
	}

	private void RequestToClose() {
		boolean param_valid = true;
		// check that the parameters are ok

		// -- Ok?
		if (param_valid) {
			ok = true;
			setVisible(false);
		}
	}

	private void initComponents() {
		int line = 0;

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setTitle(bundle.getString("FrmExportWaypoints.title"));
		setAlwaysOnTop(true);
		setMinimumSize(new Dimension(350, 200));
		setResizable(false);
		setType(java.awt.Window.Type.UTILITY);

		// -- Layout
		// ------------------------------------------------------------
		Container paneGlobal = getContentPane();
		paneGlobal.setLayout(new GridBagLayout());

		// == Panel start
		panelTags = new JPanel();
		panelTags.setLayout(new GridBagLayout());
		panelTags.setBorder(BorderFactory.createTitledBorder(bundle.getString("FrmExportWaypoints.panelTags.Title")));
		panelTags.setLayout(new GridBagLayout());
		Utils.addComponent(paneGlobal, panelTags, 0, 0, 1, 1, 1, 1, 10, 10, 0, 10, GridBagConstraints.BASELINE_LEADING,
				GridBagConstraints.BOTH);

		// High point
		chkHighPt = new JCheckBox();
		Utils.addComponent(panelTags, chkHighPt, 0, 0, 1, 1, 0, 0, 5, 5, 0, 0, GridBagConstraints.WEST,
				GridBagConstraints.HORIZONTAL);
		lbHighPt = new JLabel(bundle.getString("FrmExportWaypoints.chkHighPt.Text"),
				new javax.swing.ImageIcon(getClass().getResource("/course_generator/images/high_point.png")),
				JLabel.LEFT);
		Utils.addComponent(panelTags, lbHighPt, 1, 0, 1, 1, 1, 0, 5, 5, 0, 0, GridBagConstraints.WEST,
				GridBagConstraints.HORIZONTAL);

		// Low point
		chkLowPt = new JCheckBox();
		Utils.addComponent(panelTags, chkLowPt, 0, 1, 1, 1, 0, 0, 0, 5, 0, 0, GridBagConstraints.WEST,
				GridBagConstraints.HORIZONTAL);
		lbLowPt = new JLabel(bundle.getString("FrmExportWaypoints.chkLowPt.Text"),
				new javax.swing.ImageIcon(getClass().getResource("/course_generator/images/low_point.png")),
				JLabel.LEFT);
		Utils.addComponent(panelTags, lbLowPt, 1, 1, 1, 1, 1, 0, 0, 5, 0, 0, GridBagConstraints.WEST,
				GridBagConstraints.HORIZONTAL);

		// -- eat station
		chkEat = new JCheckBox();
		Utils.addComponent(panelTags, chkEat, 0, 2, 1, 1, 0, 0, 0, 5, 0, 0, GridBagConstraints.WEST,
				GridBagConstraints.HORIZONTAL);
		lbEat = new javax.swing.JLabel(bundle.getString("FrmExportWaypoints.lbEat.Text"),
				new javax.swing.ImageIcon(getClass().getResource("/course_generator/images/eat.png")), JLabel.LEFT);
		Utils.addComponent(panelTags, lbEat, 1, 2, 1, 1, 1, 0, 0, 5, 0, 0, GridBagConstraints.WEST,
				GridBagConstraints.HORIZONTAL);

		// -- drink station
		chkDrink = new JCheckBox();
		Utils.addComponent(panelTags, chkDrink, 0, 3, 1, 1, 0, 0, 0, 5, 0, 0, GridBagConstraints.WEST,
				GridBagConstraints.HORIZONTAL);
		lbDrink = new javax.swing.JLabel(bundle.getString("FrmExportWaypoints.lbDrink.Text"),
				new javax.swing.ImageIcon(getClass().getResource("/course_generator/images/drink.png")), JLabel.LEFT);
		Utils.addComponent(panelTags, lbDrink, 1, 3, 1, 1, 1, 0, 0, 5, 0, 0, GridBagConstraints.WEST,
				GridBagConstraints.HORIZONTAL);

		
		// -- mark
		chkMark = new JCheckBox();
		Utils.addComponent(panelTags, chkMark, 0, 4, 1, 1, 0, 0, 0, 5, 0, 0, GridBagConstraints.WEST,
				GridBagConstraints.HORIZONTAL);
		lbMark = new javax.swing.JLabel(bundle.getString("FrmExportWaypoints.lbMark.Text"),
				new javax.swing.ImageIcon(getClass().getResource("/course_generator/images/flag.png")), JLabel.LEFT);
		Utils.addComponent(panelTags, lbMark, 1, 4, 1, 1, 1, 0, 0, 5, 0, 0, GridBagConstraints.WEST,
				GridBagConstraints.HORIZONTAL);

		
		// -- Tag : Place to see
		chkPhoto = new JCheckBox();
		Utils.addComponent(panelTags, chkPhoto, 0, 5, 1, 1, 0, 0, 0, 5, 0, 0, GridBagConstraints.WEST,
				GridBagConstraints.HORIZONTAL);
		lbPhoto = new javax.swing.JLabel(bundle.getString("FrmExportWaypoints.lbPhoto.Text"),
				new javax.swing.ImageIcon(getClass().getResource("/course_generator/images/photo.png")), JLabel.LEFT);
		Utils.addComponent(panelTags, lbPhoto, 1, 5, 1, 1, 1, 0, 0, 5, 0, 0, GridBagConstraints.WEST,
				GridBagConstraints.HORIZONTAL);

		// -- Tag : note
		chkNote = new JCheckBox();
		Utils.addComponent(panelTags, chkNote, 0, 6, 1, 1, 0, 0, 0, 5, 0, 0, GridBagConstraints.WEST,
				GridBagConstraints.HORIZONTAL);
		lbNote = new javax.swing.JLabel(bundle.getString("FrmExportWaypoints.lbNote.Text"), 
				new javax.swing.ImageIcon(getClass().getResource("/course_generator/images/note.png")), JLabel.LEFT);
		Utils.addComponent(panelTags, lbNote, 1, 6, 1, 1, 1, 0, 0, 5, 0, 0, GridBagConstraints.WEST,
				GridBagConstraints.HORIZONTAL);

		// -- Tag : info
		chkInfo = new JCheckBox();
		Utils.addComponent(panelTags, chkInfo, 0, 7, 1, 1, 0, 0, 0, 5, 0, 0, GridBagConstraints.WEST,
				GridBagConstraints.HORIZONTAL);
		lbInfo = new javax.swing.JLabel(bundle.getString("FrmExportWaypoints.lbInfo.Text"), 
				new javax.swing.ImageIcon(getClass().getResource("/course_generator/images/info.png")), JLabel.LEFT);
		Utils.addComponent(panelTags, lbInfo, 1, 7, 1, 1, 1, 0, 0, 5, 0, 0, GridBagConstraints.WEST,
				GridBagConstraints.HORIZONTAL);

		// -- Tag : Roadbook
		chkRoadbook = new JCheckBox();
		Utils.addComponent(panelTags, chkRoadbook, 0, 8, 1, 1, 0, 0, 0, 5, 5, 0, GridBagConstraints.WEST,
				GridBagConstraints.HORIZONTAL);
		lbRoadbook = new javax.swing.JLabel(bundle.getString("FrmExportWaypoints.lbRoadbook.Text"),
				new javax.swing.ImageIcon(getClass().getResource("/course_generator/images/roadbook.png")),
				JLabel.LEFT);
		Utils.addComponent(panelTags, lbRoadbook, 1, 8, 1, 1, 1, 0, 0, 5, 5, 0, GridBagConstraints.WEST,
				GridBagConstraints.HORIZONTAL);

		// == BUTTONS
		// ===========================================================
		jPanelButtons = new javax.swing.JPanel();
		jPanelButtons.setLayout(new FlowLayout());
		Utils.addComponent(paneGlobal, jPanelButtons, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.HORIZONTAL);

		btCancel = new javax.swing.JButton();
		btCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/course_generator/images/cancel.png")));
		btCancel.setText(bundle.getString("Global.btCancel.text"));
		btCancel.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				setVisible(false);
			}
		});

		btOk = new javax.swing.JButton();
		btOk.setIcon(new javax.swing.ImageIcon(getClass().getResource("/course_generator/images/valid.png")));
		btOk.setText(bundle.getString("Global.btOk.text"));
		btOk.setMinimumSize(btCancel.getMinimumSize());
		btOk.setPreferredSize(btCancel.getPreferredSize());
		btOk.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				RequestToClose();
			}
		});

		// -- Add buttons
		jPanelButtons.add(btOk);
		jPanelButtons.add(btCancel);

		// --
		pack();

		setLocationRelativeTo(null);
	}

	public int getTag() {
		return tag;
	}

}
