package com.example.androidapp_exam2019.dataAccess;

import com.example.androidapp_exam2019.model.Dress;

import java.util.ArrayList;

public interface DressDataAccess {
    ArrayList<Dress> getAllDresses();

    Dress getDress(String id);
}
