//Calculator.java

//declare package
package cpu.scheduling.project.v3;

/**
 * A class for calculating cpu utilization and Ttr, Tw, Tr averages for
 * a scheduler.
 * @author Lauren
 */
public class Calculator {
    /**
     * Calculates the percentage cpu utilization.
     * 
     * @param totalTime total time scheduler was running
     * @param cpuTime total time cpu was in use
     */
    private void calcCpuUtil(int totalTime, int cpuTime) {
        cpuUtil = ((double)cpuTime / totalTime) * 100;
    }
    
    /**
     * Gets cpu utilization percent.
     * @return cpu utilization percent.
     */
    public double getCpuUtil() {
        return cpuUtil;
    }
    
    /**
     * Adds a process' individual times to the summed time variables.
     * 
     * @param rTime process' response time
     * @param wTime process' waiting time
     * @param trTime process' turnaround time
     */
    private void addToTotalTimes(int rTime, int wTime, int trTime) {
        rTimeTtl += rTime;
        wTimeTtl += wTime;
        trTimeTtl += trTime;
    }
    
    /**
     * Calculates the average Tr, Tw, Ttr for the scheduler.
     * 
     * @param amt amount of processes scheduled
     */
    private void calcAvgTimes(int amt) {
        rTimeAvg = rTimeTtl / (double)amt;
        wTimeAvg = wTimeTtl / (double)amt;
        trTimeAvg = trTimeTtl / (double)amt;
    }
    
    /**
     * Gets the average response time for the scheduler.
     * 
     * @return average response time
     */
    public double getResponseTimeAvg() {
        return rTimeAvg;
    }
    
    /**
     * Gets the average waiting time for the scheduler.
     * 
     * @return average waiting time
     */
    public double getWaitingTimeAvg() {
        return wTimeAvg;
    }
    
    /**
     * Gets the average turnaround time for the scheduler.
     * 
     * @return average turnaround time
     */
    public double getTurnaroundTimeAvg() {
        return trTimeAvg;
    }
    
    /**
     * Main code sequence.
     * Calculates cpu utilization and time averages for the scheduler.
     * 
     * @param totalTime total time scheduler was running
     * @param cpuUtilTime total time cpu was in use
     * @param processArray array of processes scheduled
     */
    public void runCalculator(int totalTime, 
            int cpuUtilTime, Process[] processArray) {
        calcCpuUtil(totalTime, cpuUtilTime);
        
        for (int i = 0; i < processArray.length; i++) {
            addToTotalTimes(processArray[i].getTr(), processArray[i].getTw(), 
                    processArray[i].getTtr());
        }
        
        calcAvgTimes(processArray.length);
    }
    
    //instance variables
    private double cpuUtil;
    private int rTimeTtl = 0, wTimeTtl = 0, trTimeTtl = 0;
    private double rTimeAvg, wTimeAvg, trTimeAvg;
}
