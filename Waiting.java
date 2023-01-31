//Waiting.java

//declare package
package Roughdraft;
//linked lists
import java.util.*;

/**
 * A class
 * @author Lauren
 */
public class Waiting {
    public Waiting() {
        
    }
    
    public void addToIOQueue(Process process) {
        ioQueue.add(process);
    }
    
    public int getIOQueueSize() {
        return ioQueue.size();
    }
    
    public Process getNextIOProcess(int i) {
        return ioQueue.get(i);
    }
    
    public void removeFromIOQueue(int i) {
        ioQueue.remove(i);
        System.out.println("DEBUG ioq after removal is " + ioQueue);
    }
    
    //instance variables
    LinkedList<Process> ioQueue = new LinkedList<>();
}
