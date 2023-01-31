//SchedulerFCFS.java

//declare package
package cpu.scheduling.project.v3;

/**
 * An implementation of superclass Scheduler, with no sorting implementation.
 * @author Lauren
 */
public class SchedulerFCFS extends Scheduler {
    /**
     * Constructor; calls to super constructor.
     * 
     * @param processArray processes to be scheduled
     * @param name name of the scheduler for display purposes
     */
    public SchedulerFCFS(Process[] processArray, String name) {
        super(processArray, name);
    }
}
