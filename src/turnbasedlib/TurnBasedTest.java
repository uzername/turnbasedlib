/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package turnbasedlib;

import Actors.QueueActor;
import TurnLibMain.BasicEvent;
import TurnLibMain.TimeScheduleQueue;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * Testing the prototype of turnbased library
 * @author ivan
 */
public class TurnBasedTest {

    /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random generator = new Random();
        System.out.println("Starting priority queue...");
        ArrayList<Actors.QueueActor> allActors = new ArrayList<>(); //all available actors
        
        Actors.QueueActor mainActor = new Actors.QueueActor();
        TurnLibMain.TimeScheduleQueue queueManager = new TimeScheduleQueue();
        mainActor.uniqueName = "mainactor";
        mainActor.setBaseDelay(TimeScheduleQueue.getRandRange(new Float(1.0), new Float(100.0) ));
        Integer actorsCount = 5;
        //filling randomly the array of actors
        for (int i=0; i<actorsCount; i++) {
            Actors.QueueActor someActor = new Actors.QueueActor();
            someActor.uniqueName="actor"+i;
            someActor.setBaseDelay(TimeScheduleQueue.getRandRange(new Float(1.0), new Float(100.0) ));
            allActors.add(someActor);
            System.out.println("created actor : [delay="+allActors.get(allActors.size()-1).getBaseDelay()+"; hash="+allActors.get(allActors.size()-1).hashCode()+"]");
        }
            System.out.println("created main actor : [delay="+mainActor.getBaseDelay()+";hash="+mainActor.hashCode()+"]");
        boolean proceed=true;
        Integer stepNumber = 0;
        while (proceed==true) { //main cycle of actions. each turn of cycle is a single game step
            //first we define actions to perform at the current step and enqueue these to Priority Queue. 
            System.out.println("=== Step No."+stepNumber+" ===");
            System.out.println(">Defining events");
            for (QueueActor singleActor : allActors) {
                //if actor is ready to perform action...
                if (singleActor.readyForAction==true) {
                    TurnLibMain.BasicEvent theEvent = new BasicEvent();
                    theEvent.LinkedData=singleActor; //passing by value
                    theEvent.setPriority(); 
                    theEvent.setRawPriority(theEvent.getPriority()*singleActor.getBaseDelay());
                    System.out.println(theEvent.toString());
                    queueManager.scheduleEvent(theEvent);
                    singleActor.readyForAction = false;
                }
            }
            System.out.println(">main actor");
            TurnLibMain.BasicEvent theEvent = new BasicEvent();
            theEvent.LinkedData=mainActor; //passing by value
            theEvent.setPriority();
            theEvent.setRawPriority(theEvent.getPriority()*mainActor.getBaseDelay());
            System.out.println(theEvent.toString());
            queueManager.scheduleEvent(theEvent);
            //process events in queue
            boolean stopTurn = false;
            System.out.println(queueManager.getQueueData());
            TurnLibMain.BasicEvent extractedEvent = null;
            do {                
                extractedEvent = queueManager.nextEvent();
                System.out.println(">>>firing event:");
                System.out.println(extractedEvent.toString());
                if (extractedEvent.LinkedData.hashCode()==mainActor.hashCode()) {
                    System.out.println("mainactor found, stoping event processing");
                    mainActor.readyForAction = true;
                    stopTurn = true;
                } else {
                    //finding whose action is this
                    for (QueueActor oneActor : allActors) {
                        if (oneActor.hashCode() == extractedEvent.LinkedData.hashCode()) {
                            oneActor.readyForAction = true; 
                            break; //hashcode is beliven to be unque
                        }
                    }
                }
            } while (!stopTurn);
            //waiting for the turn
            System.out.println("Proceed with next step? [y/n]");
            String ename=scanner.nextLine();
            if ((!ename.equalsIgnoreCase("y"))) {
                proceed=false;
            }
            stepNumber++;
        }
        System.out.println("Done!");
    }
    
}
