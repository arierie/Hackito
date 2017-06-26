package id.arieridwan.hackito.network;

import java.util.List;

import id.arieridwan.hackito.models.ItemComments;
import id.arieridwan.hackito.models.ItemStories;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by arieridwan on 20/06/2017.
 */

public interface ApiServices {

    @GET("item/{id}.json")
    Observable<ItemStories> item(@Path("id") int id);

    @GET("item/{id}.json")
    Observable<ItemComments> comment(@Path("id") int id);

    @GET("topstories.json")
    Observable<List<Integer>> topStories();

}
