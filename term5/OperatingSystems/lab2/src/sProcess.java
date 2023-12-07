public class sProcess {
  public int index;
  public int cputime;
  public int ioblocking;
  public int cpudone;
  public int ionext;
  public int numblocked;
  public int weight = 2;

  public sProcess (int index, int cputime, int ioblocking, int cpudone, int ionext, int numblocked) {
    this.index = index;
    this.cputime = cputime;
    this.ioblocking = ioblocking;
    this.cpudone = cpudone;
    this.ionext = ionext;
    this.numblocked = numblocked;
  }

  public String toString() {
    return String.format("done: %d/%d, I/O blocks: %d, I/O interval: %d", cpudone, cputime, numblocked, ioblocking);
  }

  public int timeToComplete() {
    return cputime - cpudone;
  }

  public int timeToIOBreak() {
    return ioblocking - ionext;
  }

  public void proceed(int time) {
    cpudone += time;
    ionext += time;
  }
}

