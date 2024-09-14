package hk.edu.polyu.comp.comp2021.tms.model;

import org.junit.Test;

public class TMSTest {

    TMS tms = new TMS();

    @Test
    public void CreatePrimitiveTasktest() {
        try{tms.validate_executeCommand("CreatePrimitiveTask task1 boil-water 0.3 ,");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("CreatePrimitiveTask task1 boil-water 0.3 ,");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("CreatePrimitiveTask task11232131 boil-water 0.3 ,");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("CreatePrimitiveTask 1task1 boil-water 0.3 ,");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("CreatePrimitiveTask task1/; boil-water 0.3 ,");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("CreatePrimitiveTask task2 boil-water/ 0.3 ,");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("CreatePrimitiveTask task3 0.3");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("CreatePrimitiveTask task4 boil-water 0 ,");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("CreatePrimitiveTask task5 boil-water -1 ,");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("CreatePrimitiveTask task6 boil-water 0.3 .");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("CreatePrimitiveTask task7 boil-water 0.3 456");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("CreatePrimitiveTask  task8 boil-water 0.3 ,");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("CreatePrimitiveTask task2 boil-water 0.4 ,");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("CreatePrimitiveTask task3 boil-water 0.3 ,");} catch (Exception ignored) {}
    }

    @Test
    public void testCreateCompositeTasktest() {
        try{tms.validate_executeCommand("CreatePrimitiveTask task1 boil-water 0.3 ,");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("CreatePrimitiveTask task2 boil-water 0.4 ,");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("CreatePrimitiveTask task3 boil-water 0.3 ,");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("CreateCompositeTask composi0 make-coffee task1,task2,task3");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("CreateCompositeTask composi0 make-coffee task1,task2,task3");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("CreateCompositeTask composite1 make-coffee task1,task2,task3");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("CreateCompositeTask compos0 make-coffee/ task1,task2,task3");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("CreateCompositeTask composi1 task1,task2,task3");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("CreateCompositeTask composi2  make-coffee task1,task2,task3");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("CreateCompositeTask composi3 make-coffee primitive1,primitive2,composite0");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("CreateCompositeTask composi4 erterer task1,task2,task3 rwrew");} catch (Exception ignored) {}

    }

    @Test
    public void testDeleteTask() {
        try{tms.validate_executeCommand("CreatePrimitiveTask task1 boil-water 0.3 ,");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("CreatePrimitiveTask task2 boil-water 0.4 ,");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("CreatePrimitiveTask task31 boil-water 0.3 ,");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("CreateCompositeTask composi0 make-coffee task1,task2,task3");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("DeleteTask task1");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("DeleteTask composi0");} catch (Exception ignored) {}

        try{tms.validate_executeCommand("DeleteTask 123 task1");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("DeleteTask task1 b");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("DeleteTask task;1");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("DeleteTask task23");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("CreateCompositeTask comp1 make-offee task31");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("CreateCompositeTask comp2 make-ffee comp1");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("DeleteTask comp2");} catch (Exception ignored) {}



    }

    @Test
    public void testChangeTask() {
        try{tms.validate_executeCommand("CreatePrimitiveTask task1 boil-water 0.3 ,");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("CreatePrimitiveTask task2 boil-water 0.4 task1");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("CreatePrimitiveTask task3 boil-water 0.3 ,");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("ChangeTask task1 duration 0.5");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("ChangeTask task1 dura 0.5");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("ChangeTask task1  0.5");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("ChangeTask task1 duration 67 0.5");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("ChangeTask task1 duration a");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("ChangeTask task1 name task1");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("ChangeTask task1 name task001");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("ChangeTask task001 description sadjn");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("ChangeTask task001 name tt1");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("ChangeTask task001 prerequisites .");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("ChangeTask task001 prerequisites +");} catch (Exception ignored) {}

        try{tms.validate_executeCommand("ChangeTask task001 subtasks ,");} catch (Exception ignored) {}

    }

    @Test
    public void testPrintTask() {
        try{tms.validate_executeCommand("CreatePrimitiveTask task1 boil-water 0.3 ,");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("CreatePrimitiveTask task2 boil-water 0.4 ,");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("CreatePrimitiveTask task3 boil-water 0.3 ,");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("PrintTask task1");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("PrintTask task1 123");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("PrintTask task2");} catch (Exception ignored) {}
    }

    @Test
    public void testPrintAllTasks() {
        try{tms.validate_executeCommand("CreatePrimitiveTask task1 boil-water 0.3 ,");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("CreatePrimitiveTask task2 boil-water 0.4 ,");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("CreatePrimitiveTask task3 boil-water 0.3 ,");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("PrintAllTasks");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("PrintAllTasks wqe");} catch (Exception ignored) {}
    }

    @Test
    public void testReportDuration() {
        try{tms.validate_executeCommand("CreatePrimitiveTask task1 boil-water 0.3 ,");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("CreatePrimitiveTask task2 boil-water 0.4 ,");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("CreatePrimitiveTask task3 boil-water 0.3 ,");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("CreateCompositeTask composi0 make-coffee task1,task2,task3");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("ReportDuration composi0");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("ReportDuration task1");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("ReportDuration composi1");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("ReportDuration composi0 qwe");} catch (Exception ignored) {}
    }

    @Test
    public void testDefineBasicCriterion() {
        try{tms.validate_executeCommand("             ");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("      r       ");} catch (Exception ignored) {}


        try{tms.validate_executeCommand("DefineBasicCriterion criteri1 duration > 0.1");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("DefineBasicCriterion 6criri1 duration > 0.1");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("DefineBasicCriterion cre/ri1 duration > 0.1");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("DefineBasicCriterion criterion000 duration > 0.1");} catch (Exception ignored) {}

        try{tms.validate_executeCommand("DefineBasicCriterion criter2 duration > 0.1");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("DefineBasicCriterion criter3 duration < 0.1");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("DefineBasicCriterion criter4 duration == 0.1");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("DefineBasicCriterion criter5 duration >= 0.1");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("DefineBasicCriterion criter6 duration <= 0.1");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("DefineBasicCriterion criter7 duration != 0.1");} catch (Exception ignored) {}

        try{tms.validate_executeCommand("DefineBasicCriterion  duration > 0.1");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("DefineBasicCriterion criteri0 qwe duration > 0.1");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("DefineBasicCriterion criteri2 dura > 0.1");} catch (Exception ignored) {}

        try{tms.validate_executeCommand("DefineBasicCriterion criteri3 duration > -1");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("DefineBasicCriterion criter20 duration > a");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("DefineBasicCriterion criteri4 duration ! 0.1");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("DefineBasicCriterion criteri5 name > fu89we");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("DefineBasicCriterion criteri6 description contains \"\"");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("DefineBasicCriterion criteri7 description contains fwff\"");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("DefineBasicCriterion criteri8 description contains \"wefbi");} catch (Exception ignored) {}

        try{tms.validate_executeCommand("DefineBasicCriterion criteri9 description contains \"wefbi\"");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("DefineBasicCriterion criter11 name contains \"qeo39\"");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("DefineBasicCriterion criter12 prerequisites contains ,");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("DefineBasicCriterion criter14 prerequisites contains ,,,,,");} catch (Exception ignored) {}

        try{tms.validate_executeCommand("DefineBasicCriterion criter15 prerequisites contains ,/,");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("DefineBasicCriterion criter13 subtasks contains task1,task2");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("DefineBasicCriterion criter10 name > qefb39");} catch (Exception ignored) {}

    }

