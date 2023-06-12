package com.twedoo.volcator.helper;

import io.smallrye.mutiny.Uni;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PageHelper {

    private static final String LABEL = "label";
    private static final String VALUE = "value";
    public static List<Command> paginate(List<Command> commands, int pageSize, int pageNumber, String sortField, String sortOrder) {
        int totalCommands = commands.size();
        int startIndex = (pageNumber - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, totalCommands);

        if (startIndex >= totalCommands || startIndex >= endIndex) {
            return Collections.emptyList(); // No commands in the requested page
        }
        // Apply sorting
        if (sortField != null && !sortField.isEmpty()) {
            Comparator<Command> commandComparator = getCommandComparator(sortField, sortOrder);
            commands.sort(commandComparator);
        }
        return commands.subList(startIndex, endIndex);
    }

    public static Comparator<Command> getCommandComparator(String sortField, String sortOrder) {
        Comparator<Command> comparator;

        if ("desc".equalsIgnoreCase(sortOrder)) {
            comparator = Comparator.comparing(Command::getValue).reversed();
        } else {
            comparator = Comparator.comparing(Command::getValue);
        }

        if (LABEL.equalsIgnoreCase(sortField)) {
            if ("desc".equalsIgnoreCase(sortOrder)) {
                comparator = Comparator.comparing(Command::getLabel).reversed();
            } else {
                comparator = Comparator.comparing(Command::getLabel);
            }
        }
        if (VALUE.equalsIgnoreCase(sortField)) {
            if ("desc".equalsIgnoreCase(sortOrder)) {
                comparator = Comparator.comparing(Command::getValue).reversed();
            } else {
                comparator = Comparator.comparing(Command::getValue);
            }
        }

        return comparator;
    }
    public static Uni<Boolean> hasNextPage(Uni<List<Command>> commandsUni, int pageSize, int pageNumber) {
        return commandsUni.map(commands -> {
            int totalCommands = commands.size();
            int totalPages = (int) Math.ceil((double) totalCommands / pageSize);
            return pageNumber < totalPages;
        });
    }
}
