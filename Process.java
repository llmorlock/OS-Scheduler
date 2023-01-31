//Process.java

//declare package
package Roughdraft;
import java.util.*;

/**
 *
 * @author Lauren
 */
public class Process {
    //constructor
    public Process(String name, int... burst) {
        this.responseTime = this.waitingTime = this.turnaroundTime = 0;
        this.state = ProcessState.NEW;
        this.name = name;
        
        //add in first elements to the end
        //then can be extracted without shifting
        //LIFO
        for (int i = burst.length - 1; i >= 0; i -= 2) {
            CPUBurstOrig.add(burst[i]);
            CPUBurstNext.add(burst[i]);
            if (i - 1 >= 0)  {
                IOBurstOrig.add(burst[i - 1]);
                IOBurstNext.add(burst[i - 1]);
            }
        }
        
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setState(ProcessState state) {
        this.state = state;
    }
    
    public ProcessState getState() {
        return state;
    }
    
    public void readyNextCPUBurst() {
        this.CPURemaining = CPUBurstNext.get(CPUBurstNext.size() - 1);
        CPUBurstNext.remove(CPUBurstNext.size() - 1);
    }
    
    public void decCPUBurst() {
        this.CPURemaining--;
    }
    
    public int getCPUBurst() {
        return this.CPURemaining;
    }
    
    public boolean hasNextIOBurst() {
        return !IOBurstNext.isEmpty();
    }
    
    public void readyNextIOBurst() {
        this.IORemaining = IOBurstNext.get(IOBurstNext.size() - 1);
        IOBurstNext.remove(IOBurstNext.size() - 1);
    }
    
    public void  decIOBurst() {
        this.IORemaining--;
    }
    
    public int getIOBurst() {
        return this.IORemaining;
    }
    
    //instance variables
    private int responseTime, waitingTime, turnaroundTime;
    ProcessState state;
    ArrayList<Integer> CPUBurstOrig = new ArrayList<>();
    ArrayList<Integer> CPUBurstNext = new ArrayList<>();
    ArrayList<Integer> IOBurstOrig = new ArrayList<>();
    ArrayList<Integer> IOBurstNext = new ArrayList<>();
    int CPURemaining, IORemaining;
    String name;
}
