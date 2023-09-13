import javax.swing.*;

class myThread extends Thread {
    final private JSlider slider;
    private JSpinner spinner;
    private int step = 1;
    final private int goal;

    public myThread(JSlider slider, JSpinner spinner, int goal) {
        this.slider = slider;
        this.goal = goal;

        spinner.setModel(new SpinnerNumberModel(getPriority(), Thread.MIN_PRIORITY ,Thread.MAX_PRIORITY, 1));
        spinner.addChangeListener(e->{setPriority((int) spinner.getValue());});
    }
    public void setStep (int step) {
        this.step = step;
    }

    @Override
    public void run() {
        while(!interrupted()) {
            int curVal = slider.getValue();
            slider.setValue(curVal + Integer.signum(goal - curVal) * step);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class MainWindow extends JFrame {
    private JPanel mainPanel;
    private JSlider slider;
    private JSpinner spinner1;
    private JSpinner spinner2;
    private JPanel controls;
    private JPanel inputs;
    private JButton startBtn;

    public static void main(String[] args) {
        MainWindow window = new MainWindow();
        window.setContentPane(window.mainPanel);
        window.setTitle("Lab 1a");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(450,550);
        window.setResizable(false);

        myThread thread1 = new myThread(window.slider, window.spinner1, 10);
        myThread thread2 = new myThread(window.slider, window.spinner2, 90);

        window.startBtn.addActionListener(e -> {
            thread1.start();
            thread2.start();
            window.startBtn.setEnabled(false);
        });


        window.setVisible(true);
    }
}
