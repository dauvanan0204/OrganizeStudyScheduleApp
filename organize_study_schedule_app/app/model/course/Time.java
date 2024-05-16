package app.model.course;

public class Time {
    private String dayOfWeek;
    private String time;
    private String room;

    public Time() {
    }

    public Time(String dayOfWeek, String time, String room) {
        this.dayOfWeek = dayOfWeek;
        this.time = time;
        this.room = room;
    }

    public String getDayOfWeek() {
        return this.dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getTime() {
        return this.time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRoom() {
        return this.room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public Time dayOfWeek(String dayOfWeek) {
        setDayOfWeek(dayOfWeek);
        return this;
    }

    public Time time(String time) {
        setTime(time);
        return this;
    }

    public Time room(String room) {
        setRoom(room);
        return this;
    }

    @Override
    public String toString() {
        return "{" +
                " dayOfWeek='" + getDayOfWeek() + "'" +
                ", time='" + getTime() + "'" +
                ", room='" + getRoom() + "'" +
                "}";
    }

}
