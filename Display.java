//Display.java

//declare package
package cpu.scheduling.project.v3;

/**
 * A class that displays the results of the scheduler.
 * @author Lauren
 */
public class Display {
    /**
     * Displays the times of each process in a chart.
     * 
     * @param processArray array of processes
     * @param name name of the scheduler
     * @param time total time of scheduler
     */
    public void printDiscreteResults(Process[] processArray, String name, int time) {
        System.out.println("\n------------" + name + " RESULTS------------");
        System.out.println("Total time needed to complete " + 
                processArray.length + " processes: " + time + "\n");
        
        System.out.printf("%-10s %-10s %-10s %-10s\n", " ", "Ttr", "Tw", "Tr");
        for (int i = 0; i < processArray.length; i++) {
            System.out.printf("%-10s %-10s %-10s %-10s\n", 
                    processArray[i].getName(), processArray[i].getTtr(), 
                    processArray[i].getTw(), processArray[i].getTr());
        }
    }
    
    /**
     * Prints the average times and cpu utilization of the scheduler.
     * 
     * @param calculator Calculator object for the scheduler
     */
    public void printCalcResults(Calculator calculator) {
        System.out.println("\nCPU Utilization: " + 
                calculator.getCpuUtil() + "%");
        System.out.println("Ttr Average: " + 
                calculator.getTurnaroundTimeAvg());
        System.out.println("Tw Average: " + calculator.getWaitingTimeAvg());
        System.out.println("Tr Average: " + calculator.getResponseTimeAvg());
        System.out.println("-----------END OF RESULTS-----------\n");
    }
}
