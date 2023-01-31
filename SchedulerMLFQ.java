//Scheduler MLFQ.java

//declare package
package cpu.scheduling.project.v3;

/**
 * An implementation of superclass Scheduler, with 3 levels of round-robin
 * ready queues.
 * @author Lauren
 */
public class SchedulerMLFQ extends Scheduler {
    /**
     * Constructor; calls to super constructor, also initializes Tq for each
     * ready queue level.
     * 
     * @param processArray processes to be scheduled
     * @param name name of the scheduler for display purposes
     * @param tqTimes time quantum for each ready queue level
     */
    public SchedulerMLFQ(Process[] processArray, String name, int... tqTimes) {
        super(processArray, name);
        
        readyArray = new Ready[3];
        for (int i = 0; i < tqTimes.length; i++) {
            readyArray[i] = new Ready(tqTimes[i]);
        }
    }
    
    /**
     * Puts all new processes in the first ready queue level.
     */
    @Override
    protected void extractNew() {
        readyArray[0].addAllProcesses(processArray);
        
        for (int i = 0; i < processArray.length; i++) {
            processArray[i].setState(ProcessState.READY);
            processArray[i].setMlfqLevel(0);
        }
    }
    
    /**
     * Finishes a process' io burst, removes it from the waiting queue,
     * and puts it in the first ready queue level.
     * 
     * @param i index of the process in the process array.
     */
    @Override
    protected void sendToReady(int i) {
        waitingQueue.getCurrentIOProcess(i).finishIOBurst();
        readyArray[0].addSingleProcess(waitingQueue.getCurrentIOProcess(i));
        waitingQueue.getCurrentIOProcess(i).setMlfqLevel(0);
        waitingQueue.getCurrentIOProcess(i).setState(ProcessState.READY);
        waitingQueue.removeProcess(i);
    }
    
    /**
     * Extracts a process from the ready queue and assigns it as the 
     * current running process.
     * Overloads superclass method.
     * 
     * @param i process' current ready queue level.
     */
    protected void prepareForRunning(int i) {   
        timeRunning = 0;
        runningProcess = readyArray[i].getFirstReady();
        runningProcess.setState(ProcessState.RUNNING);
        
        //sets Tr time for first time running
        if (runningProcess.getTrFlag() == false) {
            runningProcess.setTr(totalTime);
        }
    }
    
    /**
     * Pushes the running process down to the next lower queue.
     */
    private void downgradeQueue() {
        runningProcess.setMlfqLevel(runningProcess.getMlfqLevel() + 1);
        readyArray[runningProcess.getMlfqLevel()].
                addSingleProcess(runningProcess);
        runningProcess.setState(ProcessState.READY);
        runningProcess = null;
    }
    
    /**
     * Main code sequence.
     * Simulates a scheduler running processes for each time unit.
     */
    @Override
    public void runScheduler() {
        //processes in NEW go to READY
        extractNew();
        
        //runs until all processes are terminated
        while (amtTerm < processArray.length) {
            waitingFlag = runningProcessFlag =  false;

            System.out.println("Ready queue contains:");
            for (int i = 0; i < readyArray.length; i++) {
                if (!readyArray[i].isEmpty()) {
                    //processes in READY can go to RUNNING.
                    //prioritize first ready queue levels.
                    if (runningProcess == null && !runningProcessFlag) {
                        prepareForRunning(i);
                        runningProcessFlag = true;
                    }
                    
                    //if processes are in the ready queue, increment their Tw for
                    //this time unit.
                    for (int j = 0; j < readyArray[i].getSize(); j++) {
                        System.out.println("\t" + 
                            readyArray[i].getProcess(j).getName() + 
                            " (queue level " + 
                            readyArray[i].getProcess(j).getMlfqLevel() + ", " +  
                            readyArray[i].getProcess(j).getCPUBurst() + 
                            " cpu units)");
                        readyArray[i].getProcess(j).incTw();
                    }
                }
            }
            System.out.println();
            
            //a process in RUNNING can either continue being in RUNNING, go to 
            //WAITING, or go to TERMINATED.
            if (runningProcess != null) {
                System.out.println("Running process is " + 
                        runningProcess.getName() + " (queue level " + 
                        runningProcess.getMlfqLevel() + ")");
                timeRunning++;
                if (runningProcess.getCPUBurst() > 1) {
                    sendToRunning();
                    //if Tq has expired, downgrade
                    if (timeRunning >= readyArray
                            [runningProcess.getMlfqLevel()].getTq()) {
                        downgradeQueue();
                    }
                }
                else if (runningProcess.hasNextIOBurst()) {
                    prepareForWaiting();
                }
                else {
                    System.out.println(runningProcess.getName() + 
                            " has completed execution");
                    sendToTerminated();
                }
                System.out.println();
                
                //since both prepareForWaiting() and sendToTerminated() call
                //finishCPUBurst(), the cpu is still utilized for the time 
                //unit, no matter which branch is taken above
                cpuTime++;
            }
            else {
                System.out.println("No running process\n");
            }
            
            System.out.println("IO queue contains:");
            if (waitingQueue.getIOQueueSize() > 0) {
                runIOBursts();
            }
            System.out.println();
            
            //processes in WAITING can go to READY
            for (int i = 0; i < waitingQueue.getIOQueueSize(); i++) {
                if (waitingQueue.getCurrentIOProcess(i).getIOBurst() == 0) {
                    sendToReady(i);
                    i--;
                }
            }
            
            //running process is sent to waiting after processes in waiting
            //queue have already run their io burst for the time unit.
            //this way, running process does not do cpu and io bursts in the
            //same time unit.
            if (waitingFlag) {
                sendToWaiting();
            }
            
            totalTime++;
            System.out.println("------------end of time unit " + 
                    totalTime + "------------\n");
        }
        
        System.out.println("All processes terminated");
        
        calculator.runCalculator(totalTime, cpuTime, processArray);
        displayResults();
    }
    
    //instance variables
    private final Ready[] readyArray;
    private int timeRunning = 0;
    private boolean runningProcessFlag = false;
}
