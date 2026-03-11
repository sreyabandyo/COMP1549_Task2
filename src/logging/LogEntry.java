//Ri Hussain Task2 Testing

package logging;

import model.Role;
import model.AccessScope;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

//Represents a single access log entry recording who accessed what and if it was allowed.

public class LogEntry {

    private final String timestamp;
    private final String userId;
    private final Role role;
    private final String resourceName;
    private final AccessScope scope;
    private final String operation;   // "READ" or "WRITE"
    private final String result;      // "ALLOW" or "REFUSE"

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    public LogEntry(String userId, Role role, String resourceName,
                    AccessScope scope, String operation, String result) {
        this.timestamp    = LocalDateTime.now().format(FORMATTER);
        this.userId       = userId;
        this.role         = role;
        this.resourceName = resourceName;
        this.scope        = scope;
        this.operation    = operation;
        this.result       = result;
    }

    // Getters
    public String getTimestamp()    { return timestamp; }
    public String getUserId()       { return userId; }
    public Role getRole()           { return role; }
    public String getResourceName() { return resourceName; }
    public AccessScope getScope()   { return scope; }
    public String getOperation()    { return operation; }
    public String getResult()       { return result; }

    // Formats entry exactly as required by the spec:
    // 20-01-2025 16:00, user2, ADMIN, Exam Paper, WRITE, ALLOW
    
    public String toString() {
        return timestamp + ", " + userId + ", " + role + ", " +
               resourceName + ", " + operation + ", " + result;
    }
}