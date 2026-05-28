package ru.yandex.practicum.gym;

public class CounterOfTrainings {
    private Coach coach;
    private int count;

    public Coach getCoach() {
        return coach;
    }

    public void setCoach(Coach coach) {
        this.coach = coach;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public CounterOfTrainings(Coach coach, int count) {
        this.coach = coach;
        this.count = count;
    }
}
