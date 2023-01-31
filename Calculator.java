/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Roughdraft;

/**
 *
 * @author Lauren
 */
public class Calculator {
    public void calcCpuUtil(int totalTime, int cpuTime) {
        cpuUtil = ((double)cpuTime / totalTime) * 100;
    }
    
    public double getCpuUtil() {
        return cpuUtil;
    }
    
    public void addToTotalTimes(int rTime, int wTime, int trTime) {
        rTimeTtl += rTime;
        wTimeTtl += wTime;
        trTimeTtl += trTime;
    }
    
    public void calcAvgTimes(int amt) {
        System.out.println("DEBUG CALC rTimeTtl is " + rTimeTtl);
        System.out.println("DEBUG CALC wTimeTtl is " + wTimeTtl);
        System.out.println("DEBUG CALC trTimeTtl is " + trTimeTtl);
        rTimeAvg = rTimeTtl / (double)amt;
        wTimeAvg = wTimeTtl / (double)amt;
        trTimeAvg = trTimeTtl / (double)amt;
    }
    
    public double getResponseTimeAvg() {
        return rTimeAvg;
    }
    
    public double getWaitingTimeAvg() {
        return wTimeAvg;
    }
    
    public double getTurnaroundTimeAvg() {
        return trTimeAvg;
    }
    
    //instace variables
    private double cpuUtil;
    private int rTimeTtl = 0, wTimeTtl = 0, trTimeTtl = 0;
    private double rTimeAvg, wTimeAvg, trTimeAvg;
}
