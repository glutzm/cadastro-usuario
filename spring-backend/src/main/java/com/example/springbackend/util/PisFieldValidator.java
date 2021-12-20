package com.example.springbackend.util;

public class PisFieldValidator {

    public static boolean validate(String pis) {
        int tamanho = 0;
        int totalizador = 0;
        int resto = 0;
        int multiplicando = 0;
        int multiplicador = 0;
        int digito = 99;
        StringBuilder aux;
        StringBuilder pesos = new StringBuilder("3298765432");

        aux = new StringBuilder().append(pis);
        tamanho = aux.length();

        if (tamanho != 11) {
            return false;
        }

        for (int i=0; i<10; i++) {

            multiplicando = Integer.parseInt(aux.substring(i, i+1));
            multiplicador = Integer.parseInt(pesos.substring(i, i+1));
            totalizador += multiplicando * multiplicador;
        }

        resto = 11 - totalizador % 11;
        resto = resto == 10 || resto == 11 ? 0 : resto;

        digito = Integer.parseInt("" + aux.charAt(10));
        return resto == digito;
    }
}