    @Test
    public void testReportEarliestFinishTime() {
        try{tms.validate_executeCommand("CreatePrimitiveTask task1 boil-water 0.3 ,");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("CreatePrimitiveTask task2 boil-water 0.4 ,");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("CreatePrimitiveTask task3 boil-water 0.3 ,");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("CreateCompositeTask composi0 make-coffee task1,task2,task3");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("ReportEarliestFinishTime composit0");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("ReportEarliestFinishTime task1");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("ReportEarliestFinishTime composit1");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("ReportEarliestFinishTime composit0 qwe");} catch (Exception ignored) {}
    }

    @Test
    public void testDefineBinaryCriterion_ValidParameters() {
        try{tms.validate_executeCommand("DefineBasicCriterion criteri1 duration > 0.1");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("DefineBasicCriterion criteri2 duration > 0.1");} catch (Exception ignored) {}

        try{tms.validate_executeCommand("DefineBinaryCriterion BCrite5 criteri1 && criteri2");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("DefineBinaryCriterion BCrite6 criteri1 || criteri2");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("DefineBinaryCriterion BCrite7 criteri1 | criteri2");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("DefineBinaryCriterion BCrite8 criteri1 && criteri2");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("DefineBinaryCriterion BCrite1 criteri1 & criteri2");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("DefineBinaryCriterion BCrite2 criteriiyuiy1 && criteri2");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("DefineBinaryCriterion BCrite3  criteri1 && criteri2");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("DefineBinaryCriterion BCrite4 qee criteri1 && criteri2");} catch (Exception ignored) {}

    }

    @Test
    public void testDefineNegatedCriterion_ValidParameters() {
        try{tms.validate_executeCommand("DefineBasicCriterion criteri1 duration > 0.1");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("DefineBasicCriterion criteri2 duration < 0.3");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("DefineNegatedCriterion NCriter0 criteri2");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("DefineNegatedCriterion  NCriter1 criteri2");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("DefineNegatedCriterion werw NCriter2 criteri2");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("DefineNegatedCriterion NCriter3 criteri3");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("DefineNegatedCriterion NCriter4 criterteri2");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("DefineNegatedCriterion NCr65iter0 criteri2");} catch (Exception ignored) {}

    }

    @Test
    public void testPrintAllCriteria_ValidParameters() {
        try{tms.validate_executeCommand("DefineBasicCriterion criteri1 duration > 0.1");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("DefineBasicCriterion criteri2 duration < 0.3");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("PrintAllCriteria");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("PrintAllCriteria frf");} catch (Exception ignored) {}
    }

    @Test
    public void testSearch_ValidParameters() {
        try{tms.validate_executeCommand("CreatePrimitiveTask task1 boil-water 0.3 ,");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("CreatePrimitiveTask task2 boil-water 0.4 ,");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("CreatePrimitiveTask task3 boil-water 0.3 ,");} catch (Exception ignored) {}

        try{tms.validate_executeCommand("DefineBasicCriterion cr1 duration > 0.1");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("DefineBasicCriterion cr2 duration < 0.3");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("DefineBasicCriterion cr3 duration <= 0.3");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("DefineBasicCriterion cr4 duration >= 0.3");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("DefineBasicCriterion cr5 duration == 0.3");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("DefineBasicCriterion cr6 duration != 0.3");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("DefineBasicCriterion cr7 name contains \"abc\"");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("DefineBasicCriterion cr8 description contains \"avc\"");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("DefineBasicCriterion cr9 prerequisites contains task10,task11");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("DefineBasicCriterion cr10 prerequisites contains task10,,task11");} catch (Exception ignored) {}


        try{tms.validate_executeCommand("Search cr1");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("Search cr2");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("Search cr3");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("Search cr4");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("Search cr5");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("Search cr6");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("Search cr7");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("Search cr8");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("Search cr9");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("Search cr10");} catch (Exception ignored) {}


        try{tms.validate_executeCommand("Search criteri3");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("Search  criteri4");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("Search ew criteri5");} catch (Exception ignored) {}

        try{tms.validate_executeCommand("DefineBasicCriterion criteri1 duration > 0.1");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("DefineBasicCriterion criteri2 duration > 0.1");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("DefineBinaryCriterion BCrite5 criteri1 && criteri2");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("DefineBinaryCriterion BCrite6 criteri1 || criteri2");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("Search BCrite5");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("Search BCrite6");} catch (Exception ignored) {}

        try{tms.validate_executeCommand("DefineBasicCriterion criteri2 duration < 0.3");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("DefineNegatedCriterion NCriter0 criteri2");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("Search NCriter0");} catch (Exception ignored) {}

    }

