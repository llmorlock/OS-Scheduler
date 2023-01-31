//Ready.java

//declare package
package cpu.scheduling.project.v3;
//linked lists, comparator
import java.util.*;

/**
 * A class that creates a ready queue and supports certain operations.
 * @author Lauren
 */
public class Ready {
    /**
     * Default constructor.
     */
    public Ready() {}
    
    /**
     * Constructor; creates a ready queue with a time quantum for round
     * robin scheduling.
     * 
     * @param tq time quantum
     */
    public Ready(int tq) {
        this.tq = tq;
    }
    
    /**
     * Adds all processes in the given array to the ready queue.
     * 
     * @param processArray array of processes
     */
    public void addAllProcesses(Process[] processArray) {
        for (int i = 0; i < processArray.length; i++) {
            readyQueue.add(processArray[i]);
        }
    }
    
    /**
     * Adds a single process to the tail of the ready queue.
     * 
     * @param process process to be added
     */
    public void addSingleProcess(Process process) {
        readyQueue.add(process);
    }
    
    /**
     * Extracts the process at the head of the ready queue.
     * 
     * @return process at the head of the ready queue
     */
    public Process getFirstReady() {
        return readyQueue.removeFirst();
    }
    
    /**
     * Retrieves, but does not remove, a process in the ready queue.
     * 
     * @param i index of process
     * @return process
     */
    public Process getProcess(int i) {
        return readyQueue.get(i);
    }
    
    /**
     * Sorts the ready queue by shortest next cpu burst.
     */
    public void sortSJF() {
        //anonymous class implementation
        Collections.sort(readyQueue, new Comparator<Process>() {
            @Override
            public int compare (Process process1, Process process2) {
                return Integer.compare(process1.getCPUBurst(), 
                        process2.getCPUBurst());
            }
        });
    }
    
    /**
     * Determines if ready queue is empty or not.
     * 
     * @return bool if ready queue is empty
     */
    public boolean isEmpty() {
        return readyQueue.isEmpty();
    }
    
    /**
     * Gets the ready queue's time quantum.
     * 
     * @return time quantum.
     */
    public int getTq() {
        return tq;
    }
    
    /**
     * Returns the size of the ready queue.
     * 
     * @return size of the queue.
     */
    public int getSize() {
        return readyQueue.size();
    }
    
    //instance variables
    private final LinkedList<Process> readyQueue = new LinkedList<>();
    private int tq = 0;
}
