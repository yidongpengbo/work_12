package com.example.pullrecyclerview.IPresenter;

import java.util.HashMap;

public interface IPresenter {
    //void startRequest(String path,Class clazz);
    void startRequest(String path,HashMap<String,String> map,Class clazz);
}
