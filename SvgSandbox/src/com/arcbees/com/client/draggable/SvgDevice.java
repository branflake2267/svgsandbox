package com.arcbees.com.client.draggable;

import org.vectomatic.dom.svg.OMSVGSVGElement;

import com.google.gwt.user.client.ui.Widget;

public class SvgDevice extends OMSVGSVGElement {

  public SvgDevice() {
    super();
  }
  
  @Override
  public void add(Widget w) {
    super.add(w);
  }

  @Override
  public void insert(Widget w, int beforeIndex) {
    if (beforeIndex == getWidgetCount()) {
      beforeIndex--;
    }
    super.insertChild(w, beforeIndex);
  }
  
}
