import javax.swing.*;

class myThread extends Thread {
    final private JSlider slider;
    private int step = 1;
    final private int goal;

    public myThread(JSlider slider, int goal) {
        this.slider = slider;
        this.goal = goal;
    }
    public void setStep (int step) {
        this.step = step;
    }

    @Override
    public void run() {
        MainWindow.SEMAPHORE = Integer.signum(goal - slider.getValue());
        System.out.println("Section is taken");

        while(!isInterrupted() && MainWindow.SEMAPHORE != 0) {
            int curVal = slider.getValue();
            slider.setValue(curVal + Integer.signum(goal - curVal) * step);
        }

        MainWindow.SEMAPHORE = 0;
        System.out.println("Section is free now");
    }
}

public class MainWindow extends JFrame {
    volatile static int SEMAPHORE = 0;
    private myThread currThread;
    private JPanel mainPanel;
    private JSlider slider;
    private JPanel controls;
    private JButton start1Btn;
    private JButton stop1Btn;
    private JButton start2Btn;
    private JButton stop2Btn;
    private JLabel sectionStateInfo;


    public static void main(String[] args) {
        MainWindow window = new MainWindow();
        window.setContentPane(window.mainPanel);
        window.setTitle("Lab 1b");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(450,550);
        window.setResizable(false);

        window.start1Btn.addActionListener(e -> {
            if (SEMAPHORE == 0) {
                window.currThread = new myThread(window.slider, 10);
                window.currThread.setPriority(Thread.MIN_PRIORITY);
                window.currThread.start();

                window.start1Btn.setEnabled(false);
                window.stop1Btn.setEnabled(true);
            }
            else {
                window.sectionStateInfo.setText("Oops, the section is taken...");
            }
        });
        window.stop1Btn.addActionListener(e -> {
            if (SEMAPHORE == -1) {
                window.currThread.interrupt();

                window.start1Btn.setEnabled(true);
                window.stop1Btn.setEnabled(false);

                window.sectionStateInfo.setText("");
            }
        });

        window.start2Btn.addActionListener(e -> {
            if (SEMAPHORE == 0) {
                window.currThread = new myThread(window.slider, 90);
                window.currThread.setPriority(Thread.MAX_PRIORITY);
                window.currThread.start();

                window.start2Btn.setEnabled(false);
                window.stop2Btn.setEnabled(true);
            }
            else {
                window.sectionStateInfo.setText("Oops, the section is taken...");
            }
        });
        window.stop2Btn.addActionListener(e -> {
            if (SEMAPHORE == 1) {
                window.currThread.interrupt();

                window.start2Btn.setEnabled(true);
                window.stop2Btn.setEnabled(false);

                window.sectionStateInfo.setText("");
            }
        });

        window.setVisible(true);
    }
}
