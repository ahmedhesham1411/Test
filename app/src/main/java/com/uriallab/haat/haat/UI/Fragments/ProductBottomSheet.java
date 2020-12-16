package com.uriallab.haat.haat.UI.Fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.databinding.ObservableField;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;
import com.uriallab.haat.haat.DataModels.CheckModel;
import com.uriallab.haat.haat.DataModels.Country;
import com.uriallab.haat.haat.DataModels.ProductsModel;
import com.uriallab.haat.haat.DataModels.StoreProductsModel;
import com.uriallab.haat.haat.R;
import com.uriallab.haat.haat.UI.Activities.Updates.PhotoViewActivity;
import com.uriallab.haat.haat.UI.Adapters.RecyclerCheckAdapter;
import com.uriallab.haat.haat.Utilities.IntentClass;

import java.util.ArrayList;
import java.util.List;

public class ProductBottomSheet extends BottomSheetDialogFragment {

    private ArrayList<CheckModel> items = new ArrayList<>();
    private RecyclerCheckAdapter recyclerCheckAdapter;

    private Activity activity;

    private double tPrice = 0;

    private int productQuantity = 1;
    LinearLayout sheetLayout;
    private ProductsModel.ResultEntity.ProductsEntity product;
    RelativeLayout addProductClick;
    private FrameLayout deleteQuantity;
    private TextView quantityTxt;
    private TextView priceTxt;
    Double price;
    private List<StoreProductsModel.ProductBean> productMenuModelList;
    String discr;
    private ObservableField<String> totalPrice;
    LinearLayout linearLayout;
    public ProductBottomSheet() {
    }

    @SuppressLint("ValidFragment")
    public ProductBottomSheet(Activity activity, LinearLayout linearLayout, ProductsModel.ResultEntity.ProductsEntity product, FrameLayout deleteQuantity, TextView quantityTxt,
                              TextView priceTxt, List<StoreProductsModel.ProductBean> productMenuModelList,
                              ObservableField<String> totalPrice,Double price,String discr) {
        this.activity = activity;
        this.linearLayout=linearLayout;
        this.product = product;
        this.deleteQuantity = deleteQuantity;
        this.quantityTxt = quantityTxt;
        this.priceTxt = priceTxt;
        this.productMenuModelList = productMenuModelList;
        this.totalPrice = totalPrice;
        this.price = price;
        this.discr=discr;
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void setupDialog(final Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        View contentView = View.inflate(activity, R.layout.product_bottom_sheet, null);
        dialog.setContentView(contentView);
        sheetLayout = dialog.findViewById(R.id.advertiser_products_bottom_sheet);

        dialog.setOnShowListener(dialog1 -> {
            BottomSheetDialog d = (BottomSheetDialog) dialog1;

            FrameLayout bottomSheet = d.findViewById(com.google.android.material.R.id.design_bottom_sheet);
            BottomSheetBehavior.from(bottomSheet).setState(BottomSheetBehavior.STATE_EXPANDED);
        });

        addProductClick = dialog.findViewById(R.id.add_product_click);
        TextView addProduct = dialog.findViewById(R.id.add_product);
        FrameLayout plus = dialog.findViewById(R.id.plus_frame);
        FrameLayout minus = dialog.findViewById(R.id.minus_frame);
        final TextView productName = dialog.findViewById(R.id.product_name);
        final TextView quantity = dialog.findViewById(R.id.quantity);
        final TextView price = dialog.findViewById(R.id.price);
        RoundedImageView productImg = dialog.findViewById(R.id.product_img);
        TextView name = dialog.findViewById(R.id.namebs);
        TextView pricebs = dialog.findViewById(R.id.pricetxtbs);
        TextView dis = dialog.findViewById(R.id.disc_bs);
        RecyclerView recyclerViewCheck = dialog.findViewById(R.id.check_list_recycler);
        CheckModel checkModel = new CheckModel(10.00,activity.getResources().getString(R.string.mange_juice));
        CheckModel checkModel1 = new CheckModel(20.00,activity.getResources().getString(R.string.vegetable_salad));
        CheckModel checkModel2 = new CheckModel(30.00,activity.getResources().getString(R.string.pepsi));
        items.add(checkModel);
        items.add(checkModel1);
        items.add(checkModel2);
        recyclerViewCheck.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(activity.getApplicationContext());
        recyclerViewCheck.setLayoutManager(layoutManager);





        name.setText(product.getProduct_name());
        Double aaa = Double.parseDouble(product.getProduct_price());
        //pricebs.setText(aaa.toString());
        dis.setText(product.getProduct_description());
        pricebs.setText(Double.parseDouble(product.getProduct_price()) + " " + activity.getString(R.string.currency));

        Picasso.get().load(product.getProduct_icon()).into(productImg);

        productName.setText(product.getProduct_name());

        productImg.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("img", product.getProduct_icon());
            IntentClass.goToActivity(activity, PhotoViewActivity.class, bundle);

        });

