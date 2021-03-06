package com.example.androidapp_exam2019.view.fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.example.androidapp_exam2019.dataAccess.DressDAO;
import com.example.androidapp_exam2019.dataAccess.DressDataAccess;
import com.example.androidapp_exam2019.viewModel.DressViewModel;
import com.example.androidapp_exam2019.R;
import com.example.androidapp_exam2019.dataAccess.retrofit.RetrofitSingleton;
import com.example.androidapp_exam2019.constants.AppSharedPreferences;
import com.example.androidapp_exam2019.dataAccess.IDressApi;
import com.example.androidapp_exam2019.model.Dress;
import com.example.androidapp_exam2019.model.FavoriteDress;
import com.example.androidapp_exam2019.model.Order;
import com.example.androidapp_exam2019.model.OrderLine;
import com.example.androidapp_exam2019.model.post.FavoritePost;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


/**
 * A simple {@link Fragment} subclass.
 */
public class ArticleFragment extends Fragment {

    @BindView(R.id.articleNameId) public TextView articleName;
    @BindView(R.id.articlePriceId) public TextView articlePrice;
    @BindView(R.id.articleDateBeginId) public TextView articleDateBegin;
    @BindView(R.id.articleDateEndId) public TextView articleDateEnd;
    @BindView(R.id.articleAvailabilityId) public TextView articleAvailability;
    @BindView(R.id.articleDressButtonFavoriteId) public ToggleButton articleDressButtonFavorite;
    @BindView(R.id.articleButtonAddToCardId) public Button articleButtonAddToCard;
    @BindView(R.id.articleDescriptionId) public TextView articleDescription;
    @BindView(R.id.articleSizeId) public TextView articleSize;
    @BindView(R.id.articlePictureId) public ImageView articlePicture;
    private Retrofit retrofit;
    private IDressApi dressApi;
    private DressViewModel model;
    private DressDataAccess dataAccess;
    private Dress dress;
    private String favoriteId;


    public ArticleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_article, container, false);
        ButterKnife.bind(this, view);
        dataAccess = new DressDAO(getActivity());

        model = ViewModelProviders.of(getActivity()).get(DressViewModel.class);
        String dressId = model.getDressId().getValue();

        retrofit = RetrofitSingleton.getClient();
        dressApi = retrofit.create(IDressApi.class);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(AppSharedPreferences.SHARED_PREFERENCES, Context.MODE_PRIVATE);

        dress = dataAccess.getDress(dressId);
        setViewElements(dress, view);

        Call<FavoriteDress> callIsFavorite = dressApi.isFavorite(sharedPreferences.getString(AppSharedPreferences.USERNAME, ""), dressId);
        callIsFavorite.enqueue(new Callback<FavoriteDress>() {
            @Override
            public void onResponse(Call<FavoriteDress> call, Response<FavoriteDress> response) {
                if (!response.isSuccessful()) {
                    if (getContext() != null)
                        Toast.makeText(getContext(), response.message(), Toast.LENGTH_LONG).show();
                    return;
                }
                favoriteId = response.body().getFavoriteId();
                articleDressButtonFavorite.setChecked(response.body().getFavorite());
            }

            @Override
            public void onFailure(Call<FavoriteDress> call, Throwable t) {
                if (getContext() != null)
                    Toast.makeText(getContext(), getString(R.string.networkConnectionError), Toast.LENGTH_LONG).show();
            }
        });

        articleDressButtonFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (articleDressButtonFavorite.isChecked()) {
                    Call<Void> callPostFavorite = dressApi.postFavorite(new FavoritePost(null, sharedPreferences.getString(AppSharedPreferences.USER_ID, ""), dress.getId()));
                    callPostFavorite.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (!response.isSuccessful()) {
                                Toast.makeText(getContext(), response.message(), Toast.LENGTH_LONG).show();
                                return;
                            }
                            articleDressButtonFavorite.setChecked(true);
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            if (getContext() != null)
                                Toast.makeText(getContext(), getString(R.string.networkConnectionError), Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    Call<Void> callDeleteFavorite = dressApi.deleteFavorite(favoriteId);
                    callDeleteFavorite.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (!response.isSuccessful()) {
                                if (getContext() != null)
                                    Toast.makeText(getContext(), response.message(), Toast.LENGTH_LONG).show();
                                return;
                            }
                            articleDressButtonFavorite.setChecked(false);
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            if (getContext() != null)
                                Toast.makeText(getContext(), getString(R.string.networkConnectionError), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });

        articleButtonAddToCard.setOnClickListener(new View.OnClickListener() {
            Order order;
            @Override
            public void onClick(View v) {
                Call<Order> callGetOrder = dressApi.getOrderOfUser();
                callGetOrder.enqueue(new Callback<Order>() {
                    @Override
                    public void onResponse(Call<Order> call, Response<Order> response) {
                        if (!response.isSuccessful()) {
                            if (getContext() != null)
                                Toast.makeText(getContext(), response.message(), Toast.LENGTH_LONG).show();
                            return;
                        }
                        order = response.body();
                        order.addOrderLine(new OrderLine(
                                null,
                                null,
                                null,
                                dress.getPrice(),
                                dress.getDressName(),
                                dress.isAvailable(),
                                dress.getUrlImage(),
                                order.getId(),
                                dress.getId())
                        );
                    }

                    @Override
                    public void onFailure(Call<Order> call, Throwable t) {
                        if (getContext() != null)
                            Toast.makeText(getContext(), getString(R.string.networkConnectionError), Toast.LENGTH_LONG).show();
                    }
                });

                Call<Order> callPutOrder = dressApi.putOrder(order);
                callPutOrder.enqueue(new Callback<Order>() {
                    @Override
                    public void onResponse(Call<Order> call, Response<Order> response) {
                        if (!response.isSuccessful()) {
                            if (getContext() != null)
                                Toast.makeText(getContext(), response.message(), Toast.LENGTH_LONG).show();
                            return;
                        }
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerId, new CardFragment()).commit();
                    }

                    @Override
                    public void onFailure(Call<Order> call, Throwable t) {
                        if (getContext() != null)
                            Toast.makeText(getContext(), getString(R.string.networkConnectionError), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        return view;
    }

    public void setViewElements(Dress dress, View view) {
        articleName.setText(dress.getDressName());
        if (dress.isAvailable()) {
            articleAvailability.setText(getString(R.string.dressAvailable));
            articleAvailability.setTextColor(getResources().getColor(R.color.available));
        } else {
            articleAvailability.setText(getString(R.string.dressNotAvailable));
            articleAvailability.setTextColor(getResources().getColor(R.color.notAvailable));
        }
        articlePrice.setText(dress.getPrice().toString() + " €");
        articleDescription.setText(dress.getDescription());
        articleSize.setText((dress.getSize()));
        if (dress.getDateBeginAvailable() != null)
            articleDateBegin.setText(dress.getDateBeginAvailable().toString());
        if (dress.getDateEndAvailable() != null)
            articleDateEnd.setText(dress.getDateEndAvailable().toString());
        Glide.with(view).load(dress.getUrlImage()).into(articlePicture);

    }

}
