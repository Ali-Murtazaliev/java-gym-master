package ru.yandex.practicum.gym;

import java.util.*;

public class Timetable {

    private HashMap<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> timetable;

    public Timetable() {
        timetable = new HashMap<>();
    }

    public void addNewTrainingSession(TrainingSession trainingSession) {
        timetable.putIfAbsent(trainingSession.getDayOfWeek(), new TreeMap<>());
        timetable.get(trainingSession.getDayOfWeek()).putIfAbsent(trainingSession.getTimeOfDay(), new ArrayList<>());
        timetable.get(trainingSession.getDayOfWeek()).get(trainingSession.getTimeOfDay()).add(trainingSession);
    }

    public TreeMap<TimeOfDay, List<TrainingSession>> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        //как реализовать, тоже непонятно, но сложность должна быть О(1)

            return timetable.getOrDefault(dayOfWeek, new TreeMap<>());

    }

    public List<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        //как реализовать, тоже непонятно, но сложность должна быть О(1)
        return timetable.getOrDefault(dayOfWeek, new TreeMap<>()).getOrDefault(timeOfDay, new ArrayList<>());
    }

    public List<CounterOfTrainings> getCountByCoaches() {
        Map<Coach, Integer> lessonsCount = new HashMap<>();
        for (TreeMap<TimeOfDay, List<TrainingSession>> day : timetable.values()) {
            for (List<TrainingSession> trainings : day.values()) {
                for (TrainingSession training : trainings) {
                    Coach coach = training.getCoach();
                    lessonsCount.put(coach, lessonsCount.getOrDefault(coach, 0) + 1);
                }
            }
        }
        List<CounterOfTrainings> sum = new ArrayList<>();
        for (Coach coach : lessonsCount.keySet()) {
            sum.add(new CounterOfTrainings(coach, lessonsCount.get(coach)));
        }
        sum.sort((a, b) -> b.getCount() - a.getCount());
        return sum;
    }

}
