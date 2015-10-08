package TurnLibMain;

/**
 * Event class to be processed in Queue.
 * making it maximally generic!
 * @author ivan
 */
public class BasicEvent implements java.io.Serializable {
    public Object LinkedData=null;
    private Float priority;    
    public Integer getID() {
        return this.hashCode();
    }

    /**
     * @return the priority
     */
    public Float getPriority() {
        return priority;
    }

    /**
     * randomly generate priority multiplier in range 0.25 ... 2.0 . 
     * It is initial setting of priority
     * It will be then multiplied on entity's basic speed
     */
    public void setPriority() {
        this.priority = TurnLibMain.TimeScheduleQueue.getRandRange(new Float(0.25), new Float(2.0) );
    }
    /**
     * @param val increase priority onto a specific value
     */
    public void modifyPriority(Float val) {
        priority+=val;
    }
    /**
     *  @param val set priority to this value in explicit maner. 
     * Use it only when you know what you're doing, because everything will get messed one day
     */
    public void setRawPriority(Float val) {
        this.priority = val;
    }
    @Override
    public String toString() {
        String linearRepr="[Event: id="+this.getID()+"; priorityMultiplier="+this.getPriority()+
                          "; entity="+((Actors.QueueActor) (this.LinkedData)).uniqueName+"]";
        return linearRepr;
    }
    
}
