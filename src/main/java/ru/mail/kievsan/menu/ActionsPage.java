package ru.mail.kievsan.menu;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class ActionsPage extends AbstractPage {
    protected final Map<String, Action> actions;

    public ActionsPage(String pageTitle) {
        super(pageTitle);
        this.actions = new LinkedHashMap<>();
    }

    public void add(String key, String actionTitle, Runnable runnable) {
        actions.put(key, new Action(actionTitle, runnable));
    }

    protected boolean runAction(String actionId) {
        Action cmd = actions.get(actionId);
        if (cmd == null) return false;
        cmd.run();
        return true;
    }

    protected String getMapView(Map<String, Action> map) {
        return map.entrySet()
                .stream()
                .map(e -> e.getKey() + "\t" + e.getValue().getTitle())
                .collect(Collectors.joining("\n"));
    }

    @Override
    public String getView() {
        return getMapView(actions);
    }

    @Override
    public void handle(CurrentChannel channel) {
        while (!runAction(channel.readLine())) {
            channel.println("Команда не распознана, повторите ввод.");
        }
    }
}
