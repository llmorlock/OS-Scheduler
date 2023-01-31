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
public class Ready {
    public Ready() {
        
    }
    
    public Ready(int tq) {
        this.tq = tq;
    }
    
    public void addAllProcesses(Process[] processArray) {
        for (int i = 0; i < processArray.length; i++) {
            readyQueue.add(processArray[i]);
        }
        System.out.println("DEBUG initial rq is: " + readyQueue);
    }
    
    public void addToReadyQueue(Process process) {
        readyQueue.add(process);
    }
    
    public Process getFirstReady() {
        return readyQueue.removeFirst();
    }
    
    public boolean isEmpty() {
        return readyQueue.isEmpty();
    }
    
//    public Comparator<Process> getCompBySJF() {
//        return new
//            Comparator<Process>() {
//                @Override
//                public int compare (Process process1, Process process2) {
//                    return Integer.compare(process1.getCPUBurst(),process2.getCPUBurst());
//                }
//            };
//    }
    
//    public void sortRqForSJF() {
//        Collections.sort(readyQueue, new Comparator<Process>() {
//            @Override
//            public int compare (Process process1, Process process2) {
//                return Integer.compare(process1.getCPUBurst(), process2.getCPUBurst());
//            }
//        });
//    }
    
    public LinkedList getReadyQueue() {
        return readyQueue;
    }
    
    public int getTq() {
        return tq;
    }
    
    //instance variables
    LinkedList<Process> readyQueue = new LinkedList<>();
    int tq = 0;
}
