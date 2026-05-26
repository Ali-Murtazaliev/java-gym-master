package ru.yandex.practicum.gym;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

public class TimetableTest {

    @Test
    void testGetTrainingSessionsForDaySingleSession() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        Assertions.assertEquals(1, timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).size());
        Assertions.assertEquals(0,timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY).size());

        //Проверить, что за понедельник вернулось одно занятие
        //Проверить, что за вторник не вернулось занятий
    }

    @Test
    void testGetTrainingSessionsForDayMultipleSessions() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);

        Assertions.assertEquals(1, timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).size());
        Assertions.assertEquals(2, timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY).size());
        Assertions.assertEquals(new TimeOfDay(13, 0),
                timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY).firstKey());
        Assertions.assertEquals(new TimeOfDay(20, 0),
                timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY).lastKey());
        Assertions.assertEquals(0, timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY).size());

        // Проверить, что за понедельник вернулось одно занятие
        // Проверить, что за четверг вернулось два занятия в правильном порядке: сначала в 13:00, потом в 20:00
        // Проверить, что за вторник не вернулось занятий
    }

    @Test
    void testGetTrainingSessionsForDayAndTime() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);
        Assertions.assertEquals(1, timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY,
                new TimeOfDay(13, 0)).size());
        Assertions.assertEquals(0, timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY,
                new TimeOfDay(14, 0)).size());
        //Проверить, что за понедельник в 13:00 вернулось одно занятие
        //Проверить, что за понедельник в 14:00 не вернулось занятий
    }

    @Test
    void testEmptyTimetable() {
        Timetable timetable  = new Timetable();
        Assertions.assertTrue(timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).isEmpty());

        // Проверить случай когда расписание пустое
    }

    @Test
    void testMultipleTrainingSessionsAtSameTime() {
        Timetable timetable = new Timetable();

        Group groupAdultAcrobatic = new Group("Акробатика для взрослых", Age.ADULT, 90);
        Coach coachAcrobatic = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession AcrobaticTrainingSession =  new TrainingSession(groupAdultAcrobatic, coachAcrobatic,
                DayOfWeek.MONDAY, new TimeOfDay(12, 0));
        timetable.addNewTrainingSession(AcrobaticTrainingSession);

        Group groupAdultMMA = new Group("ММА для взрослых", Age.ADULT, 90);
        Coach coachMMA = new Coach("Conor", "Mcgregor", "Сергеевич");
        TrainingSession MmaTrainingSession = new TrainingSession(groupAdultMMA, coachMMA,
                DayOfWeek.MONDAY, new TimeOfDay(12, 0));
        timetable.addNewTrainingSession(MmaTrainingSession);

        Assertions.assertEquals(2, timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY,
                new TimeOfDay(12, 0)).size());


        // Проверить случай когда два занятия в одно и тоже время
    }

    @Test
    void testForBoundaryTime() {
        Timetable timetable = new Timetable();

        Group groupAdultAcrobatic = new Group("Акробатика для взрослых", Age.ADULT, 90);
        Coach coachAcrobatic = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession AcrobaticTrainingSession =  new TrainingSession(groupAdultAcrobatic, coachAcrobatic,
                DayOfWeek.MONDAY, new TimeOfDay(23, 59));
        timetable.addNewTrainingSession(AcrobaticTrainingSession);

        Group groupAdultMMA = new Group("ММА для взрослых", Age.ADULT, 90);
        Coach coachMMA = new Coach("Conor", "Mcgregor", "Сергеевич");
        TrainingSession MmaTrainingSession = new TrainingSession(groupAdultMMA, coachMMA,
                DayOfWeek.MONDAY, new TimeOfDay(0, 0));
        timetable.addNewTrainingSession(MmaTrainingSession);

        Assertions.assertEquals(new TimeOfDay(0, 0),
                timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).firstKey());
        Assertions.assertEquals(new TimeOfDay(23, 59),
                timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).lastKey());
        // Проверить порядок занятий
    }

    @Test
    void testForLessonsCount() {
        Timetable timetable = new Timetable();

        Group groupAdultAcrobatic = new Group("Акробатика для взрослых", Age.ADULT, 90);
        Coach coachAcrobatic = new Coach("Conor", "Mcgregor", "Сергеевич");
        TrainingSession AcrobaticTrainingSession =  new TrainingSession(groupAdultAcrobatic, coachAcrobatic,
                DayOfWeek.MONDAY, new TimeOfDay(23, 59));
        timetable.addNewTrainingSession(AcrobaticTrainingSession);

        Group groupAdultMMA = new Group("ММА для взрослых", Age.ADULT, 90);
        Coach coachMMA = new Coach("Conor", "Mcgregor", "Сергеевич");
        TrainingSession MmaTrainingSession = new TrainingSession(groupAdultMMA, coachMMA,
                DayOfWeek.MONDAY, new TimeOfDay(0, 0));
        timetable.addNewTrainingSession(MmaTrainingSession);
        Assertions.assertEquals(2, timetable.getCountByCoaches().get(0).getCount());
    }

    @Test
    void testSortCoachesByTrainingCount() {
        Timetable timetable = new Timetable();

        Group groupAdultAcrobatic = new Group("Акробатика для взрослых", Age.ADULT, 90);
        Coach coachAcrobatic = new Coach("Conor", "Mcgregor", "Сергеевич");
        TrainingSession AcrobaticTrainingSession =  new TrainingSession(groupAdultAcrobatic, coachAcrobatic,
                DayOfWeek.MONDAY, new TimeOfDay(23, 59));
        timetable.addNewTrainingSession(AcrobaticTrainingSession);

        Group groupAdultMMA = new Group("ММА для взрослых", Age.ADULT, 90);
        TrainingSession MmaTrainingSession = new TrainingSession(groupAdultMMA, coachAcrobatic,
                DayOfWeek.MONDAY, new TimeOfDay(0, 0));
        timetable.addNewTrainingSession(MmaTrainingSession);

        Group groupKidsMMA = new Group("ММА для взрослых", Age.ADULT, 90);
        Coach coachKidsMMA = new Coach("Donald", "Trump", "Сергеевич");
        TrainingSession MmaTrainingSessionForKids = new TrainingSession(groupAdultMMA, coachKidsMMA,
                DayOfWeek.MONDAY, new TimeOfDay(0, 0));
        timetable.addNewTrainingSession(MmaTrainingSessionForKids);

        Assertions.assertEquals(2, timetable.getCountByCoaches().get(0).getCount());
        Assertions.assertEquals(1, timetable.getCountByCoaches().get(1).getCount());
    }

    @Test
    void testForEmptyTimetable() {
        Timetable timetable = new Timetable();
        Assertions.assertTrue(timetable.getCountByCoaches().isEmpty());
    }

}
