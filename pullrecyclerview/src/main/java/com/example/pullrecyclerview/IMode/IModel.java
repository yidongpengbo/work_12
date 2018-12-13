package com.example.pullrecyclerview.IMode;

import com.example.pullrecyclerview.Until.MainCallBack;

import java.util.HashMap;

public interface IModel {
   // void requestData(String path, Class clazz, MainCallBack mainCallBack);
    void requestData(String path, HashMap<String,String> map, Class clazz, MainCallBack mainCallBack);
}
