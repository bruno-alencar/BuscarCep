package cep.com.example.fic.buscarcep.util;

import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import cep.com.example.fic.buscarcep.model.Cep;


public class Consultar {
    public static Cep peloCep(String cepParaPesquisar) throws Exception{
            Cep cepEncontrado = null;

            URL endereco = new URL("https://viacep.com.br/ws/"+cepParaPesquisar+"/json/");

            Scanner leitor = new Scanner(endereco.openConnection().getInputStream());

            StringBuilder stringGrande = new StringBuilder();

            while (leitor.hasNextLine()) {
                stringGrande.append(leitor.nextLine());
            }

            leitor.close();

            Gson conversor = new Gson();
            cepEncontrado = conversor.fromJson(stringGrande.toString(), Cep.class);

        return cepEncontrado;
    }
}
