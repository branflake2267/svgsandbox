package com.arcbees.com.client.svgdrag;

import java.util.ArrayList;

import org.vectomatic.dom.svg.OMSVGSVGElement;

import com.allen_sauer.gwt.dnd.client.DragContext;
import com.allen_sauer.gwt.dnd.client.util.DOMUtil;
import com.allen_sauer.gwt.dnd.client.util.DragClientBundle;
import com.allen_sauer.gwt.dnd.client.util.WidgetLocation;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class SvgPositionDropController extends SvgAbstractPositioningDropController {

  static class Draggable {
    public int desiredX;
    public int desiredY;

    public int relativeX;
    public int relativeY;

    final int offsetHeight;
    final int offsetWidth;

    final Widget widget;

    public Draggable(Widget widget) {
      this.widget = widget;
      offsetWidth = widget.getOffsetWidth();
      offsetHeight = widget.getOffsetHeight();
    }
  }

  final OMSVGSVGElement dropTarget;

  int dropTargetClientHeight;
  int dropTargetClientWidth;

  int dropTargetOffsetX;
  int dropTargetOffsetY;

  final ArrayList<Draggable> draggableList = new ArrayList<Draggable>();
  
  /**
   * Basic constructor.
   * 
   * @param svgRootAsTheDropTarget the absolute panel drop target
   */
  public SvgPositionDropController(OMSVGSVGElement svgRootAsTheDropTarget) {
    super(svgRootAsTheDropTarget);
    this.dropTarget = svgRootAsTheDropTarget;
  }

  @Override
  public void onDrop(DragContext context) {
    super.onDrop(context);
  }

  @Override
  public void onEnter(DragContext context) {
    super.onEnter(context);

    dropTargetClientWidth = DOMUtil.getClientWidth(dropTarget.getElement());
    dropTargetClientHeight = DOMUtil.getClientHeight(dropTarget.getElement());
    calcDropTargetOffset();

    int draggableAbsoluteLeft = context.draggable.getAbsoluteLeft();
    int draggableAbsoluteTop = context.draggable.getAbsoluteTop();
    for (Widget widget : context.selectedWidgets) {
      Draggable draggable = new Draggable(widget);
      draggable.relativeX = widget.getAbsoluteLeft() - draggableAbsoluteLeft;
      draggable.relativeY = widget.getAbsoluteTop() - draggableAbsoluteTop;
      draggableList.add(draggable);
    }
  }

  @Override
  public void onLeave(DragContext context) {
    super.onLeave(context);
    
    draggableList.clear();
  }

  @Override
  public void onMove(DragContext context) {
    super.onMove(context);
    
    for (Draggable draggable : draggableList) {
      draggable.desiredX = context.desiredDraggableX - dropTargetOffsetX + draggable.relativeX;
      draggable.desiredY = context.desiredDraggableY - dropTargetOffsetY + draggable.relativeY;
      draggable.desiredX = Math.max(0, Math.min(draggable.desiredX, dropTargetClientWidth - draggable.offsetWidth));
      draggable.desiredY = Math.max(0, Math.min(draggable.desiredY, dropTargetClientHeight - draggable.offsetHeight));
      
      System.out.println("x=" + draggable.desiredX + " y=" + draggable.desiredY);
      
      draggable.widget.getElement().setAttribute("x", Integer.toString(draggable.desiredX));
      draggable.widget.getElement().setAttribute("y", Integer.toString(draggable.desiredY));
    }

    // may have changed due to scrollIntoView() or user driven scrolling
    calcDropTargetOffset();
  }

  private void calcDropTargetOffset() {
    WidgetLocation dropTargetLocation = new WidgetLocation(dropTarget, null);
    dropTargetOffsetX = dropTargetLocation.getLeft() + DOMUtil.getBorderLeft(dropTarget.getElement());
    dropTargetOffsetY = dropTargetLocation.getTop() + DOMUtil.getBorderTop(dropTarget.getElement());
    //System.out.println(dropTargetOffsetX + ", " + dropTargetOffsetY);
  }
  
}