        tPrice = Double.parseDouble(product.getProduct_price());


        price.setText(Double.parseDouble(product.getProduct_price()) + " " + activity.getString(R.string.currency));







        recyclerCheckAdapter = new RecyclerCheckAdapter(activity.getApplicationContext(),items,price,tPrice,priceTxt,plus,deleteQuantity,linearLayout,addProduct,productMenuModelList,product,productQuantity,quantityTxt,sheetLayout,dialog);
        recyclerViewCheck.setAdapter(recyclerCheckAdapter);

        recyclerCheckAdapter.notifyDataSetChanged();

        plus.setOnClickListener(v -> {
            String quantityTxtq = quantity.getText().toString();

            productQuantity = (Integer.parseInt(quantityTxtq) + 1);

            quantity.setText(productQuantity + "");

            tPrice = (Double.parseDouble(quantity.getText().toString()) * Double.parseDouble(product.getProduct_price()));

            price.setText(tPrice + " " + activity.getString(R.string.currency));
            priceTxt.setText(tPrice + "" );
            recyclerCheckAdapter = new RecyclerCheckAdapter(activity.getApplicationContext(),items,price,tPrice,priceTxt,plus,deleteQuantity,linearLayout,addProduct,productMenuModelList,product,productQuantity,quantityTxt,sheetLayout,dialog);
            recyclerViewCheck.setAdapter(recyclerCheckAdapter);

            recyclerCheckAdapter.notifyDataSetChanged();
        });

        minus.setOnClickListener(v -> {
            String quantityTxtq = quantity.getText().toString();

            if (Integer.parseInt(quantityTxtq) > 1) {
                productQuantity = (Integer.parseInt(quantityTxtq) - 1);

                quantity.setText(productQuantity + "");
                recyclerCheckAdapter = new RecyclerCheckAdapter(activity.getApplicationContext(),items,price,tPrice,priceTxt,plus,deleteQuantity,linearLayout,addProduct,productMenuModelList,product,productQuantity,quantityTxt,sheetLayout,dialog);
                recyclerViewCheck.setAdapter(recyclerCheckAdapter);

                recyclerCheckAdapter.notifyDataSetChanged();
            }

            tPrice = (Double.parseDouble(quantity.getText().toString()) * Double.parseDouble(product.getProduct_price()));

            price.setText(tPrice + " " + activity.getString(R.string.currency));
            priceTxt.setText(tPrice + "" );
        });

        addProductClick.setOnClickListener(v -> {
            try {
                Log.e("list_size", productMenuModelList.size() + " before add");
                productMenuModelList.add(new StoreProductsModel.ProductBean(product.getProduct_name(), tPrice, productQuantity));
                Log.e("list_size", productMenuModelList.size() + " after add");
            } catch (Exception e) {
                Log.e("list_size", "add Exception" + e.getMessage());
                e.printStackTrace();
            }

            product.setSelected(true);

            deleteQuantity.setVisibility(View.VISIBLE);
            quantityTxt.setVisibility(View.VISIBLE);
            quantityTxt.setText(productQuantity + "");
            linearLayout.setBackgroundResource(R.drawable.btnorangeee);


            double ttPrice = 0;
            for (int i = 0; i < productMenuModelList.size(); i++) {
                double itemPrice = productMenuModelList.get(i).getPrice() * productMenuModelList.get(i).getQuantity();
                ttPrice += itemPrice;
            }
            totalPrice.set(ttPrice +" "+ activity.getString(R.string.currency));

            dismiss();
        });


    }
}