// Riyah Hussain Task2 Testing

package test;

import model.User;
import model.Role;
import model.Resource;
import model.AccessScope;
import security.AccessPolicy;
import security.AccessPolicy.Operation;
import security.AccessPolicy.AccessDecision;
import logging.AccessLog;
import logging.LogEntry;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AccessPolicyTest {

    private AccessPolicy policy;
    private AccessLog accessLog;

    //
    @BeforeEach
    public void setUp() {
        policy = new AccessPolicy();
        accessLog = new AccessLog();
    }

    // 
    @Test
    public void testStudentReadPublicResource() {
        User student = new User("user1", Role.STUDENT);
        Resource lectureNotes = new Resource("Lecture Notes", AccessScope.PUBLIC);

        AccessDecision decision = policy.checkAccess(student, lectureNotes, Operation.READ);

        assertEquals(AccessDecision.ALLOW, decision,
            "STUDENT should be allowed to READ a PUBLIC resource");
    }

    //
    @Test
    public void testStudentWriteConfidentialDenied() {
        User student = new User("user2", Role.STUDENT);
        Resource examPaper = new Resource("Exam Paper", AccessScope.CONFIDENTIAL);

        AccessDecision decision = policy.checkAccess(student, examPaper, Operation.WRITE);

        assertEquals(AccessDecision.REFUSE, decision,
            "STUDENT should be refused WRITE access to a CONFIDENTIAL resource");
    }

    // 
    @Test
    public void testAdminWriteConfidentialAllowed() {
        User admin = new User("user3", Role.ADMIN);
        Resource examPaper = new Resource("Exam Paper", AccessScope.CONFIDENTIAL);

        AccessDecision decision = policy.checkAccess(admin, examPaper, Operation.WRITE);

        assertEquals(AccessDecision.ALLOW, decision,
            "ADMIN should be allowed to WRITE a CONFIDENTIAL resource");
    }

    // 
    @Test
    public void testAccessLoggedCorrectly() {
        User admin = new User("user4", Role.ADMIN);
        Resource printer = new Resource("Printer", AccessScope.INTERNAL);

        //
        accessLog.log(admin.getId(), admin.getRole(), printer, "WRITE", true);

        //
        assertEquals(1, accessLog.getEntries().size(),
            "There should be exactly 1 log entry");

        // 
        LogEntry entry = accessLog.getEntries().get(0);
        assertEquals("user4", entry.getUserId());
        assertEquals(Role.ADMIN, entry.getRole());
        assertEquals("Printer", entry.getResourceName());
        assertEquals("WRITE", entry.getOperation());
        assertEquals("ALLOW", entry.getResult());

        //
        String logOutput = entry.toString();
        assertTrue(logOutput.contains("user4"), "Log should contain userId");
        assertTrue(logOutput.contains("ADMIN"), "Log should contain role");
        assertTrue(logOutput.contains("Printer"), "Log should contain resource name");
        assertTrue(logOutput.contains("WRITE"), "Log should contain operation");
        assertTrue(logOutput.contains("ALLOW"), "Log should contain result");
    }
}