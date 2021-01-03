package com.uriallab.haat.haat.viewModels;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.TextHttpResponseHandler;
import com.uriallab.haat.haat.API.APIModel;
import com.uriallab.haat.haat.DataModels.CategoryModel;
import com.uriallab.haat.haat.DataModels.GoogleStoresModel;
import com.uriallab.haat.haat.DataModels.ServerStoresModel;
import com.uriallab.haat.haat.R;
import com.uriallab.haat.haat.SharedPreferences.ConfigurationFile;
import com.uriallab.haat.haat.Test.NetworkUtil2;
import com.uriallab.haat.haat.UI.Activities.Updates.StoresActivity;
import com.uriallab.haat.haat.Utilities.Dialogs;
import com.uriallab.haat.haat.Utilities.GlobalVariables;
import com.uriallab.haat.haat.Utilities.LoadingDialog;

import java.lang.reflect.Type;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class StoresViewModel extends ViewModel {

    public ObservableField<String> categoryName = new ObservableField<>();

    public ObservableInt rotation = new ObservableInt(0);

    @SuppressLint("StaticFieldLeak")
    private StoresActivity activity;
    private CompositeSubscription mSubscriptions = new CompositeSubscription();
    private GoogleStoresModel data,googleStoresModel;
    final LoadingDialog loadingDialog = new LoadingDialog();

    public StoresViewModel(StoresActivity activity, String type, String category, boolean isSearch, String searchTxt) {
        this.activity = activity;

        if (ConfigurationFile.getCurrentLanguage(activity).equals("ar")) {
            rotation.set(180);
        }

        if (isSearch) {
            categoryName.set(searchTxt);
            getSearchResult(searchTxt);
        } else {
            categoryName.set(category);
            getStores(type);
            //GetStory(type);
        }
    }


    public void getStores(final String catType) {

                        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                                ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                            final LoadingDialog loadingDialog = new LoadingDialog();

                            double lat = GlobalVariables.LOCATION_LAT;
                            double lng = GlobalVariables.LOCATION_LNG;
                            String urlType = "";
                            if (!catType.equals(""))
                                urlType = "&type=" + catType;
                            String url = "nearbysearch/json?key=" + activity.getResources().getString(R.string.api_key) +
                                    "&location=" + lat + "," + lng +
                                    "&rankby=distance" +
                                    urlType +
                                    "&language=" + ConfigurationFile.getCurrentLanguage(activity);
                            APIModel.getMethodForGoogle(activity, url, new TextHttpResponseHandler() {
                                @Override
                                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                                    Log.e("response", responseString + "Error");
                                    switch (statusCode) {
                                        default:
                                            APIModel.handleFailure(activity, statusCode, responseString, new APIModel.RefreshTokenListener() {
                                                @Override
                                                public void onRefresh() {
                                                    getStores(catType);
                                                }
                                            });
                                            break;
                                    }
                                }

                                @Override
                                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                                    Log.e("response", responseString);
                                    Type dataType = new TypeToken<GoogleStoresModel>() {
                                    }.getType();
                                    data = new Gson().fromJson(responseString, dataType);


                                }

                                @Override
                                public void onStart() {
                                    super.onStart();
                                    activity.isLoading = true;
                                    Dialogs.showLoading(activity, loadingDialog);
                                }
                                @Override
                                public void onFinish() {
                                    super.onFinish();
                                    activity.isLoading = false;

                                    Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        public void run() {
                                            try {
                                                try {
                                                    activity.pageToken = data.getNext_page_token();
                                                } catch (Exception e) {
                                                    activity.pageToken = "";
                                                    e.printStackTrace();
                                                }
                                                Dialogs.dismissLoading(loadingDialog);
                                                if (data.getResults().isEmpty()) {
                                                    activity.binding.noData.setVisibility(View.VISIBLE);
                                                    activity.binding.storesRecycler.setVisibility(View.GONE);

                                                } else {
                                                    activity.binding.noData.setVisibility(View.GONE);
                                                    activity.binding.storesRecycler.setVisibility(View.VISIBLE);
                                                    activity.updateRecycler(data.getResults());
                                                }
                                            }catch (Exception e){}
                                        }
                                    }, 1000);




                                }
                            });
                        }
        }

    public void GetStory(final String catType) {
        showLoading(activity,loadingDialog);
        String urlType = "";
        if (!catType.equals(""))
            urlType = "&type=" + catType;
        double lat = GlobalVariables.LOCATION_LAT;
        double lng = GlobalVariables.LOCATION_LNG;
        String location = lat + "," + lng;
        mSubscriptions.add(NetworkUtil2.getRetrofitNoHeader2()
                .GetSubCategories(activity.getResources().getString(R.string.api_key),location,"distance",catType,ConfigurationFile.getCurrentLanguage(activity))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponsee, this::handleErrorr));
    }

    private void handleResponsee(GoogleStoresModel googleStoresModel) {
        dismissLoading(loadingDialog);

        try {
            activity.pageToken = googleStoresModel.getNext_page_token();
        } catch (Exception e) {
            activity.pageToken = "";
            e.printStackTrace();
        }

        if (googleStoresModel.getResults().isEmpty()) {
            //Dialogs.dismissLoading(loadingDialog);
            activity.binding.noData.setVisibility(View.VISIBLE);
            activity.binding.storesRecycler.setVisibility(View.GONE);
        } else {
            //Dialogs.dismissLoading(loadingDialog);
            activity.binding.noData.setVisibility(View.GONE);
            activity.binding.storesRecycler.setVisibility(View.VISIBLE);
            activity.updateRecycler(googleStoresModel.getResults());
            //Dialogs.dismissLoading(loadingDialog);
        }
        //Toast.makeText(activity, "dddd", Toast.LENGTH_SHORT).show();
    }

    private void handleErrorr(Throwable throwable) {

    }


    public void GetStoryPaginate(final String catType,final String pageToken) {
        showLoading(activity,loadingDialog);
        String urlType = "";
        if (!catType.equals(""))
            urlType = "&type=" + catType;
        double lat = GlobalVariables.LOCATION_LAT;
        double lng = GlobalVariables.LOCATION_LNG;
        String location = lat + "," + lng;
        mSubscriptions.add(NetworkUtil2.getRetrofitNoHeader2()
                .GetSubCategoriesPaginate(activity.getResources().getString(R.string.api_key),location,"distance",pageToken,catType,ConfigurationFile.getCurrentLanguage(activity))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponseee, this::handleErrorrt));
    }

    private void handleErrorrt(Throwable throwable) {
    }

    private void handleResponseee(GoogleStoresModel googleStoresModel) {
        try {
            activity.pageToken = googleStoresModel.getNext_page_token();
        } catch (Exception e) {
            activity.pageToken = "";
            e.printStackTrace();
        }

        List<GoogleStoresModel.ResultsBean> storesModel = googleStoresModel.getResults();
        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Dialogs.dismissLoading(loadingDialog);
                activity.updateRecyclerPaginate(storesModel);
            }
        }, 3000);

    }

    public void getSearchResult(final String search) {
        final LoadingDialog loadingDialog = new LoadingDialog();
        double lat = GlobalVariables.LOCATION_LAT;
        double lng = GlobalVariables.LOCATION_LNG;
        String urlType = "";

        String url = "textsearch/json?query=" +  search + "&key="  +activity.getResources().getString(R.string.api_key)+
                "&location=" + lat + "," + lng +
                "&rankby=distance" +
                urlType +
                "&language=" + ConfigurationFile.getCurrentLanguage(activity);


     /*   String url = "textsearch/json?query=" + search + "&key=" + activity.getResources().getString(R.string.api_key) +
                "&language=" + ConfigurationFile.getCurrentLanguage(activity);*/
        APIModel.getMethodForGoogle(activity, url, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.e("response", responseString + "Error");
                switch (statusCode) {
                    default:
                        APIModel.handleFailure(activity, statusCode, responseString, new APIModel.RefreshTokenListener() {
                            @Override
                            public void onRefresh() {
                                getStores(search);
                            }
                        });
                        break;
                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.e("response", responseString);
                Type dataType = new TypeToken<GoogleStoresModel>() {
                }.getType();
                data = new Gson().fromJson(responseString, dataType);

                try {
                    activity.pageToken = data.getNext_page_token();
                } catch (Exception e) {
                    activity.pageToken = "";
                    e.printStackTrace();
                }

                if (data.getResults().isEmpty()) {
                    Dialogs.dismissLoading(loadingDialog);
                    activity.binding.noData.setVisibility(View.VISIBLE);
                    activity.binding.storesRecycler.setVisibility(View.GONE);
                } else {
                    Dialogs.dismissLoading(loadingDialog);
                    activity.binding.noData.setVisibility(View.GONE);
                    activity.binding.storesRecycler.setVisibility(View.VISIBLE);
                    activity.updateRecycler(data.getResults());
                }
            }

            @Override
            public void onStart() {
                super.onStart();
                activity.isLoading = true;
                Dialogs.showLoading(activity, loadingDialog);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                activity.isLoading = false;

             /*   new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Dialogs.dismissLoading(loadingDialog);
                    }
                }, 1000);*/

            }
        });
    }

    public void getStoresPaginate(final String catType, final String pageToken) {

        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            final LoadingDialog loadingDialog = new LoadingDialog();
            String urlType = "";
            if (!catType.equals(""))
                urlType = "&type=" + catType;
            String url = "nearbysearch/json?key=" + activity.getResources().getString(R.string.api_key) +
                    "&location=" + GlobalVariables.LOCATION_LAT + "," + GlobalVariables.LOCATION_LNG +
                    "&radius=2500" +
                    "&pagetoken=" + pageToken +
                    urlType +
                    "&language=" + ConfigurationFile.getCurrentLanguage(activity);
            APIModel.getMethodForGoogle(activity, url, new TextHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    Log.e("response", responseString + "Error");
                    switch (statusCode) {
                        default:
                            APIModel.handleFailure(activity, statusCode, responseString, new APIModel.RefreshTokenListener() {
                                @Override
                                public void onRefresh() {
                                    getStoresPaginate(catType, pageToken);
                                }
                            });
                            break;
                    }
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    Log.e("response", responseString);
                    Type dataType = new TypeToken<GoogleStoresModel>() {
                    }.getType();
                    data = new Gson().fromJson(responseString, dataType);

                    try {
                        activity.pageToken = data.getNext_page_token();
                    } catch (Exception e) {
                        activity.pageToken = "";
                        e.printStackTrace();
                    }

                    activity.updateRecyclerPaginate(data.getResults());
                    Dialogs.dismissLoading(loadingDialog);
                }

                @Override
                public void onStart() {
                    super.onStart();
                    activity.isLoading = true;
                    Dialogs.showLoading(activity, loadingDialog);
                }

                @Override
                public void onFinish() {
                    super.onFinish();
                    activity.isLoading = false;

/*                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Dialogs.dismissLoading(loadingDialog);
                        }
                    }, 500);*/

                }
            });
        }
    }

    public TextWatcher getSearchText() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(final Editable editable) {

                getSearchResult(editable.toString());

              /*  new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getSearchResult(editable.toString());
                    }
                }, 4000);*/

            }
        };
    }

    private boolean isLocationEnabled() {
        final LocationManager manager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            return true;
        }
        return false;
    }

    public void back() {
        activity.finish();
    }

    public static void showLoading(Activity activity, LoadingDialog loadingDialog) {
        try {
            loadingDialog.show(((AppCompatActivity) activity).getSupportFragmentManager(), "showLoading");

        } catch (Exception e) {
        }
    }

    public static void dismissLoading(LoadingDialog loadingDialog) {
        try {
            if (loadingDialog != null)
                loadingDialog.dismiss();
            loadingDialog = null;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}