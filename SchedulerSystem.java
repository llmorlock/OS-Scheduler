//System.java

//declare package
package Roughdraft;
import java.util.*;

/**
 *
 * @author Lauren
 */
public class SchedulerSystem {
    
    public static void main(String[] args) {
        int timeUnit = 0;
        int amtTerm = 0;
        boolean finished = false;
        boolean ree = false, ree2 = false;
        LinkedList<Process> readyQueue = new LinkedList<>();
        LinkedList<Process> waitingQueue = new LinkedList<>();
        //ListIterator<Process> iterator = readyQueue.listIterator();
        
        //add in full values later
        Process p1 = new Process("p1", 5, 27, 3);
        Process p2 = new Process("p2", 4, 48, 5);
        Process p3 = new Process("p3", 8, 33, 12);
        
        //set up readyQueue -- maybe make into func?
        //also need better way for looping process setups
        //new queue?
        readyQueue.add(p1);
        p1.setState(ProcessState.READY);
        readyQueue.add(p2);
        p2.setState(ProcessState.READY);
        readyQueue.add(p3);
        p3.setState(ProcessState.READY);
        
        //sort if SJF
        Process runningProcess;
        while (!finished) {
            ree2 = false;
            //Process runningProcess;
            if (!readyQueue.isEmpty()) {
                /*//extracts single process
                runningProcess = readyQueue.removeFirst();
                runningProcess.setState(ProcessState.RUNNING);
                
                //FIXME will not run IO burst simultaneously
                int allocatedCPU = runningProcess.readyNextCPUBurst();
                for (int i = 0; i < allocatedCPU; i++) {
                    allocatedCPU--;
                    timeUnit++;
                }*/
                
                runningProcess = readyQueue.getFirst();
                if (!ree) {
                    runningProcess.readyNextCPUBurst(); //FIXME
                    ree = true;
                }
                
                if (runningProcess.getCPUBurst() > 1) {
                    runningProcess.decCPUBurst();
                    //System.out.println("DEBUG Remaining CPU burst is " + runningProcess.getCPUBurst());
                }
                else {
                    //add process to ioqueue
                    //remove from ready queue
                    //set flag
                    if (runningProcess.hasNextIOBurst()) {
                        waitingQueue.add(runningProcess);
                        runningProcess.readyNextIOBurst();
                        runningProcess.setState(ProcessState.WAITING);
                        readyQueue.removeFirst();
                        ree = false;
                        System.out.println("Process " + runningProcess.getName() + " waiting");
                        ree2 = true;
                    }
                    else {
                        runningProcess.setState(ProcessState.TERMINATED);
                        readyQueue.removeFirst();
                        ree = false;
                        amtTerm++;
                        System.out.println("Process " + runningProcess.getName() + " terminated");
                    }
                }
                
            }
            /*else {  //nothing in queue
                timeUnit++;
            }*/
            timeUnit++;
            
            if (ree2) {
                //ree2 = false;
            for (int i = 0; i < waitingQueue.size() - 1; i++) {
                Process waitingProcess = waitingQueue.get(i);
                if (waitingProcess.getIOBurst() > 1) {
                    waitingProcess.decIOBurst();
                    System.out.println("DEBUG " + waitingProcess.getName() + " IO Burst");
                }
                else {
                    readyQueue.add(waitingProcess);
                    waitingProcess.setState(ProcessState.READY);
                    System.out.println("DEBUG " + waitingProcess.getName() + " to Ready");
                    waitingQueue.remove(i);
                    i--;
                }
                //System.out.println("DEBUG i is " + i);
                //System.out.println("DEBUG waitingQueue(i) is " + waitingQueue.get(i).getName());
            }
            }
            else {
                for (int i = 0; i < waitingQueue.size(); i++) {
                Process waitingProcess = waitingQueue.get(i);
                if (waitingProcess.getIOBurst() > 1) {
                    waitingProcess.decIOBurst();
                    System.out.println("DEBUG " + waitingProcess.getName() + " IO Burst");
                }
                else {
                    readyQueue.add(waitingProcess);
                    waitingProcess.setState(ProcessState.READY);
                    System.out.println("DEBUG " + waitingProcess.getName() + " to Ready");
                    waitingQueue.remove(i);
                    i--;
                }
                //System.out.println("DEBUG i is " + i);
                //System.out.println("DEBUG waitingQueue(i) is " + waitingQueue.get(i).getName());
            }
            }
            
            System.out.println("DEBUG end of time unit = " + timeUnit);
            if (amtTerm == 3) {
                finished = true;
                System.out.println("DEBUG All processes terminated");
            }
        }//end big while
        
    }
    
    //instance variables
    
}
