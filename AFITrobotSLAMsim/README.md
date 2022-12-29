# AFIT Robot SLAM Sim

This 2D simulation framework supported the Air Force Institute of Technology's Advanced Topics in Artificial Intelligence (AFIT CSCE 723) course in 2014. This simulator was designed as an simplistic environment for practicing implementations of Monte Carlo localization ([MCL](https://en.wikipedia.org/wiki/Monte_Carlo_localization)), Simultaneous Localization and Mapping ([SLAM](https://en.wikipedia.org/wiki/Simultaneous_localization_and_mapping)), and the [kidnapped robot problem](https://en.wikipedia.org/wiki/Kidnapped_robot_problem). As the human user drives the robot around the environment, error is injected into the robot motion commands (i.e. velocity and turn-rate) using a First Order Gaussian Markov Acceleration ([FOGMA](https://apps.dtic.mil/sti/citations/ADA289299)) process at each time-step. The simulator is structured to operate without a Markov Localization or SLAM solution so that students can begin by seeing how dead-reckoning approaches are affected over time.

## Launch main from:

> edu.afit.csce723.p2.robotSLAM.MonteCarloLocalization\
> edu.afit.csce723.p2.robotSLAM.FastSLAM\
> edu.afit.csce723.p2.robotSLAM.KidnappedRobot

## The user interface has four panels:

> + **Ground Truth Panel** *(upper left)*: Here you see the robot and its range-finders as it interacts with the environment. As you operate the robot with the keyboard (use A,S,D,W) small amounts of error are inserted at each time-step. The errors have little affect on a human operator, but the small errors add up and cause the internal navigation solution to diverge from reality. 
> + **Internal Navigation Panel** *(upper right)*: The robot's internal navigation is shown as a gradient path overlayed onto the environment. As errors accumulate the path may cross over obstacles or leave the map altogether. This not the robot operators fault, it is shows how accumulated error affects navigation.
> + **Position Estimate Panel** *(lower left)*: 
> + **Internal Map Panel** *(lower right)*:
