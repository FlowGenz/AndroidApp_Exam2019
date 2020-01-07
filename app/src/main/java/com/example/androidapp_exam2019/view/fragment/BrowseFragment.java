package com.example.androidapp_exam2019.view.fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.example.androidapp_exam2019.viewModel.DressViewModel;
import com.example.androidapp_exam2019.model.IOnItemSelectedListener;
import com.example.androidapp_exam2019.R;
import com.example.androidapp_exam2019.dataAccess.retrofit.RetrofitSingleton;
import com.example.androidapp_exam2019.constants.AppSharedPreferences;
import com.example.androidapp_exam2019.dataAccess.IDressApi;
import com.example.androidapp_exam2019.model.Dress;
import com.example.androidapp_exam2019.model.FavoriteDress;
import com.example.androidapp_exam2019.model.post.FavoritePost;

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
public class BrowseFragment extends Fragment {

    @BindView(R.id.rvDressId) public RecyclerView rvDress;
    @BindView(R.id.svDressId) public SearchView searchViewDress;
    private DressViewModel model;
    private Retrofit retrofit;
    private IDressApi dressApi;
    private Call<ArrayList<Dress>> call;
    private Call<FavoriteDress> callIsFavorite;

    public BrowseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_browse, container, false);
        ButterKnife.bind(this, view);

        model = ViewModelProviders.of(getActivity()).get(DressViewModel.class);

        DressesAdapter adapter = new DressesAdapter();
        rvDress.setLayoutManager(new LinearLayoutManager(this.getContext()));
        rvDress.setAdapter(adapter);
        retrofit = RetrofitSingleton.getClient();
        dressApi = retrofit.create(IDressApi.class);

        call = dressApi.getAllDresses();
        call.enqueue(new Callback<ArrayList<Dress>>() {
            @Override
            public void onResponse(Call<ArrayList<Dress>> call, Response<ArrayList<Dress>> response) {
                if (!response.isSuccessful()) {
                    if (getContext() != null)
                        Toast.makeText(getContext(), response.message(), Toast.LENGTH_LONG).show();
                    return;
                }
                adapter.setDresses(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<Dress>> call, Throwable t) {
                if (getContext() != null)
                    Toast.makeText(getContext(), getString(R.string.networkConnectionError), Toast.LENGTH_LONG).show();
            }
        });

        searchViewDress.setIconifiedByDefault(false);
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();

        call.cancel();
    }

    private class DressesViewHolder extends RecyclerView.ViewHolder {
        public ImageView dressesPicture;
        public TextView dressesName;
        public TextView dressesPrice;
        public ToggleButton dressesButtonFavorite;

        public DressesViewHolder(@NonNull View itemView, IOnItemSelectedListener listener) {
            super(itemView);
            dressesPicture = itemView.findViewById(R.id.browseListDressPicId);
            dressesName = itemView.findViewById(R.id.browseListDressNameId);
            dressesPrice = itemView.findViewById(R.id.browseListDressPriceId);
            dressesButtonFavorite = itemView.findViewById(R.id.browseListDressButtonFavoriteId);
            itemView.setOnClickListener(e -> {
                int currentPosition = getAdapterPosition();
                listener.onItemSelected(currentPosition);
            });
        }
    }

    private class DressesAdapter extends RecyclerView.Adapter<DressesViewHolder> implements Filterable {
        private ArrayList<Dress> dresses;
        private ArrayList<Dress> dressesFull;
        public String favoriteId;

        public DressesAdapter() {
            dresses = new ArrayList<>();
            dressesFull = new ArrayList<>(dresses);
        }

        @Override
        public DressesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ConstraintLayout constraintLayout = (ConstraintLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.browse_list_view, parent, false);
            DressesViewHolder vh = new DressesViewHolder(constraintLayout, position -> {
                Dress touchedDress = dresses.get(position);
                model.setDressId(touchedDress.getId());
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerId, new ArticleFragment()).commit();
            });
            return vh;
        }

        @Override
        public void onBindViewHolder(@NonNull DressesViewHolder holder, int position) {
            Dress d = dresses.get(position);
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences(AppSharedPreferences.SHARED_PREFERENCES, Context.MODE_PRIVATE);
            callIsFavorite = dressApi.isFavorite(sharedPreferences.getString(AppSharedPreferences.USERNAME, ""), d.getId());
            callIsFavorite.enqueue(new Callback<FavoriteDress>() {
                @Override
                public void onResponse(Call<FavoriteDress> call, Response<FavoriteDress> response) {
                    if (!response.isSuccessful()) {
                        Toast.makeText(getContext(), response.message(), Toast.LENGTH_LONG).show();
                        return;
                    }
                    favoriteId = response.body().getFavoriteId();
                    holder.dressesButtonFavorite.setChecked(response.body().getFavorite());
                }

                @Override
                public void onFailure(Call<FavoriteDress> call, Throwable t) {
                    if (getContext() != null)
                        Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
            holder.dressesButtonFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (holder.dressesButtonFavorite.isChecked()) {
                        Call<Void> callPostFavorite = dressApi.postFavorite(new FavoritePost(null, sharedPreferences.getString(AppSharedPreferences.USER_ID, ""), d.getId()));
                        callPostFavorite.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                if (!response.isSuccessful()) {
                                    Toast.makeText(getContext(), response.message(), Toast.LENGTH_LONG).show();
                                    return;
                                }
                                holder.dressesButtonFavorite.setChecked(true);
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
                                    Toast.makeText(getContext(), response.message(), Toast.LENGTH_LONG).show();
                                    return;
                                }
                                holder.dressesButtonFavorite.setChecked(false);
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
            Glide.with(getView()).load(dresses.get(position).getUrlImage()).into(holder.dressesPicture);
            holder.dressesName.setText(d.getDressName());
            holder.dressesPrice.setText(d.getPrice().toString() + " €");

        }

        @Override
        public int getItemCount() {
            return dresses == null ? 0 : dresses.size();
        }

        public void setDresses(ArrayList<Dress> dresses) {
            this.dresses = dresses;
            notifyDataSetChanged();
        }

        @Override
        public Filter getFilter() {
            return dressFilter;
        }

        private Filter dressFilter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                ArrayList<Dress> filteredList = new ArrayList<>();

                if (constraint == null || constraint.length() == 0) {
                    filteredList.addAll(dressesFull);
                } else {
                    String filteredPattern = constraint.toString().toLowerCase().trim();

                    for (Dress dress : dressesFull) {
                        if (dress.getDressName().toLowerCase().contains(filteredPattern)) {
                            filteredList.add(dress);
                        }
                    }
                }

                FilterResults results = new FilterResults();
                results.values = filteredList;

                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                dresses.clear();
                dresses.addAll((ArrayList) results.values);
                notifyDataSetChanged();
            }
        };
    }



}
