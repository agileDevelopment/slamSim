/**
 * This software is a work of the U.S. Government. It is not subject to copyright 
 * protection and is in the public domain. It may be used as-is or modified and 
 * re-used. The author and the Air Force Institute of Technology would appreciate 
 * credit if this software or parts of it are used or modified for re-use.
 * 
 * Created by Brian Woolley on Sep 23, 2011
 */
package edu.afit.csce723.p2.errorRobot;

import java.util.LinkedList;

import javax.management.InvalidAttributeValueException;

/**
 *
 * @author Brian Woolley (brian.woolley at ieee.org)
 */
public class Path extends LinkedList<Position> {

    /**
     * 
     */
    public Path() {
        this(1024);
    }

    /**
     * @param path
     */
    public Path(Path path) {
        this(path.size());
        addAll(path);
    }

    public Path(int maxPathLength) {
        if (maxPathLength > 0) {
            f_maxPathLength = maxPathLength;
        } else {
            f_maxPathLength = 1024;
            System.out.println(
                    "Maximum Path Length values must be positive values greater than 0. Defaulting to maximum path length of 1024.");
        }
    }

    @Override
    public Position get(int index) {
        if (index >= size())
            return getLast();
        else
            return super.get(index);
    }

    public Position getLast() {
        return get(size() - 1);
    }

    public boolean add(Position aPosition) {
        while (size() >= f_maxPathLength) {
            removeFirst();
        }
        return super.add(aPosition);
    }

    private final int f_maxPathLength;

    /** Auto-generated Serial-ID */
    private static final long serialVersionUID = 6967085243092065997L;
}
