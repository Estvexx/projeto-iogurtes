//v1.0
package com.empresa.iogurtes.gestaoiogurtes.desktop.bll.model.enums;

/**
 * Tipo de movimento de stock de matéria-prima.
 */
public enum TipoMovimentoMP {
    /** Recepção de matéria-prima; adiciona ao stock. */
    ENTRADA,
    /** Consumo; subtrai ao stock. */
    SAIDA,
    /** Ajuste manual; define o stock directamente. */
    AJUSTE
}
