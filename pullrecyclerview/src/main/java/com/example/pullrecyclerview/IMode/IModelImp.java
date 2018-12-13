package com.example.pullrecyclerview.IMode;

import com.example.pullrecyclerview.Bean;
import com.example.pullrecyclerview.LianBean;
import com.example.pullrecyclerview.Until.MainCallBack;
import com.example.pullrecyclerview.Until.MyCallBack;
import com.example.pullrecyclerview.Until.OKHttpUntil;

import java.util.HashMap;

public class IModelImp implements IModel {
    MainCallBack mMainCallBack;
    @Override
    public void requestData(String path,HashMap<String,String> map, Class clazz, MainCallBack mainCallBack) {
        this.mMainCallBack=mainCallBack;
       /* OKHttpUntil.getInstance().getEnquene(path, clazz, new MyCallBack() {
            @Override
            public void fail(Exception e) {

            }

            @Override
            public void success(Object o) {
                if (o instanceof Bean){
                    Bean bean=(Bean)o;
                    mMainCallBack.getData(bean);
                }
            }
        });*/
       OKHttpUntil.getInstance().postEnquene(path,map, clazz, new MyCallBack() {
           @Override
           public void fail(Exception e) {

           }

           @Override
           public void success(Object o) {
               if (o instanceof LianBean){
                   LianBean bean=(LianBean)o;
                   mMainCallBack.getData(bean);
               }
           }
       });
    }
}
