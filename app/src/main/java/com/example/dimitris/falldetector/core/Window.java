package com.example.dimitris.falldetector.core;

import java.util.Collections;
import java.util.LinkedList;

public class Window {

    private final int SIZE;

    private LinkedList<Float> samples;

    Window(int size){
        SIZE = size;
        samples = new LinkedList<>();
    }

    public void add(float value){
        if (!isFull()){
            samples.add(value);
            //add value
        } else {
            samples.removeFirst();
            samples.add(value);
        }
    }

    void clear(){
        samples.clear();
    }

    Boolean isFull(){
        return (samples.size() > SIZE);
    }

    Boolean isFallDetected(){
        Float max = Collections.max(samples);
        Float min = Collections.min(samples);
        Float diff = Math.abs(max-min);

        // check if min value detected earlier than max
        Boolean isFall = ( samples.indexOf(max) > samples.indexOf(min) );

        float THRESHOLD = 40f;
        return (diff> THRESHOLD && isFall);
    }
}
