// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.cma;

import com.cma.Batch;
import com.cma.BatchDataOnDemand;
import com.cma.BatchIntegrationTest;
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

privileged aspect BatchIntegrationTest_Roo_IntegrationTest {
    
    declare @type: BatchIntegrationTest: @RunWith(SpringJUnit4ClassRunner.class);
    
    declare @type: BatchIntegrationTest: @ContextConfiguration(locations = "classpath*:/META-INF/spring/applicationContext*.xml");
    
    declare @type: BatchIntegrationTest: @Transactional;
    
    @Autowired
    BatchDataOnDemand BatchIntegrationTest.dod;
    
    @Test
    public void BatchIntegrationTest.testCountBatches() {
        Assert.assertNotNull("Data on demand for 'Batch' failed to initialize correctly", dod.getRandomBatch());
        long count = Batch.countBatches();
        Assert.assertTrue("Counter for 'Batch' incorrectly reported there were no entries", count > 0);
    }
    
    @Test
    public void BatchIntegrationTest.testFindBatch() {
        Batch obj = dod.getRandomBatch();
        Assert.assertNotNull("Data on demand for 'Batch' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Batch' failed to provide an identifier", id);
        obj = Batch.findBatch(id);
        Assert.assertNotNull("Find method for 'Batch' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'Batch' returned the incorrect identifier", id, obj.getId());
    }
    
    @Test
    public void BatchIntegrationTest.testFindAllBatches() {
        Assert.assertNotNull("Data on demand for 'Batch' failed to initialize correctly", dod.getRandomBatch());
        long count = Batch.countBatches();
        Assert.assertTrue("Too expensive to perform a find all test for 'Batch', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<Batch> result = Batch.findAllBatches();
        Assert.assertNotNull("Find all method for 'Batch' illegally returned null", result);
        Assert.assertTrue("Find all method for 'Batch' failed to return any data", result.size() > 0);
    }
    
    @Test
    public void BatchIntegrationTest.testFindBatchEntries() {
        Assert.assertNotNull("Data on demand for 'Batch' failed to initialize correctly", dod.getRandomBatch());
        long count = Batch.countBatches();
        if (count > 20) count = 20;
        int firstResult = 0;
        int maxResults = (int) count;
        List<Batch> result = Batch.findBatchEntries(firstResult, maxResults);
        Assert.assertNotNull("Find entries method for 'Batch' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'Batch' returned an incorrect number of entries", count, result.size());
    }
    
    @Test
    public void BatchIntegrationTest.testFlush() {
        Batch obj = dod.getRandomBatch();
        Assert.assertNotNull("Data on demand for 'Batch' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Batch' failed to provide an identifier", id);
        obj = Batch.findBatch(id);
        Assert.assertNotNull("Find method for 'Batch' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyBatch(obj);
        Integer currentVersion = obj.getVersion();
        obj.flush();
        Assert.assertTrue("Version for 'Batch' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }
    
    @Test
    public void BatchIntegrationTest.testMergeUpdate() {
        Batch obj = dod.getRandomBatch();
        Assert.assertNotNull("Data on demand for 'Batch' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Batch' failed to provide an identifier", id);
        obj = Batch.findBatch(id);
        boolean modified =  dod.modifyBatch(obj);
        Integer currentVersion = obj.getVersion();
        Batch merged = obj.merge();
        obj.flush();
        Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        Assert.assertTrue("Version for 'Batch' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }
    
    @Test
    public void BatchIntegrationTest.testPersist() {
        Assert.assertNotNull("Data on demand for 'Batch' failed to initialize correctly", dod.getRandomBatch());
        Batch obj = dod.getNewTransientBatch(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'Batch' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'Batch' identifier to be null", obj.getId());
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
        Assert.assertNotNull("Expected 'Batch' identifier to no longer be null", obj.getId());
    }
    
    @Test
    public void BatchIntegrationTest.testRemove() {
        Batch obj = dod.getRandomBatch();
        Assert.assertNotNull("Data on demand for 'Batch' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Batch' failed to provide an identifier", id);
        obj = Batch.findBatch(id);
        obj.remove();
        obj.flush();
        Assert.assertNull("Failed to remove 'Batch' with identifier '" + id + "'", Batch.findBatch(id));
    }
    
}
