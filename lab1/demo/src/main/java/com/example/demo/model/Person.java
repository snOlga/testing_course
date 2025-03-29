package com.example.demo.model;

public abstract class Person {
    private String name;
    private Mood mood;

    public Person(String name, Mood defaultMood) throws Exception {
        if (name != null && !name.equals("") && defaultMood != null) {
            this.name = name;
            this.mood = defaultMood;
        } else
            throw new Exception();
    }

    public void setMood(Mood mood) {
        this.mood = mood;
    }

    public String getName() {
        return name;
    }

    public Mood getMood() {
        return mood;
    }
}
