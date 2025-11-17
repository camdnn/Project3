public class Animation implements Action {
    private final int repeatCount;
    private final Animatable target; // narrower type

    public Animation(Animatable target, int repeatCount) {
        this.repeatCount = repeatCount;
        this.target = target;
    }

    @Override
    public void executeAction(EventScheduler scheduler) {
        this.target.nextImage();

        if (repeatCount != 1) {
            scheduler.scheduleEvent(
                    target,
                    new Animation(target, Math.max(repeatCount - 1, 0)),
                    target.getAnimationPeriod()
            );
        }
    }
}
