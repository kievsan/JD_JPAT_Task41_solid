package ru.mail.kievsan.view;

public interface ViewersRegister {
    <T> Viewer<T> getViewerFor(Class<T> clazz);

    <T> void registerViewerFor(Class<T> clazz, Viewer<T> viewer);
}
