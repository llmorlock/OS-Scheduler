//SchedulerTester.java

//declare package
package cpu.scheduling.project.v3;

/**
 * A class that creates and calls FCFS, SJF, and MLFQ schedulers, and 
 * then displays the results.
 * @author Lauren
 */
public class SchedulerTester {
    /**
     * Main code sequence.
     * 
     * @param args not used
     */
    public static void main(String[] args) {
        System.out.println("Beginning FCFS...\n");
        SchedulerFCFS fcfs = new SchedulerFCFS(createProcessArray(), "FCFS");
        fcfs.runScheduler();
        
        System.out.println("Beginning SJF...\n");
        SchedulerSJF sjf = new SchedulerSJF(createProcessArray(), "SJF ");
        sjf.runScheduler();
        
        System.out.println("Beginning MLFQ...\n");
        //by making the last Tq = 500, it is effectively the same as FCFS
        SchedulerMLFQ mlfq = new SchedulerMLFQ(createProcessArray(), 
                "MLFQ", 5, 10, 500);
        mlfq.runScheduler();
        
        System.out.println("\n\nALL RESULTS");
        fcfs.displayResults();
        sjf.displayResults();
        mlfq.displayResults();
    }
    
    /**
     * Creates an array of hard-coded processes for each scheduler.
     * 
     * @return the array of processes
     */
    private static Process[] createProcessArray() {
        Process p1 = new Process("p1", 5, 27, 3, 31, 5, 43, 4, 18, 6, 22, 4, 
                26, 3, 24, 4);
        Process p2 = new Process("p2", 4, 48, 5, 44, 7, 42, 12, 37, 9, 76, 4, 
                41, 9, 31, 7, 43, 8);
        Process p3 = new Process("p3", 8, 33, 12, 41, 18, 65, 14, 21, 4, 61, 
                15, 18, 14, 26, 5, 31, 6);
        Process p4 = new Process("p4", 3, 35, 4, 41, 5, 45, 3, 51, 4, 61, 5, 
                54, 6, 82, 5, 77, 3);
        Process p5 = new Process("p5", 16, 24, 17, 21, 5, 36, 16, 26, 7, 31, 
                13, 28, 11, 21, 6, 13, 3, 11, 4);
        Process p6 = new Process("p6", 11, 22, 4, 8, 5, 10, 6, 12, 7, 14, 9, 
                18, 12, 24, 15, 30, 8);
        Process p7 = new Process("p7", 14, 46, 17, 41, 11, 42, 15, 21, 4, 32, 
                7, 19, 16, 33, 10);
        Process p8 = new Process("p8", 4, 14, 5, 33, 6, 51, 14, 73, 16, 87, 6);
        
        Process[] processArray = { p1, p2, p3, p4, p5, p6, p7, p8 };

        return processArray;
    }
}
