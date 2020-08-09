package com.uriallab.haat.haat.UI.Adapters;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableInt;
import androidx.databinding.ViewDataBinding;
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

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {

    private static final int CELL_MINE = 1;
    private static final int CELL_HIM = 2;

    private Activity activity;
    private List<ChatModel.ResultEntity.MessagesEntity> incomingList;
    private List<String> incomingListNew;

    private boolean isNew = false;

    private int itemId = 0;

    private ObservableInt mediaStatus = new ObservableInt(0);

    public PlayAudioManager playAudioManager = new PlayAudioManager();

    public ChatAdapter(Activity activity, List<ChatModel.ResultEntity.MessagesEntity> incomingList) {
        this.activity = activity;
        this.incomingList = incomingList;
    }

    public ChatAdapter(Activity activity, List<String> incomingListNew, boolean isNewChat) {
        this.activity = activity;
        this.incomingListNew = incomingListNew;

        isNew = true;
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

        if (isNew) {
            if (position == 0) {
                holder.mineBinding.messageTxt.setText(incomingListNew.get(position));

                holder.mineBinding.frameImg.setVisibility(View.GONE);
                holder.mineBinding.soundLin.setVisibility(View.GONE);
                holder.mineBinding.messageTxt.setVisibility(View.VISIBLE);
            } else {
                holder.mineBinding.frameImg.setVisibility(View.VISIBLE);
                holder.mineBinding.soundLin.setVisibility(View.GONE);
                holder.mineBinding.messageTxt.setVisibility(View.GONE);

                Picasso.get().load(APIModel.BASE_URL + incomingListNew.get(position)).into(holder.mineBinding.imgChat);

                holder.mineBinding.imgChat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putString("img", APIModel.BASE_URL + incomingListNew.get(position));
                        IntentClass.goToActivity(activity, PhotoViewActivity.class, bundle);
                    }
                });
            }

            holder.mineBinding.timeTxt.setText("");
        } else {

            switch (holder.getItemViewType()) {


                case CELL_MINE:

                    for (int i = 0; i < incomingList.size(); i++) {
                        if (itemId == incomingList.get(i).getChatMssgeUID())
                            incomingList.get(i).setPlaying(true);
                        else
                            incomingList.get(i).setPlaying(false);
                    }

                    try {
                        if (incomingList.get(position).getType().equals("1")) {
                            holder.mineBinding.frameImg.setVisibility(View.GONE);
                            holder.mineBinding.soundLin.setVisibility(View.GONE);
                            holder.mineBinding.messageTxt.setVisibility(View.VISIBLE);
                            holder.mineBinding.messageTxt.setText(incomingList.get(position).getMessage());
                        } else if (incomingList.get(position).getType().equals("2")) {
                            holder.mineBinding.frameImg.setVisibility(View.VISIBLE);
                            holder.mineBinding.messageTxt.setVisibility(View.GONE);
                            holder.mineBinding.soundLin.setVisibility(View.GONE);

                            Picasso.get().load(APIModel.BASE_URL + incomingList.get(position).getMessage()).into(holder.mineBinding.imgChat);

                            holder.mineBinding.imgChat.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Bundle bundle = new Bundle();
                                    bundle.putString("img", APIModel.BASE_URL + incomingList.get(position).getMessage());
                                    IntentClass.goToActivity(activity, PhotoViewActivity.class, bundle);
                                }
                            });
                        } else {
                            holder.mineBinding.frameImg.setVisibility(View.GONE);
                            holder.mineBinding.messageTxt.setVisibility(View.GONE);
                            holder.mineBinding.soundLin.setVisibility(View.VISIBLE);

                            holder.mineBinding.voiceImg.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    try {

                                        itemId = incomingList.get(position).getChatMssgeUID();

                                        Log.e("voice_click", incomingList.get(position).getMessage()+" "+mediaStatus.get()+" "+incomingList.get(position).isPlaying());

                                        if (incomingList.get(position).isPlaying()) {
                                            if (mediaStatus.get() == 0) {
                                                playAudioManager.killMediaPlayer();
                                                playAudioManager.playAudio(activity, APIModel.BASE_URL + incomingList.get(position).getMessage(), holder.mineBinding.seekbarVoiceLib, holder.mineBinding.voiceImg, mediaStatus);
                                            } else if (mediaStatus.get() == 1) {
                                                playAudioManager.pauseMediaPlayer();
                                            } else {
                                                playAudioManager.resumeMediaPlayer(activity, holder.mineBinding.seekbarVoiceLib.getProgress());
                                            }
                                        } else {
                                            mediaStatus.set(0);
                                            playAudioManager.killMediaPlayer();
                                            playAudioManager.playAudio(activity, APIModel.BASE_URL + incomingList.get(position).getMessage(), holder.mineBinding.seekbarVoiceLib, holder.mineBinding.voiceImg, mediaStatus);
                                        }
                                        notifyDataSetChanged();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            });

                            holder.mineBinding.seekbarVoiceLib.setOnSeekChangeListener(new OnSeekChangeListener() {
                                @Override
                                public void onSeeking(SeekParams seekParams) {
                                    Log.e("seeking", seekParams.progress + " pos " + position);
                                }

                                @Override
                                public void onStartTrackingTouch(IndicatorSeekBar seekBar) {
                                    playAudioManager.pauseMediaPlayer();
                                }

                                @Override
                                public void onStopTrackingTouch(IndicatorSeekBar seekBar) {
                                    playAudioManager.resumeMediaPlayer(activity, seekBar.getProgress());
                                }
                            });
                        }
                        holder.mineBinding.timeTxt.setText(incomingList.get(position).getMssge_Time().substring(0, 5));

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    break;
                case CELL_HIM:

                    for (int i = 0; i < incomingList.size(); i++) {
                        if (itemId == incomingList.get(i).getChatMssgeUID())
                            incomingList.get(i).setPlaying(true);
                        else
                            incomingList.get(i).setPlaying(false);
                    }

                    try {
                        if (incomingList.get(position).getType().equals("1")) {
                            holder.himBinding.frameImg.setVisibility(View.GONE);
                            holder.himBinding.soundLin.setVisibility(View.GONE);
                            holder.himBinding.messageTxt.setVisibility(View.VISIBLE);
                            holder.himBinding.messageTxt.setText(incomingList.get(position).getMessage());
                        } else if (incomingList.get(position).getType().equals("2")) {
                            holder.himBinding.frameImg.setVisibility(View.VISIBLE);
                            holder.himBinding.messageTxt.setVisibility(View.GONE);
                            holder.himBinding.soundLin.setVisibility(View.GONE);

                            Picasso.get().load(APIModel.BASE_URL + incomingList.get(position).getMessage()).into(holder.himBinding.imgChat);

                            holder.himBinding.imgChat.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Bundle bundle = new Bundle();
                                    bundle.putString("img", APIModel.BASE_URL + incomingList.get(position).getMessage());
                                    IntentClass.goToActivity(activity, PhotoViewActivity.class, bundle);
                                }
                            });
                        } else {
                            holder.himBinding.messageTxt.setVisibility(View.GONE);
                            holder.himBinding.frameImg.setVisibility(View.GONE);
                            holder.himBinding.soundLin.setVisibility(View.VISIBLE);

                            holder.himBinding.voiceImg.setOnClickListener(v -> {
                                try {
                                    itemId = incomingList.get(position).getChatMssgeUID();

                                    Log.e("voice_click", incomingList.get(position).getMessage()+" "+mediaStatus.get()+" "+incomingList.get(position).isPlaying());

                                    if (incomingList.get(position).isPlaying()) {
                                        if (mediaStatus.get() == 0) {
                                            playAudioManager.killMediaPlayer();
                                            playAudioManager.playAudio(activity, APIModel.BASE_URL + incomingList.get(position).getMessage(), holder.himBinding.seekbarVoiceLib, holder.himBinding.voiceImg, mediaStatus);
                                        } else if (mediaStatus.get() == 1) {
                                            playAudioManager.pauseMediaPlayer();
                                        } else {
                                            playAudioManager.resumeMediaPlayer(activity, holder.himBinding.seekbarVoiceLib.getProgress());
                                        }
                                    } else {
                                        mediaStatus.set(0);
                                        playAudioManager.killMediaPlayer();
                                        playAudioManager.playAudio(activity, APIModel.BASE_URL + incomingList.get(position).getMessage(), holder.himBinding.seekbarVoiceLib, holder.himBinding.voiceImg, mediaStatus);
                                    }

                                    notifyDataSetChanged();

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            });

                            holder.himBinding.seekbarVoiceLib.setOnSeekChangeListener(new OnSeekChangeListener() {
                                @Override
                                public void onSeeking(SeekParams seekParams) {
                                    Log.e("seeking", seekParams.progress + " pos " + position);
                                }

                                @Override
                                public void onStartTrackingTouch(IndicatorSeekBar seekBar) {
                                    playAudioManager.pauseMediaPlayer();
                                }

                                @Override
                                public void onStopTrackingTouch(IndicatorSeekBar seekBar) {
                                    playAudioManager.resumeMediaPlayer(activity, seekBar.getProgress());
                                }
                            });
                        }
                        holder.himBinding.timeTxt.setText(incomingList.get(position).getMssge_Time().substring(0, 5));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
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
        if (isNew)
            return incomingListNew.size();
        else
            return incomingList.size();
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
}