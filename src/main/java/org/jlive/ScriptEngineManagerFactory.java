package org.jlive;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.InputStreamReader;
import java.util.List;


public class ScriptEngineManagerFactory {
    private static final Logger LOGGER = LoggerFactory.getLogger(ScriptEngineManagerFactory.class);
    private static final ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");

    public static void listEngines() {
        ScriptEngineManager manager = new ScriptEngineManager();
        List<ScriptEngineFactory> engines = manager.getEngineFactories();
        for (ScriptEngineFactory engine : engines) {
            LOGGER.info("Engine name: {}", engine.getEngineName());
            LOGGER.info("Version: {}", engine.getEngineVersion());
            LOGGER.info("Language: {}", engine.getLanguageName());

            LOGGER.info("Short Names:");
            for (String names : engine.getNames()) {
                LOGGER.info(names);
            }
        }
    }

    public static Object jvmBoundary(String filePath) {
        Object map = null;
        try {
            map = engine.eval(new InputStreamReader(ScriptEngineManagerFactory.class.getResourceAsStream(filePath)));
        } catch (ScriptException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return map;
    }

    public static void main(String[] args) throws ScriptException, NoSuchMethodException {
        listEngines();
        LOGGER.info("=== run example ===");
        LOGGER.info(jvmBoundary("/test.js").toString());
    }
}
