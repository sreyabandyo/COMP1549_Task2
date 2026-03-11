// Ri Hussain Task2 Testing

package logging;

import model.Role;
import model.Resource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Records all access attempts (allowed or refused) to resources.
// Called by AccessPolicy after every allow/deny decision.

public class AccessLog {

    private final List<LogEntry> entries = new ArrayList<>();

    // Log an access attempt.
    // Called by Ruhina's AccessPolicy with the result of each access check.
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
        System.out.println(entry); // prints to console as required
    }

    // Returns an unmodifiable view of all log entries (used in JUnit tests).
    public List<LogEntry> getEntries() {
        return Collections.unmodifiableList(entries);
    }

    // Clears all log entries (useful for test setup).
    public void clear() {
        entries.clear();
    }

    // Prints all log entries to console.
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