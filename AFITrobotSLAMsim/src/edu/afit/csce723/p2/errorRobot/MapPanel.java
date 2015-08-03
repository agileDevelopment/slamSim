/**
 * This software is a work of the U.S. Government. It is not subject to copyright 
 * protection and is in the public domain. It may be used as-is or modified and 
 * re-used. The author and the Air Force Institute of Technology would appreciate 
 * credit if this software or parts of it are used or modified for re-use.
 * 
 * Created by Brian Woolley on Jul 1, 2014
 */

package edu.afit.csce723.p2.errorRobot;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JPanel;

import edu.afit.csce723.p2.util.Util;

/**
 * @author Brian Woolley (brian.woolley at ieee.org)
 *
 */
public class MapPanel extends JPanel implements Observer<Environment> {
	
	public MapPanel(Environment env) {
		assert (env != null);
		theMaze = env.getMap();
		env.registerObserver(this);
		repaint();
	}

	synchronized public void update(Environment env) {
		robotPose = new Position(env.getRobotPose());
		robotPath.add(robotPose);
		List<Double> ranges = env.getRobotSensorReadings();
		List<Double> angles = env.getSensorAngles();
		
		assert(ranges.size() == angles.size());
		Collection<Point2D> current = new ArrayList<Point2D>();
		for (int i=0; i<ranges.size(); i++) {
			current.add(convertToCartesian(robotPose, angles.get(i), ranges.get(i)));
		}
		
		if (sensors.size() > 10) {
			sensors.removeFirst();
		}
		sensors.addLast(current);
		repaint();
	}
	
	// Cartesian 
	private Point2D convertToCartesian(Position pose, Double angle, Double range) {
		Double x = pose.getX();
		Double y = pose.getY();
		
		Double theta = Util.normalize(pose.getTheta() + angle);
		Double dx = Math.cos(theta) * range;
		Double dy = Math.sin(theta) * range;
		
		return new Point2D.Double(x + dx, y+ dy);
	}

	synchronized public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        Dimension offset = theMaze.getOffset();
        g.translate(-offset.width, -offset.height);
    	renderer.renderEnvironment(theMaze, robotPath, g, getSize());

    	for (Collection<Point2D> set : sensors) {
        	renderer.renderSensorEnvironment(theMaze, set, g, getSize());
        }
}

	private Maze theMaze;
	private Position robotPose;
	private final Path robotPath = new Path();
	private final LinkedList<Collection<Point2D>> sensors = new LinkedList<Collection<Point2D>>();
    private MazeRenderingTool renderer = new MazeRenderingTool();
	// private List<Point2D> allSensors;
	// private Path path;

    /** Auto-generated serial-ID */
	private static final long serialVersionUID = -8449592975783107600L;

}
