package ru.mail.kievsan.menu;

public abstract class AbstractPage implements CurrentPage {
    protected final String pageTitle;

    public AbstractPage(String pageTitle) {
        this.pageTitle = pageTitle;
    }

    @Override
    public String getTitle() {
        return pageTitle;
    }
}
