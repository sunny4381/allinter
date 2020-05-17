package allinter.cdt;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.github.kklisura.cdt.protocol.commands.DOM;
import com.github.kklisura.cdt.protocol.commands.Emulation;
import com.github.kklisura.cdt.protocol.commands.Page;
import com.github.kklisura.cdt.protocol.commands.Runtime;
import com.github.kklisura.cdt.protocol.support.types.EventListener;
import com.github.kklisura.cdt.protocol.types.page.CaptureScreenshotFormat;
import com.github.kklisura.cdt.protocol.types.page.LayoutMetrics;
import com.github.kklisura.cdt.protocol.types.page.Navigate;
import com.github.kklisura.cdt.protocol.types.page.Viewport;
import com.github.kklisura.cdt.protocol.types.runtime.*;
import com.github.kklisura.cdt.protocol.types.runtime.Properties;
import com.github.kklisura.cdt.services.ChromeDevToolsService;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

public class Util {
    public static Navigate navigateAndWait(final ChromeDevToolsService service, final String url, final long timeoutMillis) throws InterruptedException {
        final Page page = service.getPage();
        page.enable();
        page.setLifecycleEventsEnabled(true);

        return navigateAndWait(service, page, url, timeoutMillis);
    }

    public static Navigate navigateAndWait(final ChromeDevToolsService service, final Page page, final String url, final long timeoutMillis) throws InterruptedException {
        final Object lock = new Object();
        final EventListener eventListener = page.onLoadEventFired(event -> {
            synchronized (lock) {
                lock.notify();
            }
        });

        try {
            final Navigate navigate = page.navigate(url);
            if (navigate == null) {
                throw new RuntimeException("destination unreachable");
            }

            synchronized (lock) {
                lock.wait(timeoutMillis);
            }

            return navigate;
        } finally {
            service.removeEventListener(eventListener);
        }
    }

    private static Object getResultAndRelease(final ChromeDevToolsService service, RemoteObject remoteObject) {
        if (remoteObject == null) {
            return null;
        }

        final String objectId = remoteObject.getObjectId();
        final Object ret;
        switch(remoteObject.getType()) {
        case UNDEFINED:
            ret = null;
            break;
        case FUNCTION:
            ret = JsonNodeFactory.instance.objectNode();
            break;
        case OBJECT:
            if (remoteObject.getSubtype() == null) {
                // function, object or hash
                ret = buildHash(service, objectId);
            } else {
                switch (remoteObject.getSubtype()) {
                case NULL:
                    ret = null;
                    break;
                case DATE:
                    ret = remoteObject.getDescription();
                    break;
                case NODE:
                    final DOM dom = service.getDOM();
                    final Integer nodeId = dom.requestNode(objectId);
                    ret = dom.describeNode(nodeId, null, null, null, Boolean.FALSE);
                    break;
                case ARRAY:
                    ret = buildArray(service, objectId);
                    break;
                default:
                    // function, object or hash
                    ret = buildHash(service, objectId);
                    break;
                }
            }
            break;
        default:
            ret = remoteObject.getValue();
            break;
        }

        if (objectId != null) {
            service.getRuntime().releaseObject(objectId);
        }

        return ret;
    }

    private static Object[] buildArray(final ChromeDevToolsService service, final String objectId) {
        ArrayList<Object> ret = new ArrayList<>();
        eachProperties(service, objectId, (key, value) -> {
            try {
                Integer.parseInt(key);
            } catch (NumberFormatException e) {
                // not an item of array because key is not an index number.
                return;
            }

            ret.add(getResultAndRelease(service, value));
        });

        return ret.toArray(new Object[ret.size()]);
    }

    private static Map<String, Object> buildHash(final ChromeDevToolsService service, final String objectId) {
        HashMap<String, Object> ret = new HashMap<>();
        eachProperties(service, objectId, (key, value) -> {
            ret.put(key, getResultAndRelease(service, value));
        });

        return ret;
    }

    private interface EachPropertiesCallback {
        void call(String key, RemoteObject value);
    }

    private static void eachProperties(final ChromeDevToolsService service, final String objectId, final EachPropertiesCallback callback) {
        final Properties properties = service.getRuntime().getProperties(objectId);
        for (PropertyDescriptor propDesc : properties.getResult()) {
            if (! propDesc.getEnumerable()) {
                continue;
            }

            callback.call(propDesc.getName(), propDesc.getValue());
        }
    }

    private static void raiseError(final ChromeDevToolsService service, final ExceptionDetails exceptionDetails) {
        if (exceptionDetails == null) {
            return;
        }

        final RemoteObject remoteObject = exceptionDetails.getException();
        if (remoteObject == null) {
            return;
        }

        final String error = remoteObject.getDescription();
        final String objectId = remoteObject.getObjectId();

        if (objectId != null) {
            service.getRuntime().releaseObject(objectId);
        }

        if (error != null) {
            throw new RuntimeException(error);
        }
    }

    public static Object evaluate(final ChromeDevToolsService service, final String expression) {
        final Runtime runtime = service.getRuntime();
        runtime.enable();

        final Evaluate evaluate = runtime.evaluate(expression);
        if (evaluate == null) {
            return null;
        }

        final Object ret = getResultAndRelease(service, evaluate.getResult());
        raiseError(service, evaluate.getExceptionDetails());

        return ret;
    }

    public static Object getPropertyByObjectId(final ChromeDevToolsService service, final String objectId, final String propertyName) {
        final Runtime runtime = service.getRuntime();
        runtime.enable();

        final CallArgument argument = new CallArgument();
        argument.setValue(propertyName);

        final CallFunctionOn callFunctionOn = runtime.callFunctionOn(
                "function(property) { return property.split('.').reduce((o, i) => o[i], this); }",
                objectId,
                Collections.singletonList(argument),
                Boolean.FALSE, Boolean.TRUE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, null, null);

        if (callFunctionOn == null) {
            return null;
        }

        final Object ret = getResultAndRelease(service, callFunctionOn.getResult());
        raiseError(service, callFunctionOn.getExceptionDetails());

        return ret;
    }

    public static byte[] takeScreenshot(final ChromeDevToolsService service) {
        final Page page = service.getPage();
        page.enable();

        final LayoutMetrics layoutMetrics = page.getLayoutMetrics();

        final Double width = layoutMetrics.getContentSize().getWidth();
        final Double height = layoutMetrics.getContentSize().getHeight();

        final Emulation emulation = service.getEmulation();
        emulation.setDeviceMetricsOverride(width.intValue(), height.intValue(), 1.0d, Boolean.FALSE);

        Viewport viewport = new Viewport();
        viewport.setScale(1d);

        // You can set offset with X, Y
        viewport.setX(0d);
        viewport.setY(0d);

        // Set a width, height of a page to take screenshot at
        viewport.setWidth(width);
        viewport.setHeight(height);

        final String base64ImageData = page.captureScreenshot(CaptureScreenshotFormat.PNG, 100, viewport, Boolean.TRUE);
        return Base64.getDecoder().decode(base64ImageData);
    }

    public static void saveScreenshot(final ChromeDevToolsService service, final String outputFilename) throws IOException {
        final byte[] data = takeScreenshot(service);
        try (FileOutputStream fileOutputStream = new FileOutputStream(outputFilename)) {
            fileOutputStream.write(data);
        }
    }
}
