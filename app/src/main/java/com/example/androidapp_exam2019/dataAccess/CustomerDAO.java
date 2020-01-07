package com.example.androidapp_exam2019.dataAccess;

import android.content.Intent;
import android.widget.Toast;

import com.example.androidapp_exam2019.R;
import com.example.androidapp_exam2019.constants.AppSharedPreferences;
import com.example.androidapp_exam2019.dataAccess.retrofit.CustomerRetrofit;
import com.example.androidapp_exam2019.dataAccess.retrofit.RetrofitSingleton;
import com.example.androidapp_exam2019.model.Customer;
import com.example.androidapp_exam2019.view.activity.ConnectionActivity;
import com.example.androidapp_exam2019.view.activity.PartnerActivity;
import com.example.androidapp_exam2019.view.activity.RegisterActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static java.security.AccessController.getContext;

public class CustomerDAO implements CustomerDataAccess {

    private CustomerRetrofit dataAccess;
    private Retrofit retrofit;

    public CustomerDAO() {
       retrofit = RetrofitSingleton.getClient();
       dataAccess = retrofit.create(CustomerRetrofit.class);
    }

   @Override
   public Customer getCustomer(String username) {
      Call<Customer> callGetId = dataAccess.getCustomer(username);
      callGetId.enqueue(new Callback<Customer>() {
         @Override
         public void onResponse(Call<Customer> call, Response<Customer> response) {
            if (!response.isSuccessful()) {
               if (getContext() != null)
                  Toast.makeText(getContext(), response.message(), Toast.LENGTH_LONG).show();
               return;
            }
            editor.putString(AppSharedPreferences.USER_ID, response.body().getId());
            editor.apply();
            Intent intentGoPartner = new Intent(ConnectionActivity.this, PartnerActivity.class);
            startActivity(intentGoPartner);
         }

         @Override
         public void onFailure(Call<Customer> call, Throwable t) {
            if (getContext() != null)
               Toast.makeText(getContext(), getString(R.string.networkConnectionError), Toast.LENGTH_LONG).show();
         }
      });
   }

   @Override
   public void postCustomer(Customer customer) {
      Call<Void> call = dataAccess.postCustomer(customer);
      call.enqueue(new Callback<Void>() {
         @Override
         public void onResponse(Call<Void> call, Response<Void> response) {
            if (!response.isSuccessful()) {
               if (getContext() != null)
                  Toast.makeText(getContext(), response.message(), Toast.LENGTH_LONG).show();
               return;
            }

            Toast.makeText(RegisterActivity.this, getString(R.string.registrationSuccesfull), Toast.LENGTH_SHORT).show();
            Intent intentGoConnectionScreen = new Intent(RegisterActivity.this, ConnectionActivity.class);
            startActivity(intentGoConnectionScreen);
         }

         @Override
         public void onFailure(Call<Void> call, Throwable t) {
            if (getContext() != null)
               Toast.makeText(getContext(), getString(R.string.networkConnectionError), Toast.LENGTH_LONG).show();
         }
      });
   }

   @Override
   public Customer putCustomer(String username, Customer customer) {
       return null;
       //dataAccess.putCustomer(username, customer);
   }
}
