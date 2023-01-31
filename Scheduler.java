//Scheduler.java

//declare package
package cpu.scheduling.project.v3;

/**
 * An abstract superclass for running a basic scheduler with no sorting
 * implementation.
 * @author Lauren
 */
public abstract class Scheduler {
    /**
     * Constructor; saves given name and processes.
     * 
     * @param processArray processes to be scheduled
     * @param name name of the scheduler for display purposes
     */
    public Scheduler(Process[] processArray, String name) {
        schedulerName = name;
        this.processArray = new Process[processArray.length];
        for (int i = 0; i < processArray.length; i++) {
            this.processArray[i] = processArray[i];
        }
    }
    
    /**
     * Simulates an io burst for the time unit by decrementing current io 
     * bursts of processes in the waiting queue.
     */
    protected void runIOBursts() {
        for (int i = 0; i < waitingQueue.getIOQueueSize(); i++) {
            System.out.println("\t" + 
                    waitingQueue.getCurrentIOProcess(i).getName() + " (" + 
                    waitingQueue.getCurrentIOProcess(i).getIOBurst() + 
                    " io units remaining)");
            waitingQueue.getCurrentIOProcess(i).decIOBurst();
        }
    }
    
    /**
     * Puts all new processes in the ready queue.
     */
    protected void extractNew() {
        readyQueue.addAllProcesses(processArray);
        
        for (int i = 0; i < processArray.length; i++) {
            processArray[i].setState(ProcessState.READY);
        }
    }
    
    /**
     * Finishes a process' io burst, removes it from the waiting queue,
     * and puts it in the ready queue.
     * 
     * @param i index of the process in the process array.
     */
    protected void sendToReady(int i) {
        waitingQueue.getCurrentIOProcess(i).finishIOBurst();
        readyQueue.addSingleProcess(waitingQueue.getCurrentIOProcess(i));
        waitingQueue.getCurrentIOProcess(i).setState(ProcessState.READY);
        waitingQueue.removeProcess(i);
    }
    
    /**
     * Extracts a process from the ready queue and assigns it as the 
     * current running process.
     * Sets response time if not already set.
     */
    protected void prepareForRunning() {
        runningProcess = readyQueue.getFirstReady();
        runningProcess.setState(ProcessState.RUNNING);
        
        //sets Tr time for first time running
        if (runningProcess.getTrFlag() == false) {
            runningProcess.setTr(totalTime);
        } 
    }
    
    /**
     * Simulates a cpu burst for the time unit by decrementing current cpu
     * burst of the running process.
     */
    protected void sendToRunning() {
        runningProcess.decCPUBurst();
    }
    
    /**
     * Finishes the running process' cpu burst and sets the flag for it
     * to be put in the waiting queue.
     */
    protected void prepareForWaiting() {
        runningProcess.finishCPUBurst();
        waitingFlag = true;
    }
    
    /**
     * Sends the running process to the waiting queue.
     */
    protected void sendToWaiting() {
        waitingQueue.addProcess(runningProcess);
        runningProcess.setState(ProcessState.WAITING);
        runningProcess = null;
    }
    
    /**
     * Sends the running process to the terminated state.
     * Sets turnaround time to the end of the time unit.
     */
    protected void sendToTerminated() {
        runningProcess.finishCPUBurst();
        runningProcess.setTtr(totalTime + 1);
        runningProcess.setState(ProcessState.TERMINATED);
        runningProcess = null;
        amtTerm++;
    }
    
    /**
     * Calls to display the Ttr, Tw, and Tr of each process, as well 
     * as their averages and cpu utilization.
     */
    public void displayResults() {
        display.printDiscreteResults(processArray, schedulerName, totalTime);
        display.printCalcResults(calculator);
    }
    
    /**
     * Main code sequence.
     * Simulates a scheduler running processes for each time unit.
     */
    public void runScheduler() {
        //processes in NEW go to READY
        extractNew();
        
        //runs until all processes are terminated
        while (amtTerm < processArray.length) {
            waitingFlag = false;
            
            System.out.println("Ready queue contains:");
            if (!readyQueue.isEmpty()) {
                //processes in READY can go to RUNNING
                if (runningProcess == null) {
                    prepareForRunning();
                }
                
                //if processes are in the ready queue, increment their Tw for
                //this time unit.
                for (int i = 0; i < readyQueue.getSize(); i++) {
                    System.out.println("\t" + 
                            readyQueue.getProcess(i).getName() + " (" + 
                            readyQueue.getProcess(i).getCPUBurst() + 
                            " cpu units remaining)");
                    readyQueue.getProcess(i).incTw();
                }
            }
            System.out.println();
            
            //a process in RUNNING can either continue being in RUNNING, go to 
            //WAITING, or go to TERMINATED.
            if (runningProcess != null) {
                System.out.println("Running process is " + 
                        runningProcess.getName());
                if (runningProcess.getCPUBurst() > 1) {
                    sendToRunning();
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
    protected Process[] processArray;
    protected Process runningProcess = null;
    
    protected Ready readyQueue = new Ready();
    protected Waiting waitingQueue = new Waiting();
    protected Calculator calculator = new Calculator();
    protected Display display = new Display();
    
    protected int totalTime = 0, amtTerm = 0, cpuTime = 0;
    protected boolean waitingFlag;
    
    protected final String schedulerName;
}
