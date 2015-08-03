/**
 * This software is a work of the U.S. Government. It is not subject to copyright 
 * protection and is in the public domain. It may be used as-is or modified and 
 * re-used. The author and the Air Force Institute of Technology would appreciate 
 * credit if this software or parts of it are used or modified for re-use.
 * 
 * Created by Brian Woolley on Sep 23, 2011
 */
package edu.afit.csce723.p2.errorRobot;

import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Brian Woolley (brian.woolley at ieee.org)
 *
 */
public class SensorArray {

	public void setPose(Position pose) {
		for (RangeFinder a : rangeFinders)
			a.setPose(pose);
	}

	public void addRangeFinder(double angle, double maxRange) {
		rangeFinders.add(new RangeFinder(angle, maxRange));
	}
	
	public List<Double> getNormalizedRangeReadings() {
		List<Double> ranges = new ArrayList<Double>();
		for(RangeFinder x : rangeFinders) {
			ranges.add(x.reading()/x.getMaxRange());
		}
		return ranges;		
	}

	public List<Double> getRangeReadings() {
		List<Double> ranges = new ArrayList<Double>();
		for(RangeFinder x : rangeFinders) {
			ranges.add(x.reading());
		}
		return ranges;
	}

	public List<Double> getSensorAngles() {
		List<Double> angles = new ArrayList<Double>();
		for(RangeFinder x : rangeFinders) {
			angles.add(x.getAngle());
		}
		return angles;		
	}

	public List<Line2D> getBeams() {
		List<Line2D> beams = new ArrayList<Line2D>();
		for(RangeFinder x : rangeFinders) {
			beams.add(x.getBeam());
		}
		return beams;
	}
	
	private final List<RangeFinder> rangeFinders = new ArrayList<RangeFinder>();
}
