package open.ipaas.resourceaccesscontrol;

import open.ipaas.resourceaccesscontrol.model.AccessCheckResult;
import open.ipaas.resourceaccesscontrol.model.InheritanceRule;
import open.ipaas.resourceaccesscontrol.model.Permission;
import open.ipaas.resourceaccesscontrol.service.ResourceAccessControlService;
import open.ipaas.resourceaccesscontrol.service.impl.ResourceAccessControlServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ServiceTest {

    private ResourceAccessControlService service;

    @BeforeEach
    void setUp() {
        service = new ResourceAccessControlServiceImpl();
    }

    @Test
    void testCheckSecureAccessForResource() {
        AccessCheckResult result = service.checkSecureAccessForResource("user1", "resource1", "READ");

        assertNotNull(result);
        assertTrue(result.isAllowed());
        assertEquals("User has required permission for resource", result.getReason());
        assertFalse(result.getEffectivePermissions().isEmpty());
    }

    @Test
    void testCheckSecureAccessForResourceContents() {
        AccessCheckResult result = service.checkSecureAccessForResourceContents("user1", "resource1", "READ");

        assertNotNull(result);
        assertTrue(result.isAllowed());
        assertFalse(result.getEffectivePermissions().isEmpty());
    }

    @Test
    void testGetUserEffectivePermissions() {
        List<Permission> permissions = service.getUserEffectivePermissions("user1", "resource1");

        assertNotNull(permissions);
        assertFalse(permissions.isEmpty());
        assertEquals("resource1", permissions.get(0).getResourceId());
        assertEquals("user1", permissions.get(0).getUserId());
    }

    @Test
    void testCheckUserPermission() {
        AccessCheckResult result = service.checkUserPermission("admin", "resource1", "ADMIN");

        assertNotNull(result);
        assertTrue(result.isAllowed());
    }

    @Test
    void testEvaluateInheritanceRules() {
        List<InheritanceRule> rules = service.evaluateInheritanceRules("user1", "resource1");

        assertNotNull(rules);
        assertFalse(rules.isEmpty());
        assertEquals("parent-resource1", rules.get(0).getParentId());
    }
}
