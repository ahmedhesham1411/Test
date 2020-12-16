package com.uriallab.haat.haat.UI.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableInt;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.uriallab.haat.haat.API.APIModel;
import com.uriallab.haat.haat.DataModels.ChatModel;
import com.uriallab.haat.haat.R;
import com.uriallab.haat.haat.SharedPreferences.LoginSession;
import com.uriallab.haat.haat.UI.Activities.Updates.PhotoViewActivity;
import com.uriallab.haat.haat.Utilities.IntentClass;
import com.uriallab.haat.haat.Utilities.PlayAudioManager;
import com.uriallab.haat.haat.databinding.ItemChatHimBinding;
import com.uriallab.haat.haat.databinding.ItemChatMineBinding;
import com.warkiz.widget.IndicatorSeekBar;
import com.warkiz.widget.OnSeekChangeListener;
import com.warkiz.widget.SeekParams;

import java.util.List;

/**
 * Created by Mahmoud on 4/19/2020.
 */

public class ChatAdapter2 extends RecyclerView.Adapter<ChatAdapter2.ChatViewHolder> {

    private static final int CELL_MINE = 1;
    private static final int CELL_HIM = 2;

    private Activity activity;
    private List<ChatModel.ResultEntity.MessagesEntity> incomingList;
    private List<String> incomingListNew;
    String aaa;

    private boolean isNew = false;

    private int itemId = 0;

    private ObservableInt mediaStatus = new ObservableInt(0);
    RecyclerView recyclerView;
    public PlayAudioManager playAudioManager = new PlayAudioManager();

    public ChatAdapter2(Activity activity, List<ChatModel.ResultEntity.MessagesEntity> incomingList,RecyclerView recyclerView) {
        this.activity = activity;
        this.incomingList = incomingList;
        this.recyclerView=recyclerView;
    }

    public ChatAdapter2(Activity activity, List<String> incomingListNew, boolean isNewChat,RecyclerView recyclerView) {
        this.activity = activity;
        this.incomingListNew = incomingListNew;
        isNew = true;
        this.recyclerView=recyclerView;
    }

    @Override
    public ChatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ViewDataBinding binding;
        switch (viewType) {
            case CELL_MINE:
                binding = DataBindingUtil.inflate(inflater, R.layout.item_chat_mine, parent, false);
                return new ChatViewHolder((ItemChatMineBinding) binding);
            case CELL_HIM:
                binding = DataBindingUtil.inflate(inflater, R.layout.item_chat_him, parent, false);
                return new ChatViewHolder((ItemChatHimBinding) binding);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final ChatViewHolder holder, final int position) {

         aaa = GetImage(activity);

        if (isNew) {

            if (position == 0) {
                holder.mineBinding.messageTxt.setText(incomingListNew.get(position));
                holder.mineBinding.frameImg.setVisibility(View.GONE);
                holder.mineBinding.soundLin.setVisibility(View.GONE);
                holder.mineBinding.messageTxt.setVisibility(View.VISIBLE);
                holder.mineBinding.timeTxt.setVisibility(View.VISIBLE);
                holder.mineBinding.timeTxt2.setVisibility(View.GONE);
            }else {
                holder.mineBinding.loaddd.setVisibility(View.GONE);
                HoriImgsAdapter horiImgsAdapter = new HoriImgsAdapter(incomingListNew,activity);
                holder.mineBinding.imgsRec.setVisibility(View.VISIBLE);
                holder.mineBinding.messageTxt.setText(incomingListNew.get(position));
                holder.mineBinding.frameImg.setVisibility(View.VISIBLE);
                holder.mineBinding.timeTxt2.setVisibility(View.GONE);
                holder.mineBinding.soundLin.setVisibility(View.GONE);
                holder.mineBinding.messageTxt.setVisibility(View.GONE);
                holder.mineBinding.timeTxt.setVisibility(View.GONE);
                LinearLayoutManager layoutManager
                        = new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false);
                holder.mineBinding.imgsRec.setLayoutManager(layoutManager);
                holder.mineBinding.imgsRec.setAdapter(horiImgsAdapter);
            }

/*            if (position == 0) {
                holder.mineBinding.messageTxt.setText(incomingListNew.get(position));
                holder.mineBinding.frameImg.setVisibility(View.GONE);
                holder.mineBinding.soundLin.setVisibility(View.GONE);
                holder.mineBinding.messageTxt.setVisibility(View.VISIBLE);
                holder.mineBinding.timeTxt.setVisibility(View.VISIBLE);

            } else {
                holder.mineBinding.frameImg.setVisibility(View.VISIBLE);
                holder.mineBinding.soundLin.setVisibility(View.GONE);
                holder.mineBinding.messageTxt.setVisibility(View.GONE);
                //holder.mineBinding.timeTxt.setVisibility(View.VISIBLE);
                holder.mineBinding.imgChat.setVisibility(View.VISIBLE);

                    Picasso.get().load(APIModel.BASE_URL + incomingListNew.get(position)).into(holder.mineBinding.imgChat);
                    holder.mineBinding.imgChat.setOnClickListener(v -> {
                        Bundle bundle = new Bundle();
                        bundle.putString("img", APIModel.BASE_URL + incomingListNew.get(position));
                        IntentClass.goToActivity(activity, PhotoViewActivity.class, bundle);
                    });

            }
            //holder.himBinding.timeTxt.setText(incomingList.get(position).getMssge_Time().substring(0, 5));
            holder.mineBinding.timeTxt.setText("");*/
        }
    }

    @Override
    public int getItemViewType(int position) {

        if (isNew)
            return CELL_MINE;
        else {
            if (incomingList.get(position).getSender_ID() == LoginSession.getUserData(activity).getResult().getUserData().getUserUID()) {
                Log.e("CELL_TYPE_ORDER", CELL_MINE + "");
                return CELL_MINE;
            } else {
                Log.e("CELL_TYPE_ORDER", CELL_HIM + "");
                return CELL_HIM;
            }
        }
    }

    @Override
    public int getItemCount() {
        return 2;
       /* if (isNew)
            return incomingListNew.size();
        else
            return incomingList.size();*/
    }

    public class ChatViewHolder extends RecyclerView.ViewHolder {

        private ItemChatMineBinding mineBinding;
        private ItemChatHimBinding himBinding;

        private ChatViewHolder(ItemChatMineBinding mineBinding) {
            super(mineBinding.getRoot());
            this.mineBinding = mineBinding;
        }

        private ChatViewHolder(ItemChatHimBinding himBinding) {
            super(himBinding.getRoot());
            this.himBinding = himBinding;
        }
    }

    public static String GetImage(Context context){
        SharedPreferences pref = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        return pref.getString("imageurl","default Code");
    }

}