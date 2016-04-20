package com.hai.friendlist;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class AdapterFriend extends RecyclerView.Adapter<AdapterFriend.RecyclerViewHolder> {
    private List<Friend> list;
    private Context context;

    public AdapterFriend(Context context, List<Friend> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.item_friend, viewGroup, false);
        return new RecyclerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder viewHolder, int position) {
        Friend friend = list.get(position);
        viewHolder.tvName.setText(friend.getName());
        viewHolder.tvPhone.setText(friend.getPhone());
    }


    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        public TextView tvName;
        public TextView tvPhone;

        // for show menu edit and delete
        private PopupMenu popupMenu;

        public RecyclerViewHolder(View itemView) {
            super(itemView);

            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvPhone = (TextView) itemView.findViewById(R.id.tvPhone);
            itemView.findViewById(R.id.btnMore).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popupMenu.show();
                }
            });
            setPopup(itemView);
        }

        /*
        * when show popup menu, we can click edit or delete menu item.
        * so we need create a interface to sent position for main activity process
        * */
        private void setPopup(View view) {
            popupMenu = new PopupMenu(context, view);
            popupMenu.getMenuInflater().inflate(R.menu.menu_item_friend, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.menu_edit:
                            if (listener != null) {
                                listener.edit(getAdapterPosition());
                            }
                            break;
                        case R.id.menu_delete:
                            if (listener != null) {
                                listener.del(getAdapterPosition());
                            }
                            break;
                    }
                    return true;
                }
            });
        }
    }

    public interface AdapterFriendListener {
        void del(int position);

        void edit(int position);
    }

    private AdapterFriendListener listener;

    public AdapterFriend setListener(AdapterFriendListener listener) {
        this.listener = listener;
        return this;
    }
}
