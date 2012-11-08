package com.arcbees.com.client.draggable;

import java.util.ArrayList;

import com.allen_sauer.gwt.dnd.client.AbstractDragController;
import com.arcbees.com.client.svgdrag.SvgPositionDropController;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Widget;

public class SvgPickupDragController extends AbstractDragController {
  
  static class SelectedWidgets {
    private String style;
    
    final Widget widget;

    public SelectedWidgets(Widget widget) {
      this.widget = widget;
    }
    
    public void highlight(boolean highlight) {
      if (highlight) {
        style = widget.getElement().getAttribute("style");
        widget.getElement().getStyle().setProperty("stroke", "yellow");
        widget.getElement().getStyle().setProperty("strokeWidth", "3px");
        widget.getElement().getStyle().setProperty("fillOpacity", ".5");
      } else {
        widget.getElement().setAttribute("style", style.toString());
      }
    }
  }
  
  final ArrayList<SelectedWidgets> widgets = new ArrayList<SelectedWidgets>();
  private SvgPositionDropController dropController;

  public SvgPickupDragController(AbsolutePanel boundaryPanel) {
    super(boundaryPanel);
  }

  @Override
  public void dragStart() {
    super.dragStart();
    
    for (Widget widget : context.selectedWidgets) {
      SelectedWidgets selectedWidget = new SelectedWidgets(widget);
      widgets.add(selectedWidget);
      selectedWidget.highlight(true);
    }
    
    dropController.onEnter(context);
  }
  
  @Override
  public void dragMove() {
    dropController.onMove(context);
  }
  
  @Override
  public void dragEnd() {
    super.dragEnd();
    
    for (SelectedWidgets selWidget : widgets) {
      selWidget.highlight(false);
    }
    
    dropController.onLeave(context);
  }

  public void registerDropController(SvgPositionDropController dropController) {
    this.dropController = dropController;
  }

}