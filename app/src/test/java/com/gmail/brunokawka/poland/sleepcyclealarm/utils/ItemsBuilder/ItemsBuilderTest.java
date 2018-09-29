package com.gmail.brunokawka.poland.sleepcyclealarm.utils.ItemsBuilder;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ItemsBuilderTest {

    private ItemsBuilder itemsBuilder;

    private static DateTime getDateTime(String string) {
        return DateTimeFormat.forPattern("dd/MM/yyyy HH:mm").parseDateTime(string);
    }

    @Before
    public void setUp() {
        itemsBuilder = new ItemsBuilder();
    }

    @Test
    public void testIfCanReturnNextAlarmDate_For_SleepNowStrategy() {
        itemsBuilder.setBuildingStrategy(new SleepNowBuildingStrategy());

        assertEquals(getDateTime("05/02/2011 02:30"), itemsBuilder.getNextAlarmDate(getDateTime("05/02/2011 01:00")));
        assertEquals(getDateTime("06/02/2011 01:25"), itemsBuilder.getNextAlarmDate(getDateTime("05/02/2011 23:55")));
        assertEquals(getDateTime("05/02/2011 00:00"), itemsBuilder.getNextAlarmDate(getDateTime("04/02/2011 22:30")));
    }

    @Test
    public void testIfCanReturnNextAlarmDate_For_WakeUpAtStrategy() {
        itemsBuilder.setBuildingStrategy(new WakeUpAtBuildingStrategy());

        assertEquals(getDateTime("04/02/2011 23:30"), itemsBuilder.getNextAlarmDate(getDateTime("05/02/2011 01:00")));
        assertEquals(getDateTime("05/02/2011 13:30"), itemsBuilder.getNextAlarmDate(getDateTime("05/02/2011 15:00")));
        assertEquals(getDateTime("04/02/2011 22:32"), itemsBuilder.getNextAlarmDate(getDateTime("05/02/2011 00:02")));
    }

    @Test
    public void testIfIsPossibleToCreateNextItem_For_SleepNowStrategy() {
        itemsBuilder.setBuildingStrategy(new SleepNowBuildingStrategy());
        assertTrue(itemsBuilder.isPossibleToCreateNextItem(getDateTime("05/02/2011 22:32"), getDateTime("05/02/2011 00:02")));
    }

    @Test
    public void testIfIsPossibleToCreateNextItem_For_WakeUpAtBuildingStrategy() {
        itemsBuilder.setBuildingStrategy(new WakeUpAtBuildingStrategy());
        assertTrue(itemsBuilder.isPossibleToCreateNextItem(getDateTime("04/02/2011 18:30"), getDateTime("04/02/2011 23:00")));
        assertTrue(itemsBuilder.isPossibleToCreateNextItem(getDateTime("04/02/2011 20:30"), getDateTime("05/02/2011 20:20")));
        assertTrue(itemsBuilder.isPossibleToCreateNextItem(getDateTime("04/02/2011 00:30"), getDateTime("04/02/2011 02:30")));
        assertTrue(itemsBuilder.isPossibleToCreateNextItem(getDateTime("04/02/2011 11:54"), getDateTime("04/02/2011 14:54")));
    }

    @Test
    public void testIfItsNotPossibleToCreateNextItem_For_WakeUpAtBuildingStrategy() {
        itemsBuilder.setBuildingStrategy(new WakeUpAtBuildingStrategy());
        assertFalse(itemsBuilder.isPossibleToCreateNextItem(getDateTime("04/02/2011 20:30"), getDateTime("04/02/2011 20:20")));
        assertFalse(itemsBuilder.isPossibleToCreateNextItem(getDateTime("04/02/2011 23:53"), getDateTime("05/02/2011 00:53")));
    }
}