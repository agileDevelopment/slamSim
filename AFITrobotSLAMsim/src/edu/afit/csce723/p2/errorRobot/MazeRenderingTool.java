/**
 * This software is a work of the U.S. Government. It is not subject to copyright 
 * protection and is in the public domain. It may be used as-is or modified and 
 * re-used. The author and the Air Force Institute of Technology would appreciate 
 * credit if this software or parts of it are used or modified for re-use.
 * 
 * Created by Brian Woolley on Sep 23, 2011
 */
package edu.afit.csce723.p2.errorRobot;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Collection;

/**
 *
 * @author Brian Woolley <brian.woolley at ieee.org>
 */
public class MazeRenderingTool {

    private Dimension theFootprint = Maze.getExplorerMap().getSize();
    private Dimension panelSize;
    private final int f_padding = 10;

    public void renderMap(Maze theMap, Graphics g, Dimension size) {
        drawTheMaze(theMap, g, size);
    }

    public void renderRobot(Robot theRobot, Graphics g, Dimension size) {
        drawRangefinderBeams(theRobot, g, size);
        drawTheRobot(theRobot, g, size);
        drawTheRobotHeading(theRobot.getPosition(), (int) theRobot.getRadius(), Color.WHITE, g, size);
    }

    public void renderPath(Path robotPath, Graphics g, Dimension size) {
        drawRobotPath(robotPath, g, size);
    }

    public void renderPoints(Collection<Point2D> points, int radius, Color aColor, Graphics g, Dimension size) {
        drawPoints(points, radius, aColor, g, size);
    }

    public void renderPositions(Collection<Position> poses, int length, Color aColor, Graphics g, Dimension size) {
        for (Position aPose : poses) {
            drawTheRobotHeading(aPose, length, aColor, g, size);
        }
    }

    private Line2D scale(Line2D line, double width, double height) {
        Point2D p1 = scale(line.getP1(), width, height),
                p2 = scale(line.getP2(), width, height);
        return new Line2D.Double(p1, p2);
    }

    private Point2D scale(Point2D pt, double width, double height) {
        int x = scale(pt.getX(), width, height),
                y = scale(pt.getY(), width, height);
        return new Point2D.Double(x, y);
    }

    private int scale(double a, double width, double height) {
        double frac = width / height;

        if (getWidth() / frac < getHeight()) {
            return (int) (a * (getWidth() - f_padding) / width);
        } else {
            return (int) (a * (getHeight() - f_padding) / height);
        }
    }

    private int getWidth() {
        return panelSize.width;
    }

    private int getHeight() {
        return panelSize.height;
    }

    private void setSize(Dimension size) {
        panelSize = size;
    }

    private void drawTheMaze(Maze theMaze, Graphics g, Dimension size) {
        Graphics2D g2 = (Graphics2D) g;
        setSize(size);

        // Draw the walls of the maze map
        g.setColor(Color.DARK_GRAY);
        for (Line2D wall : theMaze.getWalls()) {
            g2.draw(scale(wall, theMaze.getWidth(), theMaze.getHeight()));
        }
    }

    private void drawRangefinderBeams(Robot theRobot, Graphics g, Dimension size) {
        Graphics2D g2 = (Graphics2D) g;
        setSize(size);

        g2.setColor(Color.RED);
        for (Line2D beam : theRobot.getSensorArray().getBeams()) {
            g2.draw(scale(beam, theFootprint.getWidth(), theFootprint.getHeight()));
        }
    }

    private void drawTheRobot(Robot theRobot, Graphics g, Dimension size) {
        Graphics2D g2 = (Graphics2D) g;
        setSize(size);

        double width = theFootprint.getWidth();
        double height = theFootprint.getHeight();

        int x, y, dx, dy;
        x = scale(theRobot.getPosition().getX() - theRobot.getRadius(), width, height);
        y = scale(theRobot.getPosition().getY() - theRobot.getRadius(), width, height);
        dx = scale(2 * theRobot.getRadius(), width, height);
        dy = scale(2 * theRobot.getRadius(), width, height);
        g2.setColor(Color.BLUE);
        g2.fill(new Ellipse2D.Double(x, y, dx, dy));

    }

    private void drawTheRobotHeading(Position theRobotPose, int length, Color aColor, Graphics g, Dimension size) {
        Graphics2D g2 = (Graphics2D) g;
        setSize(size);

        double width = theFootprint.getWidth();
        double height = theFootprint.getHeight();

        int x, y, dx, dy;
        x = scale(theRobotPose.getX(), width, height);
        y = scale(theRobotPose.getY(), width, height);
        dx = x + scale(length * Math.cos(theRobotPose.getTheta()), width, height);
        dy = y + scale(length * Math.sin(theRobotPose.getTheta()), width, height);
        g2.setColor(aColor);
        g2.draw(new Line2D.Double(x, y, dx, dy));
    }

    private void drawRobotPath(Path thePath, Graphics g, Dimension size) {
        // Draw the robot path as positions that fade from dark gray (55, 55, 55) to
        // white (255, 255, 255)
        double color = 200.0;
        double step = color / thePath.size();

        Color aColor = new Color((int) color, (int) color, (int) color);
        for (Position pt : thePath) {
            aColor = new Color((int) color, (int) color, (int) color);
            drawPoint(pt, 3, aColor, g, size, theFootprint);
            color -= step;
            color = Math.max(Math.min(color, 255), 0);
        }
    }

    private void drawPoints(Collection<Point2D> points, int radius, Color aColor, Graphics g, Dimension size) {
        for (Point2D pt : points) {
            drawPoint(pt, radius, aColor, g, size, theFootprint);
        }
    }

    private void drawPoint(Point2D pt, int aRadius, Color aColor, Graphics g, Dimension size, Dimension aFootprint) {
        double width = aFootprint.getWidth();
        double height = aFootprint.getHeight();

        drawPoint(pt, aRadius, aColor, g, size, width, height);
    }

    private void drawPoint(Point2D pt, int aRadius, Color aColor, Graphics g, Dimension size, double width,
            double height) {
        // The minimum radius size is 1.
        if (aRadius < 1)
            return;

        Graphics2D g2 = (Graphics2D) g;
        setSize(size);

        int x, y, dx, dy;
        g2.setColor(aColor);

        x = scale(pt.getX() - aRadius, width, height);
        y = scale(pt.getY() - aRadius, width, height);
        dx = scale(2 * aRadius + 1, width, height);
        dy = scale(2 * aRadius + 1, width, height);
        g2.fill(new Ellipse2D.Double(x, y, dx, dy));
    }

}
