package app;

import logging.AccessLog;
import model.*;
import security.*;
import security.AccessPolicy.AccessDecision;
import security.AccessPolicy.Operation;


public class DemoApp {

    public static void main(String[] args) {

        AccessPolicy policy = new AccessPolicy();
        AccessLog log = new AccessLog();

        User guest = new User ("user1", Role.GUEST);
        User student = new User("user2", Role.STUDENT);
        User admin = new User("user3", Role.ADMIN);

        Resource lectureNotes = new Resource("Lecture Notes", AccessScope.PUBLIC);
        Resource examPaper = new Resource("Exam Paper", AccessScope.CONFIDENTIAL);
        Resource internalDoc = new Resource("Internal Document", AccessScope.INTERNAL);

        Capability<Read> readCap = new Capability<>();
        Capability<Write> writeCap = new Capability<>();

        System.out.println("=== Access Control Demo ===");

        
        //Guest reads public - ALLOW
        runDemo(policy, log, guest, lectureNotes, Operation.READ);

        //Guest reads internal - REFUSE
        runDemo(policy, log, guest, internalDoc, Operation.READ);

        //Student reads internal - ALLOW
        runDemo(policy, log, student, internalDoc, Operation.READ);

        //Student writes confidential - REFUSE
        runDemo(policy, log, student , examPaper, Operation.WRITE);

        //Admin writes confidential - ALLOW
        runDemo(policy, log, admin, examPaper, Operation.WRITE);

       
        System.out.println("\n=== TYPED CAPABILITY DEMO ===\n");

        System.out.println("Using Capability<Read> on Lecture Notes:");

        lectureNotes.read(readCap);

        System.out.println("Using Capability<Write> on Exam Paper:");

        examPaper.write(writeCap);

        System.out.println("\n=== ACCESS LOG ===\n");
        log.printAll();
    

    }

    private static void runDemo(AccessPolicy policy, AccessLog log, User user, Resource resource, Operation op) 
    {
        AccessDecision decision = policy.checkAccess(user, resource, op); 
        boolean allowed = decision == AccessDecision.ALLOW;
        log.log(user.getId(), user.getRole(), resource, op.toString(), allowed);
            
    }
}
