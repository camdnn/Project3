import processing.core.PImage;

import java.util.List;

public abstract class Actionable extends Animatable{
    private final double actionPeriod;

    public Actionable (String id, Point position, List<PImage> images, double actionPeriod, double animationPeriod) {
        super(id, position, images, animationPeriod);
        this.actionPeriod = actionPeriod;
    }

    public double getActionPeriod() {
        return actionPeriod;
    }

    public abstract void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore);
    public abstract void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler);
}
