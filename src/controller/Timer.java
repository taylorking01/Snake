package controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

/**
 * The Timer class manages a timer that can count up or down between specified start and end times.
 * It notifies registered listeners about time updates and timer completion.
 */
public class Timer {
    private int startTime;
    private int endTime;
    private int currentTime;
    private boolean isCountingUp;
    private Timeline timeline;

    // Listener interfaces
    public interface TimeUpdateListener {
        void onTimeUpdate(String formattedTime);
    }

    public interface TimerCompleteListener {
        void onTimerComplete();
    }

    private List<TimeUpdateListener> timeUpdateListeners = new ArrayList<>();
    private List<TimerCompleteListener> timerCompleteListeners = new ArrayList<>();

    /**
     * Constructs a Timer.
     *
     * @param startTime the initial time in seconds
     * @param endTime   the target time in seconds
     */
    public Timer(int startTime, int endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.currentTime = startTime;
        this.isCountingUp = endTime > startTime;
        initializeTimeline();
    }

    /**
     * Initializes the Timeline for the timer.
     */
    private void initializeTimeline() {
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            updateTime();
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
    }

    /**
     * Starts the timer.
     */
    public void start() {
        timeline.play();
    }

    /**
     * Stops the timer.
     */
    public void stop() {
        timeline.stop();
    }

    /**
     * Resets the timer to the start time.
     */
    public void reset() {
        timeline.stop();
        this.currentTime = startTime;
        notifyTimeUpdate();
    }

    /**
     * Updates the current time based on counting direction and notifies listeners.
     */
    private void updateTime() {
        if (isCountingUp) {
            currentTime++;
            if (currentTime >= endTime) {
                timeline.stop();
                notifyTimeUpdate();
                notifyTimerComplete();
            } else {
                notifyTimeUpdate();
            }
        } else {
            currentTime--;
            if (currentTime <= endTime) {
                timeline.stop();
                notifyTimeUpdate();
                notifyTimerComplete();
            } else {
                notifyTimeUpdate();
            }
        }
    }

    /**
     * Formats the current time into "MM:SS".
     *
     * @return formatted time string
     */
    private String formatTime() {
        int minutes = currentTime / 60;
        int seconds = currentTime % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    /**
     * Registers a TimeUpdateListener.
     *
     * @param listener the listener to register
     */
    public void addTimeUpdateListener(TimeUpdateListener listener) {
        timeUpdateListeners.add(listener);
    }

    /**
     * Registers a TimerCompleteListener.
     *
     * @param listener the listener to register
     */
    public void addTimerCompleteListener(TimerCompleteListener listener) {
        timerCompleteListeners.add(listener);
    }

    /**
     * Notifies all registered TimeUpdateListeners.
     */
    private void notifyTimeUpdate() {
        String formattedTime = formatTime();
        for (TimeUpdateListener listener : timeUpdateListeners) {
            listener.onTimeUpdate(formattedTime);
        }
    }

    /**
     * Notifies all registered TimerCompleteListeners.
     */
    private void notifyTimerComplete() {
        for (TimerCompleteListener listener : timerCompleteListeners) {
            listener.onTimerComplete();
        }
    }
}
