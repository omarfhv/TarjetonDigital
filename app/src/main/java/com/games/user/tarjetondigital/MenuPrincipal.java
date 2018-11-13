package com.games.user.tarjetondigital;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class MenuPrincipal extends AppCompatActivity {

    InterstitialAd mInterstitialAd;
    AdView mAdView;
    ImageView imv2, movil;
    Boolean confirmacion;
    PDFAdapter obj_adapter;
    public static int REQUEST_PERMISSIONS = 1;
    boolean boolean_permission;
    ProgressBar progresbar;
    ImageView imv;

    File dir;


    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPref;
        sharedPref = getSharedPreferences("inicio", Context.MODE_PRIVATE);
        int califica = sharedPref.getInt("califica", 0);
        if (califica == 20) {
            dialogocalifica();
            califica = 0;
        } else
            califica++;

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("califica", califica);
        editor.apply();

        if (!sharedPref.getBoolean("inicio", false)) {
            final android.support.v7.app.AlertDialog.Builder constructor = new android.support.v7.app.AlertDialog.Builder(this);
            View vista = getLayoutInflater().inflate(R.layout.alert_dialog_inicio, null);
            constructor.setView(vista);
            final android.support.v7.app.AlertDialog dialogo = constructor.create();
            Button botonok = vista.findViewById(R.id.botonok);
            final CheckBox chbx = vista.findViewById(R.id.chbxdialog);
            TextView texto = vista.findViewById(R.id.txt);
            texto.setText(getString(R.string.mensajeinicio));
            botonok.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                               SharedPreferences sharedPref;
                                               sharedPref = getSharedPreferences(
                                                       "inicio", Context.MODE_PRIVATE);
                                               SharedPreferences.Editor editor = sharedPref.edit();
                                               editor.putBoolean("inicio", chbx.isChecked());
                                               editor.commit();
                                               dialogo.cancel();
                                           }
                                       }
            );
            dialogo.show();
        }

        setContentView(R.layout.activity_menu_principal);
        mAdView = findViewById(R.id.adView1);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        progresbar = findViewById(R.id.pgbr);
        imv = findViewById(R.id.imagevi);
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-1984616735532779/9679963023");
        AdRequest adRequest1 = new AdRequest.Builder().build();
        mInterstitialAd.loadAd(adRequest1);

    }

    private void dialogocalifica() {
        ColorDrawable dialogColor = new ColorDrawable(Color.GRAY);
        dialogColor.setAlpha(0);
        final AlertDialog.Builder builder = new AlertDialog.Builder(MenuPrincipal.this);
        final LayoutInflater inflater = getLayoutInflater();
        View vi = inflater.inflate(R.layout.dialogocalifica, null);
        builder.setView(vi);
        final AlertDialog dialog = builder.create();
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawable(dialogColor);
        Button botonsi = vi.findViewById(R.id.botonsi);
        botonsi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentae4 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.tarjetonimss.user.imsswebtarjeton"));
                startActivity(intentae4);
            }
        });
        Button botonno = vi.findViewById(R.id.botonno);
        botonno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.cancel();
            }
        });

        dialog.show();

    }

    public void calendario(View view) {

        Intent intent11 = new Intent(this, Calendario.class);
        startActivity(intent11);
    }

    public void activos(View view) {
        Intent intent1 = new Intent(this, SubMenuActivos.class);
        startActivity(intent1);
        // Intent intent1 = new Intent(Intent.ACTION_VIEW, Uri.parse("http://rh.imss.gob.mx/tarjetondigital/"));
        // startActivity(intent1);
    }

    public void jubilados(View view) {
        Intent intentae4 = new Intent(Intent.ACTION_VIEW, Uri.parse("http://rh.imss.gob.mx/tarjetonjubilados/(S(lpvgwevvhy0ja2padtk4t12e))/default.aspx"));
        startActivity(intentae4);
    }

    public void Compartir(View view) {

        if (ContextCompat.checkSelfPermission(MenuPrincipal.this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(MenuPrincipal.this,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            } else {

                ActivityCompat.requestPermissions(MenuPrincipal.this,
                        new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
            }
        }
        if (confirmacion = true) {


            Intent intento = new Intent(Intent.ACTION_SEND);
            intento.setType("*/*");
            String paramString1 = Integer.toString(R.drawable.logos);
            Bitmap topo2 = BitmapFactory.decodeResource(getResources(), R.drawable.logos);
            String fileName = paramString1 + "" + ".png";
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            topo2.compress(Bitmap.CompressFormat.PNG, 40, bytes);
            File ExternalStorageDirectory = Environment.getExternalStorageDirectory();
            File file = new File(ExternalStorageDirectory + File.separator + fileName);
            FileOutputStream fileOutputStream = null;
            try {
                file.createNewFile();
                fileOutputStream = new FileOutputStream(file);
                fileOutputStream.write(bytes.toByteArray());
            } catch (IOException e) {

            } finally {
                if (fileOutputStream != null) {
                    Uri bmpUri = Uri.parse(file.getPath());
                    intento.putExtra(Intent.EXTRA_TEXT, "En esta aplicación podrás descargar y ver tu tarjetón digital, veras sus promociones" + Html.fromHtml("<br />") +
                            "y recibirás notificaciones de cuando llegue el tarjetón , así como noticias relevantes del IMSS  " + Html.fromHtml("<br />") +
                            "https://play.google.com/store/apps/details?id=com.tarjetonimss.user.imsswebtarjeton");
                    intento.putExtra(
                            Intent.EXTRA_STREAM,
                            bmpUri);
                    startActivity(Intent.createChooser(intento,
                            "Siguenos en nuestra pagina "));
                }
            }
        } else {
            Toast.makeText(MenuPrincipal.this, "Gracias, bonito día", Toast.LENGTH_SHORT).show();
        }
    }


    public void documentos(View view) {
        Intent intent121 = new Intent(this, Documentos.class);
        startActivity(intent121);

    }

    public void noti(View view) {

        Intent intent08 = new Intent(this, Noticias.class);
        startActivity(intent08);
    }

    public void Ver(View view) {

        Toast toast3 = new Toast(getApplicationContext());
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast,
                (ViewGroup) findViewById(R.id.lytLayout));

        TextView txtMsg = layout.findViewById(R.id.txtMensaje);
        txtMsg.setText("Esto puede tardar unos segundos, favor de esperar " +
                "GRACIAS");

        toast3.setDuration(Toast.LENGTH_LONG);
        toast3.setView(layout);
        toast3.show();

        Intent intent11111 = new Intent(this, MainActivity.class);
        startActivity(intent11111);


    }

    public void promociones(View view) {

        Intent intent1281 = new Intent(this, Promociones.class);
        startActivity(intent1281);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            LayoutInflater inflater = getLayoutInflater();
            View vi = inflater.inflate(R.layout.dialogoconfirm, null);
            builder.setView(vi);
            final AlertDialog dialog = builder.create();
            //decidir despues si sera cancelable o no
            dialog.setCancelable(false);
            Button botonsi = vi.findViewById(R.id.botonsi);
            botonsi.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.cancel();
                            MenuPrincipal.super.onDestroy();
                            System.exit(0);
                        }
                    }
            );
            Button botonno = vi.findViewById(R.id.botonno);
            botonno.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.cancel();

                        }
                    }
            );
            dialog.show();
            //Metodos.dialogo( this, getLayoutInflater(), "¿seguro deseas salir de la aplicacion?", 0 );
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        super.onResume();
        imv.setVisibility(View.GONE);
        progresbar.setVisibility(View.GONE);
        setTitle("Tarjeton Digital");
    }

    @Override
    protected void onPause() {
        super.onPause();
        imv.setVisibility(View.VISIBLE);
        progresbar.setVisibility(View.VISIBLE);
        setTitle("Cargando");
    }

    @Override
    protected void onStop() {
        super.onStop();
        imv.setVisibility(View.VISIBLE);
        progresbar.setVisibility(View.VISIBLE);
        setTitle("Cargando");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.consulta:
                //metodoAdd()
                Intent intentae7 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.games.user.consultatarjetondigital"));
                startActivity(intentae7);
                return true;
            case R.id.agenda:
                //metodoSearch()
                Intent intentae5= new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.games.user.agendaimss"));
                startActivity(intentae5);
                return true;
            case R.id.diagnostico:
                //metodoEdit()
                Intent intentae6 = new Intent(Intent.ACTION_VIEW, Uri.parse("http://rh.imss.gob.mx/tarjetonjubilados/(S(lpvgwevvhy0ja2padtk4t12e))/default.aspx"));
                startActivity(intentae6);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

}
