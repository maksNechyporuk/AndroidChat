package com.example.shop;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner spinner;
    ArrayList spinnerArrayList;
    ArrayAdapter spinnerAdapter;

    HashMap goodsMap;
    String goodsName;
    int Quantity=0;
    double Price=0;
    EditText userNameEditText;
    EditText userEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userNameEditText = findViewById(R.id.nameEditText);
        userEmail=findViewById(R.id.userEmail);
        createSpinner();
       createMap();
    }
    public void IncrementQuantity(View view)
    {
        Quantity+=1;
        ChangePriceAndQuantity();
    }

    public void DecrementQuantity(View view)
    {
    if(Quantity>0)
    {
        Quantity-=1;
        ChangePriceAndQuantity();
    }
    }

    public void ChangePriceAndQuantity()
    {
        TextView quantityTV=findViewById(R.id.quantityTextView);
        quantityTV.setText("" + Quantity);
        TextView priceTextView = findViewById(R.id.priceTextView);
        priceTextView.setText("" + Quantity * Price);
    }

    void createSpinner() {
        spinner = findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);
        spinnerArrayList = new ArrayList();

        spinnerArrayList.add("tshort");
        spinnerArrayList.add("jacket");
        spinnerArrayList.add("sweater");

        spinnerAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, spinnerArrayList);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
    }

    void createMap() {
        goodsMap = new HashMap();
        goodsMap.put("tshort", 10.0);
        goodsMap.put("jacket", 100.0);
        goodsMap.put("sweater", 20.0);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        goodsName = spinner.getSelectedItem().toString();
        Price = (double)goodsMap.get(goodsName);
        Quantity=0;
        ChangePriceAndQuantity();
        ImageView goodsImageView = findViewById(R.id.ProductImage);
        switch (goodsName) {
            case "tshort":
                goodsImageView.setImageResource(R.drawable.tshort);
                break;
            case "jacket":
                goodsImageView.setImageResource(R.drawable.jacket);
                break;
            case "sweater":
                goodsImageView.setImageResource(R.drawable.sweter);
                break;
            default:
                goodsImageView.setImageResource(R.drawable.tshort);
                break;
        }

    }
public void AddToCart(View view)
{

    Order order = new Order();


//Order order=new Order();
order.setPrice(Price);
order.setProductName(goodsName);
order.setQuantity(Quantity);
order.setUserName(""+userNameEditText.getText());
order.setTotalPrice(Price*Quantity);
order.setUserEmail(""+userEmail.getText());
TextView cart=findViewById(R.id.Cart);

    Intent orderIntent = new Intent(MainActivity.this, OrderActivity.class);
    orderIntent.putExtra("Email",order.getUserEmail());
    orderIntent.putExtra("order","Product name:"+ order.getProductName()+"\n"+"User name:"+order.getUserName()+"\n"
+ "Price:"+order.getPrice()+"\n"+"Quantity:"+order.getQuantity()+"\n"+"Total price:"+order.getTotalPrice()+"\n");



    startActivity(orderIntent);
}

@Override
public void onNothingSelected(AdapterView<?> parent) {

    }
}