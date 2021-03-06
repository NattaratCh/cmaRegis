// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.cma;

import com.cma.Children_profile;
import com.cma.Children_profileDataOnDemand;
import com.cma.Children_profileIntegrationTest;
import java.util.Iterator;
import java.util.List;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

privileged aspect Children_profileIntegrationTest_Roo_IntegrationTest {
    
    declare @type: Children_profileIntegrationTest: @RunWith(SpringJUnit4ClassRunner.class);
    
    declare @type: Children_profileIntegrationTest: @ContextConfiguration(locations = "classpath*:/META-INF/spring/applicationContext*.xml");
    
    declare @type: Children_profileIntegrationTest: @Transactional;
    
    @Autowired
    Children_profileDataOnDemand Children_profileIntegrationTest.dod;
    
    @Test
    public void Children_profileIntegrationTest.testCountChildren_profiles() {
        Assert.assertNotNull("Data on demand for 'Children_profile' failed to initialize correctly", dod.getRandomChildren_profile());
        long count = Children_profile.countChildren_profiles();
        Assert.assertTrue("Counter for 'Children_profile' incorrectly reported there were no entries", count > 0);
    }
    
    @Test
    public void Children_profileIntegrationTest.testFindChildren_profile() {
        Children_profile obj = dod.getRandomChildren_profile();
        Assert.assertNotNull("Data on demand for 'Children_profile' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Children_profile' failed to provide an identifier", id);
        obj = Children_profile.findChildren_profile(id);
        Assert.assertNotNull("Find method for 'Children_profile' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'Children_profile' returned the incorrect identifier", id, obj.getId());
    }
    
    @Test
    public void Children_profileIntegrationTest.testFindAllChildren_profiles() {
        Assert.assertNotNull("Data on demand for 'Children_profile' failed to initialize correctly", dod.getRandomChildren_profile());
        long count = Children_profile.countChildren_profiles();
        Assert.assertTrue("Too expensive to perform a find all test for 'Children_profile', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<Children_profile> result = Children_profile.findAllChildren_profiles();
        Assert.assertNotNull("Find all method for 'Children_profile' illegally returned null", result);
        Assert.assertTrue("Find all method for 'Children_profile' failed to return any data", result.size() > 0);
    }
    
    @Test
    public void Children_profileIntegrationTest.testFindChildren_profileEntries() {
        Assert.assertNotNull("Data on demand for 'Children_profile' failed to initialize correctly", dod.getRandomChildren_profile());
        long count = Children_profile.countChildren_profiles();
        if (count > 20) count = 20;
        int firstResult = 0;
        int maxResults = (int) count;
        List<Children_profile> result = Children_profile.findChildren_profileEntries(firstResult, maxResults);
        Assert.assertNotNull("Find entries method for 'Children_profile' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'Children_profile' returned an incorrect number of entries", count, result.size());
    }
    
    @Test
    public void Children_profileIntegrationTest.testFlush() {
        Children_profile obj = dod.getRandomChildren_profile();
        Assert.assertNotNull("Data on demand for 'Children_profile' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Children_profile' failed to provide an identifier", id);
        obj = Children_profile.findChildren_profile(id);
        Assert.assertNotNull("Find method for 'Children_profile' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyChildren_profile(obj);
        Integer currentVersion = obj.getVersion();
        obj.flush();
        Assert.assertTrue("Version for 'Children_profile' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }
    
    @Test
    public void Children_profileIntegrationTest.testMergeUpdate() {
        Children_profile obj = dod.getRandomChildren_profile();
        Assert.assertNotNull("Data on demand for 'Children_profile' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Children_profile' failed to provide an identifier", id);
        obj = Children_profile.findChildren_profile(id);
        boolean modified =  dod.modifyChildren_profile(obj);
        Integer currentVersion = obj.getVersion();
        Children_profile merged = obj.merge();
        obj.flush();
        Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        Assert.assertTrue("Version for 'Children_profile' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }
    
    @Test
    public void Children_profileIntegrationTest.testPersist() {
        Assert.assertNotNull("Data on demand for 'Children_profile' failed to initialize correctly", dod.getRandomChildren_profile());
        Children_profile obj = dod.getNewTransientChildren_profile(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'Children_profile' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'Children_profile' identifier to be null", obj.getId());
        try {
            obj.persist();
        } catch (final ConstraintViolationException e) {
            final StringBuilder msg = new StringBuilder();
            for (Iterator<ConstraintViolation<?>> iter = e.getConstraintViolations().iterator(); iter.hasNext();) {
                final ConstraintViolation<?> cv = iter.next();
                msg.append("[").append(cv.getRootBean().getClass().getName()).append(".").append(cv.getPropertyPath()).append(": ").append(cv.getMessage()).append(" (invalid value = ").append(cv.getInvalidValue()).append(")").append("]");
            }
            throw new IllegalStateException(msg.toString(), e);
        }
        obj.flush();
        Assert.assertNotNull("Expected 'Children_profile' identifier to no longer be null", obj.getId());
    }
    
    @Test
    public void Children_profileIntegrationTest.testRemove() {
        Children_profile obj = dod.getRandomChildren_profile();
        Assert.assertNotNull("Data on demand for 'Children_profile' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Children_profile' failed to provide an identifier", id);
        obj = Children_profile.findChildren_profile(id);
        obj.remove();
        obj.flush();
        Assert.assertNull("Failed to remove 'Children_profile' with identifier '" + id + "'", Children_profile.findChildren_profile(id));
    }
    
}
