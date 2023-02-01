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
import java.awt.geom.Point2D;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;

import javax.swing.JPanel;

import edu.afit.csce723.p2.util.Util;

/**
 * @author Brian Woolley (brian.woolley at ieee.org)
 *
 */
public class MapPanel extends JPanel implements Observer<Environment> {

	public MapPanel(Environment env) {
		if (env == null)
			throw new IllegalArgumentException("Environment cannot be null.");
		robotPath = new Path();
		renderer = new MazeRenderingTool();
		sensors = new LinkedList<Collection<Point2D>>();
		env.registerObserver(this);
		update(env);
	}

	synchronized public void update(Environment env) {
		theMap = env.getMap();
		robotPath.add(env.getRobotPose());

		while (sensors.size() > 10) {
			sensors.removeFirst();
		}

		// TODO: This is a good cadidate for functional refactoring
		sensors.addLast(Util.convertToCartesian(env.getRobotPose(), env.getRobotSensorReadings()));
		repaint();
	}

	synchronized public void paintComponent(Graphics g) {
		try {
			super.paintComponent(g);
			g.translate(-theMap.getOffset().width, -theMap.getOffset().height);

			// TODO: Move the offset responsibility into the MapRenderingTool so that it can
			// be scaled with all that needs to be painted.
			// Dimension offset = theMap.getOffset();
			// g.translate(-offset.width, -offset.height);

			// TODO: Maybe here we pass render a collection of drawable objects
			// and we caluate the footprint and offset ad scaling factor?
			// renderer.setSize(new Dimension(0, 0));

			renderer.renderMap(theMap, g, getSize());
			renderer.renderPath(robotPath, g, getSize());
			for (Collection<Point2D> points : sensors) {
				renderer.renderPoints(points, 1, Color.RED, g, getSize());
			}
		} catch (Exception e) {
			// Do Nothing, draw it next time.
		}
	}

	private Maze theMap;
	private final Path robotPath;
	private final LinkedList<Collection<Point2D>> sensors;
	private final MazeRenderingTool renderer;

	/** Auto-generated serial-ID */
	private static final long serialVersionUID = -8449592975783107600L;

}
