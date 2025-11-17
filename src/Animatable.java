import processing.core.PImage;

import java.util.List;

public abstract class Animatable extends Entity{
    private final double animationPeriod;

    public Animatable(String id, Point position, List<PImage> images, double animationPeriod) {
        super(id, position, images);
        this.animationPeriod = animationPeriod;
    }

    public double getAnimationPeriod() {
        return this.animationPeriod;
    }
    public abstract void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore);
}
