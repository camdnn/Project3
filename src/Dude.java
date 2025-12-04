import processing.core.PImage;

import java.util.ArrayList;
import java.util.List;

public abstract class Dude extends Actionable implements NextPos{
    public static final String DUDE_KEY = "dude";
    public static final int DUDE_ACTION_PERIOD_IDX = 0;
    public static final int DUDE_ANIMATION_PERIOD_IDX = 1;
    public static final int DUDE_RESOURCE_LIMIT_IDX = 2;
    public static final int DUDE_NUM_PROPERTIES = 3;

    public int resourceLimit;
    private final PathingStrategy strategy = new AStarPathingStrategy();
    private List<Point> currentPath = new ArrayList<>();
    private Point currentDestination = null;

    public Dude(String id, Point position, List<PImage> images,double actionPeriod, double animationPeriod, int resourceLimit) {
        super(id, position, images, actionPeriod, animationPeriod);
        this.resourceLimit = resourceLimit;
    }

    public int getResourceLimit() {
        return resourceLimit;
    }

    @Override
    public Point nextPosition(WorldModel world, Point destPos) {
        if(!destPos.equals(currentDestination) || currentPath.isEmpty()) {
            currentPath = strategy.computePath(
                    this.getPosition(),
                    destPos,
                    p-> world.withinBounds(p) && (!world.isOccupied(p) || world.getOccupancyCell(p) instanceof Stump),
                    (p1, p2) -> p1.adjacent(p2),
                    PathingStrategy.CARDINAL_NEIGHBORS
            );
            currentDestination = destPos;
        }
        if (currentPath.isEmpty()) return this.getPosition();
        return currentPath.removeFirst(); // Returns next step, removes it from path
    }
}
