package ru.mail.kievsan.menu;

public class Action implements Runnable {
    protected final String title;
    protected final Runnable runnable;

    public Action(String title, Runnable runnable) {
        this.title = title;
        this.runnable = runnable;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return title;
    }

    @Override
    public void run() {
        runnable.run();
    }
}
