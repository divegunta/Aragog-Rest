package com.aragog.rest.json;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Map;

import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import com.aragog.datamodel.Item;
import com.google.gson.Gson;

/**
 * This class serializes a {@link Item} to JSON format from POJO.
 */
@Provider
@Produces(MediaType.APPLICATION_JSON)
public final class ItemsJsonWriter implements MessageBodyWriter<Collection<Item>> {

    public long getSize(Collection<Item> t, Class<?> type, Type genericType,
            Annotation[] annotations, MediaType mediaType) {

        return -1;
    }

    /**
     * {@inheritDoc}
     */
    public boolean isWriteable(final Class<?> type, final Type genericType,
            final Annotation[] annotations, final MediaType mediaType) {

        return Collection.class.isAssignableFrom(type);
    }

    public void writeTo(Collection<Item> t, Class<?> type, Type genericType,
            Annotation[] annotations, MediaType mediaType,
            MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream)
            throws IOException, WebApplicationException {
        final Map<String, String> properties = mediaType.getParameters();
        String charset = properties.get("charset");
        if (charset == null || charset.trim().length() <= 0) {
            charset = "ISO-8859-1";
        }
        try {
            OutputStreamWriter writer = new OutputStreamWriter(entityStream, charset);
            new Gson().toJson(t, genericType, writer);
            writer.flush();
        } catch (final UnsupportedEncodingException unsupportedEncodingException) {
            throw new WebApplicationException(unsupportedEncodingException, Response
                    .status(Status.UNSUPPORTED_MEDIA_TYPE).entity("Invalid character set")
                    .type(MediaType.TEXT_PLAIN).build());
        }
    }

}
