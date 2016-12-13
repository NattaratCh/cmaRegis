// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.cma;

import com.cma.MapStudent;
import com.cma.MapStudentDataOnDemand;
import com.cma.MapStudentIntegrationTest;
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

privileged aspect MapStudentIntegrationTest_Roo_IntegrationTest {
    
    declare @type: MapStudentIntegrationTest: @RunWith(SpringJUnit4ClassRunner.class);
    
    declare @type: MapStudentIntegrationTest: @ContextConfiguration(locations = "classpath*:/META-INF/spring/applicationContext*.xml");
    
    declare @type: MapStudentIntegrationTest: @Transactional;
    
    @Autowired
    MapStudentDataOnDemand MapStudentIntegrationTest.dod;
    
    @Test
    public void MapStudentIntegrationTest.testCountMapStudents() {
        Assert.assertNotNull("Data on demand for 'MapStudent' failed to initialize correctly", dod.getRandomMapStudent());
        long count = MapStudent.countMapStudents();
        Assert.assertTrue("Counter for 'MapStudent' incorrectly reported there were no entries", count > 0);
    }
    
    @Test
    public void MapStudentIntegrationTest.testFindMapStudent() {
        MapStudent obj = dod.getRandomMapStudent();
        Assert.assertNotNull("Data on demand for 'MapStudent' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'MapStudent' failed to provide an identifier", id);
        obj = MapStudent.findMapStudent(id);
        Assert.assertNotNull("Find method for 'MapStudent' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'MapStudent' returned the incorrect identifier", id, obj.getId());
    }
    
    @Test
    public void MapStudentIntegrationTest.testFindAllMapStudents() {
        Assert.assertNotNull("Data on demand for 'MapStudent' failed to initialize correctly", dod.getRandomMapStudent());
        long count = MapStudent.countMapStudents();
        Assert.assertTrue("Too expensive to perform a find all test for 'MapStudent', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<MapStudent> result = MapStudent.findAllMapStudents();
        Assert.assertNotNull("Find all method for 'MapStudent' illegally returned null", result);
        Assert.assertTrue("Find all method for 'MapStudent' failed to return any data", result.size() > 0);
    }
    
    @Test
    public void MapStudentIntegrationTest.testFindMapStudentEntries() {
        Assert.assertNotNull("Data on demand for 'MapStudent' failed to initialize correctly", dod.getRandomMapStudent());
        long count = MapStudent.countMapStudents();
        if (count > 20) count = 20;
        int firstResult = 0;
        int maxResults = (int) count;
        List<MapStudent> result = MapStudent.findMapStudentEntries(firstResult, maxResults);
        Assert.assertNotNull("Find entries method for 'MapStudent' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'MapStudent' returned an incorrect number of entries", count, result.size());
    }
    
    @Test
    public void MapStudentIntegrationTest.testFlush() {
        MapStudent obj = dod.getRandomMapStudent();
        Assert.assertNotNull("Data on demand for 'MapStudent' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'MapStudent' failed to provide an identifier", id);
        obj = MapStudent.findMapStudent(id);
        Assert.assertNotNull("Find method for 'MapStudent' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyMapStudent(obj);
        Integer currentVersion = obj.getVersion();
        obj.flush();
        Assert.assertTrue("Version for 'MapStudent' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }
    
    @Test
    public void MapStudentIntegrationTest.testMergeUpdate() {
        MapStudent obj = dod.getRandomMapStudent();
        Assert.assertNotNull("Data on demand for 'MapStudent' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'MapStudent' failed to provide an identifier", id);
        obj = MapStudent.findMapStudent(id);
        boolean modified =  dod.modifyMapStudent(obj);
        Integer currentVersion = obj.getVersion();
        MapStudent merged = obj.merge();
        obj.flush();
        Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        Assert.assertTrue("Version for 'MapStudent' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }
    
    @Test
    public void MapStudentIntegrationTest.testPersist() {
        Assert.assertNotNull("Data on demand for 'MapStudent' failed to initialize correctly", dod.getRandomMapStudent());
        MapStudent obj = dod.getNewTransientMapStudent(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'MapStudent' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'MapStudent' identifier to be null", obj.getId());
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
        Assert.assertNotNull("Expected 'MapStudent' identifier to no longer be null", obj.getId());
    }
    
    @Test
    public void MapStudentIntegrationTest.testRemove() {
        MapStudent obj = dod.getRandomMapStudent();
        Assert.assertNotNull("Data on demand for 'MapStudent' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'MapStudent' failed to provide an identifier", id);
        obj = MapStudent.findMapStudent(id);
        obj.remove();
        obj.flush();
        Assert.assertNull("Failed to remove 'MapStudent' with identifier '" + id + "'", MapStudent.findMapStudent(id));
    }
    
}