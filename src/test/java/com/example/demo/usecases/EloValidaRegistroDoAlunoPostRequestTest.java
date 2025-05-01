package com.example.demo.usecases;

import com.example.demo.domains.ContextoDeCadeia;
import com.example.demo.gateways.requests.AlunoPostRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EloValidaRegistroDoAlunoPostRequestTest {

    private final EloValidaRegistroDoAlunoPostRequest elo = new EloValidaRegistroDoAlunoPostRequest();

    @Mock
    ContextoDeCadeia contextoMock;

    @Mock
    AlunoPostRequest alunoPostRequest;

    @Test
    void deveAprovarQuandoRegistroEhNumerico() {
        when(alunoPostRequest.registro()).thenReturn("123456");
        when(contextoMock.getAlunoPostRequest()).thenReturn(alunoPostRequest);

        elo.handle(contextoMock);

        verify(contextoMock).addApproval(eq(EloValidaRegistroDoAlunoPostRequest.class), eq("registroValidado"), eq(true));
    }

    @Test
    void deveReprovarQuandoRegistroNaoEhNumerico() {
        when(alunoPostRequest.registro()).thenReturn("abc123");
        when(contextoMock.getAlunoPostRequest()).thenReturn(alunoPostRequest);

        elo.handle(contextoMock);

        verify(contextoMock).addApproval(eq(EloValidaRegistroDoAlunoPostRequest.class), eq("registroNaoNumerico"), eq(false));
    }
}