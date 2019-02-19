package frc.libs.utils;

import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.rolling.*;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;

@Plugin(name = "ForceTriggerPolicy", category = "Core")
public class ForceTriggerPolicy implements TriggeringPolicy {
private static boolean isRolling = false;

@Override
public void initialize(RollingFileManager arg0) {
    
}

@Override
public boolean isTriggeringEvent(LogEvent arg0) {
    if (isRolling) {
        isRolling = false;
        return true;
    }
    else {
        return false;
    }
}

public static void forceRollover() {
    isRolling = true;
}

@PluginFactory
public static ForceTriggerPolicy createPolicy(){
    return new ForceTriggerPolicy();
}

}