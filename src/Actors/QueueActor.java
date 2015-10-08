package Actors;

/**
 * the character which participates in queue
 * @author ivan
 */
public class QueueActor {
    /*
    * term 'speed' is semantically ambiguous here, so using a term 'baseDelay'. 
    * The lower the base delay is, the faster is the character
    */
    private Float baseDelay;
    public Boolean readyForAction = true;
    public String uniqueName;
    /**
     * @return the baseDelay
     */
    public Float getBaseDelay() {
        return baseDelay;
    }

    /**
     * @param baseDelay the baseDelay to set
     */
    public void setBaseDelay(Float baseDelay) {
        if (baseDelay == 0) {throw new IllegalArgumentException("baseDelay appeared to be 0. Superfast character?");}
        this.baseDelay = baseDelay;
    }
    
}
