package com.example.elena.bowlingsimulation.entities;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Elena on 9/5/2017.
 */

public class Player {
    public int frameCount, shotsCount;
    public List<Integer> points=new ArrayList<>(), scores=new ArrayList<>();

    public void resetData() {
        frameCount = 0;
        shotsCount = 0;
        points.clear();
        scores.clear();
    }
}
