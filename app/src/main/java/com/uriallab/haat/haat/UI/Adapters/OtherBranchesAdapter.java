package com.uriallab.haat.haat.UI.Adapters;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;
import com.uriallab.haat.haat.DataModels.GoogleAddressModel;
import com.uriallab.haat.haat.DataModels.OtherBranchesModel;
import com.uriallab.haat.haat.Interfaces.BranchClick;
import com.uriallab.haat.haat.R;
import com.uriallab.haat.haat.SharedPreferences.ConfigurationFile;
import com.uriallab.haat.haat.Utilities.GlobalVariables;
import com.uriallab.haat.haat.Utilities.Utilities;
import com.uriallab.haat.haat.databinding.ItemOtherBranchesBinding;

import java.lang.reflect.Type;
import java.util.List;

import cz.msebera.android.httpclient.Header;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Created by Mahmoud on 4/19/2020.
 */

public class OtherBranchesAdapter extends RecyclerView.Adapter<OtherBranchesAdapter.StoresViewHolder>{

    Boolean check_show = false;
    private Activity activity;
    private List<OtherBranchesModel.BranchBean> incomingList;
    private RecyclerView recyclerView;

    private BranchClick branchClick;
    private Button go_to_next;
    int row_index;
    String address;
    Double lat,lng;
    private int last_position=0;
    private int present_position=-1;
    RecyclerView.LayoutManager layoutManager;
    ImageView check;


    public OtherBranchesAdapter(Activity activity, List<OtherBranchesModel.BranchBean> incomingList, BranchClick branchClick, Button go_to_next,RecyclerView recyclerView,RecyclerView.LayoutManager layoutManager) {
        this.activity = activity;
        this.incomingList = incomingList;
        this.branchClick = branchClick;
        this.go_to_next = go_to_next;
        this.recyclerView = recyclerView;
    this.layoutManager=layoutManager;
    }

    @Override
    public StoresViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemOtherBranchesBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_other_branches, parent, false);
        return new StoresViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(final StoresViewHolder holder, final int position) {

        holder.binding.storeName.setText(incomingList.get(position).getName());

        holder.binding.storeAddress.setText(incomingList.get(position).getAddress());

//        getAddressFromLatLng(activity, holder.binding.storeAddress, new LatLng(
//                incomingList.get(position).getLat(),
//                incomingList.get(position).getLng()));

        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            double distance = Utilities.getKilometers(GlobalVariables.LOCATION_LAT,
                    GlobalVariables.LOCATION_LNG,
                    incomingList.get(position).getLat(),
                    incomingList.get(position).getLng());

            holder.binding.storeDistance.setText(Utilities.roundPrice(distance) + "");
        }



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                address = holder.binding.storeAddress.getText().toString();
                lat = incomingList.get(position).getLat();
                lng = incomingList.get(position).getLng();

                for (int i=0;i<incomingList.size();i++){
                    if (i!=position){
                        check=layoutManager.findViewByPosition(i).findViewById(R.id.check_img);
                        check.setVisibility(GONE);
                        notifyItemChanged(i);
                    }else {
                        check=layoutManager.findViewByPosition(position).findViewById(R.id.check_img);
                        check.setVisibility(VISIBLE);
                        check_show = true;
                        notifyItemChanged(position);
                    }
                }

                notifyDataSetChanged();
