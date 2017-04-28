package com.chyss.myapplication.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.chyss.myapplication.BaseActivity;
import com.chyss.myapplication.R;
import com.chyss.myapplication.data.response.HotWord;
import com.chyss.myapplication.view.HotButton;

public class SearchActivity extends BaseActivity
{
	public static final String TAG = "SearchActivity";
	private EditText search_edit;
	private ImageButton search_imgbtn;
	private LinearLayout container1, container2, container3;
	
	private List<HotWord> hotWordList = new ArrayList<HotWord>();

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_activity_layout);
		missTitleView();
		initView(); 
	}

	private void initView()
	{
		search_edit = (EditText) findViewById(R.id.search_edit);
		search_imgbtn = (ImageButton) findViewById(R.id.search_imgbtn);
		search_imgbtn.setOnClickListener(onClickListener);

		container1 = (LinearLayout) findViewById(R.id.search_hot_container1);
		container2 = (LinearLayout) findViewById(R.id.search_hot_container2);
		container3 = (LinearLayout) findViewById(R.id.search_hot_container3);

		getHotWord();
		setHotView();
	}

	OnClickListener onClickListener = new OnClickListener()
	{
		@Override
		public void onClick(View v)
		{
			switch (v.getId())
			{
			case R.id.search_imgbtn:
				String serch_text = search_edit.getText().toString();
				if ("".equals(serch_text)) {

					Toast.makeText(SearchActivity.this, "请先输入搜索的关键词",
							Toast.LENGTH_SHORT).show();
					return;
				}
				seachAction(serch_text);
				break;

			default:
				break;
			}
		}
	};

	private void setHotView()
	{
		
		
		
		for (int i = 0; i < hotWordList.size(); i++) {
			
			HotButton hotButton = new HotButton(this, hotWordList.get(i).getHotName(), hotWordList.get(i).getImgUrl(), hotWordList.get(i).getType(), hotClickListener);
			hotButton.setTag(hotWordList.get(i).getHotName());
			
			if (i < 3) {
				container1.addView(hotButton);
			}
			else if (i < 6) {
				container2.addView(hotButton);
			}
			else if (i < 9) {
				container3.addView(hotButton);
			}
		}
	}

	protected void seachAction(String serch_text)
	{
		 
	}

	private void getHotWord()
	{
		for (int i = 0; i < 9; i++) {
			
			if (i == 2 || i == 3 || i == 7) {
				HotWord hotWord = new HotWord();
				hotWord.setHotName("hello"+i);
				hotWord.setImgUrl("http://d.hiphotos.baidu.com/baike/w%3D268%3Bg%3D0/sign=e54aa3403d6d55fbc5c6712055192877/a686c9177f3e67090ae08a613bc79f3df9dc5548.jpg");
				hotWord.setType(2);
				hotWordList.add(hotWord);
			}
			else
			{
				HotWord hotWord = new HotWord();
				hotWord.setHotName("hello"+i);
				hotWord.setImgUrl("");
				hotWord.setType(1);
				hotWordList.add(hotWord);
			}
		}
	}
	
	OnClickListener hotClickListener = new OnClickListener()
	{
		
		@Override
		public void onClick(View v)
		{
			Toast.makeText(SearchActivity.this, v.getTag().toString(), Toast.LENGTH_SHORT).show();
		}
	};
}
