package edu.itchii.jsonapi;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.kevinsawicki.http.HttpRequest;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class EditarCampo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_campo);

        String campo2 = getIntent().getExtras().getString("campo2");
        TextView textView = (TextView) findViewById(R.id.txt_edicion);
        textView.setText(campo2);

        EditText editText = (EditText)findViewById(R.id.editText);
        editText.setText(campo2);


    }

    public void guardar(View v){
        // TODO metodo para guardar
        GuardarEnServidor guardarEnServidor = new GuardarEnServidor();
        guardarEnServidor.execute(
                "http://10.214.221.21/DADM/api/insertarCampo.php", // dirección API
                "APC", // id
                "Algebra para computadoras" //Nuevo nombre al campo
        );
        //finish();
    }

    private class GuardarEnServidor extends AsyncTask<String, Void, String >{

        @Override
        protected String doInBackground(String... params) {
            String resultado = "ERROR";
            String url = params[0];
            String id = params[1];
            String campo2 = params[2];
            String url_con_info = url + "?campo1=" + id + "&campo2=" + campo2;
            //"http://10.214.221.21/DADM/api/setCampos.php?id=1&campo2=Matemáticas";

            //HttpRequest api_guardar = HttpRequest.get(url_con_info);

            Map<String, String> map = new HashMap<>();
            map.put("campo1", id);
            map.put("campo2", campo2);

            HttpRequest api_guardar = HttpRequest.get(url,true, map);

            Log.d("TEST", url_con_info);

            if (api_guardar.ok()){
                resultado = api_guardar.body();
            }

            Log.d("TEST", resultado);
            return resultado;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            switch (s){
                case "OK":
                    Toast.makeText(getApplicationContext(),
                            "GUARDADO", Toast.LENGTH_LONG).show();
                    finish();
                    break;
            }
        }
    }
}
