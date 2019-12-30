package com.example.androidapp_exam2019;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidapp_exam2019.dataAccess.IDressApi;
import com.example.androidapp_exam2019.model.Dress;
import com.example.androidapp_exam2019.model.Order;
import com.example.androidapp_exam2019.model.OrderLine;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


/**
 * A simple {@link Fragment} subclass.
 */
public class CardFragment extends Fragment {

    @BindView(R.id.rvCard) public RecyclerView rvCard;
    @BindView(R.id.cardButtonOrderNow) public Button cardButtonOrderNow;
    @BindView(R.id.cardPriceTotal) public TextView cardPriceTotal;
    @BindView(R.id.cardNbOfArticles) public TextView cardNbArticles;
    DressViewModel model;
    Retrofit retrofit;
    IDressApi dressApi;
    Call<Order> call;
    Order order;


    public CardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_card, container, false);
        ButterKnife.bind(this, view);

        model = ViewModelProviders.of(getActivity()).get(DressViewModel.class);

        CardAdapter adapter = new CardAdapter();
        rvCard.setLayoutManager(new LinearLayoutManager(this.getContext()));
        rvCard.setAdapter(adapter);
        retrofit = RetrofitSingleton.getClient();
        dressApi = retrofit.create(IDressApi.class);

        call = dressApi.getOrderOfUser();
        call.enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
                if (!response.isSuccessful()) {
                    if (getContext() != null)
                        Toast.makeText(getContext(), response.message(), Toast.LENGTH_LONG).show();
                    return;
                }
                order = response.body();
                adapter.setCard(order);
            }

            @Override
            public void onFailure(Call<Order> call, Throwable t) {
                if (getContext() != null)
                    Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        cardButtonOrderNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO VALIDER COMMANDE
                Call<Order> callValidateOrder = dressApi.putOrder(order);
                callValidateOrder.enqueue(new Callback<Order>() {
                    @Override
                    public void onResponse(Call<Order> call, Response<Order> response) {
                        if (!response.isSuccessful()) {
                            if (getContext() != null)
                                Toast.makeText(getContext(), response.message(), Toast.LENGTH_LONG).show();
                            return;
                        }
                    }

                    @Override
                    public void onFailure(Call<Order> call, Throwable t) {
                        if (getContext() != null)
                            Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
        return view;
    }

    private class CardViewHolder extends RecyclerView.ViewHolder {
        public ImageView dressesPicture;
        public TextView dressesName;
        public TextView dressesPrice;
        public TextView dressesAvailability;

        public CardViewHolder(@NonNull View itemView, IOnItemSelectedListener listener) {
            super(itemView);
            dressesPicture = itemView.findViewById(R.id.browseListDressPicId);
            dressesName = itemView.findViewById(R.id.browseListDressNameId);
            dressesPrice = itemView.findViewById(R.id.browseListDressPriceId);
            dressesAvailability = itemView.findViewById(R.id.cardListDressAvailabilityId);
            itemView.setOnClickListener(e -> {
                int currentPosition = getAdapterPosition();
                listener.onItemSelected(currentPosition);
            });
        }
    }

    private class CardAdapter extends RecyclerView.Adapter<CardViewHolder> {
        private Order order;
        private ArrayList<OrderLine> orderLines;

        public CardAdapter() {
        }

        @Override
        public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ConstraintLayout constraintLayout = (ConstraintLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_list_view, parent, false);
            CardViewHolder vh = new CardViewHolder(constraintLayout, position -> {
                OrderLine touchedOrderLine = orderLines.get(position);
                model.setdressId(orderLines.get(position).getDressId());
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerId, new ArticleFragment()).commit();
                //startActivity(intentGoDress);
            });
            return vh;
        }

        @Override
        public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
            OrderLine ol = orderLines.get(position);
            //TODO IMAGES
            holder.dressesName.setText(ol.getDressName());
            holder.dressesPrice.setText(ol.getFinalPrice().toString() + " €");
            if (ol.isDressAvailable()) {
                holder.dressesAvailability.setText(R.string.dressAvailable);
                holder.dressesAvailability.setTextColor(getResources().getColor(R.color.available));
            } else {
                holder.dressesAvailability.setText(R.string.dressNotAvailable);
                holder.dressesAvailability.setTextColor(getResources().getColor(R.color.notAvailable));
            }
        }

        @Override
        public int getItemCount() {
            return orderLines == null ? 0 : orderLines.size();
        }

        public void setCard(Order order) {
            this.order = order;
            this.orderLines = order.getOrderLines();
            notifyDataSetChanged();
        }
    }
}