    @Test
    public void testStore_ValidParameters() {
        try{tms.validate_executeCommand("CreatePrimitiveTask task1 boil-water 0.3 ,");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("CreatePrimitiveTask task2 boil-water 0.4 ,");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("CreatePrimitiveTask task3 boil-water 0.3 ,");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("CreateCompositeTask composi0 make-coffee task1,task2,task3");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("DefineBasicCriterion criteri1 duration > 0.1");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("Store TestFile1.data");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("Store d:\\TestFile1.data");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("Store d:\\TestFile1.dsghfghd");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("Store d:\\TestFile1.data fsdfs");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("Store  d:\\TestFile1.data");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("Store d:\\TestFile1.data");} catch (Exception ignored) {}

    }

    @Test
    public void testLoad_ValidParameters() {
        try{tms.validate_executeCommand(" Load d:\\TestFile1.data");} catch (Exception ignored) {}
        try{tms.validate_executeCommand(" Load d:\\TestFile1.java");} catch (Exception ignored) {}
        try{tms.validate_executeCommand(" Load d:\\TestFile1.datfsdfa");} catch (Exception ignored) {}
        try{tms.validate_executeCommand(" Load  d:\\TestFile1.data");} catch (Exception ignored) {}
        try{tms.validate_executeCommand(" Load d:\\TestFile1.data fdgdfg");} catch (Exception ignored) {}

    }

    @Test
    public void testUndo_ValidParameters() {
        try{tms.validate_executeCommand("undo");} catch (Exception ignored) {}

        try{tms.validate_executeCommand("CreatePrimitiveTask task1 boil-water 0.3 ,");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("CreatePrimitiveTask task2 boil-water 0.4 ,");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("CreatePrimitiveTask task3 boil-water 0.3 ,");} catch (Exception ignored) {}

        try{tms.validate_executeCommand("undo");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("undo sef");} catch (Exception ignored) {}
    }

    @Test
    public void testRedo_ValidParameters() {
        try{tms.validate_executeCommand("CreatePrimitiveTask task1 boil-water 0.3 ,");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("CreatePrimitiveTask task2 boil-water 0.4 ,");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("CreatePrimitiveTask task3 boil-water 0.3 ,");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("undo");} catch (Exception ignored) {}

        try{tms.validate_executeCommand("redo");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("redo sdfs");} catch (Exception ignored) {}
    }

    @Test
    public void asf(){

    }

    @Test
    public void sadf(){
        try{tms.validate_executeCommand("CreatePrimitiveTask t1 boil-water 0.3 ,");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("CreateCompositeTask t2 boil-water t1");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("CreateCompositeTask t3 boil-water t2");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("CreateCompositeTask t4 boil-water t1");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("ChangeTask t4 subtasks ,");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("ChangeTask t1 prerequisites ,,,t,,,,,,2,,");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("ChangeTask t1 prerequisites ,,,,,,,,,,,");} catch (Exception ignored) {}

        try{tms.validate_executeCommand("ChangeTask t3 subtasks t1,t2");} catch (Exception ignored) {}
        try{tms.validate_executeCommand("ChangeTask t4 subtasks t3");} catch (Exception ignored) {}

        try{tms.validate_executeCommand("DeleteTask t3");} catch (Exception ignored) {}

    }

}


