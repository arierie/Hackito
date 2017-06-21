package id.arieridwan.hackito.network;

import java.util.List;

import id.arieridwan.hackito.models.Item;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by arieridwan on 20/06/2017.
 */

public interface ApiServices {

    @GET("item/{id}.json")
    Observable<Item> item(@Path("id") long id);

    @GET("topstories.json")
    Observable<List<Long>> topStories();

}
