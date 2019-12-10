package com.example.androidapp_exam2019;


import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.androidapp_exam2019.model.Favorite;

import org.w3c.dom.Text;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritesFragment extends Fragment {


    public FavoritesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorites, container, false);
    }

    private class FavoritesViewHolder extends RecyclerView.ViewHolder {
        public ImageView favoritesIcon;
        public TextView favoritesDescription;
        public TextView favoritesPrice;

        public FavoritesViewHolder(@NonNull View itemView) {
            super(itemView);
            favoritesIcon = itemView.findViewById(R.id.favListIconId);
            favoritesDescription = itemView.findViewById(R.id.favListDescriptionId);
            favoritesPrice = itemView.findViewById(R.id.favListPriceId);
        }
    }

    private class FavoritesAdapter extends RecyclerView.Adapter<FavoritesViewHolder> {
        private ArrayList<Favorite> favorites;

        @Override
        public FavoritesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.favorites_list_view, parent, false);
            FavoritesViewHolder vh = new FavoritesViewHolder(linearLayout);
            return vh;
        }

        @Override
        public void onBindViewHolder(@NonNull FavoritesViewHolder holder, int position) {
            Favorite f = favorites.get(position);
            //TODO
            holder.favoritesDescription.setText(f.getDescription().toString());
            holder.favoritesPrice.setText(f.getPrice().toString());
        }

        @Override
        public int getItemCount() {
            return favorites == null ? 0 : favorites.size();
        }
    }

}
