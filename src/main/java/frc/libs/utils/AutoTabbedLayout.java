package frc.libs.utils;

import java.nio.charset.*;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.core.*;
import org.apache.logging.log4j.core.config.plugins.*;
import org.apache.logging.log4j.core.layout.*;

@Plugin(name = "AutoTabbedLayout", category = "Core", elementType = "layout", printObject = true)
public class AutoTabbedLayout extends AbstractStringLayout {
    protected AutoTabbedLayout(Charset charset)
    {
        super( charset );
    }

    @Override 
    public String toSerializable(LogEvent event)
    {
        int numberOfNestedLevels = getNumberOfNestedLevels(event.getMarker());
        String tabs = new String(new char[numberOfNestedLevels - 1]).replace("\0", "\t");

        String formattedMessage = event.getMessage().getFormattedMessage();
        String fullContextName = getFullMarkerName(event.getMarker());
        return tabs + fullContextName + " " + formattedMessage + System.lineSeparator();
    }

    public static String getFullMarkerName(Marker marker)
    {
        if (marker == null)
            return "";
        else if(marker.hasParents() && marker.getParents()[0] != null)
            return getFullMarkerName(marker.getParents()[0]) + "." + marker.getName();
        else
            return marker.getName();
    }

    private static int getNumberOfNestedLevels(Marker marker)
    {
        if (marker == null)
            return 0;
        else if(marker.hasParents())
            return getNumberOfNestedLevels(marker.getParents()[0]) + 1;
        else
            return 1;
    }

    @PluginFactory
    public static AutoTabbedLayout createPolicy(){
        return new AutoTabbedLayout(Charset.defaultCharset());
    }
}