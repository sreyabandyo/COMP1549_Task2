// Riyah Hussain Task2 Testing

package Test;

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

    @BeforeEach         //Runs before each test also helps the test results from not affecting each other  
    public void setUp() {
        policy = new AccessPolicy();
        accessLog = new AccessLog();
    }

    // Test1 checks if a STUDENT can READ a PUBLIc resource
    @Test
    public void testStudentReadPublicResource() {
        User student = new User("user1", Role.STUDENT);     //creates a student with ID "user1"
        Resource lectureNotes = new Resource("Lecture Notes", AccessScope.PUBLIC);

        AccessDecision decision = policy.checkAccess(student, lectureNotes, Operation.READ);

        //Checks the decision is ALLOW 
        assertEquals(AccessDecision.ALLOW, decision,
            "STUDENT should be allowed to READ a PUBLIC resource");
    }

    // TEST2 cheking a STUDENT cannot WRITE in a CONFIDENTIAL resource
    @Test
    public void testStudentWriteConfidentialDenied() {
        User student = new User("user2", Role.STUDENT);
        Resource examPaper = new Resource("Exam Paper", AccessScope.CONFIDENTIAL);

        AccessDecision decision = policy.checkAccess(student, examPaper, Operation.WRITE);

        assertEquals(AccessDecision.REFUSE, decision,
            "STUDENT should be refused WRITE access to a CONFIDENTIAL resource");
    }

    @Test  // 3Test ADMIN can WRITE CONFIDENTIAL resource
    public void testAdminWriteConfidentialAllowed() {
        User admin = new User("user3", Role.ADMIN);
        Resource examPaper = new Resource("Exam Paper", AccessScope.CONFIDENTIAL);

        AccessDecision decision = policy.checkAccess(admin, examPaper, Operation.WRITE);

        assertEquals(AccessDecision.ALLOW, decision,
            "ADMIN should be allowed to WRITE a CONFIDENTIAL resource");
    }

    @Test // 4Test access attempts are crrectly recroded 
    public void testAccessLoggedCorrectly() {
        User admin = new User("user4", Role.ADMIN);
        Resource printer = new Resource("Printer", AccessScope.INTERNAL);

        accessLog.log(admin.getId(), admin.getRole(), printer, "WRITE", true);

        assertEquals(1, accessLog.getEntries().size(),   
            "There should be exactly 1 log entry");

        //gets the first log entry to check its details and verisfies each field of the entry is correct
        LogEntry entry = accessLog.getEntries().get(0);
        assertEquals("user4", entry.getUserId());
        assertEquals(Role.ADMIN, entry.getRole());
        assertEquals("Printer", entry.getResourceName());
        assertEquals("WRITE", entry.getOperation());
        assertEquals("ALLOW", entry.getResult());

        String logOutput = entry.toString();
        assertTrue(logOutput.contains("user4"), "Log should contain userId");
        assertTrue(logOutput.contains("ADMIN"), "Log should contain role");
        assertTrue(logOutput.contains("Printer"), "Log should contain resource name");
        assertTrue(logOutput.contains("WRITE"), "Log should contain operation");
        assertTrue(logOutput.contains("ALLOW"), "Log should contain result");
    }
}