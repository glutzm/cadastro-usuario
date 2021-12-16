package com.example.springbackend.util;

import org.springframework.stereotype.Component;

@Component
public class CpfValidator {

    public boolean validate(String cpf) {
        int digito1Aux = 0, digito2Aux = 0, digitoCpf;
        int digito1 = 0, digito2 = 0, restoDivisao = 0;
        String digitoVerificador, digitoResultado;

        try{
            cpf = cpf.replace('.',' ');
            cpf = cpf.replace('-',' ');
            cpf = cpf.replaceAll(" ","");
            for (int counter = 1; counter < cpf.length() -1; counter++) {
                digitoCpf = Integer.parseInt(cpf.substring(counter - 1, counter));
                digito1Aux = digito1Aux + (11 - counter) * digitoCpf;
                digito2Aux = digito2Aux + (12 - counter) * digitoCpf;
            }
            restoDivisao = (digito1Aux % 11);
            if (restoDivisao >= 2) {
                digito1 = 11 - restoDivisao;
            }
            digito2Aux += 2 * digito1;
            restoDivisao = (digito2Aux % 11);
            if (restoDivisao >= 2) {
                digito2 = 11 - restoDivisao;
            }
            digitoVerificador = cpf.substring(cpf.length()-2, cpf.length());
            digitoResultado = String.valueOf(digito1) + String.valueOf(digito2);
            return digitoVerificador.equals(digitoResultado);
        } catch (Exception e) {
            return false;
        }
    }
}
