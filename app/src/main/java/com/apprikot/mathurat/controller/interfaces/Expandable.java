package com.apprikot.mathurat.controller.interfaces;

import com.apprikot.mathurat.controller.interfaces.Listable;

import java.util.List;

public interface Expandable extends Listable {
    List<Listable> getSubItems();

    void appendSubItems(List<? extends Listable> items, long headerId);

    boolean isExpanded();

    void toggleExpansion();
}
