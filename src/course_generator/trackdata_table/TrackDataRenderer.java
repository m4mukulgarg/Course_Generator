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

package course_generator.trackdata_table;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import org.joda.time.DateTime;

import course_generator.CgData;
import course_generator.TrackData;
import course_generator.settings.CgSettings;
import course_generator.utils.CgConst;

public class TrackDataRenderer extends DefaultTableCellRenderer {

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

		CgData data=null;
		
		CgSettings settings = ((TrackDataModel) table.getModel()).getSettings();
		setIcon(null);

		//Depending of the column number return the rendered label
		switch (column) {
			case 0: // Num
				data = (CgData) value;

				Double num = data.getNum();

				// -- Set the value
				setText(String.format("%1.0f ", num));
				setHorizontalAlignment(CENTER);

				// -- Set the background
				if (isSelected)
					setBackground(CgConst.CL_LINE_SELECTION);
				else
					setBackground(new Color(255, 255, 255));

				return this;

			case 1: // Latitude
				data = (CgData) value;
				
				Double lat = data.getLatitude();

				// -- Set the value
				setText(String.format("%1.7f", lat));
				setHorizontalAlignment(CENTER);

				// -- Set the background
				if (isSelected)
					setBackground(CgConst.CL_LINE_SELECTION);
				else
					setBackground(new Color(255, 242, 193));

				return this;

			case 2: // Longitude
				data = (CgData) value;
				
				Double lon = data.getLongitude();

				// -- Set the value
				setText(String.format("%1.7f", lon));
				setHorizontalAlignment(CENTER);

				// -- Set the background
				if (isSelected)
					setBackground(CgConst.CL_LINE_SELECTION);
				else
					setBackground(new Color(255, 242, 193));

				return this;

			case 3: // Elevation
				data = (CgData) value;
				
				Double slope = data.getSlope();

				setText(data.getElevationString(settings.Unit, false));
				setHorizontalAlignment(CENTER);
				
				// -- Set the icon next to the elevation value
				if (slope > 1.0) {
					setIcon(new javax.swing.ImageIcon(getClass().getResource("/course_generator/images/up_elev.png")));
				} else if (slope < -1.0) {
					setIcon(new javax.swing.ImageIcon(
							getClass().getResource("/course_generator/images/down_elev.png")));
				} else
					setIcon(new javax.swing.ImageIcon(
							getClass().getResource("/course_generator/images/same_level_elev.png")));

				// -- Set the color of the background. Color depend of the slope
				// value
				float tmpslope = slope.floatValue();
				Color c = Color.WHITE;

				if (tmpslope > 2.0f) {
					if (tmpslope > 50.0f)
						tmpslope = 50.0f;
					float vsl = 18.0f * tmpslope / 50.0f;
					c = Color.getHSBColor((18.0f - vsl) / 100.0f, 1f, 1f);
				} else if (tmpslope < -2.0f) {
					tmpslope = -tmpslope;
					if (tmpslope > 50.0f)
						tmpslope = 50.0f;
					/*
					float vsl = 18f * tmpslope / 50.0f;
					c = Color.getHSBColor((48f + vsl) / 100.0f, 1f, 1f);
					*/
					float vsl = 40f * tmpslope / 50.0f;
					c = Color.getHSBColor(0.35f, 1f, (100f - vsl) / 100f);
				} else
					c = Color.WHITE;

				if (isSelected)
					setBackground(CgConst.CL_LINE_SELECTION);
				else
					setBackground(c);

				return this;

			case 4: // Tags
				data = (CgData) value;
				
				int tag = data.getTag();

				// -- Count the number of active tag
				int cmpt = 0;
				int i = 1;
				while (i < 65536) {
					if ((tag & i) != 0)
						cmpt++;
					i = i * 2;
				}

				int x = 0; // X position in the resulting image

				if (cmpt > 0) {
					// -- Prepare the resulting image
					BufferedImage combined = new BufferedImage(18 * cmpt, 16, BufferedImage.TYPE_INT_ARGB);
					Graphics g = combined.getGraphics();

					// Higher point
					if ((tag & CgConst.TAG_HIGH_PT) != 0) {
						ImageIcon image = new javax.swing.ImageIcon(
								getClass().getResource("/course_generator/images/high_point.png"));
						g.drawImage(image.getImage(), x, 0, null);
						x += 18;
					}

					// Lower point
					if ((tag & CgConst.TAG_LOW_PT) != 0) {
						ImageIcon image = new javax.swing.ImageIcon(
								getClass().getResource("/course_generator/images/low_point.png"));
						g.drawImage(image.getImage(), x, 0, null);
						x += 18;
					}

					// Station
					if ((tag & CgConst.TAG_EAT_PT) != 0) {
						ImageIcon image = new javax.swing.ImageIcon(
								getClass().getResource("/course_generator/images/eat.png"));
						g.drawImage(image.getImage(), x, 0, null);
						x += 18;
					}

					// Drink
					if ((tag & CgConst.TAG_WATER_PT) != 0) {
						ImageIcon image = new javax.swing.ImageIcon(
								getClass().getResource("/course_generator/images/drink.png"));
						g.drawImage(image.getImage(), x, 0, null);
						x += 18;
					}

					// Mark
					if ((tag & CgConst.TAG_MARK) != 0) {
						ImageIcon image = new javax.swing.ImageIcon(
								getClass().getResource("/course_generator/images/flag.png"));
						g.drawImage(image.getImage(), x, 0, null);
						x += 18;
					}

					// Roadbook
					if ((tag & CgConst.TAG_ROADBOOK) != 0) {
						ImageIcon image = new javax.swing.ImageIcon(
								getClass().getResource("/course_generator/images/roadbook.png"));
						g.drawImage(image.getImage(), x, 0, null);
						x += 18;
					}

					// Photo
					if ((tag & CgConst.TAG_COOL_PT) != 0) {
						ImageIcon image = new javax.swing.ImageIcon(
								getClass().getResource("/course_generator/images/photo.png"));
						g.drawImage(image.getImage(), x, 0, null);
						x += 18;
					}

					// Note
					if ((tag & CgConst.TAG_NOTE) != 0) {
						ImageIcon image = new javax.swing.ImageIcon(
								getClass().getResource("/course_generator/images/note.png"));
						g.drawImage(image.getImage(), x, 0, null);
						x += 18;
					}

					// Info
					if ((tag & CgConst.TAG_INFO) != 0) {
						ImageIcon image = new javax.swing.ImageIcon(
								getClass().getResource("/course_generator/images/info.png"));
						g.drawImage(image.getImage(), x, 0, null);
						x += 18;
					}

					setIcon(new ImageIcon(combined));
				} else
					setIcon(null); // No image

				// -- Set the value
				setText("");
				setHorizontalAlignment(LEFT);

				// -- Set the background
				if (isSelected)
					setBackground(CgConst.CL_LINE_SELECTION);
				else
					setBackground(new Color(255, 255, 255));

				return this;

			case 5: // Distance
				data = (CgData) value;
				
				setText(data.getDistString(settings.Unit, false));
				setHorizontalAlignment(CENTER);

				// -- Set the background
				if (isSelected)
					setBackground(CgConst.CL_LINE_SELECTION);
				else
					setBackground(new Color(255, 255, 255));

				return this;

			case 6: // Total distance
				data = (CgData) value;
				
				Double total = data.getTotal(settings.Unit);

				// -- Set the value
				setText(String.format("%1.3f", total / 1000.0));
				setHorizontalAlignment(CENTER);

				// -- Set the background
				if (isSelected)
					setBackground(CgConst.CL_LINE_SELECTION);
				else
					setBackground(new Color(221, 255, 155));

				return this;

			case 7: // Difficulty
				data = (CgData) value;
				
				Double diff = data.getDiff();

				// -- Set the value
				setText(String.format("%1.0f ", diff));
				setHorizontalAlignment(CENTER);

				// -- Set the background color
				Color clDiff = Color.WHITE;
				if ((diff <= CgConst.DIFF_VERYEASY) && (diff > CgConst.DIFF_EASY)) {
					clDiff = CgConst.CL_DIFF_VERYEASY; // Color.WHITE; //--
														// paint in white
				} else if ((diff <= CgConst.DIFF_EASY) && (diff > CgConst.DIFF_AVERAGE)) {
					clDiff = CgConst.CL_DIFF_EASY; // new Color(170, 212, 0);
													// //-- Paint in green
				} else if ((diff <= CgConst.DIFF_AVERAGE) && (diff > CgConst.DIFF_HARD)) {
					clDiff = CgConst.CL_DIFF_AVERAGE; // new Color(85, 153,
														// 255); //-- Paint in
														// blue
				} else if ((diff <= CgConst.DIFF_HARD) && (diff > CgConst.DIFF_VERYHARD)) {
					clDiff = CgConst.CL_DIFF_HARD; // new Color(255, 0, 0); //--
													// Paint in red
				} else if (diff <= CgConst.DIFF_VERYHARD) {
					clDiff = CgConst.CL_DIFF_VERYHARD; // new Color(77, 77, 77);
														// //-- Paint in gray
				}

				if (isSelected)
					setBackground(CgConst.CL_LINE_SELECTION);
				else
					setBackground(clDiff);

				return this;

			case 8: // Coeff
				data = (CgData) value;
				
				Double coeff = data.getCoeff();

				// -- Set the value
				setText(String.format("%1.1f ", coeff));
				setHorizontalAlignment(CENTER);

				// -- Set the background
				if (isSelected)
					setBackground(CgConst.CL_LINE_SELECTION);
				else
					setBackground(new Color(255, 255, 255));

				return this;

			case 9: // Recovery
				data = (CgData) value;
				
				Double recup = data.getRecovery();

				// -- Set the value
				if (recup != 0) {
					DecimalFormat myFormatter = new DecimalFormat("0.#");
					String output = myFormatter.format(recup);
					setText(output + " ");
				} else
					setText("");
				setHorizontalAlignment(CENTER);

				// -- Set the background
				if (isSelected)
					setBackground(CgConst.CL_LINE_SELECTION);
				else
					setBackground(new Color(255, 255, 255));

				return this;
				
			case 10: //Time
				data = (CgData) value;
				
				// -- Set the value
				setText(data.getTimeString());				
				setHorizontalAlignment(CENTER);

				// -- Set the background
				if (isSelected)
					setBackground(CgConst.CL_LINE_SELECTION);
				else
					setBackground(new Color(255, 255, 255));
				return this;
				
			case 11: //Time limit
				data = (CgData) value;
				
				int timelimit = data.getTimeLimit();
		        int time1 = data.getTime();

		        //-- Set the value
		        setText(data.getTimeLimitString(true));
		        setHorizontalAlignment(CENTER);
		        
		        //-- Set the background
		        if (isSelected)
		            setBackground(CgConst.CL_LINE_SELECTION);
		        else {
		            if ((timelimit != 0) && (time1 > timelimit))
		               setBackground(Color.PINK);
		             else 
		               setBackground(Color.WHITE);
		        }
		        
		        return this;
		       
			case 12: //Hour
				TrackData track = (TrackData) value;

		        DateTime hour = track.data.get(row).getHour();

		        //-- Set the value
		        setText(hour.toString("E HH:mm:ss "));
		        setHorizontalAlignment(CENTER);
		        
		        //-- Set the background color
		        Color clHour = new Color(221, 255, 155); //Color.LightGreen;
		        
		        int ts_val = hour.getSecondOfDay();
		        int ts_start = track.StartNightTime.getSecondOfDay();
		        int ts_end = track.EndNightTime.getSecondOfDay();
		        if (
		           (track.bNightCoeff)
		            && (
		                (ts_val>ts_start)
		                || (ts_val<ts_end)
		               )
		            )
		            clHour = new Color(0,128,255);//95,158,160); //CadetBlue;

		        if (isSelected)
		            setBackground(CgConst.CL_LINE_SELECTION);
		        else
		            setBackground(clHour);
		        
		        return this;
		        
			case 13: //Station time
				data = (CgData) value;

				// -- Set the value
				setText(data.getStationString(true));
				setHorizontalAlignment(CENTER);

				// -- Set the background
				if (isSelected)
					setBackground(CgConst.CL_LINE_SELECTION);
				else
					setBackground(new Color(255, 255, 255));

				return this;
				
			case 14: //Name
				data = (CgData) value;

		        //-- Set the value
		        setText(data.getName());
		        setHorizontalAlignment(LEFT);
		        
		        //-- Set the background
		        if (isSelected)
		            setBackground(CgConst.CL_LINE_SELECTION);
		        else
		            setBackground(new Color(255, 255, 255));

		        return this;
		        
			case 15: //Comment
				data = (CgData) value;

		        //-- Set the value
		        setText(data.getComment());
		        setHorizontalAlignment(LEFT);
		        
		        //-- Set the background
		        if (isSelected)
		            setBackground(CgConst.CL_LINE_SELECTION);
		        else
		            setBackground(new Color(255, 255, 255));

		        return this;

		} // switch

		return this;
	}

}
