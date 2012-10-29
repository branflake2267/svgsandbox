package com.arcbees.com.client.svgdrag;

import com.allen_sauer.gwt.dnd.client.DragContext;
import com.allen_sauer.gwt.dnd.client.VetoDragException;
import com.allen_sauer.gwt.dnd.client.drop.AbstractInsertPanelDropController;
import com.allen_sauer.gwt.dnd.client.drop.FlowPanelDropController;
import com.allen_sauer.gwt.dnd.client.util.CoordinateLocation;
import com.allen_sauer.gwt.dnd.client.util.DOMUtil;
import com.allen_sauer.gwt.dnd.client.util.LocationWidgetComparator;
import com.google.gwt.user.client.ui.InsertPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

public abstract class SvgAbstractInsertPanelDropController extends SvgAbstractPositioningDropController {

  /**
   * Our drop target.
   */
  protected final InsertPanel dropTarget;

  private int dropIndex;

  private Widget positioner = null;

  /**
   * @see FlowPanelDropController#FlowPanelDropController(com.google.gwt.user.client.ui.FlowPanel)
   * 
   * @param dropTarget the insert panel drop target
   */
  public SvgAbstractInsertPanelDropController(InsertPanel dropTarget) {
    super((Panel) dropTarget);
    this.dropTarget = dropTarget;
  }

  @Override
  public void onDrop(DragContext context) {
    assert dropIndex != -1 : "Should not happen after onPreviewDrop did not veto";
    for (Widget widget : context.selectedWidgets) {
      dropTarget.insert(widget, dropIndex);
      // Works with and without drag proxy
      dropIndex = dropTarget.getWidgetIndex(widget) + 1;
    }
    super.onDrop(context);
  }

  @Override
  public void onEnter(DragContext context) {
    super.onEnter(context);
    positioner = newPositioner(context);
    int targetIndex = DOMUtil.findIntersect(dropTarget, new CoordinateLocation(context.mouseX, context.mouseY), getLocationWidgetComparator());
    dropTarget.insert(positioner, targetIndex);
  }

  @Override
  public void onLeave(DragContext context) {
    positioner.removeFromParent();
    positioner = null;
    super.onLeave(context);
  }

  @Override
  public void onMove(DragContext context) {
    super.onMove(context);
    int targetIndex = DOMUtil.findIntersect(dropTarget, new CoordinateLocation(context.mouseX, context.mouseY), getLocationWidgetComparator());

    // check that positioner not already in the correct location
    int positionerIndex = dropTarget.getWidgetIndex(positioner);

    if (positionerIndex != targetIndex && (positionerIndex != targetIndex - 1 || targetIndex == 0)) {
      if (positionerIndex == 0 && dropTarget.getWidgetCount() == 1) {
        // do nothing, the positioner is the only widget
      } else if (targetIndex == -1) {
        // outside drop target, so remove positioner to indicate a drop will not happen
        positioner.removeFromParent();
      } else {
        dropTarget.insert(positioner, targetIndex);
      }
    }
  }

  @Override
  public void onPreviewDrop(DragContext context) throws VetoDragException {
    dropIndex = dropTarget.getWidgetIndex(positioner);
    if (dropIndex == -1) {
      throw new VetoDragException();
    }
    super.onPreviewDrop(context);
  }

  /**
   * Required implementation method which provides the desired comparator strategy.
   * @return the comparator strategy to be used
   */
  protected abstract LocationWidgetComparator getLocationWidgetComparator();

  /**
   * Called by {@link AbstractInsertPanelDropController#onEnter(DragContext)} to create a new
   * positioner widget for this {@link InsertPanel} drop target. Override this method to customize
   * the look and feel of
   * your positioner. The positioner widget may not have any CSS borders or margins, although there
   * are no such restrictions on the children of the positioner widget. If borders and/or margins
   * are desired, wrap that widget in a {@link com.google.gwt.user.client.ui.SimplePanel} with a
   * <code>0px</code> border and margin.
   * 
   * @param context The current drag context.
   * @return a new positioner widget
   */
  protected abstract Widget newPositioner(DragContext context);
}
