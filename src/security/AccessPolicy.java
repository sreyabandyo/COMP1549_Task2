package security;

import model.User;
import model.Resource;
import model.Role;
import model.AccessScope;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AccessPolicy {
    // Enums in sceurity layer represents possible operations
    public enum Operation {
        READ,
        WRITE
    }
    public enum AccessDecision {
        ALLOW,
        REFUSE
    }

    // Policy evaluation method checks if user can perform an operation on a resourse
    public AccessDecision checkAccess(User user,
                                      Resource resource,
                                      Operation operation) {

        Role role = user.getRole();
        AccessScope scope = resource.getScope();

        // Admin - full access
        if (role == Role.ADMIN) {
            return AccessDecision.ALLOW;
        }

        // Staff rules
        if (role == Role.STAFF) {
            // Staff can read everything
            if (operation == Operation.READ) {
                return AccessDecision.ALLOW;
            }
            // Staff can write everything except confidential
            if (operation == Operation.WRITE && scope != AccessScope.CONFIDENTIAL) {
                return AccessDecision.ALLOW;
            }
            return AccessDecision.REFUSE;
        }

        // Student rules
        if (role == Role.STUDENT) {
            // Students can read public and internal
            if (operation == Operation.READ &&
                (scope == AccessScope.PUBLIC || scope == AccessScope.INTERNAL)) {
                return AccessDecision.ALLOW;
            }
            // Students cannot write anything
            return AccessDecision.REFUSE;
        }

        // Guest rules
        if (role == Role.GUEST) {
            // Guests can only read public
            if (operation == Operation.READ && scope == AccessScope.PUBLIC) {
                return AccessDecision.ALLOW;
            }
            return AccessDecision.REFUSE;
        }

        // Default deny if no rule allows access
        return AccessDecision.REFUSE;
    }

    // Rule enforcement method enforces the access decision
    public AccessDecision enforceAccess(User user,
                                        Resource resource,
                                        Operation operation) {

        AccessDecision decision = checkAccess(user, resource, operation);

        // Logging access attempt with user, role, resource, operation and result
        String timestamp = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
        System.out.println(timestamp + ", " 
                + user.getId() + ", " + user.getRole() + ", "
                + resource.getName() + ", " + operation + ", " + decision);

        // Enforcement
        if (decision == AccessDecision.ALLOW) {
            if (operation == Operation.READ) {
                resource.read();
            } else if (operation == Operation.WRITE) {
                resource.write();
            }
        } else {
            System.out.println("Access denied for user: " + user.getId());
        }

        // Return decision to be used in testing
        return decision;
    }
}
