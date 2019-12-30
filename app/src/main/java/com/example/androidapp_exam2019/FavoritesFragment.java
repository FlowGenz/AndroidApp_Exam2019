package com.example.androidapp_exam2019;


import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidapp_exam2019.constants.AppSharedPreferences;
import com.example.androidapp_exam2019.dataAccess.IDressApi;
import com.example.androidapp_exam2019.model.Favorite;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritesFragment extends Fragment {

    @BindView(R.id.rvFavoritesId) public RecyclerView favRv;
    Retrofit retrofit;
    IDressApi dressApi;
    Call<ArrayList<Favorite>> call;


    public FavoritesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);
        ButterKnife.bind(this, view);
        FavoritesAdapter favoritesAdapter = new FavoritesAdapter();
        favRv.setLayoutManager(new LinearLayoutManager(this.getContext()));
        favRv.setAdapter(favoritesAdapter);

        retrofit = RetrofitSingleton.getClient();
        dressApi = retrofit.create(IDressApi.class);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(AppSharedPreferences.SHARED_PREFERENCES, MODE_PRIVATE);

        call = dressApi.getFavoritesOfUser(sharedPreferences.getString(AppSharedPreferences.USERNAME, ""));
        call.enqueue(new Callback<ArrayList<Favorite>>() {
            @Override
            public void onResponse(Call<ArrayList<Favorite>> call, Response<ArrayList<Favorite>> response) {
                if (!response.isSuccessful()) {
                    if (getContext() != null)
                        Toast.makeText(getContext(), response.message(), Toast.LENGTH_LONG).show();
                    return;
                }
                favoritesAdapter.setFavorites(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<Favorite>> call, Throwable t) {
                if (getContext() != null)
                    Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();

        call.cancel();
    }

    private class FavoritesViewHolder extends RecyclerView.ViewHolder {
        public ImageView favoritesPicture;
        public TextView favoritesName;
        public TextView favoritesPrice;

        public FavoritesViewHolder(@NonNull View itemView, IOnItemSelectedListener listener) {
            super(itemView);
            favoritesPicture = itemView.findViewById(R.id.favListIconId);
            favoritesName = itemView.findViewById(R.id.favListNameId);
            favoritesPrice = itemView.findViewById(R.id.favListPriceId);
            itemView.setOnClickListener(e -> {
                int currentPosition = getAdapterPosition();
                listener.onItemSelected(currentPosition);
            });
        }
    }

    private class FavoritesAdapter extends RecyclerView.Adapter<FavoritesViewHolder> {
        private ArrayList<Favorite> favorites;

        @Override
        public FavoritesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ConstraintLayout constraintLayout = (ConstraintLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.favorites_list_view, parent, false);
            FavoritesViewHolder vh = new FavoritesViewHolder(constraintLayout, position -> {
                //Intent intentGoDress = new Intent(getActivity().getApplicationContext(), ArticleActivity.class);
                Favorite touchedFavorite = favorites.get(position);
                //startActivity(intentGoDress);
            });
            return vh;
        }

        @Override
        public void onBindViewHolder(@NonNull FavoritesViewHolder holder, int position) {
            Favorite f = favorites.get(position);
            //TODO
            holder.favoritesName.setText("Name");
            holder.favoritesPrice.setText("XX €");
        }

        @Override
        public int getItemCount() {
            return favorites == null ? 0 : favorites.size();
        }

        public void setFavorites(ArrayList<Favorite> favorites) {
            this.favorites = favorites;
            notifyDataSetChanged();
        }
    }

}
