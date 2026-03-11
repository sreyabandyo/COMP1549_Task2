// Ri Hussain Task2 Testing

package logging;

import model.Role;
import model.Resource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//

public class AccessLog {

    private final List<LogEntry> entries = new ArrayList<>();

    // 
    public void log(String userId, Role role, Resource resource,
                    String operation, boolean allowed) {

        String result = allowed ? "ALLOW" : "REFUSE";
        LogEntry entry = new LogEntry(
                userId,
                role,
                resource.getName(),
                resource.getScope(),
                operation,
                result
        );
        entries.add(entry);
        System.out.println(entry);
    }

    // Returns unmodifiable view of all log entries 
    public List<LogEntry> getEntries() {
        return Collections.unmodifiableList(entries);
    }

    // Clears all log entries
    public void clear() {
        entries.clear();
    }

    // Prints all log entries
    public void printAll() {
        if (entries.isEmpty()) {
            System.out.println("No log entries.");
            return;
        }
        for (LogEntry e : entries) {
            System.out.println(e);
        }
    }
}