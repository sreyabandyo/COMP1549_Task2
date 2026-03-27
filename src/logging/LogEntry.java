//Ri Hussain Task2 Testing

package logging;

import model.Role;
import model.AccessScope;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LogEntry {

    private final String timestamp;       // Stores date & time of attempted access
    private final String userId;          // Keeps user ID making requst
    private final Role role;              // Users role 
    private final String resourceName;    // Name of accessed resource
    private final AccessScope scope;      // Senstivity of the recourse 
    private final String operation;       // Attempt is either READ or WRITE
    private final String result;          // If its been "ALLOWED"/"REFUSED"

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

    // Returns timestamo/Id/role/resource name/sensitivity/attempt of read or write/allow or refuse
    public String getTimestamp()    { return timestamp; }
    public String getUserId()       { return userId; }
    public Role getRole()           { return role; }
    public String getResourceName() { return resourceName; }
    public AccessScope getScope()   { return scope; }
    public String getOperation()    { return operation; }
    public String getResult()       { return result; }

    
    public String toString() {
        return timestamp + ", " + userId + ", " + role + ", " +
               resourceName + ", " + operation + ", " + result;
    }
}