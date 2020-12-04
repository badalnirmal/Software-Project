package edu.example.ac3;

import java.io.Serializable;

public class Bank1 extends Bank implements Serializable {
        private
    String b_name;
    double ser_charge;
    String distance;
    double curr_from;
    double curr_to;

        public String getB_name() {
                return b_name;
        }

        public double getSer_charge() {
                return ser_charge;
        }

        public String getDistance() {
                return distance;
        }

        public double getCurr_from() {
                return curr_from;
        }

        public double getCurr_to() {
                return curr_to;
        }

        public void setB_name(String b_name) {
                this.b_name = b_name;
        }

        public void setDistance(String distance) {
                this.distance = distance;
        }

        public void setCurr_from(double curr_from) {
                this.curr_from = curr_from;
        }

        public void setCurr_to(double curr_to) {
                this.curr_to = curr_to;
        }
        public void setSer_charge(double ser_charge) {
                this.ser_charge = ser_charge;
        }

}
