package android.tamim.com.justjava;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {
    int quantity = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }



    public void increment(View view){
        if(quantity <= 100){
            quantity ++;
        }
        displayQuantity(quantity);
        //displayPrice(numberOfCoffees * 5);
    }

    public void decrement(View view){
        if(quantity > 1){
            quantity --;
        }
        displayQuantity(quantity);
        //displayPrice(numberOfCoffees * 5);
    }

    public int calculatePrice(int basePrice){
        return quantity * basePrice;
    }

    public String createOrderSummery(int price, boolean hasWhippedCream, boolean hasChocolate, String name){
        return getString(R.string.order_summary_name,name)+ "\nQuantity: " + quantity +"\nHas Whippedcream? " + hasWhippedCream +"\nHas Chocolate? " + hasChocolate + "\nPrice: $" + price + "\n" + getString(R.string.thank_you);
    }

    public void submitOrder(View view){
        CheckBox whippedCreamCheckBox = (CheckBox)findViewById(R.id.whipped_cream_check_box);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        CheckBox chocolateCheckBox = (CheckBox)findViewById(R.id.chocolate_check_box);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        int basePrice = 5;

        if(hasWhippedCream){
            basePrice += 1;
        }

        if(hasChocolate){
            basePrice += 2;
        }

        EditText nameEditText = (EditText)findViewById(R.id.name_edit_text);
        String name = nameEditText.getText().toString();

        int price = calculatePrice(basePrice);
        String message = createOrderSummery(price, hasWhippedCream, hasChocolate, name);
        displayQuantity(quantity);
        //displayPrice(message);

        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse("mailto:sjtamim.gfn@gmail.com"));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT,"Coffee Order for " + name);
        emailIntent.putExtra(Intent.EXTRA_TEXT, message);

       if(emailIntent.resolveActivity(getPackageManager()) != null){
           startActivity(emailIntent);
       }
    }

    private void displayQuantity(int number){
        TextView textView = (TextView)findViewById(R.id.quantity_text_view);
        textView.setText("" + number);
    }

//    private void displayPrice(String message){
//        TextView textView = (TextView)findViewById(R.id.order_summary_text_view);
//        //textView.setText(NumberFormat.getCurrencyInstance().format(number));
//        textView.setText(message);
//    }
}
