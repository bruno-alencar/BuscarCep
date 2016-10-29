package cep.com.example.fic.buscarcep.activity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import cep.com.example.fic.buscarcep.R;
import cep.com.example.fic.buscarcep.model.Cep;
import cep.com.example.fic.buscarcep.util.Consultar;

public class MainActivity extends AppCompatActivity {

    private EditText txtCep;
    private Cep cepConsultado;

    private EditText txtLogradouro;
    private EditText txtComplemento;
    private EditText txtBairro;
    private EditText txtCidade;
    private EditText txtEstado;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        StrictMode.ThreadPolicy.Builder regras = new StrictMode.ThreadPolicy.Builder();

        regras.detectAll();
        StrictMode.setThreadPolicy(regras.build());

        txtCep = (EditText) findViewById(R.id.txtCep);
        txtLogradouro = (EditText) findViewById(R.id.txtLogradouro);
        txtComplemento= (EditText) findViewById(R.id.txtComplemento);
        txtBairro = (EditText) findViewById(R.id.txtBairro);
        txtCidade = (EditText) findViewById(R.id.txtCidade);
        txtEstado = (EditText) findViewById(R.id.txtEstado);
    }



    public void buscarClick(View view){

        //Validar se o campo CEp está preenchido
        // Verificar se são 8 números

        String cep = txtCep.getText().toString();

        if(cep.length() == 8) {

            try {
                ConsultarCepAsyncTask pesquisador = new ConsultarCepAsyncTask();
                pesquisador.execute(cep);

                //Cep cepEncontrado = Consultar.peloCep(cep);



            } catch (Exception e) {
                e.printStackTrace();
            }

        }else{
            Toast.makeText(this, "Informe o cep com 8 números",Toast.LENGTH_SHORT).show();
            txtCep.requestFocus();
        }

    }

    public void preencherFormulario(){
        txtLogradouro.setText(cepConsultado.getLogradouro());
        txtBairro.setText(cepConsultado.getBairro());
        txtEstado.setText(cepConsultado.getUf());
        txtCidade.setText(cepConsultado.getLocalidade());
    }

    private class ConsultarCepAsyncTask extends AsyncTask<String, Void, Cep>{

        private ProgressDialog progresso;

        @Override
        protected void onPreExecute() {
            progresso = ProgressDialog.show(MainActivity.this, "Aguarde", "Pesquisando pelo CEP");
            try{
                Thread.sleep(3000);
                progresso.dismiss();
            }catch (Exception erro){

            }
        }

        @Override
        protected Cep doInBackground(String... params) {
            cepConsultado = null;
            try {
                cepConsultado = Consultar.peloCep(params[0]);


            }catch (Exception e){
                // fazer algo
            }

            return cepConsultado;
        }

        @Override
        protected void onPostExecute(Cep cep) {

            if (cep != null) {
                preencherFormulario();
            }
        }
    }
}
