package com.arcbees.com.client;

import org.vectomatic.dom.svg.OMSVGRectElement;
import org.vectomatic.dom.svg.OMSVGSVGElement;

import com.allen_sauer.gwt.dnd.client.drop.AbsolutePositionDropController;
import com.arcbees.com.client.draggable.SvgPickupDragController;
import com.arcbees.com.client.svgdrag.SvgPositionDropController;
import com.arcbees.com.client.svgdrag.SvgDropController;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class SvgSandbox implements EntryPoint {

  private AbsolutePanel boundaryPanel;
  private SvgPickupDragController dragController;
  private OMSVGSVGElement svgRootAsTheDropTarget;

  @Override
  public void onModuleLoad() {
    boundaryPanel = new AbsolutePanel();
    boundaryPanel.setSize("100%", "100%");
    RootPanel.get().add(boundaryPanel);
    boundaryPanel.addStyleName("test1");
    
    draw();
    drawItems();
  }

  private void draw() {
    dragController = new SvgPickupDragController(boundaryPanel);
    
    // SVG root
    svgRootAsTheDropTarget = new OMSVGSVGElement();
    svgRootAsTheDropTarget.setSize("1000px", "1000px");
    boundaryPanel.add(svgRootAsTheDropTarget);
    
    // Drop Controller 
    SvgPositionDropController dropController = new SvgPositionDropController(svgRootAsTheDropTarget);
    dragController.registerDropController(dropController);
  }
  
  private void drawItems() {
    
    for (int i=1; i < 2; i++) {
      int x = i * 50;
      int y = i * 50;
      OMSVGRectElement svgRect = new OMSVGRectElement(x, y, 50, 50, 0, 0);
      //svgRect.getStyle().setSVGProperty(SVGConstants.CSS_STROKE_PROPERTY, SVGConstants.CSS_BLACK_VALUE);
      //svgRect.getStyle().setSVGProperty(SVGConstants.CSS_FILL_PROPERTY, SVGConstants.CSS_NONE_VALUE);
      dragController.makeDraggable(svgRect);
      svgRootAsTheDropTarget.add(svgRect);
    }
    
  }

}
