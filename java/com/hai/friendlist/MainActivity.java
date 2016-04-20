package com.hai.friendlist;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.activeandroid.query.Select;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterFriend.AdapterFriendListener {

    private Context context;

    private EditText editName;
    private EditText editPhone;
    private Button btnAdd;

    private AdapterFriend adapter;
    private ArrayList<Friend> list;

    /*
    * index of item need edit
    * if positionEdit = -1 then we not need edit item, so when click btnAdd we insert into database
    * if positionEdit >= 0  then we need eidt item, so when click btnAdd we update item inti database
    * */
    private int positionEdit = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;

        setContentView(R.layout.activity_main);
        connectView();
        reloadData();
    }

    private void connectView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv);
        recyclerView.setHasFixedSize(false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new AdapterFriend(context, list = new ArrayList<>());
        adapter.setListener(this);
        recyclerView.setAdapter(adapter);

        editName = (EditText) findViewById(R.id.editName);
        editPhone = (EditText) findViewById(R.id.editPhone);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add();
            }
        });
    }

    private void add() {
        String name = editName.getText().toString().trim();
        String phone = editPhone.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            editName.requestFocus();
            Toast.makeText(context, R.string.please_enter_name, Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(phone)) {
            editPhone.requestFocus();
            Toast.makeText(context, R.string.please_enter_phone, Toast.LENGTH_SHORT).show();
            return;
        }
        Friend friend;
        // we not need edit
        if (positionEdit < 0) {
            friend = new Friend();
        } else { // we need edit
            friend = list.get(positionEdit);
        }

        friend.setName(name).setPhone(phone).save();
        reloadData();

        editName.setText("");
        editPhone.setText("");
        btnAdd.setText(R.string.add);
        editName.requestFocus();

        Toast.makeText(context, R.string.save_success, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void del(int position) {
        Friend.delete(Friend.class, list.get(position).getId());
        reloadData();
        Toast.makeText(context, R.string.delete_success, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void edit(int position) {
        positionEdit = position;
        editName.setText(list.get(position).getName());
        editPhone.setText(list.get(position).getPhone());
        btnAdd.setText(R.string.update);
        editName.requestFocus();
    }

    private void reloadData() {
        List<Friend> ls = new Select().from(Friend.class).execute();
        list.clear();
        list.addAll(ls);
        Collections.reverse(list);
        adapter.notifyDataSetChanged();
    }
}
