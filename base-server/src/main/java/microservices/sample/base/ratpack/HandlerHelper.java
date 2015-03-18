package microservices.sample.base.ratpack;

import com.google.gson.Gson;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import microservices.sample.InvalidValueException;
import microservices.sample.ResourceNotFoundException;
import ratpack.exec.Fulfiller;
import ratpack.func.Action;
import ratpack.handling.Context;
import ratpack.http.TypedData;

import java.net.HttpURLConnection;
import java.util.function.Consumer;

/**
 * Helper class for reading and writing json objects in ratpack handlers.
 *
 * @author mamad
 * @since 15/03/15.
 */
@Singleton
public class HandlerHelper {
    public static final String JSON_MIME_TYPE = "application/json";
    private final Gson gson;

    @Inject
    public HandlerHelper(Gson gson) {
        this.gson = gson;
    }

    public static Action<String> newJsonAction(Context context) {
        return newJsonAction(context, HttpURLConnection.HTTP_OK);
    }

    /**
     * Return a lambda function which writes the input json string to the response and sets the mime type and response code.
     *
     * @param context        request/response context
     * @param httpStatusCode response code
     * @return lambda function
     */
    public static Action<String> newJsonAction(Context context, int httpStatusCode) {
        return json -> context
                .getResponse()
                .status(httpStatusCode)
                .send(JSON_MIME_TYPE, json);
    }

    public static Action<Throwable> newErrorAction(Context context) {
        return throwable -> {
            if (throwable instanceof InvalidValueException) {
                StatusHelper.sendBadRequest(context, throwable.getMessage());
            } else if (throwable instanceof ResourceNotFoundException) {
                StatusHelper.sendNotFound(context, ((ResourceNotFoundException) throwable).getId());
            } else {
                StatusHelper.sendInternalError(context, throwable);
            }
        };
    }

    public static void handleRequestWithPromise(Context context, Action<Fulfiller<String>> action) {
        context.promise(action).onError(HandlerHelper.newErrorAction(context)).then(HandlerHelper.newJsonAction(context));
    }


    public <T> T fromBody(Context context, Class<T> typeClass) {
        TypedData requestBody = context.getRequest().getBody();
        T requestObject = gson.fromJson(requestBody.getText(), typeClass);
        /**
         * enable leak detection: -Dio.netty.leakDetectionLevel=advanced or call  ResourceLeakDetector.setLevel()
         * see http://stackoverflow.com/a/25856285
         *
         * IMPORTANT: call release after every access to request body, otherwise there will be memory leak.
         * see http://netty.io/wiki/reference-counted-objects.html
         */
        requestBody.getBuffer().release();
        return requestObject;
    }

    /**
     * Creates a new lambda to convert the response object to json and pass it to the fulfiller
     *
     * @param fulfiller fulfiller of an asynchronous promise.
     * @param <T>       type of input object
     * @return lambda consumer
     */
    public <T> Consumer<T> newJsonConsumer(Fulfiller<String> fulfiller) {
        return response -> fulfiller.success(gson.toJson(response));
    }
}
