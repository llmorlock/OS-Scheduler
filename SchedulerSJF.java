//SchedulerSJF.java

//declare package
package cpu.scheduling.project.v3;

/**
 * An implementation of superclass Scheduler, with sorting for shortest
 * next cpu burst.
 * @author Lauren
 */
public class SchedulerSJF extends Scheduler {
    /**
     * Constructor; calls to super constructor.
     * 
     * @param processArray processes to be scheduled
     * @param name name of the scheduler for display purposes
     */
    public SchedulerSJF(Process[] processArray, String name) {
        super(processArray, name);
    }
    
    /**
     * Puts all new processes in the ready queue, then sorts it.
     */
    @Override
    protected void extractNew() {
        readyQueue.addAllProcesses(processArray);
        for (int i = 0; i < processArray.length; i++) {
            processArray[i].setState(ProcessState.READY);
        }
        
        readyQueue.sortSJF();
    }
    
    /**
     * Finishes a process' io burst, removes it from the waiting queue,
     * and puts it in and sorts the ready queue.
     * 
     * @param i index of the process in the process array.
     */
    @Override
    protected void sendToReady(int i) {
        waitingQueue.getCurrentIOProcess(i).finishIOBurst();
        readyQueue.addSingleProcess(waitingQueue.getCurrentIOProcess(i));
        waitingQueue.getCurrentIOProcess(i).setState(ProcessState.READY);
        waitingQueue.removeProcess(i);
        readyQueue.sortSJF();
    }
}
