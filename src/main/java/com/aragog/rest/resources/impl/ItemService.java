package com.aragog.rest.resources.impl;

import java.util.Collection;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.aragog.Aragog;
import com.aragog.datamodel.GetItemsRequest;
import com.aragog.datamodel.Item;
import com.aragog.rest.resources.ItemResource;

/**
 * RESTFul service implementation of {@link ItemResource}
 */
public class ItemService implements ItemResource {

    @Override
    public Response getItem(UriInfo _uriInfo, String itemTitle, String _authority) {
        GetItemsRequest getItemRequest = new GetItemsRequest(itemTitle, 10);
        Aragog aragog = new Aragog();

        Collection<Item> items = aragog.searchItem(getItemRequest);
        for (Item item : items) {
            System.out.println("Item Title:" + item.getTitle() + "Item Price:" + item.getPrice());
        }

        final GenericEntity<Collection<Item>> responseEntity = new GenericEntity<Collection<Item>>(items) {};

        return Response.ok(responseEntity).build();

    }

    @Override
    public Response postItem(UriInfo _uriInfo, String _authority) {
        Aragog aragog = new Aragog();
        aragog.getItemInformation("https://newyork.craigslist.org/search/bka");

        final GenericEntity<Collection<Item>> responseEntity = null;
        return Response.ok(responseEntity).build();
    }
}
