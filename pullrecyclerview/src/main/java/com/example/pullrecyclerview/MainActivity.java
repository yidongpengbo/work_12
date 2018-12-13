package com.example.pullrecyclerview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.util.Log;
import android.widget.Toast;

import com.example.pullrecyclerview.IPresenter.IPresenterImp;
import com.example.pullrecyclerview.IView.IView;
import com.example.pullrecyclerview.Until.Apis;
import com.example.pullrecyclerview.Until.MainCallBack;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements IView {

    private XRecyclerView mXRecycler;
    XrecyclerAdapter mAdapter;
    List<LianBean.DataBean> mjihe;
    IPresenterImp mIPresenterImp;
    int mPage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //获取资源ID
        initView();
        initData();

    }

    private void initData() {
        //管理布局结构
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(OrientationHelper.VERTICAL);
        mXRecycler.setLayoutManager(manager);
        //设置刷新加载
        mXRecycler.setPullRefreshEnabled(true);
        mXRecycler.setLoadingMoreEnabled(true);
        HashMap <String, String> map = new HashMap <>();
        mPage=1;
        map.clear();
        map.put("pscid","39");
        map.put("page",mPage+"");
        mIPresenterImp.startRequest(Apis.NET_LIAN1, map,LianBean.class);


    }

    private void initView() {
        mXRecycler = (XRecyclerView) findViewById(R.id.XRecycler);
        mjihe=new ArrayList <>();
        mAdapter=new XrecyclerAdapter(MainActivity.this,mjihe);
        //适配器
        mXRecycler.setAdapter(mAdapter);
        mIPresenterImp=new IPresenterImp(this);
    }

    @Override
    public void setData(Object o) {

            LianBean bean=(LianBean)o;
        final List <LianBean.DataBean> data = bean.getData();
        mXRecycler.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override       //刷新
            public void onRefresh() {

                  mPage=1;
                  mAdapter.setMjihe(data);
                mXRecycler.refreshComplete();
            }

            @Override       //加载
            public void onLoadMore() {
                 mAdapter.addAllMjihe(data);
                mPage++;
                mXRecycler.loadMoreComplete();
            }


        });
        mAdapter.setMjihe(data);





        //添加、删除指定条目
        mAdapter.setOperation(new XrecyclerAdapter.Operation() {
            @Override   //添加
            public void add(int i) {
                    mAdapter.addMjihe(i, data.get(i));
            }

            @Override   //删除
            public void delete(int i) {
                mAdapter.deleteMjihe(i);

            }
        });
    }
}
