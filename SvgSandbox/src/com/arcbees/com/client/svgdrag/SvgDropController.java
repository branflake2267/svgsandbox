package com.arcbees.com.client.svgdrag;

import org.vectomatic.dom.svg.OMSVGElement;

import com.allen_sauer.gwt.dnd.client.DragContext;
import com.allen_sauer.gwt.dnd.client.util.DragClientBundle;
import com.allen_sauer.gwt.dnd.client.util.LocationWidgetComparator;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

public class SvgDropController extends SvgAbstractInsertPanelDropController {

  public SvgDropController(OMSVGElement dropTarget) {
    super(dropTarget);
  }

  @Override
  protected LocationWidgetComparator getLocationWidgetComparator() {
    return LocationWidgetComparator.BOTTOM_RIGHT_COMPARATOR;
  }

  @Override
  protected Widget newPositioner(DragContext context) {
    HTML positioner = new HTML("&#x203B;");
    positioner.addStyleName(DragClientBundle.INSTANCE.css().flowPanelPositioner());
    return positioner;
  }
}