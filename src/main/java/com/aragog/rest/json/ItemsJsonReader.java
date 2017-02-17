package com.aragog.rest.json;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.Provider;

import com.aragog.datamodel.Item;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

/**
 * Deserializes an HTTP body entity stream represented in json format into a java object.
 *
 */
@Provider
public class ItemsJsonReader implements MessageBodyReader<List<Item>> {

    private static final Type EXPECTED_TYPE = new TypeToken<List<Item>>() {}.getType();

    private static final String DEFAULT_CHAR_SET = "ISO-8859-1";

    public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations,
            MediaType mediaType) {
        Type[] types = null;

        return mediaType.isCompatible(MediaType.APPLICATION_JSON_TYPE)
                && List.class.isAssignableFrom(type) && ((genericType instanceof ParameterizedType)
                        ? ((types = ((ParameterizedType) genericType)
                                .getActualTypeArguments()).length == 1)
                                && Item.class.isAssignableFrom((Class<?>) types[0])
                        : false);

    }

    public List<Item> readFrom(Class<List<Item>> type, Type genericType, Annotation[] annotations,
            MediaType mediaType, MultivaluedMap<String, String> httpHeaders,
            InputStream entityStream) throws IOException, WebApplicationException {

        final Map<String, String> properties = mediaType.getParameters();
        String charset = properties.get("charset"); //$NON-NLS-1$

        if (charset == null || charset.trim().length() <= 0) {
            charset = DEFAULT_CHAR_SET;
        }

        List<Item> items = null;

        try {
            InputStreamReader inputStreamReader = new InputStreamReader(entityStream, charset);

            items = new Gson().fromJson(inputStreamReader, EXPECTED_TYPE);

        } catch (final JsonParseException jsonParseException) {
            jsonParseException.printStackTrace();
            throw new WebApplicationException(jsonParseException,
                    Response.status(Status.BAD_REQUEST).entity("Invalid JSON message.")
                            .type(MediaType.TEXT_PLAIN_TYPE).build());
        } catch (final UnsupportedEncodingException unsupportedEncodingException) {
            final StringBuilder builder = new StringBuilder(75);
            builder.append(charset);
            builder.append(" - ");
            builder.append("Invalid character set.");
            throw new WebApplicationException(unsupportedEncodingException,
                    Response.status(Status.UNSUPPORTED_MEDIA_TYPE).entity(builder.toString())
                            .type(MediaType.TEXT_PLAIN_TYPE).build());
        }

        return items;
    }

}
