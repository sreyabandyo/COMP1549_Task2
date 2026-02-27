package security;

import model.User;    
import model.Resource;
import model.Role;
import model.Scope;

public class AccessPolicy {
// Method to check if a user can READ a resource
    public boolean canRead(User user, Resource resource) {

        Role role = user.getRole();
        Scope scope = resource.getScope();

        // Admin can read everything
        if (role == Role.ADMIN) {
            return true;
        }
      
        // Staff can read PUBLIC and INTERNAL
        if (role == Role.STAFF) {
            return scope == Scope.PUBLIC || scope == Scope.INTERNAL;
        }

        // Students can read PUBLIC and INTERNAL
        if (role == Role.STUDENT) {
            return scope == Scope.PUBLIC || scope == Scope.INTERNAL;
        }

        // Guests can only read PUBLIC
        if (role == Role.GUEST) {
            return scope == Scope.PUBLIC;
        }

        // If none match, deny access
        return false;
    }

    // Method to check if a user can WRITE to a resource
    public boolean canWrite(User user, Resource resource) {

        Role role = user.getRole();
        Scope scope = resource.getScope();

        // Admin can write everything
        if (role == Role.ADMIN) {
            return true;
        }

        // Staff can write INTERNAL resources only
        if (role == Role.STAFF) {
            return scope == Scope.INTERNAL;
        }

        // Students and Guests cannot write
        if (role == Role.STUDENT || role == Role.GUEST) {
            return false;
        }

        // Default deny
        return false;
    }
}
