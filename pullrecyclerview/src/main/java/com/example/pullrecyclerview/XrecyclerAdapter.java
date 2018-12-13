package com.example.pullrecyclerview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class XrecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private Context mContext;
        private List<LianBean.DataBean> mjihe;
        private int HAS_IMAGE=0;
        int NO_IMAGE=1;
        int ITEM_COUNT=2;

    public XrecyclerAdapter(Context context, List <LianBean.DataBean> mjihe) {
        mContext = context;
        this.mjihe = mjihe;
    }

    /**
     * 刷新
     * @param mjihe
     */
    public void setMjihe(List <LianBean.DataBean> mjihe) {
        this.mjihe = mjihe;
        notifyDataSetChanged();
    }

    /**
     * 加载
     * @param jihe
     */
    public void addAllMjihe(List <LianBean.DataBean> jihe){
        mjihe.addAll(jihe);
        notifyDataSetChanged();
    }

    /**
     * 添加指定条目
     * @param i
     * @param bean
     */
    public void addMjihe(int i,LianBean.DataBean bean){
        mjihe.add(i,bean);
        notifyItemInserted(i);
    }

    /**
     * 删除指定条目
     * @param i
     */
    public void deleteMjihe(int i){
        mjihe.remove(i);
        //!!!!!!!用全局的
        notifyDataSetChanged();

    }

    @Override   //得到条目视图类型
    public int getItemViewType(int position) {
        return position%2==0?HAS_IMAGE:NO_IMAGE;
    }



    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        RecyclerView.ViewHolder holder=null;
        View view=LayoutInflater.from(mContext).inflate(R.layout.item_xrecycler,viewGroup,false);
        holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        //!!!!!!!!!!!
        ViewHolder holder= (ViewHolder) viewHolder;
           if (getItemViewType(i%2)==HAS_IMAGE){
               String images = mjihe.get(i).getImages();

               Glide.with(mContext).load(sub(images)).into(holder.mImageView);
               holder.mTextView.setText(mjihe.get(i).getTitle());
           }else {
               holder.mTextView.setText(mjihe.get(i).getTitle());
           }
           //点击删除
        holder.mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOperation.delete(i);
            }
        });
           //长按添加
        holder.mTextView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mOperation.add(i);
                return true;
            }
        });

    }
    public String sub(String images){
                List<String> listimage=new ArrayList <>();
         //对图片进行切割
        int index = images.indexOf("|");
            if (index>=0){
                String strLeft = images.substring(0, index);
                    listimage.add(strLeft);
                sub(images.substring(index+1,images.length()));
            }else {
                listimage.add(images);
        }
        String s = listimage.get(0);
        return s;
    }


    @Override
    public int getItemCount() {
        return mjihe.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView mImageView;
        TextView mTextView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView=itemView.findViewById(R.id.XRecycler_Image);
            mTextView=itemView.findViewById(R.id.XRecycler_Text);

        }
    }

    /**
     * 接口
     */
    public interface Operation{
        void add(int i);
        void delete(int i);
    }
    Operation mOperation;

    public void setOperation(Operation operation) {
        mOperation = operation;
    }
}