/*                present_position = recyclerView.getChildAdapterPosition(v);
                if(present_position!=RecyclerView.NO_POSITION) {

                    if(present_position!=-1){

                        check=layoutManager.findViewByPosition(last_position).findViewById(R.id.check_img);
                        check.setVisibility(GONE);
                        notifyItemChanged(last_position);
                        check=layoutManager.findViewByPosition(present_position).findViewById(R.id.check_img);
                        check.setVisibility(View.VISIBLE);
                        notifyItemChanged(present_position);
                        System.err.println(last_position+" -- myParent");

                        last_position=present_position;
                        System.err.println(last_position+" -- myParent2");

                    }
                }
                else {
                    check.setVisibility(View.VISIBLE);
                    notifyItemChanged(present_position);
                    System.err.println("Called extra else");}*/

               /* if( last_position!=-1 && present_position==position){
                    holder.binding.checkImg.setVisibility(View.VISIBLE);
                    Log.i("TAG",last_position+"--lastPosition--"+position+"--position--"+present_position+"--presentPosition in if");

                }
                else{
                    holder.binding.checkImg.setVisibility(GONE);
                    Log.i("TAG",last_position+"--lastPosition--"+position+"--position--"+present_position+"--presentPosition in else");

                }*/

                       /* if (check_show == true){
                            address = "";
                            lat = 0.00;
                            lng = 0.00;

                            for (int i=0;i<incomingList.size();i++){
                                if (i!=position){
                                    check=layoutManager.findViewByPosition(i).findViewById(R.id.check_img);
                                    check.setVisibility(GONE);
                                    notifyItemChanged(i);
                                    check=layoutManager.findViewByPosition(position).findViewById(R.id.check_img);
                                    check.setVisibility(VISIBLE);
                                    notifyItemChanged(position);
                                }
                                check_show = false;

                            }
                        }else if (check_show == false){
                            address = holder.binding.storeAddress.getText().toString();
                            lat = incomingList.get(position).getLat();
                            lng = incomingList.get(position).getLng();

                            for (int i=0;i<incomingList.size();i++){
                                if (i!=position){
                                    check=layoutManager.findViewByPosition(i).findViewById(R.id.check_img);
                                    check.setVisibility(GONE);
                                    notifyItemChanged(i);
                                }else {
                                    check=layoutManager.findViewByPosition(position).findViewById(R.id.check_img);
                                    check.setVisibility(VISIBLE);
                                    notifyItemChanged(position);
                                }
                                //check_show = true;
                            }
                            //check_show = true;

                        }*/



            /*    if (check_show == true){
                    address = "";
                    lat = 0.00;
                    lng = 0.00;

                    check_show = false;
                    holder.binding.checkImg.setVisibility(View.GONE);
                    notifyDataSetChanged();
                }else if (check_show == false){
                    address = holder.binding.storeAddress.getText().toString();
                    lat = incomingList.get(position).getLat();
                    lng = incomingList.get(position).getLng();

                    holder.binding.checkImg.setVisibility(View.VISIBLE);
                    check_show = true;
                    notifyDataSetChanged();
                }
*/

/*                row_index = position;
                holder.binding.checkImg.setVisibility(View.VISIBLE);
                check_show = true;

                if (row_index == position){
                    if (check_show == true){
                        check_show = false;
                        holder.binding.checkImg.setVisibility(View.GONE);
                        notifyDataSetChanged();
                    }else if (check_show == false){
                        holder.binding.checkImg.setVisibility(View.VISIBLE);
                        check_show = true;
                        notifyDataSetChanged();
                    }
                }else if (!(row_index == position)){
                    holder.binding.checkImg.setVisibility(View.GONE);
                    notifyDataSetChanged();
                }*/

            }
        });

/*        if (row_index == position){
            holder.binding.checkImg.setVisibility(View.VISIBLE);
            //notifyDataSetChanged();
        }
        else {
            holder.binding.checkImg.setVisibility(View.GONE);
            //notifyDataSetChanged();
        }*/
    /*    go_to_next.setOnClickListener(view -> branchClick.branchClick(address,
                new LatLng(lat,lng)));*/

        go_to_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check_show == true){
                    branchClick.branchClick(address,
                            new LatLng(lat,lng));
                }else if (check_show == false){
                    Toast.makeText(activity, "check", Toast.LENGTH_SHORT).show();
                }
            }
        });
       /* holder.itemView.setOnClickListener(view -> branchClick.branchClick(holder.binding.storeAddress.getText().toString(),
                new LatLng(incomingList.get(position).getLat(),
                        incomingList.get(position).getLng())));*/
    }

    private void getAddressFromLatLng(final Activity activity, final TextView textView, final LatLng latLng) {
        AsyncHttpClient client = new AsyncHttpClient();

        client.get(GlobalVariables.GET_ADDRESSES_FROM_LATLNG_URL + latLng.latitude + "," + latLng.longitude + "&key=AIzaSyAmD_A7N-SI2JbkhGh4xY_OFip7GtQRZfg&language=" + ConfigurationFile.getCurrentLanguage(activity), new TextHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.e("onFailure", responseString + "");

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.e("onSuccess", responseString);
                Type dataType = new TypeToken<GoogleAddressModel>() {
                }.getType();
                GoogleAddressModel data = new Gson().fromJson(responseString, dataType);
                if (data.status.equals("OK")) {
                    textView.setText(data.results.get(0).formatted_address);
                } else {
                    Log.e("address text :: ", data.status);
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return incomingList.size();
    }

    public class StoresViewHolder extends RecyclerView.ViewHolder {

        private ItemOtherBranchesBinding binding;

        private StoresViewHolder(ItemOtherBranchesBinding binding) {

            super(binding.getRoot());
            this.binding = binding;


        }
    }
}