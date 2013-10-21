package nl.capaxit.jaxrsamf.providers;

import flex.messaging.io.SerializationContext;
import flex.messaging.io.amf.Amf3Input;
import flex.messaging.io.amf.Amf3Output;
import nl.capaxit.jaxrsamf.endpoint.Constants;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

/**
 * @author Jamie Craane
 */
@Provider
@Produces(Constants.APPLICATION_X_AMF)
@Consumes(Constants.APPLICATION_X_AMF)
public class AmfProvider implements MessageBodyReader<Object>, MessageBodyWriter<Object> {
    public boolean isReadable(Class realType, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return true; //we'll assume everything is readable, for now.
      }

      public Object readFrom(
              Class realType,
              Type genericType,
              Annotation[] annotations,
              MediaType mediaType,
              MultivaluedMap httpHeaders,
              InputStream stream)
        throws IOException, WebApplicationException {
        SerializationContext context = new SerializationContext();
        Amf3Input input = new Amf3Input(context);
        input.setInputStream(stream);
          try {
              return input.readObject();
          } catch (ClassNotFoundException e) {
              throw new RuntimeException(e);
          }
      }

      public boolean isWriteable(Class realType, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return true; //we'll assume everything is writeable, for now.
      }

      public void writeTo(
              Object obj,
              Class realType,
              Type genericType,
              Annotation[] annotations,
              MediaType mediaType,
              MultivaluedMap httpHeaders,
              OutputStream stream) throws IOException, WebApplicationException {
        SerializationContext context = new SerializationContext();
        Amf3Output output = new Amf3Output(context);
        output.setOutputStream(stream);
        output.writeObject(obj);
      }

      public long getSize(Object o, Class type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return -1; //we don't know the size
      }
}
