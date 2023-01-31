//Waiting.java

//declare package
package cpu.scheduling.project.v3;
//linked lists
import java.util.*;

/**
 * A class that creates a waiting queue and supports certain operations.
 * @author Lauren
 */
public class Waiting {
    /**
     * Adds a single process to the tail of the ready queue.
     * 
     * @param process process to be added
     */
    public void addProcess(Process process) {
        ioQueue.add(process);
    }
    
    /**
     * Removes a process from the ready queue.
     * 
     * @param i index of process to be removed
     */
    public void removeProcess(int i) {
        ioQueue.remove(i);
    }
    
    /**
     * Retrieves, but does not remove, a process in the waiting queue.
     * 
     * @param i index of process
     * @return process
     */
    public Process getCurrentIOProcess(int i) {
        return ioQueue.get(i);
    }
    
    /**
     * Returns the size of the waiting queue.
     * 
     * @return size of the queue.
     */
    public int getIOQueueSize() {
        return ioQueue.size();
    }

    //instance variables
    private final LinkedList<Process> ioQueue = new LinkedList<>();
}
