package com.example.qrgenerator;

import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import java.util.EnumMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_WRITE_EXTERNAL_STORAGE = 1;
    EditText link;
    ImageView QrImageHolder;
    Button generalButton;

    boolean isNotGenerated = true;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setIds();

        generalButton.setOnClickListener(new ButtonFunctions());


    }

    public void setIds(){
        link = findViewById(R.id.EditTextForLink);
        QrImageHolder = findViewById(R.id.ImageViewForQr);
        generalButton = findViewById(R.id.ButtonForQrGenerateAndDownload);
    }

    public void createQr(String link){

        try {
            // Generate QR code
            Map<EncodeHintType, Object> hints = new EnumMap<>(EncodeHintType.class);
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            BitMatrix bitMatrix = new QRCodeWriter().encode(link, BarcodeFormat.QR_CODE, 1024, 1024, hints);

            // Convert BitMatrix to Bitmap
            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    bitmap.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                }
            }

            // Set the generated QR code bitmap to the ImageView
            QrImageHolder.setImageBitmap(bitmap);

        } catch (WriterException e) {
            e.printStackTrace();
        }
    }



    private class ButtonFunctions implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            if(isNotGenerated) {
                String temp = link.getText().toString();
                createQr(temp);
            }
        }
    }
}