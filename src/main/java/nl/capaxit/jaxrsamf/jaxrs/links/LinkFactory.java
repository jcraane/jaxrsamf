package nl.capaxit.jaxrsamf.jaxrs.links;

import com.google.common.base.Strings;

import javax.ws.rs.core.Link;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

/**
 * Helper class for constructing {@link javax.ws.rs.core.Link}.
 *
 * Created by jamiecraane on 18/08/14.
 */
public final class LinkFactory {
    private LinkFactory() {
    }

    /*public static Link fromResource() {

    }*/

    public static Link fromBaseUri(final UriInfo uriInfo, final String path, QueryParameter... queryParameters) {
        return fromBaseUri(uriInfo, path, "", queryParameters);
    }

    public static Link fromBaseUri(final UriInfo uriInfo, final String path, final String rel, QueryParameter... queryParameters) {
        final UriBuilder builder = uriInfo.getBaseUriBuilder().path(path);

        for (final QueryParameter param : queryParameters) {
            builder.queryParam(param.getName(), param.getValue());
        }

        final Link.Builder linkBuilder = Link.fromUri(builder.build());
        if (!Strings.isNullOrEmpty(rel)) {
            linkBuilder.rel(rel);
        }

        return linkBuilder.build();
    }
}
