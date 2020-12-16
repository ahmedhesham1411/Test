package com.uriallab.haat.haat.UI.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.uriallab.haat.haat.DataModels.CheckModel;
import com.uriallab.haat.haat.DataModels.ProductsModel;
import com.uriallab.haat.haat.DataModels.StoreProductsModel;
import com.uriallab.haat.haat.R;
import com.uriallab.haat.haat.UI.Fragments.ProductBottomSheet;

import java.util.List;

/**
 * Created by luis.ferreira on 07/07/2016.
 */
public class RecyclerCheckAdapter extends RecyclerView.Adapter<RecyclerCheckAdapter.ViewHolder> {
    private List<StoreProductsModel.ProductBean> productMenuModelList;
    private ProductsModel.ResultEntity.ProductsEntity product;

    private List<CheckModel> items;
    private Context context;
    TextView textView,priceTxtOriginal,addProduct,quantityTxt;
    Double tPrice,finalPrice;
    FrameLayout plus,delete;
    LinearLayout linearLayout,sheetLayout;
    int productQuantity;
    Dialog dialog;

    public RecyclerCheckAdapter(Context context, List<CheckModel> listas, TextView textView, Double tPrice, TextView priceTxtOriginal, FrameLayout plus, FrameLayout delete, LinearLayout linearLayout,TextView addProduct,List<StoreProductsModel.ProductBean> productMenuModelList
    ,ProductsModel.ResultEntity.ProductsEntity product,int productQuantity,TextView quantityTxt,LinearLayout sheetLayout,Dialog dialog) {
        this.context=context;
        this.items = listas;
        this.textView = textView;
        this.tPrice = tPrice;
        this.priceTxtOriginal = priceTxtOriginal;
        this.plus = plus;
        this.delete = delete;
        this.linearLayout = linearLayout;
        this.addProduct = addProduct;
        this.productMenuModelList = productMenuModelList;
        this.product = product;
        this.productQuantity = productQuantity;
        this.quantityTxt = quantityTxt;
        this.sheetLayout = sheetLayout;
        this.dialog = dialog;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public RecyclerCheckAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.checkbox_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerCheckAdapter.ViewHolder viewHolder, final int i) {

        finalPrice = tPrice;
        viewHolder.checkBox.setText(String.valueOf(items.get(i).getPrice()) + " " + context.getString(R.string.currency));
        viewHolder.textView.setText(items.get(i).getName());
        viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if ( isChecked )
                {
                    finalPrice = finalPrice + items.get(i).getPrice();

                    // perform logic
                    textView.setText(finalPrice+ " " + context.getString(R.string.currency));
                    priceTxtOriginal.setText(String.valueOf(finalPrice));
                   /* delete.setVisibility(View.VISIBLE);
                    linearLayout.setBackgroundResource(R.drawable.btnorangeee);*/
                    //Toast.makeText(context, items.get(i).getName() +" "+ String.valueOf(items.get(i).getPrice()) + " : checked", Toast.LENGTH_SHORT).show();
                }
                else if (!isChecked){
                    finalPrice = finalPrice - items.get(i).getPrice();
                    textView.setText(finalPrice + " " + context.getString(R.string.currency));
                    priceTxtOriginal.setText(String.valueOf(finalPrice));
                    //Toast.makeText(context, items.get(i).getName() +" "+ String.valueOf(items.get(i).getPrice())+ " : unchecked", Toast.LENGTH_SHORT).show();

                }

            }
        });

        addProduct.setOnClickListener(v -> {
            try {
                Log.e("list_size", productMenuModelList.size() + " before add");
                productMenuModelList.add(new StoreProductsModel.ProductBean(product.getProduct_name(), finalPrice, productQuantity));
                Log.e("list_size", productMenuModelList.size() + " after add");
            } catch (Exception e) {
                Log.e("list_size", "add Exception" + e.getMessage());
                e.printStackTrace();
            }

            product.setSelected(true);

            delete.setVisibility(View.VISIBLE);
            quantityTxt.setVisibility(View.VISIBLE);
            quantityTxt.setText(productQuantity + "");
            linearLayout.setBackgroundResource(R.drawable.btnorangeee);


            double ttPrice = 0;
            for (int ii = 0; ii < productMenuModelList.size(); ii++) {
                double itemPrice = productMenuModelList.get(ii).getPrice() * productMenuModelList.get(ii).getQuantity();
                ttPrice += itemPrice;
            }
            dialog.dismiss();
            // sheetLayout.setVisibility(View.GONE);
            /*totalPrice.set(ttPrice +" "+ context.getString(R.string.currency));

            dismiss();*/
            //ProductBottomSheet.dismiss();

        });

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private CheckBox checkBox;
        TextView textView;
        public ViewHolder(View view) {
            super(view);

            checkBox = view.findViewById(R.id.check_price);
            textView = view.findViewById(R.id.check_name);
        }
    }

}