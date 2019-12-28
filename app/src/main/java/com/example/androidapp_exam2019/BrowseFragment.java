package com.example.androidapp_exam2019;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidapp_exam2019.model.Dress;

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
    Retrofit retrofit;
    IDressApi dressApi;
    Call<ArrayList<Dress>> call;

    public BrowseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_browse, container, false);
        ButterKnife.bind(this, view);
        DressesAdapter adapter = new DressesAdapter();
        rvDress.setLayoutManager(new LinearLayoutManager(this.getContext()));
        rvDress.setAdapter(adapter);
        searchViewDress.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        retrofit = RetrofitSingleton.getClient();
        dressApi = retrofit.create(IDressApi.class);

        call = dressApi.getAllDresses();
        call.enqueue(new Callback<ArrayList<Dress>>() {
            @Override
            public void onResponse(Call<ArrayList<Dress>> call, Response<ArrayList<Dress>> response) {
                if (!response.isSuccessful()) {
                    //Toast.makeText(getContext(), response.toString(), Toast.LENGTH_LONG).show();
                    return;
                }
                adapter.setDresses(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<Dress>> call, Throwable t) {
                //Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
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
        public ImageButton dressesButtonFavorite;

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

        public DressesAdapter() {
            dresses = new ArrayList<>();
            dressesFull = new ArrayList<>(dresses);
        }

        @Override
        public DressesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ConstraintLayout constraintLayout = (ConstraintLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.browse_list_view, parent, false);
            DressesViewHolder vh = new DressesViewHolder(constraintLayout, position -> {
                Intent intentGoDress = new Intent(getActivity().getApplicationContext(), ArticleActivity.class);
                Dress touchedDress = dresses.get(position);
                startActivity(intentGoDress);
            });
            return vh;
        }

        @Override
        public void onBindViewHolder(@NonNull DressesViewHolder holder, int position) {
            Dress d = dresses.get(position);
            //TODO IMAGES
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
