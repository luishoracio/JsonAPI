package edu.itchii.jsonapi;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.kevinsawicki.http.HttpRequest;

import org.json.JSONArray;
import org.json.JSONException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    @Override
    protected void onStart() {
        super.onStart();
        GetAPI obtenerApi = new GetAPI();
        obtenerApi.execute("http://10.214.221.21/DADM/api/getCampos.php");

    }

    private class GetAPI extends AsyncTask<String, Void, JSONArray>{

        @Override
        protected JSONArray doInBackground(String... params) {
            JSONArray jsonArray = null;

            try{
                HttpRequest login_request = HttpRequest.get(params[0]);
                if (login_request.ok()){
                    try {
                        String jsonString = login_request.body();
                        Log.d("test", jsonString);
                        jsonArray = new JSONArray(jsonString);
                    }
                    catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            } catch (HttpRequest.HttpRequestException exception) {
                exception.printStackTrace();
            }

            return jsonArray;
        }

        @Override
        protected void onPostExecute(JSONArray resultado) {
            LinearLayout linearLayout =(LinearLayout) findViewById(R.id.contenedorMaterias);
            try {
                for (int i = 0; i < resultado.length(); i++){
                    RelativeLayout relativeLayout = (RelativeLayout) getLayoutInflater().inflate(R.layout.materias, null);
                    TextView textView1 = (TextView) relativeLayout.findViewById(R.id.textView);

                    final String campo2 = resultado.getJSONObject(i).getString("campo2");
                    textView1.setText(campo2);

                    Button button = (Button) relativeLayout.findViewById(R.id.btn_editar);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getApplicationContext(), EditarCampo.class);

                            intent.putExtra("campo2", campo2);

                            startActivity(intent);
                        }
                    });

                    linearLayout.addView(relativeLayout);
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}
