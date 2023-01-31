/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Roughdraft;

/**
 *
 * @author Lauren
 */
public class SchedulerTester {
    public static void main(String[] args) {
        //test numbers
//        Process p1 = new Process("p1", 6, 2, 8);
//        Process p2 = new Process("p2", 17, 13, 4);
//        Process p3 = new Process("p3", 5, 8, 8);
//        Process[] processArray = { p1, p2, p3 };
        
        //test actual numbers
//        Process p1 = new Process("p1", 5, 27, 3);
//        Process p2 = new Process("p2", 4, 48, 5);
//        Process p3 = new Process("p3", 8, 33, 12);
//        Process[] processArray = { p1, p2, p3 };
        
//        //actual numbers
        Process p1 = new Process("p1", 5, 27, 3, 31, 5, 43, 4, 18, 6, 22, 4, 26, 3, 24, 4);
        Process p2 = new Process("p2", 4, 48, 5, 44, 7, 42, 12, 37, 9, 76, 4, 41, 9, 31, 7, 43, 8);
        Process p3 = new Process("p3", 8, 33, 12, 41, 18, 65, 14, 21, 4, 61, 15, 18, 14, 26, 5, 31, 6);
        Process p4 = new Process("p4", 3, 35, 4, 41, 5, 45, 3, 51, 4, 61, 5, 54, 6, 82, 5, 77, 3);
        Process p5 = new Process("p5", 16, 24, 17, 21, 5, 36, 16, 26, 7, 31, 13, 28, 11, 21, 6, 13, 3, 11, 4);
        Process p6 = new Process("p6", 11, 22, 4, 8, 5, 10, 6, 12, 7, 14, 9, 18, 12, 24, 15, 30, 8);
        Process p7 = new Process("p7", 14, 46, 17, 41, 11, 42, 15, 21, 4, 32, 7, 19, 16, 33, 10);
        Process p8 = new Process("p8", 4, 14, 5, 33, 6, 51, 14, 73, 16, 87, 6);
        Process[] processArray = { p1, p2, p3, p4, p5, p6, p7, p8 };
        
        SchedulerFCFS fcfs = new SchedulerFCFS(processArray);
        fcfs.secondaryMain();
        
        resetProcesses(processArray);
        SchedulerSJF sjf = new SchedulerSJF(processArray);
        sjf.secondaryMain();
        
        resetProcesses(processArray);
        SchedulerMLFQ mlfq = new SchedulerMLFQ(processArray);
        mlfq.secondaryMain();
    }
    
    public static void resetProcesses(Process[] processArray) {
        for (int i = 0; i < processArray.length; i++) {
            processArray[i].reset();
            System.out.println(processArray[i].getName() + " is reset");
        }
    }
}
