package com.example.slotmachine;

public class Wheel extends Thread{
    interface WheelListener {
        void newImage(int image);
    }

    private static Image[] images = {
            new Image(R.drawable.s1,1), new Image(R.drawable.s2,1),
            new Image(R.drawable.s3,777), new Image(R.drawable.s4,1),
            new Image(R.drawable.s5,7), new Image(R.drawable.s6,1)
    };
    private int currentIndex;
    private WheelListener wheelListener;
    private long frameDuration;
    private long start;
    private boolean isStarted;
    private int valueImage;

    public int getCurrentIndex() {
        return currentIndex;
    }

    public int getValueImage() {
        return valueImage;
    }

    public Wheel(WheelListener wheelListener, long frameDuration, long start){
        this.wheelListener = wheelListener;
        this.frameDuration = frameDuration;
        this.start = start;
        currentIndex = 0;
        isStarted = true;
    }

    public void nextImage() {
        currentIndex++;

        if(currentIndex == images.length) {
            currentIndex = 0;
        }
    }

    @Override
    public void run() {
        try {
            Thread.sleep(start);
            doRun();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void doRun() throws InterruptedException{
        while (isStarted) {
            Thread.sleep(frameDuration);
            if(wheelListener != null) {
                valueImage = images[currentIndex].getValue();
                wheelListener.newImage(images[currentIndex].getImage());
            }
            nextImage();
        }
    }

    public void stopWheel() {
        isStarted = false;
    }
}
