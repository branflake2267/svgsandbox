package com.arcbees.com.client.svgdrag;

import java.util.ArrayList;

import org.vectomatic.dom.svg.OMSVGSVGElement;

import com.allen_sauer.gwt.dnd.client.DragContext;
import com.allen_sauer.gwt.dnd.client.util.DOMUtil;
import com.allen_sauer.gwt.dnd.client.util.WidgetLocation;
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
    
    @Override
    public String toString() {
      String s = "";
      s += "desired xy=" + desiredX + "," + desiredY + " ";
      s += "relative xy=" + relativeX + "," + relativeY + " ";
      s += "offset hw=" + offsetHeight + ", " + offsetWidth + " ";
      return s;
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
    assert draggableList.size() == 0;
    
    calcDropTargetOffset();

    String width = ComputedStyle.getStyleProperty(dropTarget.getElement(), "width").replace("px", "");
    String height = ComputedStyle.getStyleProperty(dropTarget.getElement(), "height").replace("px", "");
    dropTargetClientWidth = Integer.parseInt(width);
    dropTargetClientHeight = Integer.parseInt(height);
    
    int draggableAbsoluteLeft = context.draggable.getAbsoluteLeft();
    int draggableAbsoluteTop = context.draggable.getAbsoluteTop();
    
    for (Widget widget : context.selectedWidgets) {
      Draggable draggable = new Draggable(widget);
      draggable.relativeX = widget.getAbsoluteLeft() - draggableAbsoluteLeft;
      draggable.relativeY = widget.getAbsoluteTop() - draggableAbsoluteTop;
      //System.out.println("OnEnter.draggable.relative=" + draggable.relativeX + "," + draggable.relativeY);
      
      draggableList.add(draggable);
      
      print("onEnter ", context, draggable);
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
      //System.out.println("OnMove.1.draggable.desired=" + draggable.desiredX + "," + draggable.desiredY);
      
      draggable.desiredX = Math.max(0, Math.min(draggable.desiredX, dropTargetClientWidth - draggable.offsetWidth));
      draggable.desiredY = Math.max(0, Math.min(draggable.desiredY, dropTargetClientHeight - draggable.offsetHeight));
      //System.out.println("OnMove.2.draggable.desired=" + draggable.desiredX + "," + draggable.desiredY);
      
      // set position
      draggable.widget.getElement().setAttribute("x", Integer.toString(draggable.desiredX));
      draggable.widget.getElement().setAttribute("y", Integer.toString(draggable.desiredY));
      
      print("onMove ", context, draggable);
    }

    // may have changed due to scrollIntoView() or user driven scrolling
    //calcDropTargetOffset();
  }
  
  private void calcDropTargetOffset() {
    //WidgetLocation dropTargetLocation = new WidgetLocation(dropTarget, null);
    dropTargetOffsetX = dropTarget.getParent().getAbsoluteLeft();
    dropTargetOffsetY = dropTarget.getParent().getAbsoluteTop();
    System.out.println("calcDropTargetOffset(): " + dropTargetOffsetX + ", " + dropTargetOffsetY);
  }
  
  private void print(String m, DragContext context, Draggable draggable) {
    String s = "" + m + " ";
    s += "context.mouse=" + context.mouseX + "," + context.mouseY + " ";
    s += "context.desired=" + context.desiredDraggableX + "," + context.desiredDraggableY + " ";
    s += "target=" + dropTargetOffsetX + "," + dropTargetOffsetY + " ";
    s += " clientwh=" + dropTargetClientWidth + ", " + dropTargetClientHeight + " ";
    s += " draggable.desired=" + draggable.desiredX + "," + draggable.desiredY + " ";
    s += " draggable.relative=" + draggable.relativeX + "," + draggable.relativeY + " ";
    s += " draggable.offsetWH=" + draggable.offsetWidth + "," + draggable.offsetHeight + " ";
    
    System.out.println(s);
  }
}
