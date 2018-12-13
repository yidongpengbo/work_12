package com.example.pullrecyclerview.IPresenter;

import com.example.pullrecyclerview.Bean;
import com.example.pullrecyclerview.IMode.IModelImp;
import com.example.pullrecyclerview.IView.IView;
import com.example.pullrecyclerview.LianBean;
import com.example.pullrecyclerview.Until.MainCallBack;

import java.util.HashMap;

public class IPresenterImp implements IPresenter {
    IView mIView;
    IModelImp mIModelImp;

    public IPresenterImp(IView IView) {
        mIView = IView;
        mIModelImp=new IModelImp();
    }

    @Override
    public void startRequest(String path, HashMap<String,String> map, Class clazz) {
        mIModelImp.requestData(path, map,clazz, new MainCallBack() {
            @Override
            public void getData(Object o) {
                if (o instanceof LianBean){
                    LianBean bean=(LianBean) o;
                    mIView.setData(bean);
                }
            }
        });
    }
}
