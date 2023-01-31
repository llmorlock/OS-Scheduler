//Process.java

//declare package
package cpu.scheduling.project.v3;
//linked lists
import java.util.*;

/**
 * A class for separating a process' cpu and io bursts and supporting
 * other operations.
 * @author Lauren
 */
public class Process {
    /**
     * Constructor; separates cpu and io bursts.
     * 
     * @param name name of the process for display purposes
     * @param burst bursts in the format of (cpu, io, cpu, ...)
     */
    public Process(String name, int... burst) {
        this.name = name;
        
        //LIFO implementation
        for (int i = burst.length - 1; i >= 0; i -= 2) {
            cpuBurstNext.add(burst[i]);
            if (i - 1 >= 0)  {
                ioBurstNext.add(burst[i - 1]);
            }
        }
    }
    
    //cpu burst operations
    /**
     * Decrements the cpu burst at the end of the stack.
     */
    public void decCPUBurst() {
        cpuBurstNext.set(cpuBurstNext.size() - 1, cpuBurstNext.peekLast() - 1);
    }
    
    /**
     * Removes the cpu burst at the end of the stack.
     */
    public void finishCPUBurst() {
        cpuBurstNext.removeLast();
    }
    
    /**
     * Retrieves, but does not remove, the cpu burst at the end of the stack.
     * 
     * @return cpu burst at the end of the stack
     */
    public int getCPUBurst() {
        return cpuBurstNext.peekLast();
    }
    
    //io burst operations
    /**
     * Determines if the io burst queue is empty.
     * 
     * @return bool if io queue is empty or not
     */
    public boolean hasNextIOBurst() {
        return !ioBurstNext.isEmpty();
    }
    
    /**
     * Decrements the io burst at the end of the stack.
     */
    public void decIOBurst() {
        ioBurstNext.set(ioBurstNext.size() - 1, ioBurstNext.peekLast() - 1);
    }
    
    /**
     * Removes the io burst at the end of the stack.
     */
    public void finishIOBurst() {
        ioBurstNext.removeLast();
    }
    
    /**
     * Retrieves, but does not remove, the io burst at the end of the stack.
     * 
     * @return io burst at the end of the stack
     */
    public int getIOBurst() {
        return ioBurstNext.peekLast();
    }
    
    //other setters
    /**
     * Set the process state.
     * 
     * @param state ProcessState enum
     */
    public void setState(ProcessState state) {
        this.state = state;
    }
    
    /**
     * Sets the turnaround time.
     * 
     * @param time turnarounnd time
     */
    public void setTtr(int time) {
        turnaroundTime = time;
    }
    
    /**
     * Increments the waiting time.
     */
    public void incTw() {
        waitingTime++;
    }
    
    /**
     * Sets the response time.
     * Sets the response time flag, so the time cannot be set again.
     * 
     * @param time response time
     */
    public void setTr(int time) {
        responseTime = time;
        trFlag = true;
    }
    
    /**
     * Sets the mlfq level.
     * 
     * @param level mlfq level
     */
    public void setMlfqLevel(int level) {
        queueLevel = level;
    }
    
    //other getters
    /**
     * Gets the name of the process.
     * 
     * @return name of the process
     */
    public String getName() {
        return name;
    }
    
    /**
     * Gets the turnaround time.
     * 
     * @return turnaround time
     */
    public int getTtr() {
        return turnaroundTime;
    }
    
    /**
     * Gets the waiting time.
     * 
     * @return waiting time
     */
    public int getTw() {
        return waitingTime;
    }
    
    /**
     * Gets the response time.
     * 
     * @return response time
     */
    public int getTr() {
        return responseTime;
    }
    
    /**
     * Gets the response time flag.
     * 
     * @return response time flag
     */
    public boolean getTrFlag() {
        return trFlag;
    }
    
    /**
     * Gets the current mlfq level.
     * 
     * @return mlfq level
     */
    public int getMlfqLevel() {
        return queueLevel;
    }
    
    //instance variables
    private int responseTime = 0, waitingTime = 0, turnaroundTime = 0;
    //not used unless in round robin
    private int queueLevel = -1;
    private boolean trFlag = false;
    
    private final String name;
    private ProcessState state = ProcessState.NEW;
    
    private final LinkedList<Integer> cpuBurstNext = new LinkedList<>(), 
            ioBurstNext = new LinkedList<>();
}
