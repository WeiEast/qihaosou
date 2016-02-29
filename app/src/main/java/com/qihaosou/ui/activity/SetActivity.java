package com.qihaosou.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.qihaosou.R;
import com.qihaosou.util.L;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author:wenjundu
 * Email: 179451678@qq.com
 * Date:  2016/1/19
 * Description:设置
 */
public class SetActivity extends BaseActivity {
    private ListView setMenuListView;
    private String[] menuTexts;
    @Override
    protected void init() {
        setTitle(getString(R.string.set));
        setMenuListView= (ListView) findViewById(R.id.set_listview);
    }

    @Override
    protected void addListener() {

        setMenuListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=null;
                switch (position){
                    case 0:
                        intent=new Intent(SetActivity.this,VerifyPhoneActivity.class);
                        break;
                    case 1:
                        intent=new Intent(SetActivity.this,UserEmialActivity.class);
                        break;
                    case 2:
                        break;
                }
                if(intent!=null)
                    startActivity(intent);
            }
        });
    }

    @Override
    protected void addData() {
        fullMenuListView();
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_set;
    }

    private void fullMenuListView(){
        menuTexts=getResources().getStringArray(R.array.set_menu_list);
        List<Map<String,String>> data=new ArrayList<Map<String,String>>();
        for(String s: menuTexts){
            Map<String,String> map=new HashMap<String,String>();
            map.put("menuText",s);
            data.add(map);
        }
        SimpleAdapter simpleAdapter=new SimpleAdapter(this,data,R.layout.item_set_menu,new String[]{"menuText"},new int[]{R.id.item_set_menu_text});
        setMenuListView.setAdapter(simpleAdapter);
    }
}
