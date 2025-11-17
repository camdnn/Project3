public class Activity implements Action{
    private final WorldModel world;
    private final ImageStore imageStore;
    private final Actionable target;

    public Activity(Actionable target, WorldModel world, ImageStore imageStore) {
        this.world = world;
        this.imageStore = imageStore;
        this.target = target;
    }

    public void executeAction(EventScheduler scheduler) {
        target.executeActivity(this.world, this.imageStore, scheduler);
    }

}
