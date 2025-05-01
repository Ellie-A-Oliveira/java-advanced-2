package com.example.demo.usecases;

import com.example.demo.domains.ContextoDeCadeia;
import com.example.demo.gateways.requests.AlunoPostRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CadeiaDeResponsabilidadeDeValidacaoDoAlunoPostRequestTest {

    @Mock
    EloValidadorDoAlunoPostRequest elo1;

    @Mock
    EloValidadorDoAlunoPostRequest elo2;

    @InjectMocks
    CadeiaDeResponsabilidadeDeValidacaoDoAlunoPostRequest cadeia;

    @Test
    void deveExecutarTodosOsElosNaOrdem() {
        ContextoDeCadeia contextoInicial = mock(ContextoDeCadeia.class);
        ContextoDeCadeia contextoIntermediario = mock(ContextoDeCadeia.class);
        ContextoDeCadeia contextoFinal = mock(ContextoDeCadeia.class);

        when(elo1.handle(contextoInicial)).thenReturn(contextoIntermediario);
        when(elo2.handle(contextoIntermediario)).thenReturn(contextoFinal);

        cadeia = new CadeiaDeResponsabilidadeDeValidacaoDoAlunoPostRequest(List.of(elo1, elo2));

        ContextoDeCadeia resultado = cadeia.handle(contextoInicial);

        verify(elo1).handle(contextoInicial);
        verify(elo2).handle(contextoIntermediario);
        assertEquals(contextoFinal, resultado);
    }

    @Test
    void deveRetornarMesmoContextoQuandoNaoHaElos() {
        ContextoDeCadeia contexto = mock(ContextoDeCadeia.class);

        CadeiaDeResponsabilidadeDeValidacaoDoAlunoPostRequest cadeia =
                new CadeiaDeResponsabilidadeDeValidacaoDoAlunoPostRequest(Collections.emptyList());

        ContextoDeCadeia resultado = cadeia.handle(contexto);

        assertSame(contexto, resultado, "Quando não há elos, o contexto retornado deve ser o mesmo");
    }
}