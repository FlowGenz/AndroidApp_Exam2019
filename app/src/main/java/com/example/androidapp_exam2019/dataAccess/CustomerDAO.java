package com.example.androidapp_exam2019.dataAccess;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.androidapp_exam2019.R;
import com.example.androidapp_exam2019.constants.AppSharedPreferences;
import com.example.androidapp_exam2019.dataAccess.retrofit.CustomerRetrofit;
import com.example.androidapp_exam2019.dataAccess.retrofit.RetrofitSingleton;
import com.example.androidapp_exam2019.model.Customer;
import com.example.androidapp_exam2019.view.activity.ConnectionActivity;
import com.example.androidapp_exam2019.view.activity.PartnerActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CustomerDAO implements CustomerDataAccess {

    private CustomerRetrofit dataAccess;
    private Retrofit retrofit;
    private Activity activity;

    public CustomerDAO(Activity activity) {
       retrofit = RetrofitSingleton.getClient();
       dataAccess = retrofit.create(CustomerRetrofit.class);
       this.activity = activity;
    }

   @Override
   public void userLogin(String username, SharedPreferences.Editor editor) {
      Call<Customer> callGetId = dataAccess.getCustomer(username);
      callGetId.enqueue(new Callback<Customer>() {
         @Override
         public void onResponse(Call<Customer> call, Response<Customer> response) {
            if (!response.isSuccessful()) {
               if (activity.getApplicationContext() != null)
               	Toast.makeText(activity.getApplicationContext(), response.message(), Toast.LENGTH_LONG).show();
               return;
            }
            editor.putString(AppSharedPreferences.USER_ID, response.body().getId());
            editor.apply();
            Intent intentGoPartner = new Intent(activity, PartnerActivity.class);
            activity.startActivity(intentGoPartner);
         }

         @Override
         public void onFailure(Call<Customer> call, Throwable t) {
            if (activity.getApplicationContext() != null)
               Toast.makeText(activity.getApplicationContext(), activity.getString(R.string.networkConnectionError), Toast.LENGTH_LONG).show();
         }
      });
   }

   @Override
   public void registerUser(Customer customer) {
      Call<Void> call = dataAccess.postCustomer(customer);
      call.enqueue(new Callback<Void>() {
         @Override
         public void onResponse(Call<Void> call, Response<Void> response) {
            if (!response.isSuccessful()) {
               if (activity.getApplicationContext() != null)
                  Toast.makeText(activity.getApplicationContext(), response.message(), Toast.LENGTH_LONG).show();
               return;
            }

            Toast.makeText(activity, activity.getString(R.string.registrationSuccesfull), Toast.LENGTH_SHORT).show();
            Intent intentGoConnectionScreen = new Intent(activity, ConnectionActivity.class);
            activity.startActivity(intentGoConnectionScreen);
         }

         @Override
         public void onFailure(Call<Void> call, Throwable t) {
            if (activity.getApplicationContext() != null)
               Toast.makeText(activity.getApplicationContext(), activity.getString(R.string.networkConnectionError), Toast.LENGTH_LONG).show();
         }
      });
   }
}
