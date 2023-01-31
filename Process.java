/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Roughdraft;

import java.util.*;

/**
 *
 * @author Lauren
 */
public class Process {
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
    
    public void reset() {
        this.responseTime = this.waitingTime = this.turnaroundTime = 0;
        this.state = ProcessState.NEW;
        responseTimeFlag = true;
        
        CPUBurstNext.clear();
        IOBurstNext.clear();
        for (int i = 0; i < CPUBurstOrig.size(); i++) {
            CPUBurstNext.add(CPUBurstOrig.get(i));
        }
        
        for (int i = 0; i < IOBurstOrig.size(); i++) {
            IOBurstNext.add(IOBurstOrig.get(i));
        }
    }
    
    public String getName() {
        return name;
    }
    
    public void setState(ProcessState state) {
        this.state = state;
    }
    
    public ProcessState getState() {
        return state;
    }
    
    /*public void readyNextCPUBurst() {
        this.CPURemaining = CPUBurstNext.get(CPUBurstNext.size() - 1);
        CPUBurstNext.remove(CPUBurstNext.size() - 1);
    }
    
    public void decCPUBurst() {
        this.CPURemaining--;
    }*/
    
    public void decCPUBurst() {
        int i = CPUBurstNext.size() - 1;
        System.out.println("DEBUG amt of cpu burst is " + CPUBurstNext.size());
        System.out.println("DEBUG index to dec is " + i);
        CPUBurstNext.set(i, CPUBurstNext.get(i) - 1);
    }
    
    public int getCPUBurst() {
        int i = CPUBurstNext.size() - 1;
        return CPUBurstNext.get(i);
    }
    
    public void finishCPUBurst() {
        int i = CPUBurstNext.size() - 1;
        CPUBurstNext.remove(i);
        //CPUBurstNext.pop();
    }
    
    public boolean hasNextIOBurst() {
        return !IOBurstNext.isEmpty();
    }
    
    public void decIOBurst() {
        int i = IOBurstNext.size() - 1;
        IOBurstNext.set(i, IOBurstNext.get(i) - 1);
    }
    
    public int getIOBurst() {
        int i = IOBurstNext.size() - 1;
        return IOBurstNext.get(i);
    }
    
    public void finishIOBurst() {
        int i = IOBurstNext.size() - 1;
        IOBurstNext.remove(i);
    }
    
    public void setTtr(int time) {
        turnaroundTime = time;
    }
    
    public void incTw() {
        waitingTime++;
    }
    
    public void setTr(int time) {
        if (responseTimeFlag) {
            responseTime = time;
            responseTimeFlag = false;
        }
    }
    
    public int getTtr() {
        return turnaroundTime;
    }
    
    public int getTr() {
        return responseTime;
    }
    
    public int getTw() {
        return waitingTime;
    }
    
    public void setMlfqLevel(int level) {
        queueLevel = level;
    }
    
    public int getMlfqLevel() {
        return queueLevel;
    }
    
    //instance variables
    private int responseTime, waitingTime, turnaroundTime;
    private ProcessState state;
    private boolean responseTimeFlag = true;
    private ArrayList<Integer> CPUBurstOrig = new ArrayList<>();
    private ArrayList<Integer> CPUBurstNext = new ArrayList<>();
    private ArrayList<Integer> IOBurstOrig = new ArrayList<>();
    private ArrayList<Integer> IOBurstNext = new ArrayList<>();
//    private Stack CPUBurstOrig = new Stack();
//    private Stack CPUBurstNext = new Stack();
//    private Stack IOBurstOrig = new Stack();
//    private Stack IOBurstNext = new Stack();
    
    private int CPURemaining, IORemaining;
    private String name;
    private int queueLevel = -1;
}
