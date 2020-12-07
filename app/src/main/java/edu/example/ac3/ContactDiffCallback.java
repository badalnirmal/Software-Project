package edu.example.ac3;

import androidx.recyclerview.widget.DiffUtil;

import java.util.ArrayList;

public class ContactDiffCallback extends DiffUtil.Callback {
    private ArrayList<Bank1> mOldList;
    private ArrayList<Bank1> mNewList;

    public ContactDiffCallback(ArrayList<Bank1> oldList, ArrayList<Bank1> newList) {
        this.mOldList = oldList;
        this.mNewList = newList;
    }
    @Override
    public int getOldListSize() {
        return mOldList.size();
    }

    @Override
    public int getNewListSize() {
        return mNewList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        // add a unique ID property on Contact and expose a getId() method
        return mOldList.get(oldItemPosition).getSer_charge() == mNewList.get(newItemPosition).getSer_charge();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        Bank1 oldContact = mOldList.get(oldItemPosition);
        Bank1 newContact = mNewList.get(newItemPosition);

        if (oldContact.getB_name() == newContact.getB_name() && oldContact.getDistance() == newContact.getDistance() && oldContact.getSer_charge() == newContact.getSer_charge()){
            return true;
        }
        return false;
    }
}
