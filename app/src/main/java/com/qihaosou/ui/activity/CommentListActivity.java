package com.qihaosou.ui.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.lzy.okhttputils.OkHttpUtils;
import com.qihaosou.R;
import com.qihaosou.bean.CommentBean;
import com.qihaosou.callback.CommentAllCallBack;
import com.qihaosou.net.UriHelper;
import com.qihaosou.view.CircleImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Created by wenjundu
 * Date:on 2016/3/8
 * Description:评论列表
 */
public class CommentListActivity extends BaseActivity{
    private ListView listView;
    private List<CommentBean> list;
    private CommentAdapter adapter;
    private String uuid="3ce8cec92c834d4c9b912e48b41dab60";
    @Override
    protected void init() {
        listView= (ListView) findViewById(R.id.comment_listview);

    }

    @Override
    protected void addListener() {

    }

    @Override
    protected void addData() {
        setTitle("评论列表");
        list=new ArrayList<CommentBean>();
        adapter=new CommentAdapter(this,list);
        listView.setAdapter(adapter);
        getComments(uuid);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_comment_list;
    }
    private void getComments(String uuid){
        OkHttpUtils.post(UriHelper.getInstance().getComments(uuid)).tag(this).execute(new CommentAllCallBack() {
            @Override
            public void onResponse(List<CommentBean> commentBeans) {
                list.addAll(commentBeans);
                adapter.notifyDataSetChanged();
            }
        });
    }
    class CommentAdapter extends BaseAdapter{
        private LayoutInflater inflater;
        private List<CommentBean> list;
        public CommentAdapter(Context context,List<CommentBean> list){
            this.list=list;
            inflater=LayoutInflater.from(context);
        }
        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder=null;
            if(convertView==null){
                viewHolder=new ViewHolder();
                convertView=inflater.inflate(R.layout.item_comment,null);
                viewHolder.userIV= (CircleImageView) convertView.findViewById(R.id.iv_user_header);
                viewHolder.userNameTV= (TextView) convertView.findViewById(R.id.tv_user_name);
                viewHolder.timeTV= (TextView) convertView.findViewById(R.id.tv_comment_time);
                viewHolder.contentTV= (TextView) convertView.findViewById(R.id.tv_comment_content);
                convertView.setTag(viewHolder);
            }else{
                viewHolder= (ViewHolder) convertView.getTag();
            }
            CommentBean commentBean=list.get(position);
            viewHolder.userNameTV.setText(commentBean.getNickName());
            viewHolder.timeTV.setText(commentBean.getPublishDate());
            viewHolder.contentTV.setText(commentBean.getContent());
            return convertView;
        }
    }
    static class ViewHolder{
        CircleImageView userIV;
        TextView userNameTV,timeTV,contentTV;
    }
}
