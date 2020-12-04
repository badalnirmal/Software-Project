package edu.example.ac3;

import java.io.Serializable;

public abstract class Bank {
    String curr_from_s;
    String curr_to_s;

    public void setCurr_from_s(String curr_from) {
        this.curr_from_s = curr_from;
    }

    public void setCurr_to_s(String curr_to) {
        this.curr_to_s = curr_to;
    }

    public String getCurr_from_s()
    {
        return curr_from_s;
    }
    public String getCurr_to_s()
    {
        return curr_to_s;
    }
}

