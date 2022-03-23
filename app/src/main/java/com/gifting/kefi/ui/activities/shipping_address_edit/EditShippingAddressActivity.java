package com.gifting.kefi.ui.activities.shipping_address_edit;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.gifting.kefi.R;
import com.gifting.kefi.data.model.AddressModel;
import com.gifting.kefi.databinding.ActivityEditShippingAddressBinding;
import com.gifting.kefi.util.DialogCustom;
import com.gifting.kefi.util.Language;

public class EditShippingAddressActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityEditShippingAddressBinding binding;
    private EditShippingAddressViewModel editShippingAddressViewModel;
    private AddressModel addressModel;
    private DialogCustom dialogCustom;
    private  String string="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_shipping_address);

        Language.changeBackDependsLanguage(binding.backImage,getApplicationContext());


        editShippingAddressViewModel = new ViewModelProvider(this).get(EditShippingAddressViewModel.class);


        editShippingAddressViewModel.getAddressModelMutableLiveData().observe(this, addressModel -> {
            Toast.makeText(getApplicationContext(),string, Toast.LENGTH_SHORT).show();
            dialogCustom.dismissDialog();
            onBackPressed();

        });
        editShippingAddressViewModel.getFailText().observe(this, failText -> {
            Toast.makeText(getApplicationContext(), failText, Toast.LENGTH_SHORT).show();

        });

        binding.back.setOnClickListener(this);
        binding.saveAddressButton.setOnClickListener(this);


        if (getIntent().getExtras() != null) {

            addressModel = getIntent().getExtras().getParcelable("EditAddress");
            binding.houseAndApartmentEditText.setText(addressModel.getHouse_and_apartment());
            binding.streetNameEditText.setText(addressModel.getStreet_name());
            binding.districtEditText.setText(addressModel.getDistrict_or_area_name());
            binding.cityNameEditText.setText(addressModel.getCity_name());
        }


    }

    @Override
    public void onClick(View v) {
        dialogCustom = new DialogCustom(this);
        dialogCustom.showDialog();
        if (v == binding.back) {
            onBackPressed();
        } else if (v == binding.saveAddressButton) {
            if ((!binding.houseAndApartmentEditText.getText().toString().equals("")) && (!binding.streetNameEditText.getText().toString().equals("")) && (!binding.districtEditText.getText().toString().equals("")) && (!binding.cityNameEditText.getText().toString().equals(""))) {
                if (addressModel == null) {
                    addressModel = new AddressModel(binding.cityNameEditText.getText().toString(), binding.districtEditText.getText().toString() , binding.houseAndApartmentEditText.getText().toString()  , binding.streetNameEditText.getText().toString() , "");
                    string=getString(R.string.address_added_successfully);
                } else {
                    addressModel = new AddressModel(binding.cityNameEditText.getText().toString(), binding.districtEditText.getText().toString() , binding.houseAndApartmentEditText.getText().toString()  , binding.streetNameEditText.getText().toString() , addressModel.getAddress_id());
                    string=getString(R.string.address_updated_successfully);
                }
                editShippingAddressViewModel.setNewAddress(addressModel);

            } else {
                Toast.makeText(getApplicationContext(), getString(R.string.please_fill_all_information), Toast.LENGTH_SHORT).show();
                dialogCustom.dismissDialog();
            }
        }



    }
}