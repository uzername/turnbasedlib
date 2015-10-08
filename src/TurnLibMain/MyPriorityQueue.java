package TurnLibMain;

import java.util.ArrayList;
import java.util.Objects;

/**
 * My priority queue, necessary for step by step event processing. 
 * PriorityQueue in java core does not have enough capabilities for this 
 * (especially removing scheduled event from the middle of queue)
 * (event might be canceled due to influence of other events and performers)
 * @author ivan
 */
public class MyPriorityQueue {
    private ArrayList<BasicEvent> myQueue;
    
    public MyPriorityQueue() {
        myQueue = new ArrayList<>();
    }
    public Integer getLength() {
        return myQueue.size();
    }
    public void enqueEvent(BasicEvent evt) {
        Integer finalPos = 0;
        Integer high = this.getLength();
        while (finalPos<high) {
            Integer middle = (int)( (finalPos+high)/2 );
            if (evt.getPriority()<this.myQueue.get(middle).getPriority()) {
                high = middle;
            } else {
                finalPos=middle+1;
            }
        }
        this.myQueue.add(finalPos, evt);
    }
    public void adjustPriorities(Float value2Adjust) {
        for (int i = 0; i < myQueue.size(); i++) {
            myQueue.get(i).modifyPriority(value2Adjust);
        }
    
    }
    public BasicEvent dequeEvent() {
       BasicEvent res = myQueue.get(0);
       myQueue.remove(0);
       return res;
    }
    public void removeEventByID(Integer theID) {
        for (int i = 0; i < myQueue.size(); i++) {
            if (Objects.equals(myQueue.get(i).getID(), theID)) {
                myQueue.remove(i); return;
            }
        }
        return;
    }
    
    @Override
    public String toString() {
        StringBuilder tmpResult = new StringBuilder();
        System.out.println(">>All queue: ");
        for (BasicEvent someEvent : myQueue) {
            tmpResult.append("\n [").append(someEvent.toString()).append("] ; ");
        }
        return tmpResult.toString();
    }
}
