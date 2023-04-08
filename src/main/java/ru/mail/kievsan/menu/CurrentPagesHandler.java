package ru.mail.kievsan.menu;

import java.io.*;
import java.util.Scanner;

public class CurrentPagesHandler implements Closeable, CurrentChannel {
    protected Scanner scanner;
    protected PrintStream output;
    protected CurrentPage page;

    public CurrentPagesHandler(InputStream in, OutputStream out) {
        scanner = new Scanner(in);
        output = new PrintStream(out, true);
    }

    @Override
    public void close() throws IOException {
        page = null;
        scanner.close();
        output.close();
    }

    public void setPage(CurrentPage page) {
        this.page = page;
    }

    @Override
    public String readLine() {
        return scanner.nextLine().trim();
    }

    @Override
    public void write(Object obj) {
        output.print(obj);
    }

    public void mainLoop() {
        while (page != null) {
            println(page.getTitle());
            println("=".repeat(20));
            println(page.getView());
            page.handle(this);
        }
    }
}
