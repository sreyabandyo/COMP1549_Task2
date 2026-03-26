// Sreya Bandyopadhyay

package model;

import security.Capability;
import security.Read;
import security.Write;


public class Resource {

    private final String name;
    private final AccessScope scope;

    public Resource(String name, AccessScope scope) {
        this.name = name;
        this.scope = scope;
    }

    public String getName() {
        return name;
    }

    public AccessScope getScope() {
        return scope;
    }

    public void read() {
        System.out.println("[READ] Accessing resource:" + name);
    }

    public void write() {
        System.out.println("[WRITE] Modifying resource:" + name);
    }

    public void read(Capability<Read> cap) {
            System.out.println("[READ] Capability checked access to:" + name);


    }

    public void write(Capability<Write> cap) {
            System.out.println("[WRITE] Capability checked access to:" + name);

    }

    @Override
    public String toString() {
        return "Resource{name='" + name + "', scope=" + scope + "}";
    }
}