package com.exercise.AndroidSQLite;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class AndroidSQLite extends Activity {

	EditText inputContent1, inputContent2;
	Button buttonAdd, buttonDeleteAll;

	private SQLiteAdapter mySQLiteAdapter;
	ListView listContent;

	SimpleCursorAdapter cursorAdapter;
	Cursor cursor;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		inputContent1 = (EditText) findViewById(R.id.content1);
		inputContent2 = (EditText) findViewById(R.id.content2);
		buttonAdd = (Button) findViewById(R.id.add);
		buttonDeleteAll = (Button) findViewById(R.id.deleteall);

		listContent = (ListView) findViewById(R.id.contentlist);

		mySQLiteAdapter = new SQLiteAdapter(this);
		mySQLiteAdapter.openToWrite();

		cursor = mySQLiteAdapter.queueAll();
		String[] from = new String[] { SQLiteAdapter.KEY_ID,
				SQLiteAdapter.KEY_CONTENT1, SQLiteAdapter.KEY_CONTENT2 };
		int[] to = new int[] { R.id.id, R.id.text1, R.id.text2 };
		cursorAdapter = new SimpleCursorAdapter(this, R.layout.row, cursor,
				from, to);
		listContent.setAdapter(cursorAdapter);

		buttonAdd.setOnClickListener(buttonAddOnClickListener);
		buttonDeleteAll.setOnClickListener(buttonDeleteAllOnClickListener);

	}

	Button.OnClickListener buttonAddOnClickListener = new Button.OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			String data1 = inputContent1.getText().toString();
			String data2 = inputContent2.getText().toString();
			mySQLiteAdapter.insert(data1, data2);
			updateList();
		}

	};

	Button.OnClickListener buttonDeleteAllOnClickListener = new Button.OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			mySQLiteAdapter.deleteAll();
			updateList();
		}

	};

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mySQLiteAdapter.close();
	}

	private void updateList() {
		cursor.requery();
	}

}