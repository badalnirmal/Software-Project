package edu.example.ac3;

import java.io.Serializable;

public abstract class Bank {
    String curr_from_s;
    String curr_to_s;

    public void setCurr_from(String curr_from) {
        this.curr_from_s = curr_from;
    }

    public void setCurr_to(String curr_to) {
        this.curr_to_s = curr_to;
    }
}

