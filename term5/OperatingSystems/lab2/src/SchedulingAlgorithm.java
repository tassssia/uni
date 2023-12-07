// Run() is called from Scheduling.main() and is where
// the scheduling algorithm written by the user resides.
// User modification should occur within the Run() function.

import java.util.ArrayList;
import java.util.Random;
import java.io.*;

public class SchedulingAlgorithm {
  PrintStream out;
  ArrayList<sProcess> processes;
  sProcess process = null;
  ArrayList<sProcess> tickets = new ArrayList<>();
  int maxTimeQuantumSize = 50; // ms
  boolean breakQuantums = true;

  public SchedulingAlgorithm(ArrayList<sProcess> processes) {
    String resultsFile = "Summary-Processes";
    try {
      out = new PrintStream(new FileOutputStream(resultsFile));
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

    this.processes = processes;
    for (sProcess p : processes) {
      for (int i = 0; i < p.weight; i += 1) {
        tickets.add(p);
      }
    }
  }

  void print(String state) {
    String line = String.format("Process %d: %s (%s)", process.index, state, process);
    out.println(line);
  }

  public void elect() {
    int i = new Random().nextInt(tickets.size());
    process = tickets.get(i);
    print("registered");
  }

  public Results run(int runtime, Results result) {
    result.schedulingType = "Preemptive";
    result.schedulingName = "Lottery";

    int completed = 0;
    int comptime = 0;
    int timeQuantum;
    for (; comptime <= runtime; comptime += timeQuantum) {
      elect();

      int timeToComplete = process.timeToComplete();
      int timeToIOBreak = process.timeToIOBreak();

      if (breakQuantums) {
        timeQuantum = Math.min(maxTimeQuantumSize, Math.min(timeToComplete, timeToIOBreak));
      } else {
        timeQuantum = maxTimeQuantumSize;
      }
      process.proceed(timeQuantum);

      if (timeToComplete <= timeQuantum) {
        completed++;
        print("completed");
        if (completed == processes.size()) {
          result.compuTime = comptime;
          return result;
        }

        while(tickets.remove(process)) {}
      }

      if (timeToIOBreak <= timeQuantum) {
        print("I/O blocked");
        process.numblocked += 1;
        process.ionext = 0;
      }
    }

    result.compuTime = comptime;
    return result;
  }
}
