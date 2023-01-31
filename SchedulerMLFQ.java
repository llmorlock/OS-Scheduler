/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Roughdraft;

/**
 *
 * @author Lauren
 */
public class SchedulerMLFQ {
    //constructor
    public SchedulerMLFQ(Process[] processArray) {
        this.processArray = new Process[processArray.length];
        for (int i = 0; i < processArray.length; i++) {
            this.processArray[i] = processArray[i];
        }
    }
    
    public void rqSetUp() {
        //add all processes to queue1 initially
        readyObj0.addAllProcesses(processArray);
        for (int i = 0; i < processArray.length; i++) {
            processArray[i].setState(ProcessState.READY);
            processArray[i].setMlfqLevel(0);
        }
    }
    
    public void runIOBursts() {
        for (int i = 0; i < waitingObj.getIOQueueSize(); i++) {
            System.out.println("DEBUG " + waitingObj.getNextIOProcess(i).getName() + " IO burst");
            waitingObj.getNextIOProcess(i).decIOBurst();
            if (waitingObj.getNextIOProcess(i).getName().equals("p5")) {
                System.out.println("DEBUG P5 next remaining burst is " + waitingObj.getNextIOProcess(i).getIOBurst());
            }
        }
    }
    
    public void sendToReady() {
        for (int i = 0; i < waitingObj.getIOQueueSize(); i++) {
            if (waitingObj.getNextIOProcess(i).getIOBurst() == 0) {
                System.out.println("DEBUG " + waitingObj.getNextIOProcess(i).getName() + " sent to ready");
                waitingObj.getNextIOProcess(i).finishIOBurst();
                readyObj0.addToReadyQueue(waitingObj.getNextIOProcess(i));
                waitingObj.getNextIOProcess(i).setMlfqLevel(0);
                waitingObj.getNextIOProcess(i).setState(ProcessState.READY);
                waitingObj.removeFromIOQueue(i);
                i--;
            }
        }
    }
    
    public void sendToRunning() {
        runningProcess.decCPUBurst();
    }
    
    public void prepareForWaiting() {
        runningProcess.finishCPUBurst();
        waitingFlag = true;
    }
    
    public void sendToWaiting() {
        //runningProcess.finishCPUBurst();
        waitingObj.addToIOQueue(runningProcess);
        runningProcess.setState(ProcessState.WAITING);
        runningProcess = null;
    }
    
    public void sendToTerminated() {
        runningProcess.setState(ProcessState.TERMINATED);
        runningProcess = null;
        amtTerm++;
    }
    
//    public void prepareForRunning(Ready readyObj) {
//        timeRunning = 0;
//        runningProcess = readyObj.getFirstReady();
//        runningProcess.setState(ProcessState.RUNNING);
//        System.out.println("DEBUG running process is " + runningProcess.getName());
//        runningProcess.setTr(totalTime);
//    }
    
    public void prepareForRunning(int i) {
        timeRunning =0;
        runningProcess = readyArray[i].getFirstReady();
        runningProcess.setState(ProcessState.RUNNING);
        System.out.println("DEBUG running process is " + runningProcess.getName());
        runningProcess.setTr(totalTime);
    }
    
    public void downgradeQueue() {
        runningProcess.setMlfqLevel(runningProcess.getMlfqLevel() + 1);
        readyArray[runningProcess.getMlfqLevel()].addToReadyQueue(runningProcess);
        runningProcess.setState(ProcessState.READY);
        runningProcess = null;
    }
    
