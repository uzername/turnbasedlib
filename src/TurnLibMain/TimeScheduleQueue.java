package TurnLibMain;

import java.util.PriorityQueue;

/**
 * manages the Priority Queue. Also handles the steps logic. The main point is:
 * The gameflow is discrete and runs in steps. At the beginning of each step actors
 * decide what to do. Each action takes some time. They are added into queue
 * during the step the actions are done. These are the 'atomic' non-interruptable tasks.
 * there's a main character and other actors. They share the same QueueActor model.
 * It would be OK to define a step duration, which is equal to main character duration.
 * That means that TimeSheduleQueue manages queue discretly. 
 * After reaching the main character marker the game resolver stops. It will proceed only 
 * on receiving event from main character. Other characters are added and processed in the common way.
 * 
 * Fast characters may be given chance to perform another 
 * fast action during the span of the step.Slow actions should be moved to another step. 
 * In this case the character is not given a chance to select another action before 
 * finishing one. Long actions should be interruptable, with (possible) fixation of 
 * intermediate influences to game world
 * @author ivan
 */
public class TimeScheduleQueue {
    
    public static Float getRandRange(Float Min, Float Max) {
        return new Float(Min + (Math.random() * ((Max - Min) + 1.0)));
    }
    
    public Float turnDuration;
    //all scheduled events
    private MyPriorityQueue scheduledEvents;
    public TimeScheduleQueue() {
        scheduledEvents = new MyPriorityQueue();
    }
    /**
     *  Schedules an event to occur after a certain delay
     */
    public void scheduleEvent(BasicEvent evt) {
        this.scheduledEvents.enqueEvent(evt);
    }
    
    /**
    * Dequeues event from queue 
    * @returns the next event to occur.
    */
    public BasicEvent nextEvent() {
        BasicEvent resEvent = this.scheduledEvents.dequeEvent(); //passing by value?
        this.scheduledEvents.adjustPriorities(-resEvent.getPriority());
        return resEvent;
    }
    /**
     * Cancels a pending event, removing it from queue
     */
    public void cancelEvent(Integer IDtoCancel) {
        this.scheduledEvents.removeEventByID(IDtoCancel);
    }
    /**
     * get string representation of current queue state. Good for debug.
     * @return 
     */
    public String getQueueData() {
        return this.scheduledEvents.toString();
    }
}
