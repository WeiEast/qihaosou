package com.qihaosou.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;

import com.qihaosou.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: Created by wenjundu
 * Date:on 2016/2/19
 * Description:
 */
public class SelectPatentCategoryPopup extends PopupWindow {
    private Context context;
    private View mPopView;
    private ListView listView;
    private String[] patentCategorys;
    private int defaultSelected;
    private SelectPatentCategoryListener selectPatentCategoryListener;
    public SelectPatentCategoryPopup(Context context){
        super(context);
        this.context=context;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mPopView=inflater.inflate(R.layout.popup_select_patent_category,(ViewGroup)null);
        setContentView(mPopView);
        setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        // 点击外面的控件也可以使得PopUpWindow dimiss
        this.setOutsideTouchable(true);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);//0xb0000000
        // ColorDrawable dw = new ColorDrawable(0x00000000);
        // 设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);//半透明颜色
        init(mPopView);
    }
    public void setSelectPatentCategoryListener(SelectPatentCategoryListener selectPatentCategoryListener){
        this.selectPatentCategoryListener=selectPatentCategoryListener;
    }
    private void init(View view) {
        listView= (ListView) view.findViewById(R.id.lv_select_patent_categroy);

        patentCategorys=new String[]{"所有类型","实用类型","发明公布"};
        SimpleAdapter adapter = new SimpleAdapter(context, getListViewData(),
                R.layout.item_select_patent_category, new String[] { "text" },
                new int[] { R.id.tv_addsenior_search });
        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);//开启单选模式
        //默认选择第一行
        listView.setItemChecked(defaultSelected, true);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(selectPatentCategoryListener!=null){
                    defaultSelected=position;
                    selectPatentCategoryListener.selected(patentCategorys[position]);
                    dismiss();
                }
            }
        });
    }
    private List<Map<String, Object>> getListViewData() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        for(int i=0;i<patentCategorys.length;i++){

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("text", patentCategorys[i]);
            list.add(map);
        }
        return list;
    }
    public interface SelectPatentCategoryListener{
        void selected(String categoryName);
    }
}