    public void secondaryMain() {
        rqSetUp();
        
        while (amtTerm < processArray.length) {
            waitingFlag = false;
            
//            if (runningProcess == null && !readyObj.isEmpty()) {
//                runningProcess = readyObj.getFirstReady();
//                runningProcess.setState(ProcessState.RUNNING);
//                System.out.println("DEBUG running process is " + runningProcess.getName());
//                //sets tResponse to beginning of time of response
//                runningProcess.setTr(totalTime);
//            }
            
//            if (runningProcess == null) {
//                if (!readyObj0.isEmpty()) {
//                    prepareForRunning(readyObj0);
//                }
//                else if (!readyObj1.isEmpty()) {
//                    prepareForRunning(readyObj1);
//                }
//                else if (!readyObj2.isEmpty()) {
//                    prepareForRunning(readyObj2);
//                }
//            }

            if (runningProcess == null) {
                for(int i = 0; i < readyArray.length; i++) {
                    if (!readyArray[i].isEmpty()) {
                        prepareForRunning(i);
                        break;
                    }
                }
            }
            
            //increments tWaiting if process in ready queue
            for (int i = 0; i < processArray.length; i++) {
                if (processArray[i].getState() == ProcessState.READY) {
                    System.out.println("DEBUG P" + (i + 1) + " inc tw");
                    processArray[i].incTw();
                }
            }

//            if (runningProcess != null) {
//                if (runningProcess.getCPUBurst() > 1) {
//                    sendToRunning();
//                }
//                else if (runningProcess.hasNextIOBurst()) {
//                    System.out.println("DEBUG " + runningProcess.getName() + " prepared for waiting");
//                    prepareForWaiting();
//                }
//                else {
//                    System.out.println("DEBUG " + runningProcess.getName() + " sent to terminated");
//                    //sets tTurnaround to end of time of termination
//                    runningProcess.setTtr(totalTime + 1);
//                    sendToTerminated();
//                }
//                cpuUtilTime++;
//            }
            
            if (runningProcess != null) {
                System.out.println("DEBUG time running is " + timeRunning);
                System.out.println("DEBUG remaining CPU burst is " + runningProcess.getCPUBurst());
                timeRunning++;
                if (runningProcess.getCPUBurst() > 1) {
//                    if (timeRunning < readyArray[runningProcess.getMlfqLevel()].getTq()) {
//                        sendToRunning();
//                    }
//                    else {  //timeRunning > tq
//                        System.out.println("DEBUG " + runningProcess.getName() + " is downgrading");
//                        sendToRunning();
//                        downgradeQueue();
//                        //continue;
//                    }
                    sendToRunning();
                    if (timeRunning >= readyArray[runningProcess.getMlfqLevel()].getTq()) {
                        downgradeQueue();
                    }
                }
                else if (runningProcess.hasNextIOBurst()) {
                    System.out.println("DEBUG " + runningProcess.getName() + " prepared for waiting");
                    prepareForWaiting();
                }
                else {
                    System.out.println("DEBUG " + runningProcess.getName() + " sent to terminated");
                    runningProcess.setTtr(totalTime + 1);
                    sendToTerminated();
                }
                
                cpuUtilTime++;
            }

            
            
            runIOBursts();
            sendToReady();
            
            if (waitingFlag) {
                System.out.println("DEBUG " + runningProcess.getName() + " sent to waiting");
                sendToWaiting();    //this way does not run cpu and io in same unit
            }
            
            totalTime++;
            System.out.println("DEBUG ------------ end of time unit " + totalTime);
        }
        
        System.out.println("DEBUG all processes terminated");
        System.out.println("DEBUG CPU Util Time is " + cpuUtilTime);
        
        //calculations
        calculatorObj.calcCpuUtil(totalTime, cpuUtilTime);
        for (int i = 0; i < processArray.length; i++) {
            calculatorObj.addToTotalTimes(processArray[i].getTr(), processArray[i].getTw(), processArray[i].getTtr());
        }
        calculatorObj.calcAvgTimes(processArray.length);
        
        //DEBUG
        for (int i = 0; i < processArray.length; i++) {
            System.out.println(processArray[i].getName() + " Tw is " + processArray[i].getTw());
        }
        
        //display results
        System.out.println("------------MLFQ RESULTS------------");
        System.out.println("CPU Utilization: " + calculatorObj.getCpuUtil() + "%");
        System.out.println("Ttr Average: " + calculatorObj.getTurnaroundTimeAvg());
        System.out.println("Tw Average: " + calculatorObj.getWaitingTimeAvg());
        System.out.println("Tr Average: " + calculatorObj.getResponseTimeAvg());
        System.out.println("-----------END OF RESULTS-----------");
    }
    
    //instance variables
    private Process[] processArray;
    private Ready readyObj0 = new Ready(5), readyObj1 = new Ready(10), readyObj2 = new Ready(500);
    private Ready[] readyArray = { readyObj0, readyObj1, readyObj2 };
    private Waiting waitingObj = new Waiting();
    private Calculator calculatorObj = new Calculator();
    private int totalTime = 0, amtTerm = 0;
    private Process runningProcess = null;
    private boolean waitingFlag;
    
    private int cpuUtilTime = 0;
    private int timeRunning = 0;
}
