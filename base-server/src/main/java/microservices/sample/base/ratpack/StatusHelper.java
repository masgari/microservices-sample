package microservices.sample.base.ratpack;

import com.google.common.base.Strings;
import com.google.common.base.Throwables;
import io.netty.handler.codec.http.HttpResponseStatus;
import ratpack.handling.Context;
import ratpack.http.internal.DefaultStatus;

import static java.net.HttpURLConnection.*;

/**
 * Utility methods for sending http response codes to the clients.
 *
 * @author mamad
 * @since 15/03/15.
 */
public final class StatusHelper {
    private StatusHelper() {
    }

    public static void sendBadRequest(Context context, String message) {
        sendStatus(context, message, HTTP_BAD_REQUEST);
    }

    public static void sendInternalError(Context context, Throwable error) {
        sendStatus(context, Throwables.getStackTraceAsString(error), HTTP_INTERNAL_ERROR);
    }

    public static void sendNotImplemented(Context context, String message) {
        sendStatus(context, message, HTTP_NOT_IMPLEMENTED);
    }

    public static void sendNotFound(Context context, String id) {
        sendStatus(context, "There is no entity with id:" + id, HTTP_NOT_FOUND);
    }

    public static void sendStatus(Context context, String reasonPhrase, int statusCode) {
        sendStatus(context, new HttpResponseStatus(statusCode, Strings.nullToEmpty(reasonPhrase)));
    }

    public static void sendStatus(Context context, HttpResponseStatus status) {
        context.getResponse().status(new DefaultStatus(status)).send();
    }
}
